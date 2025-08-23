package io.mgueye.oop.code_smell.primitive_obession.delivery_service;

import lombok.Getter;

public class Init {

  public static void main(String[] args) {
    Init.DeliveryService deliveryService = new DeliveryService();
    Init.Package aPackage = new Init.Package(
      new Recipient("Dieynab",
        new Address("Hasbrouk Appartment", "Ithaca", "0000", Country.US)), 10, 50000);
    System.out.printf("Shipping cost : %s ; Delivery label : %s%n",
      deliveryService.calculateShippingCost(aPackage),
      deliveryService.deliveryLabel(aPackage));
  }

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
  public static enum Country {
    US(5), FR(10), OTHER(15);

    private final double surcharge;

    Country(double surcharge) {
      this.surcharge = surcharge;
    }

    public double surcharge() { return surcharge; }

    public static Country from(String code) {
      return switch (code)  {
        case "US" -> US;
        case "FR" -> FR;
        default -> OTHER;
      };
    }
  }

  @Getter
  public static class Address {
    private final String street;
    private final String city;
    private final String zip;
    private final Country country;

    public Address(String street, String city, String zip, Country country) {
      this.street = street;
      this.city = city;
      this.zip = zip;
      this.country = country;
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
          address.getCountry();
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
        .getCountry()
        .name();
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
