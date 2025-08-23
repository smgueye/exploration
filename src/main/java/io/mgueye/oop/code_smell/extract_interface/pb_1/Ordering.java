package io.mgueye.oop.code_smell.extract_interface.pb_1;

import java.util.List;
import java.util.Optional;

public interface Ordering {
  String createOrder(String customerId, List<String> itemSkus);
  Optional<OrderManager.Order> getOrder(String orderId);
  List<OrderManager.Order> listOrders();
  boolean cancelOrder(String orderId);
}
