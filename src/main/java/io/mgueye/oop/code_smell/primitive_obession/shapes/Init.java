package io.mgueye.oop.code_smell.primitive_obession.shapes;

import lombok.Getter;

public class Init {

  public sealed static abstract class Shape permits Circle, Rectangle, Triangle {

    public abstract double area();
    public abstract double perimeter();
    public abstract String svg();
  }

  @Getter
  static final class Circle extends Shape {
    private final double radius;

    public Circle(double radius) {
      this.radius = radius;
    }

    @Override
    public double area() {
      return Math.PI * radius * radius;
    }

    @Override
    public double perimeter() {
      return 2 * Math.PI * radius;
    }

    @Override
    public String svg() {
      return "<circle r=\"" + radius + "\" />";
    }
  }

  @Getter
  static final class Rectangle extends Shape {
    private final double height;
    private final double width;

    public Rectangle(double height, double width) {
      this.height = height;
      this.width = width;
    }

    @Override
    public double area() {
      return width * height;
    }

    @Override
    public double perimeter() {
      return 2 * (width + height);
    }

    @Override
    public String svg() {
      return "<rect width=\"" + width + "\" height=\"" + height + "\" />";
    }
  }

  static final class Triangle extends Shape {
    private final double sideA;
    private final double sideB;
    private final double sideC;

    Triangle(double sideA, double sideB, double sideC) {
      this.sideA = sideA;
      this.sideB = sideB;
      this.sideC = sideC;
    }

    @Override
    public double area() {
      double s = (sideA + sideB + sideC) / 2.0;
      return Math.sqrt(s*(s-sideA)*(s-sideB)*(s-sideC));
    }

    @Override
    public double perimeter() {
      return sideA + sideB + sideC;
    }

    @Override
    public String svg() {
      return "<polygon points=\"0,0 " + sideA + ",0 " + sideB + "," + sideC + "\" />";
    }
  }

}
