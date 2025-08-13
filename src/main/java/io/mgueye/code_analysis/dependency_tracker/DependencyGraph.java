package io.mgueye.code_analysis.dependency_tracker;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;


public class DependencyGraph {
  private final Map<String, Set<String>> graph = new HashMap<>();

  public void addDependency(String sourceClass, String targetClass) {
    graph.putIfAbsent(sourceClass, new HashSet<>());
    graph.get(sourceClass).add(targetClass);
  }

  public void printCircularDependencies() {
    System.out.println("\nChecking for circular dependencies...");
    graph.forEach((source, targets) -> {
      targets.forEach(target -> {
        if (graph.containsKey(target) &&
            graph.get(target).contains(source)) {
          System.out.println("  Circular dependency: " +
              source + " ↔ " + target);
        }
      });
    });
  }
}