package io.mgueye.oop.code_smell.extract_classes;

import java.util.HashMap;

public class Init {
  public static void main(String[] args) {
    OrderHandler orderHandler = new OrderHandler("A", "SN", "SMG");
    orderHandler.processOrder("DSFED", new HashMap<>());
  }
}
