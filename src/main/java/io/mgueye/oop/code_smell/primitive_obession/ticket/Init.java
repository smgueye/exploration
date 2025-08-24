package io.mgueye.oop.code_smell.primitive_obession.ticket;

import lombok.Getter;

public class Init {

  public static void main(String[] args) {
    System.out.println(new Init.Ticket("A-AA", PriorityType.HIGH, CategoryType.BUG).label());
    System.out.println(new Init.Ticket("B-BB", PriorityType.DEFAULT, CategoryType.BUG).label());
    System.out.println(new Init.Ticket("C-CC", PriorityType.HIGH, CategoryType.DEFAULT).label());
    System.out.println(new Init.Ticket("D-DD", PriorityType.MEDIUM, CategoryType.TASK).label());
  }

  @Getter
  public static class PriorityType {
    private final int slaHour;
    private final String type;

    private PriorityType(int slaHour, String type) {
      this.slaHour = slaHour;
      this.type = type;
    }

    public static final PriorityType HIGH = new PriorityType(4, "HIGH");
    public static final PriorityType MEDIUM = new PriorityType(24, "MEDIUM");
    public static final PriorityType LOW = new PriorityType(72, "LOW");
    public static final PriorityType DEFAULT = new PriorityType(999, "DEFAULT");

    public static PriorityType of(String s) {
      return switch (s) {
        case "HIGH" -> HIGH;
        case "MEDIUM" -> MEDIUM;
        case "LOW" -> LOW;
        default -> DEFAULT;
      };
    }
  }

  @Getter
  public static class CategoryType {
    private final int assigneeTeamId;
    private final String type;

    private CategoryType(int assigneeTeamId, String type) {
      this.assigneeTeamId = assigneeTeamId;
      this.type = type;
    }

    public static final CategoryType BUG = new CategoryType(10, "BUG");
    public static final CategoryType FEATURE = new CategoryType(20, "FEATURE");
    public static final CategoryType TASK = new CategoryType(30, "TASK");
    public static final CategoryType DEFAULT = new CategoryType(-1, "DEFAULT");

    public static CategoryType of(String s) {
      return switch (s) {
        case "BUG" -> BUG;
        case "FEATURE" -> FEATURE;
        case "TASK" -> TASK;
        default -> DEFAULT;
      };
    }
  }

  public static class Ticket {
    private final String id;
    private final PriorityType priority; // "LOW", "MEDIUM", "HIGH"
    private final CategoryType category; // "BUG", "FEATURE", "TASK"

    public Ticket(String id, PriorityType priority, CategoryType category) {
      this.id = id;
      this.priority = priority;
      this.category = category;
    }

    public int slaHours() {
      return priority.getSlaHour();
    }

    public int defaultAssigneeTeamId() {
      return category.getAssigneeTeamId();
    }

    public String label() {
      return "[" + priority.getType() + "] " + category.getType() + " #" + id;
    }
  }
}
