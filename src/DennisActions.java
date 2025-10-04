import java.util.Random;

/**
 * DennisActions - Handles all actions in the Dennis scene
 */
public record DennisActions(Model MODEL) {

    public DennisActions(Model MODEL) {
        this.MODEL = MODEL;
    }

    public ActionResult handleAction(String verb, String noun, String input) {
        int progress = MODEL.getSceneProgress("DENNIS");

        // Progress 0: Need to call out to Dennis
        if (progress == 0) {
            if (verb.equals("call") && (noun.equals("peasant") || noun.equals("man") || noun.equals("woman"))) {
                MODEL.incrementSceneProgress("DENNIS");

                String response;
                if (input != null) {
                    String lower = input.toLowerCase();
                    if (lower.contains("old")) {
                        response = "I'm not that old you know. I'm 37.\n" +
                                "You can call me Dennis. You didn't bother to find out, didn't you.";
                    } else if (lower.contains("woman")) {
                        response = "I'm a man you dimwit.";
                    } else {
                        response = "Ye calling out to me?";
                    }
                } else {
                    response = "Ye calling out to me?";
                }
                return new ActionResult(true, response + "Who are you anyways to talk to me like that" +
                        " eh?", "", MODEL.getCurrentHint());
            }
            return new ActionResult(false, "Try calling out to the peasant.");
        }

        // Progress 1: Need to ask about castle
        if (progress == 1) {
            if (verb.equals("ask") && noun.equals("castle")) {
                MODEL.incrementSceneProgress("DENNIS");
                return new ActionResult(true, "Well why should I answer you.", "", MODEL.getCurrentHint());
            }
            return new ActionResult(false, "Perhaps you should ask about something nearby.");
        }

        // Progress 2: Need to introduce or state authority
        if (progress == 2) {
            if (verb.equals("introduce")) {
                MODEL.incrementSceneProgress("DENNIS");
                String[] lines = {"Oh King eh? Very nice. How did you get that eh? " +
                        "By exploiting the inferiors. By hanging on to outdated imperialist " +
                        "dogma which perpetuates the economic and social differences in our society! " +
                        "If there's ever gonna be progress...",
                        "Didn't know we have a King. I thought we were an autonomous collective.",
                        "Well I didn't vote for you."};
                String response = lines[new Random().nextInt(lines.length)];
                String narration = "At this point, you reckon this person is not going to give out " +
                        "information easily. You either should coerce the information out of them, " +
                        "or walk away.";
                return new ActionResult(true, response, narration, MODEL.getCurrentHint());
            }
            return new ActionResult(false, "You should state who you are.");
        }

        // Progress 3: Need to threaten/fight or pass
        if (progress == 3) {
            if (verb.equals("threaten") || verb.equals("fight")) {
                String response = "What I object to is that you automatically treat me like an inferior!\n\n" +
                        "We're an anarcho-syndicalist commune. We take it in turns to act as a " +
                        "sort of executive officer for the week.\n\n" +
                        "(As the King beats him up)\n\n" +
                        "Now we see the violence inherent in the system! " +
                        "Help! Help! I'm being repressed!";
                String narration = "Dennis runs away, and you continue on your journey.";

                // Transition to Black Knight scene
                MODEL.setCurrentScene("BLACK_KNIGHT");
                MODEL.setSceneProgress("DENNIS", 4);

                return new ActionResult(true, response,
                        narration + "\n\n" + MODEL.getCurrentSceneNarration(),
                        MODEL.getCurrentHint());
            }
            if (verb.equals("pass") || verb.equals("walk")) {
                String narration = "You decide to walk away from Dennis and continue on your journey.";
                MODEL.setCurrentScene("BLACK_KNIGHT");
                MODEL.setSceneProgress("DENNIS", 4);
                return new ActionResult(true, "",
                        narration + "\n\n" + MODEL.getCurrentSceneNarration(),
                        MODEL.getCurrentHint());
            }
            return new ActionResult(false, "You need to either deal with Dennis or move on.");
        }

        return new ActionResult(false, "That action doesn't make sense right now.");
    }
}