package io.mgueye.code_analysis.dependency_tracker;

import com.github.javaparser.JavaParser;
import com.github.javaparser.StaticJavaParser;
import com.github.javaparser.ast.CompilationUnit;
import com.github.javaparser.ast.body.ClassOrInterfaceDeclaration;
import com.github.javaparser.ast.body.FieldDeclaration;
import com.github.javaparser.ast.visitor.VoidVisitorAdapter;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.HashMap;
import java.util.Map;

public class DependencyAnalyzer {

  private final Map<String, Map<String, String>> classDependencies = new HashMap<>();

  public void analyze(CompilationUnit cu) {
    cu.accept(new ClassVisitor(), null);
  }

  private class ClassVisitor extends VoidVisitorAdapter<Void> {
    @Override
    public void visit(ClassOrInterfaceDeclaration classDecl, Void arg) {
      String className = classDecl.getNameAsString();
      classDependencies.putIfAbsent(className, new HashMap<>());

      // Visit all fields to find autowired dependencies
      classDecl.accept(new FieldVisitor(className), null);

      super.visit(classDecl, arg);
    }
  }

  private class FieldVisitor extends VoidVisitorAdapter<Void> {
    private final String currentClass;

    public FieldVisitor(String currentClass) {
      this.currentClass = currentClass;
    }

    @Override
    public void visit(FieldDeclaration field, Void arg) {
      // Check for @Autowired annotation
      boolean isAutowired = field.getAnnotations().stream()
          .anyMatch(ann -> ann.getNameAsString().equals("Autowired"));

      if (isAutowired) {
        String dependencyType = field.getElementType().asString();
        String fieldName = field.getVariable(0).getNameAsString();

        classDependencies.get(currentClass)
            .put(fieldName, dependencyType);
      }
      super.visit(field, arg);
    }
  }

  public void printDependencies() {
    System.out.println("Spring Dependency Analysis:");
    classDependencies.forEach((className, deps) -> {
      System.out.println("\nClass: " + className);
      if (deps.isEmpty()) {
        System.out.println("  No @Autowired dependencies");
      } else {
        System.out.println("  Dependencies:");
        deps.forEach((name, type) ->
            System.out.println("    " + name + " : " + type));
      }
    });
  }

  public static void main(String[] args)  throws Exception  {
    String code = "import org.springframework.beans.factory.annotation.Autowired;\n" +
        "class ServiceA {\n" +
        "  @Autowired\n" +
        "  private RepositoryA repoA;\n" +
        "}\n" +
        "class ServiceB {\n" +
        "  @Autowired\n" +
        "  private RepositoryB repoB;\n" +
        "  @Autowired\n" +
        "  private ServiceA serviceA;\n" +
        "}";
    File file = new File(args[0]);

    CompilationUnit cu = StaticJavaParser.parse(file);
    DependencyAnalyzer analyzer = new DependencyAnalyzer();
    analyzer.analyze(cu);
    analyzer.printDependencies();
  }
}