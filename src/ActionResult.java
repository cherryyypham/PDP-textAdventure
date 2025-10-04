/**
 * ActionResult - Encapsulates the result of processing an action
 * Contains validity status, messages, narration, and hints
 */
public class ActionResult {
    private final boolean valid;
    private final String message;
    private String narration;
    private String hint;

    public ActionResult(boolean valid, String message) {
        this.valid = valid;
        this.message = message;
        this.narration = "";
        this.hint = "";
    }

    public ActionResult(boolean valid, String message, String narration, String hint) {
        this.valid = valid;
        this.message = message;
        this.narration = narration;
        this.hint = hint;
    }

    public boolean isValid() {
        return valid;
    }

    public String getMessage() {
        return message;
    }

    public String getNarration() {
        return narration;
    }

    public String getHint() {
        return hint;
    }

    public void setNarration(String narration) {
        this.narration = narration;
    }

    public void setHint(String hint) {
        this.hint = hint;
    }
}