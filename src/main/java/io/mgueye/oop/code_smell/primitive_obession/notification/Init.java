package io.mgueye.oop.code_smell.primitive_obession.notification;

import lombok.Getter;

public class Init {

  @Getter
  public sealed abstract class Notification permits EmailNotification, SmsNotification, PushNotification {
    private final String recipient;
    private final String message;

    public Notification(String recipient, String message) {
      this.recipient = recipient;
      this.message = message;
    }

    public abstract String channelName();
    public abstract int maxLength();
    public abstract String render();
    public abstract boolean canScheduleAtNight();
  }

  final class EmailNotification extends Notification {
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

  final class SmsNotification extends Notification {
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

    @Override
    public boolean canScheduleAtNight() {
      return false;
    }
  }

  final class PushNotification extends Notification {
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

    @Override
    public boolean canScheduleAtNight() {
      return true;
    }
  }
}
