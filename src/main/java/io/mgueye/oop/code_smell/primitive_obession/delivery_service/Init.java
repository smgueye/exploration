package io.mgueye.oop.code_smell.primitive_obession.delivery_service;

import lombok.Getter;

public class Init {

  public static class DeliveryService {

    public double calculateShippingCost(Package pack) {
      return pack.calculateShippingCost();
    }

    public String deliveryLabel(Package pack) {
      return pack
        .getRecipient()
        .deliveryLabel();
    }
  }

  @Getter
  public static class Address {
    private final String street;
    private final String city;
    private final String zip;
    private final String countryCode;

    public Address(String street, String city, String zip, String countryCode) {
      this.street = street;
      this.city = city;
      this.zip = zip;
      this.countryCode = countryCode;
    }
  }

  @Getter
  public static class Recipient {
    private final String name;
    private final Address address;

    public Recipient(String name, Address address) {
      this.name = name;
      this.address = address;
    }

    public String deliveryLabel() {
      return "To: " + name + "\n" +
          address.getStreet() + "\n" +
          address.getCity() + " " + address.getZip() + "\n" +
          address.getCountryCode();
    }
  }

  @Getter
  public static class Package {
    private final Recipient recipient;
    private final double weightKg;
    private final double distanceKm;

    public Package(Recipient recipient, double weightKg, double distanceKm) {
      this.weightKg = weightKg;
      this.distanceKm = distanceKm;
      this.recipient = recipient;
    }

    public double calculateShippingCost() {
      final double base = weightKg * 2 + distanceKm * 0.5;
      final String countryCode = recipient
        .getAddress()
        .getCountryCode();
      if ("US".equals(countryCode)) {
        return base + 5;
      } else if ("FR".equals(countryCode)) {
        return base + 10;
      } else {
        return base + 15;
      }
    }
  }
}
