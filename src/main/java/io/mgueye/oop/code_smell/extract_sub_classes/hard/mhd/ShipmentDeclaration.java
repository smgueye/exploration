package io.mgueye.oop.code_smell.extract_sub_classes.hard.mhd;

public class ShipmentDeclaration {

  private final String hsCode;
  private final String incoterms;
  private final double declaredValue;

  public ShipmentDeclaration(String hsCode, String incoterms, double declaredValue) {
    this.hsCode = hsCode;
    this.incoterms = incoterms;
    this.declaredValue = declaredValue;
  }

  public String getHsCode() {
    return hsCode;
  }

  public String getIncoterms() {
    return incoterms;
  }

  public double getDeclaredValue() {
    return declaredValue;
  }
}
