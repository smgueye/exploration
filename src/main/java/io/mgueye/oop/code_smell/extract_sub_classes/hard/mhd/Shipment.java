package io.mgueye.oop.code_smell.extract_sub_classes.hard.mhd;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.util.Objects;

public class Shipment {

  private final String id;
  private String trackingNumber;

  // country
  private final String originCountry;
  private final String destinationCountry;

  // recipient
  private Recipient recipient;

  // Routing
  private String remoteAreaCode;
  private String stateZone;
  private String pickupPointId;

  // CustomsDeclaration
  private String hsCode;
  private String incoterms;
  private double declaredValue;

  // Dimension
  private double weightKg;
  private double lengthCm;
  private double widthCm;
  private double heightCm;

  // Option
  private ShipmentOption shipmentOption;

  // Billing
  private double volumetricDivisor;
  private double vatRate;
  private double baseRatePerKg;
  private double insuranceRate;
  private double customsDutyRate;
  private double ruralSurcharge;
  private double weekendSurcharge;
  private double currencyRateToBilling;


  public Shipment(
      String id,
      String originCountry,
      String destinationCountry,
      double weightKg,
      double lengthCm,
      double widthCm,
      double heightCm) {
    this.id = id;
    this.originCountry = originCountry;
    this.destinationCountry = destinationCountry;
    this.weightKg = weightKg;
    this.lengthCm = lengthCm;
    this.widthCm = widthCm;
    this.heightCm = heightCm;
    this.baseRatePerKg = 6.75;
    this.volumetricDivisor = 5000.0;
    this.vatRate = 0.20;
    this.customsDutyRate = 0.08;
    this.insuranceRate = 0.012;
    this.ruralSurcharge = 12.0;
    this.weekendSurcharge = 15.0;
    this.currencyRateToBilling = 1.0;
  }

  public void setRecipient(String name, String line1, String line2, String city, String stateOrProvince, String postalCode) {
    this.recipient = new Recipient(name, line1, line2, city, stateOrProvince, postalCode);
  }

  public void setOptions(boolean express, boolean weekendPickup, boolean dangerousGoods) {
    this.shipmentOption = new ShipmentOption(express, weekendPickup, dangerousGoods);
  }

  public void setDomesticRouting(String stateZone, String pickupPointId, String remoteAreaCode) {
    this.routing = new ShipmentDomesticRouting(stateZone, pickupPointId, remoteAreaCode);
  }

  public void setInternationalDetails(String hsCode, String incoterms, double declaredValue) {
    this.hsCode = hsCode;
    this.incoterms = incoterms;
    this.declaredValue = declaredValue;
  }

  public void setBilling(double baseRatePerKg, double vatRate, double customsDutyRate, double insuranceRate, double currencyRateToBilling) {
    this.baseRatePerKg = baseRatePerKg;
    this.vatRate = vatRate;
    this.customsDutyRate = customsDutyRate;
    this.insuranceRate = insuranceRate;
    this.currencyRateToBilling = currencyRateToBilling;
  }

  public void setDimensionalDivisor(double volumetricDivisor) {
    this.volumetricDivisor = volumetricDivisor;
  }

  public boolean validate() {
    if (recipient.getName() == null || recipient.getName().isBlank()) return false;
    if (recipient.getAddressLine1() == null || recipient.getAddressLine1().isBlank()) return false;
    if (recipient.getCity() == null || recipient.getCity().isBlank()) return false;
    if (recipient.getPostalCode() == null || recipient.getPostalCode().isBlank()) return false;

    boolean international = !Objects.equals(originCountry, destinationCountry);

    if (international) {
      if (hsCode == null || hsCode.isBlank()) return false;
      if (incoterms == null || incoterms.isBlank()) return false;
      if (declaredValue <= 0) return false;
      if (recipient.getStateOrProvince() == null || recipient.getStateOrProvince().isBlank()) return false;
    } else {
      if (stateZone == null || stateZone.isBlank()) return false;
      if (pickupPointId != null && pickupPointId.length() < 4) return false;
    }

    if (this.shipmentOption.isDangerousGoods()) {
      if (international) {
        if (!"DAP".equals(incoterms) && !"DDP".equals(incoterms)) return false;
      } else {
        if (!"Z1".equals(stateZone) && !"Z2".equals(stateZone)) return false;
      }
    }

    if (this.shipmentOption.isExpress() && international && weightKg > 30) return false;

    return weightKg > 0 && lengthCm > 0 && widthCm > 0 && heightCm > 0;
  }

  public String generateLabel() {
    boolean international = !Objects.equals(originCountry, destinationCountry);
    String service = this.shipmentOption.isExpress() ? "EXP" : "STD";
    StringBuilder b = new StringBuilder();
    b.append("ID:").append(id).append("\n");
    b.append("FROM:").append(originCountry).append(" TO:").append(destinationCountry).append("\n");
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
      b.append("HS:").append(hsCode).append(" ").append("INCOTERMS:").append(incoterms).append("\n");
      b.append("DECLARED:").append(declaredValue).append("\n");
    } else {
      if (pickupPointId != null) b.append("PICKUP:").append(pickupPointId).append("\n");
      if (remoteAreaCode != null) b.append("REMOTE:").append(remoteAreaCode).append("\n");
      if (stateZone != null) b.append("ZONE:").append(stateZone).append("\n");
    }

    b.append("DG:").append(this.shipmentOption.isDangerousGoods() ? "Y" : "N").append("\n");
    b.append("DIM:").append(lengthCm).append("x").append(widthCm).append("x").append(heightCm).append("cm\n");
    b.append("WT:").append(weightKg).append("kg\n");
    return b.toString();
  }

  public int estimateDeliveryDays(LocalDate shipDate) {
    boolean international = !Objects.equals(originCountry, destinationCountry);
    int days;
    if (international) {
      days = this.shipmentOption.isExpress() ? 3 : 7;
      if ("DDP".equals(incoterms)) days -= 1;
      if (this.shipmentOption.isDangerousGoods()) days += 2;
    } else {
      days = this.shipmentOption.isExpress() ? 1 : 3;
      if (remoteAreaCode != null && !remoteAreaCode.isBlank()) days += 2;
      if ("Z3".equals(stateZone)) days += 1;
    }
    if (this.shipmentOption.isWeekendPickup() && (shipDate.getDayOfWeek() == DayOfWeek.SATURDAY || shipDate.getDayOfWeek() == DayOfWeek.SUNDAY)) {
      days += 1;
    }
    return Math.max(days, 1);
  }

  public String assignTrackingNumber() {
    boolean international = !Objects.equals(originCountry, destinationCountry);
    String prefix = international ? "INTL" : "DOM";
    String mode = this.shipmentOption.isExpress() ? "X" : "S";
    this.trackingNumber = prefix + "-" + mode + "-" + id + "-" + System.nanoTime();
    return trackingNumber;
  }

  public double calculateTotalCharge() {
    boolean international = !Objects.equals(originCountry, destinationCountry);

    double volumetricWeight = (lengthCm * widthCm * heightCm) / volumetricDivisor;
    double chargeableWeight = Math.max(weightKg, volumetricWeight);

    double transport = chargeableWeight * baseRatePerKg;
    if (this.shipmentOption.isExpress()) transport *= 1.35;

    double extras = 0.0;
    if (this.shipmentOption.isDangerousGoods()) extras += 25.0;
    if (this.shipmentOption.isWeekendPickup()) extras += weekendSurcharge;
    if (!Objects.equals(originCountry, destinationCountry)) {
      transport *= 1.15;
    } else {
      if (remoteAreaCode != null && !remoteAreaCode.isBlank()) extras += ruralSurcharge;
    }

    double insurance = 0.0;
    if (international) {
      insurance = declaredValue * insuranceRate;
    }

    double duties = 0.0;
    if (international) {
      double dutiable = declaredValue;
      if ("DDP".equals(incoterms)) {
        duties = dutiable * customsDutyRate;
      }
    }

    double subtotal = transport + extras + insurance + duties;

    double vatBase = subtotal;
    if (international) {
      if ("DDP".equals(incoterms)) {
        vatBase += declaredValue;
      }
    }

    double vat = vatBase * vatRate;

    double totalLocal = subtotal + vat;

    double totalBilling = totalLocal * currencyRateToBilling;

    return Math.round(totalBilling * 100.0) / 100.0;
  }

  public String getTrackingNumber() {
    return trackingNumber;
  }

  public String getOriginCountry() {
    return originCountry;
  }

  public String getDestinationCountry() {
    return destinationCountry;
  }

  public double getBalanceChargeableWeight() {
    double volumetricWeight = (lengthCm * widthCm * heightCm) / volumetricDivisor;
    return Math.max(weightKg, volumetricWeight);
  }
}
