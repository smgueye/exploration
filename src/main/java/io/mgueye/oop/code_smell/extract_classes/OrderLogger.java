package io.mgueye.oop.code_smell.extract_classes;

import java.util.ArrayList;
import java.util.List;

public class OrderLogger {

  private final List<String> orderLogs = new ArrayList<>();

  public void add(String message) {
    orderLogs.add(message);
    System.out.println("[LOG] " + message);
  }

  public void printLogs() {
    System.out.println("OrderCalculator Logs:");
    for (String log : orderLogs) {
      System.out.println(log);
    }
  }
}
