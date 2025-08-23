package io.mgueye.oop.code_smell.extract_interface.pb_1;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.time.Instant;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

public class OrderManager implements Ordering, Notification, Payment, Persistence, Pricing, Reporting {
  // In-memory DB + cache (pretend)
  private final Map<String, Order> db = new ConcurrentHashMap<>();
  private final Map<String, Order> cache = new ConcurrentHashMap<>();

  // State for rate limit & audit
  private final Deque<Instant> paymentCalls = new ArrayDeque<>();
  private final List<String> auditLog = new ArrayList<>();

  @Override
  public String createOrder(String customerId, List<String> itemSkus) {
    String id = UUID.randomUUID().toString();
    Order o = new Order(id, customerId, new ArrayList<>(itemSkus), "CREATED", Instant.now());
    db.put(id, o);
    logAudit("CREATE:" + id);
    putCache(o);
    return id;
  }

  @Override
  public Optional<Order> getOrder(String orderId) {
    Order c = getCache(orderId);
    if (c != null) return Optional.of(c);
    Order o = db.get(orderId);
    if (o != null) putCache(o);
    return Optional.ofNullable(o);
  }

  @Override
  public List<Order> listOrders() {
    return new ArrayList<>(db.values());
  }

  @Override
  public boolean cancelOrder(String orderId) {
    Order o = db.get(orderId);
    if (o == null) return false;
    o.status = "CANCELLED";
    logAudit("CANCEL:" + orderId);
    putCache(o);
    return true;
  }

  @Override
  public boolean validatePayment(String orderId, String cardToken) {
    return cardToken != null && cardToken.length() > 8 && db.containsKey(orderId);
  }

  @Override
  public boolean chargeCard(String orderId, String cardToken, long cents) {
    enforcePaymentRateLimit();
    if (!validatePayment(orderId, cardToken)) return false;
    logAudit("CHARGE:" + orderId + ":" + cents);
    return true;
  }

  @Override
  public boolean refundPayment(String orderId, long cents) {
    enforcePaymentRateLimit();
    logAudit("REFUND:" + orderId + ":" + cents);
    return true;
  }

  @Override
  public long calculateTax(long subtotalCents, String countryIso) {
    if ("FR".equalsIgnoreCase(countryIso)) return Math.round(subtotalCents * 0.20);
    if ("DE".equalsIgnoreCase(countryIso)) return Math.round(subtotalCents * 0.19);
    return Math.round(subtotalCents * 0.10);
  }

  @Override
  public long applyPromotions(long subtotalCents, String promoCode) {
    if (promoCode == null) return subtotalCents;
    if (promoCode.startsWith("SAVE10")) return Math.max(0, subtotalCents - 10_00);
    if (promoCode.startsWith("HALF")) return subtotalCents / 2;
    return subtotalCents;
  }

  @Override
  public void sendEmailConfirmation(String orderId, String email) {
    logAudit("EMAIL:" + orderId + "->" + email);
    // pretend SMTP
  }

  @Override
  public void sendSms(String orderId, String phone) {
    logAudit("SMS:" + orderId + "->" + phone);
    // pretend SMS
  }

  @Override
  public File exportOrdersCsv(File out) throws IOException {
    try (PrintWriter pw = new PrintWriter(new FileWriter(out))) {
      pw.println("orderId,customerId,status,createdAt");
      for (Order o : db.values()) {
        pw.printf("%s,%s,%s,%s%n", o.id, o.customerId, o.status, o.createdAt);
      }
    }
    logAudit("EXPORT_CSV:" + out.getAbsolutePath());
    return out;
  }

  @Override
  public void openDbConnection() { /* pretend */ }

  @Override
  public void closeDbConnection() { /* pretend */ }

  @Override
  public void saveToDb(Order o) { db.put(o.id, o); logAudit("SAVE:" + o.id); }

  public Order getCache(String orderId) { return cache.get(orderId); }
  public void putCache(Order o) { cache.put(o.id, o); }
  public void clearCache() { cache.clear(); }

  public void logAudit(String line) {
    auditLog.add(Instant.now() + " " + line);
  }

  public List<String> getAuditTrail() {
    return Collections.unmodifiableList(auditLog);
  }

  public boolean healthCheck() {
    return true; // pretend ok
  }

  public void enforcePaymentRateLimit() {
    Instant now = Instant.now();
    paymentCalls.addLast(now);
    while (!paymentCalls.isEmpty() && paymentCalls.peekFirst().isBefore(now.minusSeconds(1))) {
      paymentCalls.removeFirst();
    }
    if (paymentCalls.size() > 5) {
      throw new IllegalStateException("Payment rate limit exceeded");
    }
  }

  // ----- Domain -----
  public static class Order {
    public final String id;
    public final String customerId;
    public final List<String> itemSkus;
    public String status;
    public final Instant createdAt;

    public Order(String id, String customerId, List<String> itemSkus, String status, Instant createdAt) {
      this.id = id;
      this.customerId = customerId;
      this.itemSkus = itemSkus;
      this.status = status;
      this.createdAt = createdAt;
    }
  }
}
