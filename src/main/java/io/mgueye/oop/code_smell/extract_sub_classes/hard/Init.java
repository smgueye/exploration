package io.mgueye.oop.code_smell.extract_sub_classes.hard;

import io.mgueye.oop.code_smell.extract_sub_classes.hard.mhd_shipment.ShipmentHandler;

public class Init {
  public static void main(String[] args) {
    final ShipmentHandler shipmentHandler = new ShipmentHandler("AAA", "USA", "USA", 1000.45, 889.95, 94.0, 130.0);
    shipmentHandler.setRecipient("Dieynab Diatta", "1024 Hasbrouck Appartment", "Cornell Campus", "Ithaca", "New York (NY)", "14850");
    shipmentHandler.setOptions(true, false, true);
    shipmentHandler.setDomesticRouting("UTC - 05:00", "PI-PT-ID:555", "329");
    shipmentHandler.setInternationalDetails("8501.10.3000", "CPT", 150000.0);

    System.out.printf("tracking Number: %s%n", shipmentHandler.assignTrackingNumber());
    System.out.printf("Is valid shipmentHandler: %s%n", shipmentHandler.validate());
    System.out.printf("Label: %s%n", shipmentHandler.generateLabel());
    System.out.printf("Charge: %s%n", shipmentHandler.calculateTotalCharge());
    System.out.printf("Balancing chargeable weight: %s%n", shipmentHandler.getBalanceChargeableWeight());
  }
}
