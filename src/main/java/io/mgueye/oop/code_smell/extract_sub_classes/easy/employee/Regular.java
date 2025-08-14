package io.mgueye.oop.code_smell.extract_sub_classes.easy.employee;


public class Regular extends Employee {

  public Regular(String name, String type, double baseSalary) {
    super(name, "REGULAR", baseSalary);
  }

  public void assignTeamBudget(double amount) {
    throw new UnsupportedOperationException("Regular employees cannot manage team budget");
  }

  public void setDirectReports(int count) {
    throw new UnsupportedOperationException("Regular employees cannot manage direct reports");
  }

  public boolean approvePurchase(double amount) {
    throw new UnsupportedOperationException("Regular employees cannot approve purchase");
  }

  public void scheduleOneOnOne(String reportName) {
    throw new UnsupportedOperationException("Regular employees cannot schedule one on one");
  }
}
