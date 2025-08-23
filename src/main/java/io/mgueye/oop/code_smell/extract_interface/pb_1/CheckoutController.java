package io.mgueye.oop.code_smell.extract_interface.pb_1;

import java.util.List;

public class CheckoutController {

  private final Ordering ordering;
  private final Pricing billing;
  private final Payment payment;
  private final Notification notification;

  public CheckoutController(Ordering ordering,
                            Pricing pricing,
                            Payment payment,
                            Notification notification) {
    this.ordering = ordering;
    this.billing = pricing;
    this.payment = payment;
    this.notification = notification;
  }

  public String checkout(String customerId, List<String> skus, String cardToken, String email, String countryIso, String promoCode) {
    String id = ordering.createOrder(customerId, skus);
    long subtotal = skus.stream().mapToLong(s -> 1999).sum(); // pretend flat price
    long discounted = billing.applyPromotions(subtotal, promoCode);
    long tax = billing.calculateTax(discounted, countryIso);
    long total = discounted + tax;
    if (!payment.chargeCard(id, cardToken, total)) {
      ordering.cancelOrder(id);
      return "FAILED";
    }
    notification.sendEmailConfirmation(id, email);
    return id;
  }
}
