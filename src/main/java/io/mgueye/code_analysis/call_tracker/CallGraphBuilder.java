package io.mgueye.code_analysis.call_tracker;

import com.github.javaparser.ast.CompilationUnit;
import com.github.javaparser.ast.body.MethodDeclaration;
import com.github.javaparser.ast.expr.MethodCallExpr;
import com.github.javaparser.ast.visitor.VoidVisitorAdapter;

import java.util.*;

public class CallGraphBuilder {
  private Map<String, Set<String>> callGraph = new HashMap<>();
  private final Set<String> declaredMethods = new HashSet<>();
  private Deque<String> methodStack = new ArrayDeque<>();

  public void build(CompilationUnit cu) {
    cu.accept(new MethodDeclarationCollector(), null);
    cu.accept(new MethodVisitor(), null);
  }

  private class MethodDeclarationCollector extends VoidVisitorAdapter<Void> {
    @Override
    public void visit(MethodDeclaration method, Void arg) {
      declaredMethods.add(method.getNameAsString());
      super.visit(method, arg);
    }
  }

  private class MethodVisitor extends VoidVisitorAdapter<Void> {
    @Override
    public void visit(MethodDeclaration method, Void arg) {
      methodStack.push(method.getNameAsString());
      callGraph.putIfAbsent(method.getNameAsString(), new HashSet<>());
      super.visit(method, arg);
      methodStack.pop();
    }

    @Override
    public void visit(MethodCallExpr call, Void arg) {
      String callName = call.getNameAsString();
      if (declaredMethods.contains(callName) && !call.getScope().isPresent()) {
        String caller = methodStack.peek();
        String callee = call.getNameAsString();
        callGraph.get(caller).add(callee);
      }
      super.visit(call, arg);
    }
  }

  public void printCallGraph() {
    callGraph.forEach((caller, callees) -> {
      System.out.println(caller + " calls:");
      callees.forEach(callee -> System.out.println("  → " + callee));
    });
  }
}