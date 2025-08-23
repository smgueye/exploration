package io.mgueye.oop.code_smell.extract_interface.pb_2;

public interface Account {
  String createAccount(String email);

  boolean activate(String accountId);

  boolean deactivate(String accountId);

  String getStatus(String accountId);
}
