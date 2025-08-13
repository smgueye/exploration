package io.mgueye.sandbox.generics;

public abstract class Humain {
  private String name;
  private String title;

  public Humain(String name, String title) {
    this.name = name;
    this.title = title;
  }

  public String getName() {
    return name;
  }

  public String getTitle() {
    return title;
  }

  public abstract <T> T getSomething();
}
