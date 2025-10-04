/**
 * ActionProcessor - Processes game actions based on verb and noun
 * Delegates to specific action handlers and manages scene transitions
 */
public class ActionProcessor {
    private final Model MODEL;
    private final DennisActions dennisActions;
    private final BlackKnightActions blackKnightActions;
    private final FrenchCastleActions frenchCastleActions;
    private final KnightsNiActions knightsNiActions;

    public ActionProcessor(Model MODEL) {
        this.MODEL = MODEL;
        this.dennisActions = new DennisActions(MODEL);
        this.blackKnightActions = new BlackKnightActions(MODEL);
        this.frenchCastleActions = new FrenchCastleActions(MODEL);
        this.knightsNiActions = new KnightsNiActions(MODEL);
    }

    /**
     * Process action based on current scene
     */
    public ActionResult processAction(String verb, String noun, String input) {
        String currentScene = MODEL.getCurrentScene();

        return switch (currentScene) {
            case "DENNIS" -> dennisActions.handleAction(verb, noun, input);
            case "BLACK_KNIGHT" -> blackKnightActions.handleAction(verb, noun);
            case "FRENCH_CASTLE" -> frenchCastleActions.handleAction(verb, noun);
            case "KNIGHTS_NI" -> knightsNiActions.handleAction(verb, noun);
            default -> new ActionResult(false, "Unknown scene state.");
        };
    }
}