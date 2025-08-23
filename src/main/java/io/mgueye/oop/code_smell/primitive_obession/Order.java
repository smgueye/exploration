package io.mgueye.oop.code_smell.primitive_obession;

public class Order {
  private String customerName;
  private String customerPhone;

  public Order(String customerName, String customerPhone) {
    this.customerName = customerName;
    this.customerPhone = customerPhone;
  }

  public String getCustomerName() {
    return customerName;
  }

  public String getCustomerPhone() {
    return customerPhone;
  }

  public String printSummary() {
    return "Order for " + customerName + " (" + customerPhone + ")";
  }
}
