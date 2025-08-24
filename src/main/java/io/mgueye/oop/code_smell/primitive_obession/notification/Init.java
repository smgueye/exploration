package io.mgueye.oop.code_smell.primitive_obession.notification;

import lombok.Getter;

public class Init {

  @Getter
  public abstract class Notification {
    private final String recipient;
    private final String message;

    public Notification(String recipient, String message) {
      this.recipient = recipient;
      this.message = message;
    }

    public String channelName() {
        throw new IllegalArgumentException("Unknown type.");
    }

    public int maxLength() {
      throw new IllegalArgumentException("Unknown type.");
    }

    public String render() {
      throw new IllegalArgumentException("Unknown type.");
    }

    public boolean canScheduleAtNight() {
      throw new IllegalArgumentException("Unknown type.");
    }
  }

  class EmailNotification extends Notification {
    public EmailNotification(String recipient, String message) {
      super(recipient, message);
    }

    @Override
    public String channelName() {
      return "Email";
    }

    @Override
    public int maxLength() {
      return 10000;
    }

    @Override
    public String render() {
      return "To: " + getRecipient() + "\n\n" + getMessage();
    }

    @Override
    public boolean canScheduleAtNight() {
      return true;
    }
  }

  class SmsNotification extends Notification {
    public SmsNotification(String recipient, String message) {
      super(recipient, message);
    }

    @Override
    public String channelName() {
      return "SMS";
    }

    @Override
    public int maxLength() {
      return 160;
    }

    @Override
    public String render() {
      return getRecipient() + ": " + getMessage();
    }

    public boolean canScheduleAtNight() {
      return false;
    }
  }

  class PushNotification extends Notification {
    public PushNotification(String recipient, String message) {
      super(recipient, message);
    }

    @Override
    public String channelName() {
      return "Push";
    }

    @Override
    public int maxLength() {
      return 200;
    }

    @Override
    public String render() {
      return "[PUSH] " + getMessage();
    }

    public boolean canScheduleAtNight() {
      return true;
    }
  }
}
