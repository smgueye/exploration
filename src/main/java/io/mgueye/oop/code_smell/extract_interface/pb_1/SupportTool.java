package io.mgueye.oop.code_smell.extract_interface.pb_1;

public class SupportTool {

  private final Notification notification;
  private final Payment payment;

  public SupportTool(Notification notification, Payment payment) {
    this.notification = notification;
    this.payment = payment;
  }

  public String refundAndNotify(String orderId, long cents, String phone) {
    boolean ok = payment.refundPayment(orderId, cents);
    if (ok) notification.sendSms(orderId, phone);
    return ok ? "OK" : "FAIL";
  }
}
