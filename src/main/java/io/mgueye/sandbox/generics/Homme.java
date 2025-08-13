package io.mgueye.sandbox.generics;

public class Homme extends Humain {

  private Integer aNumber;

  public Homme(String name, String title, Integer aNumber) {
    super(name, title);
    this.aNumber = aNumber;
  }

  @Override
  public Integer getSomething() {
    return aNumber;
  }
}
