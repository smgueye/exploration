package io.mgueye.oop.code_smell.primitive_obession.employee;

import lombok.Getter;

public class Init {

  public static void main(String[] args) {
  }

  public class Employee {
    private final String name;
    private final EmployeeType type;

    public Employee(String name, EmployeeType type) {
      this.name = name;
      this.type = type;
    }

    public int payAmount() {
      return type.getPay();
    }
    public String getTitle() {
      return type.getTitle();
    }
  }

  @Getter
  public static class EmployeeType {
    private final String title;
    private final int pay;

    public EmployeeType(String title, int pay) {
      this.title = title;
      this.pay = pay;
    }

    public static final EmployeeType ENGINEER = new EmployeeType("Engineer", 3000);
    public static final EmployeeType MANAGER = new EmployeeType("Manager", 5000);
    public static final EmployeeType SALES = new EmployeeType("Salesperson", 4000);
  }
}
