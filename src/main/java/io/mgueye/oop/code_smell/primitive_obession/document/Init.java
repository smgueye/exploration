package io.mgueye.oop.code_smell.primitive_obession.document;

import lombok.Getter;
import lombok.Setter;

import java.time.Instant;

public class Init {
  public static void main(String[] args) {
  }

  public static class Document {
    private DocumentState documentState;

    @Getter
    @Setter
    private String title;

    @Getter
    @Setter
    private String content;

    private Instant publishedAt;
    private Instant archivedAt;

    public Document(String title, String content) {
      this.title = title;
      this.content = content;
      this.documentState = new DraftType();
    }

    public int getStateCode() { return documentState.getCode().ordinal(); }

    public void publish() {
      documentState.publish(this);
    }

    public void archive() {
     documentState.archive(this);
    }

    public boolean canShare() {
      return documentState.canShare(this);
    }

    public String visibilityLabel() {
      return documentState.visibilityLabel(this);
    }

    public String statusLine() {
      return documentState.statusLine(this);
    }
  }

  @Getter
  enum DocumentStateCode {
    DRAFT(0),
    PUBLISHED(1),
    ARCHIVED(2);

    private final int code;

    DocumentStateCode(int code) {
      this.code = code;
    }

    public static DocumentStateCode from(int code) {
      return switch (code) {
        case 0 -> DRAFT;
        case 1 -> PUBLISHED;
        case 2 -> ARCHIVED;
        default -> throw new IllegalArgumentException();
      };
    }
  }

  @Getter
  static sealed abstract class DocumentState permits DraftType, PublishedType, ArchivedType {
    private final DocumentStateCode code;

    public DocumentState(DocumentStateCode code) {
      this.code = code;
    }

    public abstract void editTitle(Document document, String newTitle);
    public abstract void editContent(Document document, String newContent);

    public abstract void publish(Document document);
    public abstract void archive(Document document);
    public abstract boolean canShare(Document document);
    public abstract String visibilityLabel(Document document);
    public abstract String statusLine(Document document);
  }

  static final class DraftType extends DocumentState {

    public DraftType() {
      super(DocumentStateCode.DRAFT);
    }

    @Override
    public void editTitle(Document document, String newTitle) {
      document.title = newTitle;
    }

    @Override
    public void editContent(Document document, String newContent) {
      document.content = newContent;
    }

    @Override
    public void publish(Document document) {
      document.publishedAt = Instant.now();
      document.documentState = new PublishedType();
    }

    @Override
    public void archive(Document document) {
      document.archivedAt = Instant.now();
      document.documentState = new ArchivedType();
    }

    @Override
    public boolean canShare(Document document) {
      return false;
    }

    @Override
    public String visibilityLabel(Document document) {
      return "Private (Draft)";
    }

    @Override
    public String statusLine(Document document) {
      return "DRAFT";
    }
  }

  static final class PublishedType extends DocumentState {

    public PublishedType() {
      super(DocumentStateCode.PUBLISHED);
    }


    @Override
    public void editTitle(Document document, String newTitle) {
      throw new IllegalStateException("Cannot edit title after publish");
    }

    @Override
    public void editContent(Document document, String newContent) {
      throw new IllegalStateException("Cannot edit content after publish");
    }

    @Override
    public void publish(Document document) {
    }

    @Override
    public void archive(Document document) {
      document.archivedAt = Instant.now();
      document.documentState = new ArchivedType();
    }

    @Override
    public boolean canShare(Document document) {
      return true;
    }

    @Override
    public String visibilityLabel(Document document) {
      return "Public";
    }

    @Override
    public String statusLine(Document document) {
      return "PUBLISHED @ " + document.publishedAt;
    }
  }

  static final class ArchivedType extends DocumentState {

    public ArchivedType() {
      super(DocumentStateCode.ARCHIVED);
    }

    @Override
    public void editTitle(Document document, String newTitle) {
      throw new IllegalStateException("Cannot edit title when archived");
    }

    @Override
    public void editContent(Document document, String newContent) {
      throw new IllegalStateException("Cannot edit content when archived");
    }

    @Override
    public void publish(Document document) {
      throw new IllegalStateException("Cannot publish an archived document");
    }

    @Override
    public void archive(Document document) {
    }

    @Override
    public boolean canShare(Document document) {
      return false;
    }

    @Override
    public String visibilityLabel(Document document) {
      return "Private (Archived)";
    }

    @Override
    public String statusLine(Document document) {
      return "ARCHIVED @ " + document.archivedAt;
    }
  }
}
