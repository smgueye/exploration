package io.mgueye.oop.code_smell.extract_sub_classes.hard.mhd_shipment;

import java.time.DayOfWeek;
import java.time.LocalDate;

public class InternationalShipment extends Shipment{

  public InternationalShipment(String id,
                               String trackingNumber,
                               String originCountry,
                               String destinationCountry,
                               Dimension dimension,
                               Billing billing) {
    super(id,
        trackingNumber,
        originCountry,
        destinationCountry,
        dimension,
        billing);
  }

  @Override
  public boolean validate() {
    String hsCode = this.declaration.getHsCode();
    String incoterms = this.declaration.getIncoterms();
    double declaredValue = this.declaration.getDeclaredValue();

    if (hsCode == null || hsCode.isBlank())
      return false;
    if (incoterms == null || incoterms.isBlank())
      return false;
    if (declaredValue <= 0)
      return false;
    if (recipient.getStateOrProvince() == null || recipient.getStateOrProvince().isBlank())
      return false;

    if (this.option.isDangerousGoods() && !"DAP".equals(incoterms) && !"DDP".equals(incoterms))
        return false;

    if (this.option.isExpress() && this.dimension.getWeightKg() > 30)
      return false;

    return true;
  }

  @Override
  public String generateLabel() {
    String service = this.option.isExpress() ? "EXP" : "STD";
    StringBuilder b = new StringBuilder();
    b.append("ID:").append(getId()).append("\n");
    b.append("FROM:").append(getOriginCountry()).append(" TO:").append(getDestinationCountry()).append("\n");
    b.append("SERVICE:").append(service).append("\n");
    b.append("RECIPIENT:").append(recipient.getName()).append("\n");
    b.append("ADDRESS:").append(recipient.getAddressLine1());
    if (recipient.getAddressLine2() != null && !recipient.getAddressLine2().isBlank()) {
      b.append(", ").append(recipient.getAddressLine2());
    }
    b.append(", ").append(recipient.getCity());
    if (recipient.getStateOrProvince() != null) b.append(", ").append(recipient.getStateOrProvince());
    b.append(" ").append(recipient.getPostalCode()).append("\n");
    b.append("HS:").append(this.declaration.getHsCode()).append(" ").append("INCOTERMS:").append(this.declaration.getIncoterms()).append("\n");
    b.append("DECLARED:").append(this.declaration.getDeclaredValue()).append("\n");
    b.append("DG:").append(this.option.isDangerousGoods() ? "Y" : "N").append("\n");
    b.append("DIM:").append(this.dimension.getLengthCm()).append("x").append(this.dimension.getWidthCm()).append("x").append(this.dimension.getHeightCm()).append("cm\n");
    b.append("WT:").append(this.dimension.getWeightKg()).append("kg\n");
    return b.toString();
  }

  @Override
  public int estimateDeliveryDays(LocalDate shipDate) {
    int days = this.option.isExpress() ? 3 : 7;

    if ("DDP".equals(declaration.getIncoterms())) days -= 1;
    if (this.option.isDangerousGoods()) days += 2;
    if (this.option.isWeekendPickup() && (shipDate.getDayOfWeek() == DayOfWeek.SATURDAY || shipDate.getDayOfWeek() == DayOfWeek.SUNDAY)) {
      days += 1;
    }
    return Math.max(days, 1);
  }

  @Override
  public double calculateTotalCharge() {
    double volumetricWeight = (dimension.getLengthCm() * dimension.getWidthCm() * dimension.getHeightCm())
        / billing.getVolumetricDivisor();
    double chargeableWeight = Math.max(dimension.getWeightKg(), volumetricWeight);

    double transport = chargeableWeight * billing.getBaseRatePerKg();
    if (option.isExpress()) transport *= 1.35;
    transport *= 1.15; // international uplift

    double extras = 0.0;
    if (option.isDangerousGoods()) extras += 25.0;
    if (option.isWeekendPickup()) extras += billing.getWeekendSurcharge();

    double insurance = declaration.getDeclaredValue() * billing.getInsuranceRate();

    double duties = 0.0;
    if ("DDP".equals(declaration.getIncoterms())) {
      duties = declaration.getDeclaredValue() * billing.getCustomsDutyRate();
    }

    double subtotal = transport + extras + insurance + duties;

    double vatBase = subtotal;
    if ("DDP".equals(declaration.getIncoterms())) {
      vatBase += declaration.getDeclaredValue();
    }

    double vat = vatBase * billing.getVatRate();
    double totalLocal = subtotal + vat;
    double totalBilling = totalLocal * billing.getCurrencyRateToBilling();

    return Math.round(totalBilling * 100.0) / 100.0;
  }
}
