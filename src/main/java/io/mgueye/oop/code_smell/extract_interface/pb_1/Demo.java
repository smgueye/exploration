package io.mgueye.oop.code_smell.extract_interface.pb_1;

import java.io.File;
import java.util.Arrays;

public class Demo {

  public static void main(String[] args) throws Exception {
    OrderManager om = new OrderManager();
    Notification notification = om;
    Reporting reporting = om;
    Ordering ordering = om;
    Payment payment = om;
    Pricing pricing = om;

    CheckoutController c = new CheckoutController(ordering, pricing, payment, notification);
    ReportingJob r = new ReportingJob(reporting);
    SupportTool s = new SupportTool(notification, payment);

    String id = c.checkout("C1", Arrays.asList("SKU1","SKU2"), "tok_123456789", "alice@example.com", "FR", "SAVE10");
    System.out.println("Order: " + id);
    System.out.println("Audit size: " + om.getAuditTrail().size());
    File f = r.nightlyExport(new File("."));
    System.out.println("Exported: " + f.getAbsolutePath());
    System.out.println(s.refundAndNotify(id, 500, "+33123456789"));
  }
}
