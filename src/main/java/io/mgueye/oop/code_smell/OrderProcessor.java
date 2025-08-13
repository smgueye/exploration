package io.mgueye.oop.code_smell;

import java.util.*;

public class OrderProcessor {

  private String processorId;
  private String processorLocation;
  private String supportContact;
  private Map<String, Integer> inventory = new HashMap<>();
  private List<String> orderLogs = new ArrayList<>();
  private double taxRate = 0.2;
  private double discountThreshold = 500.0;
  private double discountRate = 0.05;

  public OrderProcessor(String processorId, String processorLocation, String supportContact) {
    this.processorId = processorId;
    this.processorLocation = processorLocation;
    this.supportContact = supportContact;
  }

  // ================= Inventory Management =================
  public void addItemToInventory(String item, int quantity) {
    inventory.put(item, inventory.getOrDefault(item, 0) + quantity);
    log("Added " + quantity + " of " + item + " to inventory.");
  }

  public boolean checkItemAvailability(String item, int quantity) {
    return inventory.getOrDefault(item, 0) >= quantity;
  }

  public void removeItemFromInventory(String item, int quantity) {
    if (checkItemAvailability(item, quantity)) {
      inventory.put(item, inventory.get(item) - quantity);
      log("Removed " + quantity + " of " + item + " from inventory.");
    } else {
      log("Attempted to remove unavailable item: " + item);
    }
  }

  public void printInventoryReport() {
    System.out.println("Inventory Report:");
    for (Map.Entry<String, Integer> entry : inventory.entrySet()) {
      System.out.println(entry.getKey() + " - " + entry.getValue());
    }
  }

  // ================= Order Handling =================
  public double calculateOrderTotal(Map<String, Integer> items) {
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

  public boolean processOrder(String customerId, Map<String, Integer> items) {
    for (Map.Entry<String, Integer> entry : items.entrySet()) {
      if (!checkItemAvailability(entry.getKey(), entry.getValue())) {
        log("Order failed: item not available - " + entry.getKey());
        return false;
      }
    }
    for (Map.Entry<String, Integer> entry : items.entrySet()) {
      removeItemFromInventory(entry.getKey(), entry.getValue());
    }
    double total = calculateOrderTotal(items);
    log("Order processed for customer " + customerId + ". Total: " + total);
    return true;
  }

  private double getItemPrice(String item) {
    // Simulated pricing logic
    return item.length() * 2.5;
  }

  // ================= Logging & Support =================
  public void log(String message) {
    orderLogs.add(message);
    System.out.println("[LOG] " + message);
  }

  public void printLogs() {
    System.out.println("Order Logs:");
    for (String log : orderLogs) {
      System.out.println(log);
    }
  }

  public void contactSupport(String issue) {
    System.out.println("Contacting support at " + supportContact + ": " + issue);
  }

  public void printProcessorDetails() {
    System.out.println("Processor ID: " + processorId);
    System.out.println("Location: " + processorLocation);
    System.out.println("Support Contact: " + supportContact);
  }
}
