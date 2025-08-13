package io.mgueye.code_analysis.call_tracker;

import com.github.javaparser.StaticJavaParser;
import com.github.javaparser.ast.CompilationUnit;
import com.github.javaparser.ast.body.MethodDeclaration;
import com.github.javaparser.ast.expr.MethodCallExpr;
import com.github.javaparser.ast.visitor.VoidVisitorAdapter;

import java.io.File;
import java.io.FileNotFoundException;

public class MethodCallDetector {



  public static void main(String[] args) throws FileNotFoundException {
    File file = new File(args[0]);
    CompilationUnit cu = StaticJavaParser.parse(file);
    cu.accept(new MethodVisitor(), null);
  }

  private static class MethodVisitor extends VoidVisitorAdapter<Void> {
    @Override
    public void visit(MethodDeclaration method, Void arg) {
      System.out.println("Analyzing method: " + method.getName());

      method.findAll(MethodCallExpr.class).forEach(call -> {
        System.out.println("  - Calls: " + call.getName());
        System.out.println("    - At line: " + call.getBegin().get().line);
      });

      super.visit(method, arg);
    }
  }
}
