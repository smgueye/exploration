package io.mgueye.code_analysis.call_tracker;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.github.javaparser.StaticJavaParser;
import com.github.javaparser.ast.CompilationUnit;

import java.io.File;
import java.io.FileNotFoundException;

public class Init {
  public static void main(String[] args) throws FileNotFoundException, JsonProcessingException {
    File file = new File(args[0]);

    CompilationUnit cu = StaticJavaParser.parse(file);
    CallGraphBuilder callGraphBuilder = new CallGraphBuilder();
    callGraphBuilder.build(cu);
    // callGraphBuilder.printCallGraph();
    // System.out.println(callGraphBuilder.getNodes());
    System.out.println(callGraphBuilder.getLinks());
    // InternalCallTracker tracker = new InternalCallTracker();
    // tracker.analyze(cu);
    // tracker.printResults();
  }
}
