/**
 * FrenchCastleActions - Handles all actions in the French Castle scene
 */
public record FrenchCastleActions(Model MODEL) {

    public ActionResult handleAction(String verb, String noun) {
        int progress = MODEL.getSceneProgress("FRENCH_CASTLE");

        // Progress 0: Ask about grail/shelter
        if (progress == 0) {
            if (verb.equals("ask") && (noun.equals("castle") || noun.equals("french") || noun.equals("grail"))) {
                MODEL.incrementSceneProgress("FRENCH_CASTLE");
                String response = "[French accent] Well, I'll ask him, but I don't think he'll be very keen. " +
                        "He's already got one, you see.";
                String narration = "You are quite perplexed. How could a French lord come into possession of the Holy Grail?";
                return new ActionResult(true, response, narration, MODEL.getCurrentHint());
            }
            return new ActionResult(false, "Try asking about the grail or requesting entrance.");
        }

        // Progress 1: Try to enter castle
        if (progress == 1) {
            if (verb.equals("ask") && noun.equals("castle")) {
                MODEL.incrementSceneProgress("FRENCH_CASTLE");
                return new ActionResult(true,
                        "[French accent] Of course not! You are English-types.",
                        "", MODEL.getCurrentHint());
            }
            return new ActionResult(false, "Perhaps you should ask to see their grail.");
        }

        // Progress 2: Threaten them
        if (progress == 2) {
            if (verb.equals("threaten")) {
                MODEL.incrementSceneProgress("FRENCH_CASTLE");
                String response = "[French accent] You don't frighten us, English pigs-dogs. " +
                        "Go and boil your bottoms, sons of a silly person. " +
                        "I blow my nose at you, so-called Arthur King. " +
                        "You and all your silly English k—nights.\n\n" +
                        "[French accent] I don't want to talk to you no more, you empty-headed, " +
                        "animal food-trough wiper. I fart in your general direction. " +
                        "Your mother was a hamster, and your father smelt of elderberries.\n\n" +
                        "[French accent] Now go away or I shall taunt you a second time.";
                return new ActionResult(true, response, "", MODEL.getCurrentHint());
            }
            return new ActionResult(false, "You should threaten them!");
        }

        // Progress 3-5: Cows being launched
        if (progress >= 3 && progress < 6) {
            MODEL.incrementSceneProgress("FRENCH_CASTLE");

            if (progress == 3) {
                String narration = "Fetchez la vache!\n\nA cow was catapulted in your general direction.";
                return new ActionResult(true, "", narration, MODEL.getCurrentHint());
            } else if (progress == 4) {
                String narration = "More livestock rain down upon you!";
                return new ActionResult(true, "", narration, MODEL.getCurrentHint());
            } else if (progress == 5) {
                if (verb.equals("run") || verb.equals("flee")) {
                    MODEL.incrementSceneProgress("FRENCH_CASTLE");
                    String narration = "You and your knights retreat to safety.";
                    return new ActionResult(true, "", narration, MODEL.getCurrentHint());
                } else {
                    String narration = "A massive cow lands on you!";
                    MODEL.setPlayerDead(true);
                    return new ActionResult(true, "", narration, "You have died. Game Over.");
                }
            }
        }

        // Progress 6: Plan the wooden rabbit
        if (progress == 6) {
            if (verb.equals("plan")) {
                MODEL.incrementSceneProgress("FRENCH_CASTLE");
                String narration = "Along with your gallant knights, you wheeled a wooden rabbit to the gate " +
                        "of the French Castle. Unsuspectingly, the greedy French took the rabbit into their base. " +
                        "Outside the castle, you listen to the debrief of the next part of the plan.";
                String response = "Sir Bedivere: 'Now, Lancelot, Galahad, and I wait until nightfall and then " +
                        "leap out of the rabbit, taking the French by surprise. Not only by surprise, " +
                        "but totally unarmed!!'\n\n" +
                        "You ask: 'Who leaps out?'\n\n" +
                        "Sir Bedivere looks around at Lancelot, Galahad, and you standing outside the castle.\n\n" +
                        "The plan has failed spectacularly. You move on to continue your quest.";

                // Transition to Knights Ni
                MODEL.setCurrentScene("KNIGHTS_NI");
                return new ActionResult(true, response,
                        narration + "\n\n" + MODEL.getCurrentSceneNarration(),
                        MODEL.getCurrentHint());
            }
            return new ActionResult(false, "You need a better plan!");
        }

        return new ActionResult(false, "That action doesn't make sense right now.");
    }
}