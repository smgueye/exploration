package io.mgueye.oop.code_smell.extract_sub_classes.easy.vehicule;

public class Vehicle {
  private String make;
  private String model;
  private int year;

  public Vehicle(String make, String model, int year) {
    this.make = make;
    this.model = model;
    this.year = year;
  }

  // Normal behavior
  public void drive() {
    System.out.println("Driving " + make + " " + model);
  }
}
