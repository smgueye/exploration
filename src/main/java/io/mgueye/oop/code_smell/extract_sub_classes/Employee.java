package io.mgueye.oop.code_smell.extract_sub_classes;

public class Employee {
  private final String name;
  private final String type; // "REGULAR" or "MANAGER"
  private double baseSalary;

  private double teamBudget;
  private int directReports;

  public Employee(String name, String type, double baseSalary) {
    this.name = name;
    this.type = type;
    this.baseSalary = baseSalary;
  }

  public String label() {
    return type + ":" + name;
  }

  public void assignTeamBudget(double amount) {
    if (!"MANAGER".equals(type)) {
      System.out.println("Not allowed");
      return;
    }
    this.teamBudget = amount;
  }

  public void setDirectReports(int count) {
    if (!"MANAGER".equals(type)) {
      System.out.println("Not allowed");
      return;
    }
    this.directReports = count;
  }

  public boolean approvePurchase(double amount) {
    if (!"MANAGER".equals(type)) return false;
    if (amount <= teamBudget) {
      teamBudget -= amount;
      return true;
    }
    return false;
  }

  public void scheduleOneOnOne(String reportName) {
    if (!"MANAGER".equals(type)) {
      System.out.println("Not allowed");
      return;
    }
    System.out.println("1:1 scheduled with " + reportName);
  }

  public double payMonthly() {
    double pay = baseSalary;
    if ("MANAGER".equals(type)) {
      pay += teamBudget * 0.01 + directReports * 150;
    }
    return pay;
  }
}
