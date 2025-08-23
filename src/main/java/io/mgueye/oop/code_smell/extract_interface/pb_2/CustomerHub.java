package io.mgueye.oop.code_smell.extract_interface.pb_2;

import java.time.Instant;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

public class CustomerHub implements Account, Subscription, Loyalty, Messaging, Search, Analytics, Privacy {

  private final Map<String, Customer> customers = new ConcurrentHashMap<>();
  private final Map<String, Customer> cache = new ConcurrentHashMap<>();
  private final List<String> audit = new ArrayList<>();
  private final Deque<Instant> msgCalls = new ArrayDeque<>();
  private boolean redactPii = false;

  @Override
  public String createAccount(String email) {
    String id = UUID.randomUUID().toString();
    Customer c = new Customer(id, email, "PENDING", false, 0, new HashSet<>());
    customers.put(id, c);
    putCache(c);
    log("CREATE_ACC:" + id);
    return id;
  }

  @Override
  public boolean activate(String accountId) { // (1) Account-activation
    Customer c = customers.get(accountId);
    if (c == null) return false;
    c.accountStatus = "ACTIVE";
    log("ACC_ACTIVATE:" + accountId);
    putCache(c);
    return true;
  }

  @Override
  public boolean deactivate(String accountId) { // (1) Account-deactivation
    Customer c = customers.get(accountId);
    if (c == null) return false;
    c.accountStatus = "SUSPENDED";
    log("ACC_DEACT:" + accountId);
    putCache(c);
    return true;
  }

  @Override
  public String getStatus(String accountId) { // (1) Account status
    Customer c = customers.get(accountId);
    return c == null ? "UNKNOWN" : c.accountStatus;
  }

  @Override
  public boolean activate(String accountId, String planCode) { // (2) Subscription-activation
    Customer c = customers.get(accountId);
    if (c == null) return false;
    c.activePlans.add(planCode);
    log("SUB_ACTIVATE:" + accountId + ":" + planCode);
    return true;
  }

  @Override
  public boolean deactivate(String accountId, String planCode) { // (2) Subscription-deactivation
    Customer c = customers.get(accountId);
    if (c == null) return false;
    boolean removed = c.activePlans.remove(planCode);
    if (removed) log("SUB_DEACT:" + accountId + ":" + planCode);
    return removed;
  }

  @Override
  public Set<String> getStatus(String accountId, boolean subscriptions) { // (2) Subscription status
    Customer c = customers.get(accountId);
    return c == null ? Collections.emptySet() : Collections.unmodifiableSet(c.activePlans);
  }

  @Override
  public int addPoints(String accountId, int delta) {
    Customer c = customers.get(accountId);
    if (c == null) return 0;
    c.points = Math.max(0, c.points + delta);
    log("LOYALTY_ADD:" + accountId + ":" + delta);
    return c.points;
  }

  @Override
  public boolean redeem(String accountId, int points) {
    Customer c = customers.get(accountId);
    if (c == null || c.points < points) return false;
    c.points -= points;
    log("LOYALTY_REDEEM:" + accountId + ":" + points);
    return true;
  }

  @Override
  public void sendEmail(String accountId, String subject, String body) {
    rateLimit();
    Customer c = customers.get(accountId);
    log("EMAIL:" + accountId + ":" + safe(c, subject) + ":" + safe(c, body));
  }

  @Override
  public void sendSms(String accountId, String body) {
    rateLimit();
    Customer c = customers.get(accountId);
    log("SMS:" + accountId + ":" + safe(c, body));
  }

  @Override
  public Optional<String> findByEmail(String email) {
    for (Customer c : customers.values()) {
      if (Objects.equals(c.email, email)) return Optional.of(c.id);
    }
    return Optional.empty();
  }

  @Override
  public List<String> suggestByPrefix(String emailPrefix) {
    List<String> ids = new ArrayList<>();
    for (Customer c : customers.values()) {
      if (c.email.startsWith(emailPrefix)) ids.add(c.id);
    }
    return ids;
  }

  @Override
  public void enablePiiRedaction() { redactPii = true; }

  @Override
  public void disablePiiRedaction() { redactPii = false; }

  @Override
  public Map<String, Object> getCohortMetrics() {
    Map<String, Object> m = new LinkedHashMap<>();
    m.put("total", customers.size());
    m.put("active", customers.values().stream().filter(c -> "ACTIVE".equals(c.accountStatus)).count());
    m.put("withPlans", customers.values().stream().filter(c -> !c.activePlans.isEmpty()).count());
    return m;
  }

  @Override
  public String exportAudit() {
    return String.join("\n", audit);
  }

  // ===== Internals =====
  private void log(String line) { audit.add(Instant.now() + " " + line); }
  private void rateLimit() {
    Instant now = Instant.now();
    msgCalls.addLast(now);
    while (!msgCalls.isEmpty() && msgCalls.peekFirst().isBefore(now.minusSeconds(1))) {
      msgCalls.removeFirst();
    }
    if (msgCalls.size() > 3) throw new IllegalStateException("Messaging rate limit exceeded");
  }
  private String safe(Customer c, String s) { return redactPii ? s.replaceAll("[A-Za-z0-9]", "*") : s; }
  private Customer getCache(String id) { return cache.get(id); }
  private void putCache(Customer c) { cache.put(c.id, c); }

  static class Customer {
    final String id;
    final String email;
    String accountStatus; // PENDING|ACTIVE|SUSPENDED
    boolean marketingOptIn;
    int points;
    final Set<String> activePlans;

    Customer(String id, String email, String status, boolean optIn, int points, Set<String> plans) {
      this.id = id; this.email = email; this.accountStatus = status;
      this.marketingOptIn = optIn; this.points = points; this.activePlans = plans;
    }
  }
}
