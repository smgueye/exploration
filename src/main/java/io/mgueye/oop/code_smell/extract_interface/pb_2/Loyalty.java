package io.mgueye.oop.code_smell.extract_interface.pb_2;

public interface Loyalty {
  int addPoints(String accountId, int delta);

  boolean redeem(String accountId, int points);
}
