package io.mgueye.oop.code_smell.extract_sub_classes.hard.mhd_shipment;

public class DomesticRouting {


  private final String stateZone;
  private final String pickupPointId;
  private final String remoteAreaCode;

  public DomesticRouting(String stateZone, String pickupPointId, String remoteAreaCode) {
    this.stateZone = stateZone;
    this.pickupPointId = pickupPointId;
    this.remoteAreaCode = remoteAreaCode;
  }

  public String getStateZone() {
    return stateZone;
  }

  public String getPickupPointId() {
    return pickupPointId;
  }

  public String getRemoteAreaCode() {
    return remoteAreaCode;
  }
}
