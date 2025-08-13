package io.mgueye.datas;

import java.util.ArrayList;
import java.util.List;

public class ListTest {
  static class A {
    private String label;

    public A(String label) {
      this.label = label;
    }

    public String getLabel() {
      return label;
    }

    public void setLabel(String label) {
      this.label = label;
    }

    @Override
    public String toString() {
      return label;
    }
  }
  public static void main(String[] args) {
    List<A> aList = List.of(new A("COP;CROQ"), new A("CIP;CRICK"), new A("CAP;CRAP"));
    for (A a : aList) {
      a.setLabel(a.getLabel().replace(";", ","));
    }
    System.out.println(aList);

  }
}
