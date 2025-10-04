import java.util.Scanner;

/**
 * Controller class that manages the game flow
 * Coordinates between Model and View components
 */
public class Controller {
    private final Model MODEL;
    private final View VIEW;
    private final Scanner SC;
    private boolean gameRunning;

    public Controller() {
        this.MODEL = new Model();
        this.VIEW = new View();
        this.SC = new Scanner(System.in);
        this.gameRunning = true;
    }

    /**
     * Main game loop - starts and manages the game
     */
    public void startGame() {
        VIEW.displayWelcome();
        VIEW.displayNarration(MODEL.getCurrentSceneNarration());
        VIEW.displayHint(MODEL.getCurrentHint());

        while (gameRunning) {
            VIEW.displayPrompt();
            String input = SC.nextLine();

            if (input.equalsIgnoreCase(":q")) {
                gameRunning = false;
                VIEW.displayMessage("Thou hast perished most inconveniently. The developers responsible for" +
                        "thy demise have been sacked. Their replacements have also been sacked. A small shrubbery" +
                        "has been ordered in their honor.");
                break;
            }

            if (input.equalsIgnoreCase(":h")) {
                VIEW.displayHint(MODEL.getCurrentHint());
            }

            processCommand(input);
        }

        SC.close();
    }

    /**
     * Processes user commands and updates game state
     */
    private void processCommand(String input) {
        // Parse the input
        InputParser parser = new InputParser();
        String[] parsed = parser.processInput(input);

        if (parsed == null) {
            VIEW.displayError("Verily, thy command maketh as much sense as a duck in chainmail!");
            VIEW.displayHint("Try using a verb and noun, like 'talk man' or 'enter castle'");
            return;
        }

        String verb = parsed[0];
        String noun = parsed[1];

        // Process the action
        ActionProcessor processor = new ActionProcessor(MODEL);
        ActionResult result = processor.processAction(verb, noun, input);

        // Display action results
        if (!result.isValid()) {
            VIEW.displayError(result.getMessage());
            VIEW.displayHint(MODEL.getCurrentHint());
        } else {
            if (result.getNarration() != null && !result.getNarration().isEmpty()) {
                VIEW.displayNarration(result.getNarration());
            }
            if (result.getMessage() != null && !result.getMessage().isEmpty()) {
                VIEW.displayDialogue(result.getMessage());
            }
            if (result.getHint() != null && !result.getHint().isEmpty()) {
                VIEW.displayHint(result.getHint());
            }

            if (MODEL.isPlayerDead()) {
                VIEW.displayNarration("GAME OVER! Thou hast kicked the bucket, shuffled off this mortal" +
                        " coil, and made a complete pig’s ear of it");
                gameRunning = false;
            }
        }
    }
}