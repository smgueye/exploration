package io.mgueye.oop.code_smell.extract_sub_classes.hard;

import io.mgueye.oop.code_smell.extract_sub_classes.hard.mhd_shipment.Billing;
import io.mgueye.oop.code_smell.extract_sub_classes.hard.mhd_shipment.Dimension;
import io.mgueye.oop.code_smell.extract_sub_classes.hard.mhd_shipment.InternationalShipment;
import io.mgueye.oop.code_smell.extract_sub_classes.hard.mhd_shipment.ShipmentHandler;

public class Init {
  public static void main(String[] args) {
    final InternationalShipment shipment = new InternationalShipment("AAA", "USA", "USA", "SN",
        new Dimension(1, 1, 1, 1),
        new Billing(1, 1, 1, 1, 1, 1, 1, 1));
    shipment.setRecipient("Dieynab Diatta", "1024 Hasbrouck Appartment", "Cornell Campus", "Ithaca", "New York (NY)", "14850");
    shipment.setOptions(true, false, true);
    shipment.setDomesticRouting("UTC - 05:00", "PI-PT-ID:555", "329");
    shipment.setInternationalDetails("8501.10.3000", "CPT", 150000.0);

    System.out.printf("tracking Number: %s%n", shipment.assignTrackingNumber());
    System.out.printf("Is valid shipment: %s%n", shipment.validate());
    System.out.printf("Label: %s%n", shipment.generateLabel());
    System.out.printf("Charge: %s%n", shipment.calculateTotalCharge());
    System.out.printf("Balancing chargeable weight: %s%n", shipment.getBalanceChargeableWeight());
  }
}
