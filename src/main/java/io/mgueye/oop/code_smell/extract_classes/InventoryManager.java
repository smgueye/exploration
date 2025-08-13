package io.mgueye.oop.code_smell.extract_classes;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

public class InventoryManager {

  private final OrderLogger log;

  private Map<String, Integer> inventory = new HashMap<>();

  public InventoryManager(OrderLogger log) {
    this.log = log;
  }

  public Map<String, Integer> getInventory() {
    return Collections.unmodifiableMap(inventory);
  }

  public void addItemToInventory(String item, int quantity) {
    inventory.put(item, inventory.getOrDefault(item, 0) + quantity);
    log.add("Added " + quantity + " of " + item + " to inventory.");
  }

  public boolean checkItemAvailability(String item, int quantity) {
    return inventory.getOrDefault(item, 0) >= quantity;
  }

  public void removeItemFromInventory(String item, int quantity) {
    if (checkItemAvailability(item, quantity)) {
      inventory.put(item, inventory.get(item) - quantity);
      log.add("Removed " + quantity + " of " + item + " from inventory.");
    } else {
      log.add("Attempted to remove unavailable item: " + item);
    }
  }
}
