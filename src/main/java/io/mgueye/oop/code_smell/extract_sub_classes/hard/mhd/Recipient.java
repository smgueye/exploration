package io.mgueye.oop.code_smell.extract_sub_classes.hard.mhd;

public class Recipient {

  private String name;
  private String addressLine1;
  private String addressLine2;
  private String city;
  private String stateOrProvince;
  private String postalCode;

  public Recipient(String name,
                   String addressLine1,
                   String addressLine2,
                   String city,
                   String stateOrProvince,
                   String postalCode) {
    this.name = name;
    this.addressLine1 = addressLine1;
    this.addressLine2 = addressLine2;
    this.city = city;
    this.stateOrProvince = stateOrProvince;
    this.postalCode = postalCode;
  }

  public String getName() {
    return name;
  }

  public String getAddressLine1() {
    return addressLine1;
  }

  public String getAddressLine2() {
    return addressLine2;
  }

  public String getCity() {
    return city;
  }

  public String getStateOrProvince() {
    return stateOrProvince;
  }

  public String getPostalCode() {
    return postalCode;
  }
}
