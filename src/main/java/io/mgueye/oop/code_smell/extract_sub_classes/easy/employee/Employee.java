package io.mgueye.oop.code_smell.extract_sub_classes.easy.employee;

import lombok.Getter;

public abstract class Employee {
  private final String name;
  @Getter private final String type;
  protected double baseSalary;

  private double teamBudget;
  private int directReports;

  public Employee(String name, String type, double baseSalary) {
    this.name = name;
    this.type = type;
    this.baseSalary = baseSalary;
  }

  public abstract void assignTeamBudget(double amount);

  public abstract void setDirectReports(int count);

  public abstract boolean approvePurchase(double amount);

  public abstract void scheduleOneOnOne(String reportName);

  public double payMonthly() {
    return baseSalary;
  }
}
