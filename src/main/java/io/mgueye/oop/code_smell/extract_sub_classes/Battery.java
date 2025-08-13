package io.mgueye.oop.code_smell.extract_sub_classes;

public class Battery {

  private double capacity;
  private double charge;

  public void chargeBattery(double kWh) {
    if (capacity == 0) {
      System.out.println("This vehicle has no battery!");
      return;
    }
    charge = Math.min(charge + kWh, capacity);
    System.out.println("Charged battery to " + charge + " kWh");
  }

  public double getBatteryPercentage() {
    if (capacity == 0) {
      System.out.println("This vehicle has no battery!");
      return 0;
    }
    return (charge / capacity) * 100;
  }
}
