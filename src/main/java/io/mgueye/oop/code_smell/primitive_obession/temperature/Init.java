package io.mgueye.oop.code_smell.primitive_obession.temperature;

public class Init {

  public class TemperatureService {

    // takes raw primitives
    public boolean isFever(double temperatureCelsius) {
      return temperatureCelsius > 37.5;
    }

    // again breaks the object into primitives
    public String advice(double temperatureCelsius) {
      if (temperatureCelsius > 39) {
        return "High fever, see a doctor!";
      } else if (temperatureCelsius > 37.5) {
        return "Mild fever, take rest.";
      } else {
        return "Normal temperature.";
      }
    }
  }

  public class Patient {
    private String name;
    private double temperatureCelsius;

    public Patient(String name, double temperatureCelsius) {
      this.name = name;
      this.temperatureCelsius = temperatureCelsius;
    }

    public String getName() {
      return name;
    }

    public double getTemperatureCelsius() {
      return temperatureCelsius;
    }
  }

  public static void main(String[] args) {
  }
}
