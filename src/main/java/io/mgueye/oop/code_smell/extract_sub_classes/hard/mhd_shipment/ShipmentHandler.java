package io.mgueye.oop.code_smell.extract_sub_classes.hard.mhd_shipment;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.util.Objects;

public class ShipmentHandler {

  private final Shipment shipment;
  private Recipient recipient;
  private DomesticRouting domesticRouting;
  private Declaration declaration;
  private final Dimension dimension;
  private Option option;
  private final Billing billing;


  public ShipmentHandler(
      String id,
      String originCountry,
      String destinationCountry,
      double weightKg,
      double lengthCm,
      double widthCm,
      double heightCm) {
    this.shipment = new Shipment(id, "", originCountry, destinationCountry);
    this.dimension = new Dimension(weightKg, lengthCm, widthCm, heightCm);
    this.billing = new Billing(6.75, 0.20, 0.08, 12.0, 15.0, 0.012, 1.0, 5000.0);
  }

  public void setRecipient(String name, String line1, String line2, String city, String stateOrProvince, String postalCode) {
    this.recipient = new Recipient(name, line1, line2, city, stateOrProvince, postalCode);
  }

  public void setOptions(boolean express, boolean weekendPickup, boolean dangerousGoods) {
    this.option = new Option(express, weekendPickup, dangerousGoods);
  }

  public void setDomesticRouting(String stateZone, String pickupPointId, String remoteAreaCode) {
    this.domesticRouting = new DomesticRouting(stateZone, pickupPointId, remoteAreaCode);
  }

  public void setInternationalDetails(String hsCode, String incoterms, double declaredValue) {
    this.declaration = new Declaration(hsCode, incoterms, declaredValue);
  }

  public void setBilling(double baseRatePerKg, double vatRate, double customsDutyRate, double insuranceRate, double currencyRateToBilling, double volumetricDivisor) {
    this.billing.setBaseRatePerKg(baseRatePerKg);
    this.billing.setVatRate(vatRate);
    this.billing.setCustomsDutyRate(customsDutyRate);
    this.billing.setInsuranceRate(insuranceRate);
    this.billing.setCurrencyRateToBilling(currencyRateToBilling);
    this.billing.setVolumetricDivisor(volumetricDivisor);
  }

  public boolean validate() {
    if (recipient.getName() == null || recipient.getName().isBlank()) return false;
    if (recipient.getAddressLine1() == null || recipient.getAddressLine1().isBlank()) return false;
    if (recipient.getCity() == null || recipient.getCity().isBlank()) return false;
    if (recipient.getPostalCode() == null || recipient.getPostalCode().isBlank()) return false;
    // if (!recipient.isValid()) return false;

    boolean international = !Objects.equals(this.shipment.getOriginCountry(), this.shipment.getDestinationCountry());

    String hsCode = this.declaration.getHsCode();
    String incoterms = this.declaration.getIncoterms();
    Double declaredValue = this.declaration.getDeclaredValue();
    if (international) {
      if (hsCode == null || hsCode.isBlank()) return false;
      if (incoterms == null || incoterms.isBlank()) return false;
      if (declaredValue <= 0) return false;
      if (recipient.getStateOrProvince() == null || recipient.getStateOrProvince().isBlank()) return false;
    } else {
      if (domesticRouting.getStateZone() == null || domesticRouting.getStateZone().isBlank()) return false;
      if (domesticRouting.getPickupPointId() != null && domesticRouting.getPickupPointId().length() < 4) return false;
    }

    if (this.option.isDangerousGoods()) {
      if (international) {
        if (!"DAP".equals(incoterms) && !"DDP".equals(incoterms)) return false;
      } else {
        if (!"Z1".equals(domesticRouting.getStateZone()) && !"Z2".equals(domesticRouting.getStateZone())) return false;
      }
    }

    if (this.option.isExpress() && international && this.dimension.getWeightKg() > 30) return false;

    return this.dimension.getWeightKg() > 0 && this.dimension.getLengthCm() > 0 && this.dimension.getWidthCm() > 0 && this.dimension.getHeightCm() > 0;
  }

  public String generateLabel() {
    boolean international = !Objects.equals(this.shipment.getOriginCountry(), this.shipment.getDestinationCountry());
    String service = this.option.isExpress() ? "EXP" : "STD";
    StringBuilder b = new StringBuilder();
    b.append("ID:").append(this.shipment.getId()).append("\n");
    b.append("FROM:").append(this.shipment.getOriginCountry()).append(" TO:").append(this.shipment.getDestinationCountry()).append("\n");
    b.append("SERVICE:").append(service).append("\n");
    b.append("RECIPIENT:").append(recipient.getName()).append("\n");
    b.append("ADDRESS:").append(recipient.getAddressLine1());
    if (recipient.getAddressLine2() != null && !recipient.getAddressLine2().isBlank()) {
      b.append(", ").append(recipient.getAddressLine2());
    }
    b.append(", ").append(recipient.getCity());
    if (recipient.getStateOrProvince() != null) b.append(", ").append(recipient.getStateOrProvince());
    b.append(" ").append(recipient.getPostalCode()).append("\n");

    if (international) {
      b.append("HS:").append(this.declaration.getHsCode()).append(" ").append("INCOTERMS:").append(this.declaration.getIncoterms()).append("\n");
      b.append("DECLARED:").append(this.declaration.getDeclaredValue()).append("\n");
    } else {
      if (domesticRouting.getPickupPointId() != null) b.append("PICKUP:").append(domesticRouting.getPickupPointId()).append("\n");
      if (domesticRouting.getRemoteAreaCode() != null) b.append("REMOTE:").append(domesticRouting.getRemoteAreaCode()).append("\n");
      if (domesticRouting.getStateZone() != null) b.append("ZONE:").append(domesticRouting.getStateZone()).append("\n");
    }

    b.append("DG:").append(this.option.isDangerousGoods() ? "Y" : "N").append("\n");
    b.append("DIM:").append(this.dimension.getLengthCm()).append("x").append(this.dimension.getWidthCm()).append("x").append(this.dimension.getHeightCm()).append("cm\n");
    b.append("WT:").append(this.dimension.getWeightKg()).append("kg\n");
    return b.toString();
  }

  public int estimateDeliveryDays(LocalDate shipDate) {
    boolean international = !Objects.equals(this.shipment.getOriginCountry(), this.shipment.getDestinationCountry());
    int days;
    if (international) {
      days = this.option.isExpress() ? 3 : 7;
      if ("DDP".equals(declaration.getIncoterms())) days -= 1;
      if (this.option.isDangerousGoods()) days += 2;
    } else {
      days = this.option.isExpress() ? 1 : 3;
      if (domesticRouting.getStateZone() != null && !domesticRouting.getStateZone().isBlank()) days += 2;
      if ("Z3".equals(domesticRouting.getStateZone())) days += 1;
    }
    if (this.option.isWeekendPickup() && (shipDate.getDayOfWeek() == DayOfWeek.SATURDAY || shipDate.getDayOfWeek() == DayOfWeek.SUNDAY)) {
      days += 1;
    }
    return Math.max(days, 1);
  }

  public String assignTrackingNumber() {
    boolean international = !Objects.equals(this.shipment.getOriginCountry(), this.shipment.getDestinationCountry());
    String prefix = international ? "INTL" : "DOM";
    String mode = this.option.isExpress() ? "X" : "S";
    return String.format("%s-%s-%s-%s", prefix, mode, this.shipment.getId(), System.nanoTime());
  }

  public double calculateTotalCharge() {
    boolean international = !Objects.equals(this.shipment.getOriginCountry(), this.shipment.getDestinationCountry());

    double volumetricWeight = (this.dimension.getLengthCm() * this.dimension.getWidthCm() * this.dimension.getHeightCm()) / this.billing.getVolumetricDivisor();
    double chargeableWeight = Math.max(this.dimension.getWeightKg(), volumetricWeight);

    double transport = chargeableWeight * this.billing.getBaseRatePerKg();
    if (this.option.isExpress()) transport *= 1.35;

    double extras = 0.0;
    if (this.option.isDangerousGoods()) extras += 25.0;
    if (this.option.isWeekendPickup()) extras += this.billing.getBaseRatePerKg();
    if (!Objects.equals(this.shipment.getOriginCountry(), this.shipment.getDestinationCountry())) {
      transport *= 1.15;
    } else {
      if (domesticRouting.getRemoteAreaCode() != null && !domesticRouting.getRemoteAreaCode().isBlank()) extras += this.billing.getRuralSurcharge();
    }

    double insurance = 0.0;
    if (international) {
      insurance = this.declaration.getDeclaredValue() * this.billing.getInsuranceRate();
    }

    double duties = 0.0;
    if (international) {
      double dutiable = this.declaration.getDeclaredValue();
      if ("DDP".equals(this.declaration.getIncoterms())) {
        duties = dutiable * this.billing.getCustomsDutyRate();
      }
    }

    double subtotal = transport + extras + insurance + duties;

    double vatBase = subtotal;
    if (international) {
      if ("DDP".equals(this.declaration.getIncoterms())) {
        vatBase += this.declaration.getDeclaredValue();
      }
    }

    double vat = vatBase * this.billing.getVatRate();

    double totalLocal = subtotal + vat;

    double totalBilling = totalLocal * this.billing.getCurrencyRateToBilling();

    return Math.round(totalBilling * 100.0) / 100.0;
  }



  public double getBalanceChargeableWeight() {
    double volumetricWeight = (this.dimension.getLengthCm() * this.dimension.getWidthCm() * this.dimension.getHeightCm()) / this.billing.getVolumetricDivisor();
    return Math.max(this.dimension.getWeightKg(), volumetricWeight);
  }
}
