package io.mgueye.oop.code_smell.extract_interface.pb_1;

public interface Payment {

  boolean validatePayment(String orderId, String cardToken);
  boolean chargeCard(String orderId, String cardToken, long cents);
  boolean refundPayment(String orderId, long cents);
}
