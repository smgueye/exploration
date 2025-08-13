package io.mgueye.sandbox.generics;


public class Main {
  public static void main(String[] args) {
    Homme homme = new Homme("Mouhamed Gueye", "Senior Software Engineer", 31);
    Femme femme = new Femme("Dieynab Diatta", "Economist", "I'm Ali's mother");
    Integer aNumber = homme.getSomething();
    System.out.println(aNumber);
    String aThing = femme.getSomething();
    System.out.println(aThing);
  }
}
