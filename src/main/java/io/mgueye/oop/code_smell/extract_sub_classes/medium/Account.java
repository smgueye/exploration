package io.mgueye.oop.code_smell.extract_sub_classes.medium;

public class Account {
  private final String accountNumber;
  private final String owner;
  private double balance;

  // Only for premium accounts
  private double creditLimit;
  private double cashbackRate;

  public Account(String accountNumber, String owner, double balance) {
    this.accountNumber = accountNumber;
    this.owner = owner;
    this.balance = balance;
  }

  // Deposit money
  public void deposit(double amount) {
    balance += amount;
    System.out.println("Deposited " + amount + ". New balance: " + balance);
  }

  // Withdraw money
  public boolean withdraw(double amount) {
    if (balance >= amount) {
      balance -= amount;
      System.out.println("Withdrew " + amount + ". New balance: " + balance);
      return true;
    }
    System.out.println("Insufficient funds");
    return false;
  }

  // Monthly interest calculation
  public double calculateMonthlyInterest() {
    if (creditLimit > 0) {
      // Premium account: special interest calculation
      return balance * 0.02 + creditLimit * 0.01;
    } else {
      // Regular account
      return balance * 0.01;
    }
  }

  // Cashback calculation
  public double calculateCashback(double purchaseAmount) {
    if (cashbackRate > 0) {
      return purchaseAmount * cashbackRate;
    }
    return 0;
  }
}
