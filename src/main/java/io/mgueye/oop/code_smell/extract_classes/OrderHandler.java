package io.mgueye.oop.code_smell.extract_classes;

import java.util.*;

public class OrderHandler {

  private final String processorId;
  private final String processorLocation;
  private final String supportContact;

  private final InventoryManager inventoryManager;
  private OrderCalculator orderCalculator;
  private final OrderLogger log;

  public OrderHandler(String processorId, String processorLocation, String supportContact) {
    this.processorId = processorId;
    this.processorLocation = processorLocation;
    this.supportContact = supportContact;

    this.log = new OrderLogger();

    this.inventoryManager = new InventoryManager(log);
    this.orderCalculator = new OrderCalculator();
  }

  public void printInventoryReport() {
    System.out.println("InventoryManager Report:");
    for (Map.Entry<String, Integer> entry : inventoryManager.getInventory().entrySet()) {
      System.out.println(entry.getKey() + " - " + entry.getValue());
    }
  }

  public boolean processOrder(String customerId, Map<String, Integer> items) {
    for (Map.Entry<String, Integer> entry : items.entrySet()) {
      if (!inventoryManager.checkItemAvailability(entry.getKey(), entry.getValue())) {
        log.add("OrderCalculator failed: item not available - " + entry.getKey());
        return false;
      }
    }
    for (Map.Entry<String, Integer> entry : items.entrySet()) {
      inventoryManager.removeItemFromInventory(entry.getKey(), entry.getValue());
    }
    double total = orderCalculator.calculateOrderTotal(items);
    log.add("OrderCalculator processed for customer " + customerId + ". Total: " + total);
    return true;
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
