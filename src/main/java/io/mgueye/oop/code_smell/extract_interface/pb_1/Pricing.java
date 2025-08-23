package io.mgueye.oop.code_smell.extract_interface.pb_1;

public interface Pricing {

  long calculateTax(long subtotalCents, String countryIso);
  long applyPromotions(long subtotalCents, String promoCode);
}
