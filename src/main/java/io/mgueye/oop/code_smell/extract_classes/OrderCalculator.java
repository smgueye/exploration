package io.mgueye.oop.code_smell.extract_classes;

import java.util.Map;

public class OrderCalculator {

  private final double taxRate = 0.2;
  private final double discountThreshold = 500.0;
  private final double discountRate = 0.05;

  public double calculateOrderTotal(
      Map<String, Integer> items) {
    double total = 0;
    for (Map.Entry<String, Integer> entry : items.entrySet()) {
      double price = getItemPrice(entry.getKey());
      total += price * entry.getValue();
    }
    if (total > discountThreshold) {
      total -= total * discountRate;
    }
    total += total * taxRate;
    return total;
  }

  public double getItemPrice(String item) {
    // Simulated pricing logic
    return item.length() * 2.5;
  }
}
