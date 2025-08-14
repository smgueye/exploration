package io.mgueye.oop.code_smell.extract_sub_classes.medium;

public class RegularAccount extends Account {

  public RegularAccount(
      final String accountNumber,
      final String owner,
      final double balance) {
    super(accountNumber, owner, balance);
  }
}
