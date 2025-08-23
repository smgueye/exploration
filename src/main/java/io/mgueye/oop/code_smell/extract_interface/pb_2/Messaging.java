package io.mgueye.oop.code_smell.extract_interface.pb_2;

public interface Messaging {
  void sendEmail(String accountId, String subject, String body);

  void sendSms(String accountId, String body);
}
