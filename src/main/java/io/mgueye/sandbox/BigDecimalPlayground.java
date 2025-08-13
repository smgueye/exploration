package io.mgueye.sandbox;

import java.math.BigDecimal;
import java.math.RoundingMode;

public class BigDecimalPlayground {
  public static void main(String[] args) {
    BigDecimal hundred = new BigDecimal(100);
    BigDecimal tva = BigDecimal.valueOf(20);
    BigDecimal prixVente = BigDecimal.valueOf(1.5);
    BigDecimal prixMinimum = BigDecimal.valueOf(0.659); //1.5011);

    BigDecimal a = hundred.subtract(prixMinimum.multiply(hundred).divide(prixVente, 4, RoundingMode.HALF_UP));
    BigDecimal b = hundred.multiply(tva).divide(hundred.add(tva), 4, RoundingMode.HALF_UP);
    BigDecimal pe = a.subtract(b);
    System.out.printf("%s - %s = %s \n",a, b, a.subtract(b));

    BigDecimal montant = prixMinimum.multiply(hundred).divide(hundred.subtract(pe.add(hundred.multiply(tva).divide(hundred.add(tva), 4, RoundingMode.HALF_UP))), 4, RoundingMode.HALF_UP);
    System.out.printf("%s\n", montant.setScale(2, RoundingMode.HALF_EVEN));

    System.out.println(new BigDecimal("1.00").compareTo(BigDecimal.ZERO));
  }
}
