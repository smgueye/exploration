package io.mgueye.oop.code_smell.primitive_obession.temperature;

import lombok.Getter;

public class Init {

  public static class TemperatureService {

    public boolean isFever(Patient patient) {
      return patient
          .getTemperature()
          .getValueInCelsius() > 37.5;
    }

    public String advice(Patient patient) {
      double temperature= patient
          .getTemperature()
          .getValueInCelsius();
      if (temperature > 39) {
        return "High fever, see a doctor!";
      } else if (temperature > 37.5) {
        return "Mild fever, take rest.";
      } else {
        return "Normal temperature.";
      }
    }
  }

  @Getter
  public static class Temperature {

    private final double valueInCelsius;

    public Temperature(double valueInCelsius) {
      this.valueInCelsius = valueInCelsius;
    }
  }

  @Getter
  public static class Patient {

    private final String name;
    private final Temperature temperature;

    public Patient(String name, Temperature temperature) {
      this.name = name;
      this.temperature = temperature;
    }
  }

  public static void main(String[] args) {
  }
}
