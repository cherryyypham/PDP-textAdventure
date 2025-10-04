/**
 * BlackKnightActions - Handles all actions in the Black Knight scene
 */
public record BlackKnightActions(Model MODEL) {
    public BlackKnightActions(Model MODEL) {
        this.MODEL = MODEL;
    }

    public ActionResult handleAction(String verb, String noun) {
        int progress = MODEL.getSceneProgress("BLACK_KNIGHT");

        // Progress 0: Need to talk to Black Knight
        if (progress == 0) {
            if (verb.equals("talk") && noun.equals("knight")) {
                MODEL.incrementSceneProgress("BLACK_KNIGHT");
                return new ActionResult(true, "...", "", MODEL.getCurrentHint());
            }
            return new ActionResult(false, "Try talking to the knight.");
        }

        // Progress 1: Try to pass, but knight blocks
        if (progress == 1) {
            if (verb.equals("pass") || verb.equals("cross")) {
                MODEL.incrementSceneProgress("BLACK_KNIGHT");
                String response = "None shall pass.";
                String narration = "I have no quarrels with you, good Sir Knight, but I must cross this bridge.\n\n" +
                        "Then you shall die.";
                return new ActionResult(true, response, narration, MODEL.getCurrentHint());
            }
            return new ActionResult(false, "Try to pass the knight.");
        }

        // Progress 2+: Fighting the Black Knight
        if (progress >= 2) {
            if (verb.equals("fight") || verb.equals("attack")) {
                int limbs = MODEL.getBlackKnightLimbs();

                if (limbs > 0) {
                    MODEL.removeBlackKnightLimb();
                    limbs--;

                    String response = switch (limbs) {
                        case 3 -> "'Tis but a scratch.";
                        case 2 -> "I've had worse.";
                        case 1 -> "Come on, you pansy! Have at you!";
                        case 0 ->
                                "All right, we'll call it a draw.";
                        default -> "";
                    };

                    String narration = "Despite missing limb(s), the black knight still charges at you and " +
                            "won't let you leave";

                    return new ActionResult(true, response, narration, MODEL.getCurrentHint());
                }
            }

            if ((verb.equals("pass") || verb.equals("cross")) && MODEL.getBlackKnightLimbs() == 0) {
                String response = "Running away, eh? You yellow bastards! Come back here and take what's coming to you! I'll bite your legs off!!!";
                String narration = "You successfully cross the bridge.";

                MODEL.setCurrentScene("FRENCH_CASTLE");
                return new ActionResult(true, response,
                        narration + "\n\n" + MODEL.getCurrentSceneNarration(),
                        MODEL.getCurrentHint());
            }

            return new ActionResult(false, "Keep fighting or try to pass!");
        }

        return new ActionResult(false, "That action doesn't make sense right now.");
    }
}