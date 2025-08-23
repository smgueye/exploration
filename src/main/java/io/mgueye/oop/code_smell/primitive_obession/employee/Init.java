package io.mgueye.oop.code_smell.primitive_obession.employee;

public class Init {

  public static void main(String[] args) {
  }

  public abstract class Employee {
    private final String name;

    public Employee(String name) {
      this.name = name;
    }

    public abstract int payAmount();
    public abstract String getTitle();
  }

  public class Engineer extends Employee {
    public Engineer(String name, int type) {
      super(name);
    }

    @Override
    public int payAmount() {
      return 3000;
    }

    @Override
    public String getTitle() {
      return "Engineer";
    }
  }

  public class Manager extends Employee {
    public Manager(String name) {
      super(name);
    }

    @Override
    public int payAmount() {
      return 5000;
    }

    @Override
    public String getTitle() {
      return "Manager";
    }
  }

  public class Salesperson extends Employee {
    public Salesperson(String name) {
      super(name);
    }

    @Override
    public int payAmount() {
      return 4000;
    }

    @Override
    public String getTitle() {
      return "Salesperson";
    }
  }
}
