package io.mgueye.oop.code_smell.extract_sub_classes.hard.mhd_shipment;

public class Declaration {

  private final String hsCode;
  private final String incoterms;
  private final double declaredValue;

  public Declaration(String hsCode, String incoterms, double declaredValue) {
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
