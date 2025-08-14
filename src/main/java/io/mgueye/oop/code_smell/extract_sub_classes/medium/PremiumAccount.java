package io.mgueye.oop.code_smell.extract_sub_classes.medium;

public class PremiumAccount extends Account {

  private final double creditLimit;
  private final double cashbackRate;

  public PremiumAccount(
    final String accountNumber,
    final String owner,
    final double balance,
    final double creditLimit,
    final double cashbackRate) {
    super(accountNumber, owner, balance);

    this.creditLimit = creditLimit;
    this.cashbackRate = cashbackRate;
  }

  @Override
  public double calculateMonthlyInterest() {
    double interest = balance * 0.02;
    if (creditLimit > 0)
      return interest + creditLimit * 0.01;
    return interest;
  }

  public double calculateCashback(double purchaseAmount) {
    if (cashbackRate > 0)
      return purchaseAmount * cashbackRate;
    return 0;
  }
}
