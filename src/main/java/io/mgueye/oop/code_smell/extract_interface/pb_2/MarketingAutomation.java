package io.mgueye.oop.code_smell.extract_interface.pb_2;

import java.util.List;

public class MarketingAutomation {

  private final Privacy privacy;
  private final Messaging messaging;
  private final Search search;

  public MarketingAutomation(Privacy privacy, Messaging messaging, Search search) {
    this.privacy = privacy;
    this.messaging = messaging;
    this.search = search;
  }

  public void sendCampaign(String accountId, String subject, String body) {
    privacy.enablePiiRedaction();
    messaging.sendEmail(accountId, subject, body);
    privacy.disablePiiRedaction();
  }

  public List<String> expandAudience(String emailPrefix) {
    return search.suggestByPrefix(emailPrefix);
  }
}
