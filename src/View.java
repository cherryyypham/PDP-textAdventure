/**
 * View class that handles all output display
 * Manages formatting and presentation of game text
 */
public class View {

    public void displayWelcome() {
        System.out.println("=====================================================");
        System.out.println("England 93^2 AD");
        System.out.println();
        System.out.println("\tDear Valiant Knight with Questionable Honor,\n\n" +
                "\tYou shall accompany King Arthur journey in Search of the Holy Grail. " +
                "Bring your wits, bravery, and strength to the test through this thrilling " +
                "adventure of the Round Table!");
        System.out.println();
        System.out.println("Type ':q' to end the game or ':h' to display hints.");
        System.out.println();
        System.out.println("=====================================================");

    }

    public void displayPrompt() {
        System.out.print("\n> ");
    }

    public void displayNarration(String text) {
        System.out.println("\n--- " + text);
    }

    public void displayDialogue(String text) {
        System.out.println("\n\"" + text + "\"");
    }

    public void displayMessage(String text) {
        System.out.println("\n" + text);
    }

    public void displayError(String error) {
        System.out.println("\n[!] " + error);
    }

    public void displayHint(String hint) {
        System.out.println("\n[HINT] " + hint);
    }
}