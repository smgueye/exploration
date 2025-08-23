package io.mgueye.oop.code_smell.extract_sub_classes.hard.mhd_shipment;


public class ShipmentHandler {

  private final Shipment shipment;

  public ShipmentHandler(Shipment shipment) {
    this.shipment = shipment;
  }

  public Shipment getShipment() {
    return this.shipment;
  }
}
