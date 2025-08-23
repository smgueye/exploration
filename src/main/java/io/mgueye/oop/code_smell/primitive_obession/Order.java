package io.mgueye.oop.code_smell.primitive_obession;

public class Order {
  private Customer customer;

  public Order(Customer customer) {
    this.customer = customer;
  }

  public Customer getCustomer() {
    return customer;
  }

  public String printSummary() {
    return "Order for " + customer.getName() + " (" + customer.getPhone() + ")";
  }
}
