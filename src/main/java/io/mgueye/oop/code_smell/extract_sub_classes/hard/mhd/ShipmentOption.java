package io.mgueye.oop.code_smell.extract_sub_classes.hard.mhd;

public class ShipmentOption {

  private final boolean express ;
  private final boolean weekendPickup;
  private final boolean dangerousGoods;

  public ShipmentOption(boolean express, boolean weekendPickup, boolean dangerousGoods) {
    this.express = express;
    this.weekendPickup = weekendPickup;
    this.dangerousGoods = dangerousGoods;
  }

  public boolean isExpress() {
    return express;
  }

  public boolean isWeekendPickup() {
    return weekendPickup;
  }

  public boolean isDangerousGoods() {
    return dangerousGoods;
  }
}
