package io.mgueye.oop.code_smell.extract_sub_classes.easy.employee;

import lombok.Setter;

public class Manager extends Employee {

  private double teamBudget;
  @Setter
  private int directReports;

  public Manager(final String name, final double baseSalary, final double teamBudget, final int directReports) {
    super(name, "MANAGER", baseSalary);

    this.teamBudget = teamBudget;
    this.directReports = directReports;
  }

  public void assignTeamBudget(double amount) {
    this.teamBudget = amount;
  }

  public boolean approvePurchase(double amount) {
    if (amount <= teamBudget) {
      teamBudget -= amount;
      return true;
    }
    return false;
  }

  public void scheduleOneOnOne(String reportName) {
    System.out.println("1:1 scheduled with " + reportName);
  }

  public double payMonthly() {
    return baseSalary + teamBudget * 0.01 + directReports * 150;
  }
}
