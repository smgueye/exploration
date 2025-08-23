package io.mgueye.oop.code_smell.extract_sub_classes.hard.mhd_shipment;

import java.time.LocalDate;
import java.util.Objects;

public abstract class Shipment {
  protected final String id;
  protected String trackingNumber;
  protected final String originCountry;
  protected final String destinationCountry;
  protected Recipient recipient;
  protected DomesticRouting domesticRouting;
  protected Declaration declaration;
  protected final Dimension dimension;
  protected Option option;
  protected final Billing billing;

  public Shipment(
      String id,
      String trackingNumber,
      String originCountry,
      String destinationCountry,
      Dimension dimension,
      Billing billing) {
    this.id = id;
    this.trackingNumber = trackingNumber;
    this.originCountry = originCountry;
    this.destinationCountry = destinationCountry;
    this.dimension = dimension;
    this.billing = billing;
  }

  public String getId() {
    return id;
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

  public void setBilling(
      double baseRatePerKg,
      double vatRate,
      double customsDutyRate,
      double insuranceRate,
      double currencyRateToBilling,
      double volumetricDivisor) {
    this.billing.setBaseRatePerKg(baseRatePerKg);
    this.billing.setVatRate(vatRate);
    this.billing.setCustomsDutyRate(customsDutyRate);
    this.billing.setInsuranceRate(insuranceRate);
    this.billing.setCurrencyRateToBilling(currencyRateToBilling);
    this.billing.setVolumetricDivisor(volumetricDivisor);
  }


  public String assignTrackingNumber() {
    boolean international = !Objects.equals(this.getOriginCountry(), this.getDestinationCountry());
    String prefix = international ? "INTL" : "DOM";
    String mode = this.option.isExpress() ? "X" : "S";
    return String.format("%s-%s-%s-%s", prefix, mode, this.getId(), System.nanoTime());
  }

  public double getBalanceChargeableWeight() {
    double volumetricWeight = (this.dimension.getLengthCm() * this.dimension.getWidthCm() * this.dimension.getHeightCm()) / this.billing.getVolumetricDivisor();
    return Math.max(this.dimension.getWeightKg(), volumetricWeight);
  }

  public abstract boolean validate();

  public abstract String generateLabel();

  public abstract int estimateDeliveryDays(LocalDate shipDate);

  public abstract double calculateTotalCharge();
}
