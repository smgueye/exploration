package io.mgueye.oop.code_smell.extract_interface.pb_2;

public class AdminConsole {

  private final Account account;
  private final Subscription subscription;

  public AdminConsole(Account account,
                      Subscription subscription) {
    this.account = account;
    this.subscription = subscription;
  }

  public void activateAccountAndPlan(String accountId, String planCode) {
    account.activate(accountId);                 // account activation
    subscription.activate(accountId, planCode);       // subscription activation
  }

  public String suspendAndReport(String accountId) {
    account.deactivate(accountId);
    return account.getStatus(accountId);
  }
}
