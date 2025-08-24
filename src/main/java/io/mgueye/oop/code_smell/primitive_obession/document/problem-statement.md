# Refactoring using : Replace Type Code with State/Strategy

## Questions

1. Can I use Replace type code with subclass ? - No because in this problem, the methods are not appliable to everz state,
   polymorphisme will not help here.
2. 

```java
import java.time.Instant;

public class Document {
    // 0 = DRAFT, 1 = PUBLISHED, 2 = ARCHIVED
    private int state;
    private String title;
    private String content;
    private Instant publishedAt;
    private Instant archivedAt;

    public Document(String title, String content) {
        this.title = title;
        this.content = content;
        this.state = 0; // DRAFT
    }

    public String getTitle() { return title; }
    public String getContent() { return content; }
    public int getStateCode() { return state; }

    public void editTitle(String newTitle) {
        switch (state) {
            case 0: this.title = newTitle; break;              // DRAFT: ok
            case 1: throw new IllegalStateException("Cannot edit title after publish");
            case 2: throw new IllegalStateException("Cannot edit title when archived");
            default: throw new IllegalArgumentException();
        }
    }

    public void editContent(String newContent) {
        switch (state) {
            case 0: this.content = newContent; break;          // DRAFT: ok
            case 1: throw new IllegalStateException("Cannot edit content after publish");
            case 2: throw new IllegalStateException("Cannot edit content when archived");
            default: throw new IllegalArgumentException();
        }
    }

    public void publish() {
        switch (state) {
            case 0:
                this.state = 1;
                this.publishedAt = Instant.now();
                break;
            case 1:
                // already published — no-op
                break;
            case 2:
                throw new IllegalStateException("Cannot publish an archived document");
            default:
                throw new IllegalArgumentException();
        }
    }

    public void archive() {
        switch (state) {
            case 0:
                this.state = 2;
                this.archivedAt = Instant.now();
                break;
            case 1:
                this.state = 2;
                this.archivedAt = Instant.now();
                break;
            case 2:
                // already archived — no-op
                break;
            default:
                throw new IllegalArgumentException();
        }
    }

    public boolean canShare() {
        switch (state) {
            case 0: return false; // DRAFT not shareable
            case 1: return true;  // PUBLISHED shareable
            case 2: return false; // ARCHIVED not shareable
            default: throw new IllegalArgumentException();
        }
    }

    public String visibilityLabel() {
        switch (state) {
            case 0: return "Private (Draft)";
            case 1: return "Public";
            case 2: return "Private (Archived)";
            default: throw new IllegalArgumentException();
        }
    }

    public String statusLine() {
        switch (state) {
            case 0: return "DRAFT";
            case 1: return "PUBLISHED @ " + publishedAt;
            case 2: return "ARCHIVED @ " + archivedAt;
            default: throw new IllegalArgumentException();
        }
    }
}

```