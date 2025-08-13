package io.mgueye.code_analysis.call_tracker;

import com.github.javaparser.ast.CompilationUnit;
import com.github.javaparser.ast.body.MethodDeclaration;
import com.github.javaparser.ast.expr.MethodCallExpr;
import com.github.javaparser.ast.visitor.VoidVisitorAdapter;

import java.util.*;

public class StackBasedCallTracker {

  // Stack to track the current call chain
  private final Deque<String> callStack = new ArrayDeque<>();

  // Stores all complete call chains we find
  private final List<List<String>> capturedChains = new ArrayList<>();

  // For tracking recursion
  private final List<String> recursionPoints = new ArrayList<>();

  public void analyze(CompilationUnit cu) {
    cu.accept(new MethodVisitor(), null);
  }

  private class MethodVisitor extends VoidVisitorAdapter<Void> {
    @Override
    public void visit(MethodDeclaration method, Void arg) {
      // Push current method onto stack
      String methodName = method.getNameAsString();
      callStack.push(methodName);

      // Check for recursion
      if (callStack.stream().filter(m -> m.equals(methodName)).count() > 1) {
        recursionPoints.add(methodName);
      }

      // Visit all contents of this method
      super.visit(method, arg);

      // Before popping, save the current call chain
      if (!callStack.isEmpty()) {
        capturedChains.add(new ArrayList<>(callStack));
      }

      // Pop the method from stack
      callStack.pop();
    }

    @Override
    public void visit(MethodCallExpr call, Void arg) {
      // Push method call onto stack
      String callName = call.getNameAsString();
      callStack.push(callName);

      // Visit arguments (which might contain other method calls)
      super.visit(call, arg);

      // Before popping, save the current call chain
      if (!callStack.isEmpty()) {
        capturedChains.add(new ArrayList<>(callStack));
      }

      // Pop the method call from stack
      callStack.pop();
    }
  }

  public void printResults() {
    System.out.println("Complete Call Chains Found:");
    for (List<String> chain : capturedChains) {
      // Reverse to show caller → callee order
      List<String> displayChain = new ArrayList<>(chain);
      Collections.reverse(displayChain);
      System.out.println("  " + String.join(" → ", displayChain));
    }

    if (!recursionPoints.isEmpty()) {
      System.out.println("\nPotential Recursion Points:");
      recursionPoints.forEach(point ->
          System.out.println("  ↻ " + point));
    }
  }
}