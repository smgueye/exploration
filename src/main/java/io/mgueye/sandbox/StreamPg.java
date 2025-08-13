package io.mgueye.sandbox;

import java.util.*;
import java.util.logging.Level;
import java.util.logging.Logger;

public class StreamPg {

  public static Logger logger = Logger.getLogger(StreamPg.class.getName());
  static class Pair {
    private String k;
    private String v;

    public Pair(String k, String v) {
      this.k = k;
      this.v = v;
    }
  }

  // How to implement continue using java stream apis. ?
  private void continueInStream(ArrayList<Integer> numbers) {
    numbers.forEach(item -> {
      if (item % 2 == 0)
        return;
      logger.info(String.valueOf(item));
    });
  }

  // How to use .reduce ?
  private int computeSum(ArrayList<Integer> numbers) {
    Optional<Integer> sum = numbers.stream().reduce(Integer::sum);
    return sum.isPresent() ? sum.get() : -1;
  }

  private double computeAverage(ArrayList<Integer> numbers) {
    return numbers
        .stream()
        .mapToInt(e -> e)
        .average()
        .getAsDouble();
  }

  public static void main(String[] args) {
    final List<Pair> CITY_INPUT = Arrays.asList(
        new Pair("DependencyAnalyzer", "B"),
        new Pair("B", "DependencyAnalyzer"),
        new Pair("DependencyAnalyzer", "B"),
        new Pair("B", "DependencyAnalyzer"),
        new Pair("DependencyAnalyzer", "B"));
    HashMap<String, List<String>> M = new HashMap<>();
    CITY_INPUT.forEach(p -> {
      M.computeIfAbsent(p.k, k -> new ArrayList<>()).add(p.v);
    });
    StreamPg streamPlayground = new StreamPg();
    streamPlayground.run();
  }

  private void run() {
    ArrayList<Integer> numbers = new ArrayList<>(List.of(1, 2, 3, 4, 5));

    continueInStream(numbers);

    logger.log(Level.INFO, "{0}", computeSum(numbers));

    logger.log(Level.INFO, "{0}", computeAverage(numbers));
  }
}
