package io.mgueye.oop.code_smell.extract_interface.pb_1;

public interface Notification {

  void sendEmailConfirmation(String orderId, String email);
  void sendSms(String orderId, String phone);
}
