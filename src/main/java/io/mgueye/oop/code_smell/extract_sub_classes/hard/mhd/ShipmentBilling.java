package io.mgueye.oop.code_smell.extract_sub_classes.hard.mhd;

public class ShipmentBilling {

  private double baseRatePerKg;
  private double vatRate;
  private double customsDutyRate;
  private double insuranceRate;
  private double volumetricDivisor;
  private double ruralSurcharge;
  private double weekendSurcharge;
  private double currencyRateToBilling;

  public ShipmentBilling(double baseRatePerKg, double vatRate, double customsDutyRate, double ruralSurcharge,
                         double weekendSurcharge,
                         double insuranceRate,
                         double currencyRateToBilling,
                         double volumetricDivisor) {
    this.baseRatePerKg = baseRatePerKg;
    this.vatRate = vatRate;
    this.customsDutyRate = customsDutyRate;
    this.ruralSurcharge = ruralSurcharge;
    this.insuranceRate = insuranceRate;
    this.weekendSurcharge = weekendSurcharge;
    this.currencyRateToBilling = currencyRateToBilling;
    this.volumetricDivisor = volumetricDivisor;
  }

  public double getBaseRatePerKg() {
    return baseRatePerKg;
  }

  public void setBaseRatePerKg(double baseRatePerKg) {
    this.baseRatePerKg = baseRatePerKg;
  }

  public double getVatRate() {
    return vatRate;
  }

  public void setVatRate(double vatRate) {
    this.vatRate = vatRate;
  }

  public double getCustomsDutyRate() {
    return customsDutyRate;
  }

  public void setCustomsDutyRate(double customsDutyRate) {
    this.customsDutyRate = customsDutyRate;
  }

  public double getInsuranceRate() {
    return insuranceRate;
  }

  public void setInsuranceRate(double insuranceRate) {
    this.insuranceRate = insuranceRate;
  }

  public double getVolumetricDivisor() {
    return volumetricDivisor;
  }

  public void setVolumetricDivisor(double volumetricDivisor) {
    this.volumetricDivisor = volumetricDivisor;
  }

  public double getRuralSurcharge() {
    return ruralSurcharge;
  }

  public void setRuralSurcharge(double ruralSurcharge) {
    this.ruralSurcharge = ruralSurcharge;
  }

  public double getWeekendSurcharge() {
    return weekendSurcharge;
  }

  public void setWeekendSurcharge(double weekendSurcharge) {
    this.weekendSurcharge = weekendSurcharge;
  }

  public double getCurrencyRateToBilling() {
    return currencyRateToBilling;
  }

  public void setCurrencyRateToBilling(double currencyRateToBilling) {
    this.currencyRateToBilling = currencyRateToBilling;
  }
}
