package io.mgueye.oop.code_smell.extract_interface.pb_2;

public class Demo2 {

  public static void main(String[] args) {
    CustomerHub hub = new CustomerHub();
    Account account = hub;
    Subscription subscription = hub;
    Privacy privacy = hub;
    Messaging messaging = hub;
    Search search = hub;
    Loyalty loyalty = hub;
    Analytics analytics = hub;

    AdminConsole admin = new AdminConsole(account, subscription);
    MarketingAutomation mkt = new MarketingAutomation(privacy, messaging, search);
    SupportDesk sup = new SupportDesk(loyalty, messaging, analytics, search);

    String id = hub.createAccount("alice@example.com");
    admin.activateAccountAndPlan(id, "BASIC");
    mkt.sendCampaign(id, "Hi Alice", "Welcome!");
    System.out.println(sup.metrics());
    System.out.println("Audit:\n" + hub.exportAudit());
  }
}
