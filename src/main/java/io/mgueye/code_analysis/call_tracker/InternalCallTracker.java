package io.mgueye.code_analysis.call_tracker;

import com.github.javaparser.ast.CompilationUnit;
import com.github.javaparser.ast.body.MethodDeclaration;
import com.github.javaparser.ast.expr.MethodCallExpr;
import com.github.javaparser.ast.visitor.VoidVisitorAdapter;

import java.util.*;

public class InternalCallTracker {
  private final Set<String> declaredMethods = new HashSet<>();
  private final Deque<String> callStack = new ArrayDeque<>();
  private final List<List<String>> internalChains = new ArrayList<>();

  public void analyze(CompilationUnit cu) {
    // First pass: collect all method declarations in the class
    cu.accept(new MethodDeclarationCollector(), null);

    // Second pass: track only internal calls
    cu.accept(new InternalMethodVisitor(), null);
  }

  private class MethodDeclarationCollector extends VoidVisitorAdapter<Void> {
    @Override
    public void visit(MethodDeclaration method, Void arg) {
      declaredMethods.add(method.getNameAsString());
      super.visit(method, arg);
    }
  }

  private class InternalMethodVisitor extends VoidVisitorAdapter<Void> {
    @Override
    public void visit(MethodDeclaration method, Void arg) {
      callStack.push(method.getNameAsString());
      super.visit(method, arg);
      callStack.pop();
    }

    @Override
    public void visit(MethodCallExpr call, Void arg) {
      String callName = call.getNameAsString();

      // Only track if:
      // 1. It's a call to a declared method, AND
      // 2. It's not qualified (not called on an object)
      if (declaredMethods.contains(callName) && !call.getScope().isPresent()) {
        callStack.push(callName);
        internalChains.add(new ArrayList<>(callStack));
        super.visit(call, arg);
        callStack.pop();
      } else {
        super.visit(call, arg);
      }
    }
  }

  public void printResults() {
    System.out.println("Internal Call Chains:");
    for (List<String> chain : internalChains) {
      Collections.reverse(chain);
      System.out.println("  " + String.join(" → ", chain));
    }
  }
}