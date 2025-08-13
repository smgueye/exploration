package io.mgueye.sandbox.generics;

public class Femme extends Humain {
  private String aThing;
  public Femme(String name, String title, String aThing) {
    super(name, title);
    this.aThing = aThing;
  }

  /**
   * @return
   */
  @Override
  public String getSomething() {
    return aThing;
  }
}
