package io.mgueye.oop.code_smell.extract_interface.pb_2;

import java.util.Set;

public interface Subscription {
  boolean activate(String accountId, String planCode);

  boolean deactivate(String accountId, String planCode);

  Set<String> getStatus(String accountId, boolean subscriptions);
}
