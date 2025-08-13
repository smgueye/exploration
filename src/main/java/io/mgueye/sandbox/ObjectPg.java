package io.mgueye.sandbox;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ObjectPg {

  public static void main(String[] args) {
    Map<Integer, List<Integer>> M = new HashMap<>();
    M.put(1, List.of(11, 12, 13));
    M.put(2, List.of(21, 22, 23));
    M.put(3, List.of(31, 32, 33));

    Map<Integer, List<Integer>> K = new HashMap<>(M);

    M.keySet().retainAll(List.of(1));
    System.out.println(M);
    System.out.println(K);

    if (Boolean.TRUE.equals(true))
      System.out.println("-- OK");
    else
      System.out.println("-- KO");
  }
}
