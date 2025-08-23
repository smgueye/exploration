package io.mgueye.oop.code_smell.primitive_obession.invoice;

import lombok.Getter;

public class Init {

  @Getter
  public static class Product {
    private final String name;

    public Product(String name) {
      this.name = name;
    }
  }

  @Getter
  public static class Order {
    private final Product product;
    private final double unitPrice;
    private final int quantity;
    private final double taxRate;

    public Order(Product product, double unitPrice, int quantity, double taxRate) {
      this.product = product;
      this.unitPrice = unitPrice;
      this.quantity = quantity;
      this.taxRate = taxRate;
    }
  }

  public static class InvoiceService {

    public double calculateTotal(Invoice invoice) {
      return invoice.calculateTotal();
    }

    public String printSummary(Invoice invoice) {
      return invoice.printSummary();
    }
  }

  public static class Invoice {
    private final Order order;

    public Invoice(Order order) {
      this.order = order;
    }

    public Order getOrder() { return order; }

    public double calculateTotal() {
      double subtotal = order.getUnitPrice() * order.getQuantity();
      return subtotal + (subtotal * order.getTaxRate());
    }

    public String printSummary() {
      double total = calculateTotal();
      return order.getQuantity() + "x " + order.getProduct().getName() + " @ " + order.getUnitPrice() + " each. Total (with tax): " + total;
    }
  }
}
