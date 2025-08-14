package io.mgueye.oop.code_smell.extract_sub_classes.easy.vehicule;

public class ElectricVehicle extends Vehicle {

  private final double batteryCapacity;
  private double currentCharge;

  public ElectricVehicle(String make, String model, int year, double batteryCapacity, double currentCharge) {
    super(make, model, year);
    this.batteryCapacity = batteryCapacity;
    this.currentCharge = currentCharge;
  }

  public void chargeBattery(double kWh) {
    if (batteryCapacity == 0) {
      System.out.println("This vehicle has no battery!");
      return;
    }
    currentCharge = Math.min(currentCharge + kWh, batteryCapacity);
    System.out.println("Charged battery to " + currentCharge + " kWh");
  }

  public double getBatteryPercentage() {
    if (batteryCapacity == 0) {
      System.out.println("This vehicle has no battery!");
      return 0;
    }
    return (currentCharge / batteryCapacity) * 100;
  }
}
