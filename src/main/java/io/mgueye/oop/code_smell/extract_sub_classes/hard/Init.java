package io.mgueye.oop.code_smell.extract_sub_classes.hard;

import io.mgueye.oop.code_smell.extract_sub_classes.hard.mhd.Shipment;

public class Init {
  public static void main(String[] args) {
    final Shipment shipment = new Shipment("AAA", "SN", "USA", 1000.45, 889.95, 94.0, 130.0);
    shipment.setRecipient("Dieynab Diatta", "1024 Hasbrouck Appartment", "Cornell Campus", "Ithaca", "New York (NY)", "14850");
    shipment.setOptions(true, false, true);

    System.out.printf("tracking Number: %s%n", shipment.assignTrackingNumber());
    System.out.printf("originCountry: %s%n", shipment.getOriginCountry());
    System.out.printf("destinationCountry: %s%n", shipment.getDestinationCountry());
    System.out.printf("Is valid shipment: %s%n", shipment.validate());
    System.out.printf("Label: %s%n", shipment.generateLabel());
    System.out.printf("Charge: %s%n", shipment.calculateTotalCharge());
    System.out.printf("Balancing chargeable weight: %s%n", shipment.getBalanceChargeableWeight());
  }
}
