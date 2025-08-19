package io.mgueye.code_analysis.call_tracker;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.javaparser.ast.CompilationUnit;
import com.github.javaparser.ast.body.MethodDeclaration;
import com.github.javaparser.ast.expr.MethodCallExpr;
import com.github.javaparser.ast.visitor.VoidVisitorAdapter;

import java.util.*;

public class CallGraphBuilder {
  private Map<String, Set<String>> callGraph = new HashMap<>();
  private final Set<CallGraphNode> declaredMethods = new HashSet<>();
  private Deque<String> methodStack = new ArrayDeque<>();

  public void build(CompilationUnit cu) {
    cu.accept(new MethodDeclarationCollector(), null);
    cu.accept(new MethodVisitor(), null);
  }

  private class MethodDeclarationCollector extends VoidVisitorAdapter<Void> {
    @Override
    public void visit(MethodDeclaration method, Void arg) {
      declaredMethods.add(new CallGraphNode(method.getNameAsString(), method.getParameters()));
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
      if (declaredMethods.stream().anyMatch(callGraphNode -> callGraphNode.getMethodName().equals(callName)) && !call.getScope().isPresent()) {
        String caller = methodStack.peek();
        String callee = call.getNameAsString();
        callGraph.get(caller).add(callee);
      }
      super.visit(call, arg);
    }
  }

  public Set<CallGraphNode> getNodes() {
    return this.declaredMethods;
  }

  public String getLinks() throws JsonProcessingException {
    ObjectMapper objectMapper = new ObjectMapper();
    List<Map<String, Object>> links = new ArrayList<>();
    final int[] idx = {1};
    callGraph.forEach((caller, callees) -> {
      callees.forEach(callee -> {
        links.add(new HashMap<>() {{
          put("id", idx[0]++);
          put("source", caller);
          put("target", callee);
          put("value", 1);
        }});
      });
    });
    return objectMapper.writeValueAsString(links);
  }

  public void printCallGraph() {
    callGraph.forEach((caller, callees) -> {
      System.out.println(caller + " calls:");
      callees.forEach(callee -> System.out.println("  → " + callee));
    });
  }
}