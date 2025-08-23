package io.mgueye.oop.code_smell.extract_sub_classes.hard.mhd_shipment;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.util.Objects;

public class LocalShipment extends Shipment {

  public LocalShipment(String id,
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
    if (this.recipient.isNotValid(false)) return false;
    if (this.domesticRouting.getStateZone() == null || domesticRouting.getStateZone().isBlank()) return false;
    if (domesticRouting.getPickupPointId() != null && domesticRouting.getPickupPointId().length() < 4) return false;
    if (this.option.isDangerousGoods() && !"Z1".equals(domesticRouting.getStateZone()) && !"Z2".equals(domesticRouting.getStateZone())) return false;

    return true;
  }

  @Override
  public String generateLabel() {
    String service = this.option.isExpress() ? "EXP" : "STD";
    StringBuilder b = new StringBuilder();
    b.append("ID:").append(this.getId()).append("\n");
    b.append("FROM:").append(this.getOriginCountry()).append(" TO:").append(this.getDestinationCountry()).append("\n");
    b.append("SERVICE:").append(service).append("\n");
    b.append("RECIPIENT:").append(recipient.getName()).append("\n");
    b.append("ADDRESS:").append(recipient.getAddressLine1());
    if (recipient.getAddressLine2() != null && !recipient.getAddressLine2().isBlank()) {
      b.append(", ").append(recipient.getAddressLine2());
    }
    b.append(", ").append(recipient.getCity());
    if (recipient.getStateOrProvince() != null) b.append(", ").append(recipient.getStateOrProvince());
    b.append(" ").append(recipient.getPostalCode()).append("\n");

    if (domesticRouting.getPickupPointId() != null) b.append("PICKUP:").append(domesticRouting.getPickupPointId()).append("\n");
    if (domesticRouting.getRemoteAreaCode() != null) b.append("REMOTE:").append(domesticRouting.getRemoteAreaCode()).append("\n");
    if (domesticRouting.getStateZone() != null) b.append("ZONE:").append(domesticRouting.getStateZone()).append("\n");

    b.append("DG:").append(this.option.isDangerousGoods() ? "Y" : "N").append("\n");
    b.append("DIM:").append(this.dimension.getLengthCm()).append("x").append(this.dimension.getWidthCm()).append("x").append(this.dimension.getHeightCm()).append("cm\n");
    b.append("WT:").append(this.dimension.getWeightKg()).append("kg\n");
    return b.toString();
  }

  @Override
  public int estimateDeliveryDays(LocalDate shipDate) {
    int days = this.option.isExpress() ? 1 : 3;
    if (domesticRouting.getStateZone() != null && !domesticRouting.getStateZone().isBlank()) days += 2;
    if ("Z3".equals(domesticRouting.getStateZone())) days += 1;
    if (this.option.isWeekendPickup() && (shipDate.getDayOfWeek() == DayOfWeek.SATURDAY || shipDate.getDayOfWeek() == DayOfWeek.SUNDAY)) {
      days += 1;
    }
    return Math.max(days, 1);
  }

  @Override
  public double calculateTotalCharge() {
    double volumetricWeight = (this.dimension.getLengthCm() * this.dimension.getWidthCm() * this.dimension.getHeightCm()) / this.billing.getVolumetricDivisor();
    double chargeableWeight = Math.max(this.dimension.getWeightKg(), volumetricWeight);
    double transport = chargeableWeight * this.billing.getBaseRatePerKg() * 1.15;
    if (this.option.isExpress())
      transport *= 1.35;

    double extras = 0.0;
    if (this.option.isDangerousGoods()) extras += 25.0;
    if (this.option.isWeekendPickup()) extras += this.billing.getBaseRatePerKg();

    double subtotal = transport + extras;
    double vatBase = subtotal;
    double vat = vatBase * this.billing.getVatRate();
    double totalLocal = subtotal + vat;
    double totalBilling = totalLocal * this.billing.getCurrencyRateToBilling();

    return Math.round(totalBilling * 100.0) / 100.0;
  }
}
