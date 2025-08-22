package io.mgueye.oop.code_smell.extract_sub_classes.hard.mhd;

import java.util.Objects;

public class Shipment {
  private final String id;
  private String trackingNumber;
  private final String originCountry;
  private final String destinationCountry;

  public Shipment(String id, String trackingNumber, String originCountry, String destinationCountry) {
    this.id = id;
    this.trackingNumber = trackingNumber;
    this.originCountry = originCountry;
    this.destinationCountry = destinationCountry;
  }

  public String getId() {
    return id;
  }

  public String getTrackingNumber() {
    return trackingNumber;
  }

  public String getOriginCountry() {
    return originCountry;
  }

  public String getDestinationCountry() {
    return destinationCountry;
  }

  public boolean isInternational() {
    return !Objects.equals(originCountry, destinationCountry);
  }
}
