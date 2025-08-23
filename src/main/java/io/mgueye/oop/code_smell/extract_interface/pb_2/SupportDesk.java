package io.mgueye.oop.code_smell.extract_interface.pb_2;

import java.util.Map;
import java.util.Optional;

public class SupportDesk {

  private final Loyalty loyalty;
  private final Messaging messaging;
  private final Analytics analytics;
  private final Search search;

  public SupportDesk(Loyalty loyalty,
                     Messaging messaging,
                     Analytics analytics,
                     Search search) {
    this.loyalty = loyalty;
    this.messaging = messaging;
    this.analytics = analytics;
    this.search = search;
  }

  public String refundWithSms(String accountId, int points, String sms) {
    boolean ok = loyalty.redeem(accountId, points);
    if (ok) messaging.sendSms(accountId, sms);
    return ok ? "OK" : "FAIL";
  }

  public Optional<String> lookup(String email) {
    return search.findByEmail(email);
  }

  public Map<String, Object> metrics() {
    return analytics.getCohortMetrics();
  }
}
