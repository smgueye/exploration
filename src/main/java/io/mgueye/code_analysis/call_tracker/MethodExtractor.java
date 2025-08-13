package io.mgueye.code_analysis.call_tracker;

import com.github.javaparser.StaticJavaParser;
import com.github.javaparser.ast.CompilationUnit;
import com.github.javaparser.ast.body.MethodDeclaration;

import java.io.File;


public class MethodExtractor {
  public static void main(String[] args) throws Exception {
    File file = new File(args[0]);
    String commit = args[1];
    CompilationUnit cu = StaticJavaParser.parse(file);

    cu.findAll(MethodDeclaration.class).forEach(method -> {
      String name = method.getNameAsString();
      int begin = method.getBegin().get().line;
      int end = method.getEnd().get().line;
      System.out.println(commit + "," + name + "," + begin + "," + end);
    });
  }
}
