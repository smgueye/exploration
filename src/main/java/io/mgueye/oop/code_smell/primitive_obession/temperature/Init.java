package io.mgueye.oop.code_smell.primitive_obession.temperature;

public class Init {

  public class TemperatureService {

    public boolean isFever(Temperature temperature) {
      return temperature.getInCelsius() > 37.5;
    }

    public String advice(Temperature temperature) {
      if (temperature.getInCelsius() > 39) {
        return "High fever, see a doctor!";
      } else if (temperature.getInCelsius() > 37.5) {
        return "Mild fever, take rest.";
      } else {
        return "Normal temperature.";
      }
    }
  }

  public class Temperature {
    private double inCelsius;

    public Temperature(double inCelsius) {
      this.inCelsius = inCelsius;
    }

    public double getInCelsius() {
      return inCelsius;
    }
  }

  public class Patient {
    private String name;
    private Temperature temperature;

    public Patient(String name, Temperature temperature) {
      this.name = name;
      this.temperature = temperature;
    }

    public String getName() {
      return name;
    }

    public Temperature getTemperature() {
      return temperature;
    }
  }

  public static void main(String[] args) {
  }
}
