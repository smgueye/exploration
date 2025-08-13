package io.mgueye.oop.code_smell.extract_sub_classes;

public class Vehicle {
  private final String make;
  private final String model;
  private final int year;
  private final Battery battery;

  public Vehicle(String make, String model, int year, Battery battery) {
    this.make = make;
    this.model = model;
    this.year = year;
    this.battery = battery;
  }

  // Normal behavior
  public void drive() {
    System.out.println("Driving " + make + " " + model);
  }
}
