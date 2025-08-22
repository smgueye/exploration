package io.mgueye.oop.code_smell.extract_sub_classes.hard.mhd;

public class ShipmentDimension {

  private double weightKg;
  private double lengthCm;
  private double widthCm;
  private double heightCm;

  public ShipmentDimension(double weightKg, double lengthCm, double widthCm, double heightCm) {
    this.weightKg = weightKg;
    this.lengthCm = lengthCm;
    this.widthCm = widthCm;
    this.heightCm = heightCm;
  }

  public double getWeightKg() {
    return weightKg;
  }

  public double getLengthCm() {
    return lengthCm;
  }

  public double getWidthCm() {
    return widthCm;
  }

  public double getHeightCm() {
    return heightCm;
  }
}
