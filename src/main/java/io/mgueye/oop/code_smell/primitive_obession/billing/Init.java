package io.mgueye.oop.code_smell.primitive_obession.billing;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class Init {

  public static void main(String[] args) {
    Init.BillingService billingService = new Init.BillingService();
    Invoice invoice = new Invoice(
        "X00-AAA-2025",
        new IsoDate("2025-02-03"),
        new IsoDate("2025-05-15"),
        new ExchangeSetting(
            CurrencyType.USD,
            CurrencyType.EUR,
            1.5),
        new Customer("Mouhamed GUEYE", new Address("33 Rue Kleber", "Bordeaux", "33800", Country.FR)),
        5.5,
        5.5,
        TimeZone.UTC);
    invoice.addItem(new LineItem(new Product("POT A"), 126.5, 2, 30.4));
    invoice.addItem(new LineItem(new Product("CART B"), 302.59, 12, 12.4));
    invoice.setPaidDate(new IsoDate("2025-04-03"));
    System.out.println(billingService.printInvoice(invoice));
  }

  public static class BillingService {

    public String printInvoice(Invoice invoice) {
      String currency = invoice.getExchangeSetting().getBase().name();
      String displayCurrency = invoice.getExchangeSetting().getDisplay().name();
      double discounted = invoice.calculateDiscount();
      double converted = invoice.convertCurrency();
      Customer customer = invoice.getCustomer();
      Address address = customer.getAddress();
      TimeZone paymentTimeZone = invoice.getPaymentTimeZone();

      StringBuilder sb = new StringBuilder();
      sb.append("Invoice #").append(invoice.getInvoiceNumber()).append("\n");
      sb.append("Customer: ").append(customer.getName()).append("\n");
      sb.append(address.getStreet()).append("\n");
      sb.append(address.getCity()).append(" ").append(address.getZip()).append("\n");
      sb.append(address.getCountry()).append("\n\n");

      sb.append("Issue Date: ").append(invoice.getIssueDateIso()).append("\n");
      sb.append("Due Date (").append(paymentTimeZone).append("): ").append(invoice.getDueDate()).append("\n\n");

      for (LineItem li : invoice.getItems()) {
        sb.append(li.getQuantity()).append(" x ").append(li.getProduct().getName())
            .append(" @ ").append(li.getUnitPrice()).append(" ").append(currency)
            .append(" (+").append((int) (li.getTaxRate() * 100)).append("% tax)")
            .append(" = ").append(li.calculateTotal())
            .append(" ").append(currency).append("\n");
      }

      sb.append("\nSubtotal (after discount ").append((int) (invoice.getDiscountPercent() * 100)).append("%): ")
          .append(discounted).append(" ").append(currency).append("\n");

      sb.append("Payable in ").append(displayCurrency).append(": ").append(converted).append(" ").append(displayCurrency).append("\n");

      if (invoice.isOverDue()) {
        double feeConv = invoice.convertCurrency();
        sb.append("Late fee: ").append(feeConv).append(" ").append(displayCurrency).append("\n");
      }

      return sb.toString();
    }
  }

  @Getter
  enum TimeZone {
    EST("EST"), GMT("GMT"), UTC("UTC");

    private final String value;

    TimeZone(String value) {
      this.value = value;
    }

    public static TimeZone from(String value) {
      return switch (value) {
        case "EST" -> EST;
        case "GMT" -> GMT;
        default -> UTC;
      };
    }
  }

  @Getter
  enum CurrencyType {
    USD("USD"), EUR("EUR"), XOF("XOF");

    private final String code;

    CurrencyType(String currency) {
      this.code = currency;
    }

    public static CurrencyType from(String code) {
      return switch (code) {
        case "USD" -> USD;
        case "EUR" -> EUR;
        case "XOF" -> XOF;
        default -> null;
      };
    }
  }

  @Getter
  enum Country {
    FR("FR"), US("US"), OTHER("OTHER");

    private final String code;

    Country(String code) {
      this.code = code;
    }

    public static Country from(String code) {
      return switch (code)  {
        case "US" -> US;
        default -> OTHER;
      };
    }
  }

  @Getter
  static class Product {
    private final String name;

    Product(String name) {
      this.name = name;
    }
  }

  @Getter
  static class LineItem {
    private final Product product;
    private final double unitPrice;         // in invoice currencyType
    private final int quantity;
    private final double taxRate;           // e.g., 0.20 for 20%

    public LineItem(Product product, double unitPrice, int quantity, double taxRate) {
      this.product = product;
      this.unitPrice = unitPrice;
      this.quantity = quantity;
      this.taxRate = taxRate;
    }

    public double calculateTotal() {
      double subtotal = unitPrice * quantity;
      double tax = subtotal * taxRate;
      return subtotal + tax;
    }
  }

  @Getter
  static class IsoDate {
    private final String date;

    IsoDate(String date) {
      this.date = date;
    }

    public int compareTo(IsoDate dueDate) {
      if (Objects.isNull(dueDate)) {
        return 1;
      }
      return date.compareTo(dueDate.getDate());
    }

    public int daysBetween(IsoDate endAt) {
      String[] s = date.split("-");
      String[] e = endAt.getDate().split("-");
      int start = Integer.parseInt(s[0]) * 365 + Integer.parseInt(s[1]) * 30 + Integer.parseInt(s[2]);
      int end = Integer.parseInt(e[0]) * 365 + Integer.parseInt(e[1]) * 30 + Integer.parseInt(e[2]);
      return Math.max(0, end - start);
    }

    public boolean isOverdue(IsoDate dateToCheck) {
      return compareTo(dateToCheck) > 0;
    }

    @Override
    public String toString() {
      return date;
    }
  }

  @Getter
  static class Address {
    private final String street;
    private final String city;
    private final String zip;
    private final Country country;

    public Address(String street, String city, String zip, Country country) {
      this.street = street;
      this.city = city;
      this.zip = zip;
      this.country = country;
    }
  }

  @Getter
  static class Customer {
    private final String name;
    private final Address address;

    Customer(String name, Address address) {
      this.name = name;
      this.address = address;
    }
  }

  @Getter
  static class ExchangeSetting {
    private final CurrencyType base;
    private final CurrencyType display;
    private final double rate;

    ExchangeSetting(CurrencyType base, CurrencyType display, double rate) {
      this.base = base;
      this.display = display;
      this.rate = rate;
    }
  }

  @Getter
  static class Invoice {
    private final String invoiceNumber;
    private final IsoDate issueDateIso;          // "yyyy-MM-dd"
    private final IsoDate dueDate;            // "yyyy-MM-dd"
    @Setter
    private IsoDate paidDate;           // nullable, "yyyy-MM-dd"
    private final ExchangeSetting exchangeSetting;          // "USD", "EUR"...
    private final double discountPercent;       // e.g., 0.10 for 10%
    private final double annualLateRatePercent; // e.g., 12.0
    private final Customer customer;
    private final TimeZone paymentTimeZone;
    private final List<LineItem> items = new ArrayList<>();

    public Invoice(String invoiceNumber,
                   IsoDate issueDateIso,
                   IsoDate dueDate,
                   ExchangeSetting exchangeSetting,
                   Customer customer,
                   double discountPercent,
                   double annualLateRatePercent,
                   TimeZone paymentTimeZone) {
      this.invoiceNumber = invoiceNumber;
      this.issueDateIso = issueDateIso;
      this.dueDate = dueDate;
      this.exchangeSetting = exchangeSetting;
      this.customer = customer;
      this.discountPercent = discountPercent;
      this.annualLateRatePercent = annualLateRatePercent;
      this.paymentTimeZone = paymentTimeZone;
    }

    public void addItem(LineItem item) {
      this.items.add(item);
    }

    public boolean isOverDue() {
      LocalDate nowInLocal = LocalDate.now();
      IsoDate nowInIso = new IsoDate(String.format("%s-%s-%s", nowInLocal.getYear(), nowInLocal.getMonth(), nowInLocal.getDayOfMonth()));
      boolean isOverdue = dueDate.isOverdue(nowInIso);
      if (Objects.isNull(paidDate) &&  isOverdue) {
        return false;
      }
      return dueDate.isOverdue(paidDate);
    }

    public double calculateTotal() {
      return this.items
        .stream()
        .map(LineItem::calculateTotal)
        .reduce(0.0, Double::sum);
    }

    public double calculateDiscount() {
      double amount = calculateTotal();
      return amount - (amount * discountPercent);
    }

    public double calculateLateFee() {
      if (Objects.nonNull(paidDate) && paidDate.compareTo(dueDate) <= 0)
        return 0.0;

      double amount = calculateDiscount();
      int daysLate = 1;
      if (Objects.nonNull(paidDate))
        daysLate = dueDate.daysBetween(paidDate);
      double dailyRate = annualLateRatePercent / 365.0;
      return amount * dailyRate * daysLate / 100.0;
    }

    public double convertCurrency() {
      double amount = calculateDiscount();
      if (isOverDue())
        amount = calculateLateFee();

      final String fromCurrency = exchangeSetting.getBase().name();
      String toCurrency = exchangeSetting.getDisplay().name();
      if (fromCurrency.equals(toCurrency))
        return amount;
      return amount * exchangeSetting.getRate();
    }
  }
}
