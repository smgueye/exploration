package io.mgueye.oop.code_smell.extract_sub_classes.medium;

public abstract class Account {
  protected final String accountNumber;
  protected final String owner;
  protected double balance;

  public Account(String accountNumber, String owner, double balance) {
    this.accountNumber = accountNumber;
    this.owner = owner;
    this.balance = balance;
  }

  public void deposit(double amount) {
    balance += amount;
    System.out.println("Deposited " + amount + ". New balance: " + balance);
  }

  public boolean withdraw(double amount) {
    if (balance >= amount) {
      balance -= amount;
      System.out.println("Withdrew " + amount + ". New balance: " + balance);
      return true;
    }
    System.out.println("Insufficient funds");
    return false;
  }

  public double calculateMonthlyInterest() {
    return balance * 0.01;
  }

  public abstract double calculateCashback(double purchaseAmount);

  @Override
  public String toString() {
    return getClass().getSimpleName() + " " + accountNumber + " owned by " + owner;
  }
}
