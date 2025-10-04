/**
 * KnightsNiActions - Handles all actions in the Knights Who Say Ni scene
 */
public record KnightsNiActions(Model MODEL) {
    public KnightsNiActions(Model MODEL) {
        this.MODEL = MODEL;
    }

    public ActionResult handleAction(String verb, String noun) {
        int progress = MODEL.getSceneProgress("KNIGHTS_NI");

        // Progress 0: Introduce yourself (triggers "it")
        if (progress == 0) {
            if (verb.equals("introduce") || verb.equals("talk")) {
                MODEL.incrementSceneProgress("KNIGHTS_NI");
                String response = "Don't say that word!";
                String narration = "The Knights scream in agony as you accidentally say 'it'.\n\n" +
                        "I cannot tell you what word. Suffice to say, that is one of the words " +
                        "that the Knights Who Say Ni cannot hear.";
                return new ActionResult(true, response, narration, MODEL.getCurrentHint());
            }
            return new ActionResult(false, "Try introducing yourself.");
        }

        // Progress 1: Knights demand shrubbery
        if (progress == 1) {
            if (verb.equals("talk")) {
                MODEL.incrementSceneProgress("KNIGHTS_NI");
                String response = "NI! NI! NI! NI! NI! NI! NI! NI! NI! NI! NI! NI! NI! NI! NI! NI! " +
                        "NI! NI! NI! NI! NI! NI! NI! NI! NI! NI! NI! NI! NI! NI!";
                String narration = "You and your entourage crumble before the ferocious Knights Who Say Ni.";
                String message = "We shall say 'Ni' again to you, if you do not appease us. " +
                        "We want a shrubbery! One that looks nice, and not too expensive. NOW GO!";

                return new ActionResult(true, response, narration + "\n\n" + message, MODEL.getCurrentHint());
            }
            return new ActionResult(false, "Try talking to them.");
        }

        // Progress 2: Get the shrubbery
        if (progress == 2) {
            if (verb.equals("talk") && noun.equals("shrubbery")) {
                MODEL.setHasShrubbery(true);
                MODEL.incrementSceneProgress("KNIGHTS_NI");

                String narration = "After a perilous journey, you find Roger the Shrubber. " +
                        "He arranges, designs, and sells shrubberies. " +
                        "You manage to get a shrub back to the Knights Who Say Ni.";
                String response = "I like this laurel especially. Although, there's one small problem. " +
                        "We're now no longer the Knights Who Say Ni. " +
                        "We're now the Knights Who Say 'Ekke Ekke Ekke Ptang Zoo Boing Arouza.' " +
                        "Therefore, we must give you a test. " +
                        "You must cut down this tree with a herring!";

                return new ActionResult(true, response, narration, MODEL.getCurrentHint());
            }
            return new ActionResult(false, "You need to find a shrubbery!");
        }

        // Progress 3: Cut down tree with herring (impossible task)
        if (progress == 3) {
            if (verb.equals("cut") && noun.equals("tree")) {
                String narration = "You attempt to cut down a tree with a herring. " +
                        "You realize this is but a futile task.";
                String response = "Dear Knights Who Recently Say Ni. That is an impossible request. " +
                        "Cut down a tree with a herring? It can't be done.";
                String ending = "The Knights Who Recently Say Ni acknowledge your wisdom. " +
                        "They let you pass, and you continue on your quest for the Holy Grail.\n\n" +
                        "Your adventure continues...";

                MODEL.incrementSceneProgress("KNIGHTS_NI");
                return new ActionResult(true, response, narration + "\n\n" + ending,
                        "Congratulations! You've completed this chapter of your quest!");
            }
            return new ActionResult(false, "They want you to cut down the tree!");
        }

        if (progress >= 4) {
            return new ActionResult(true, "Your quest continues beyond this tale...",
                    "", "Thank you for playing!");
        }

        return new ActionResult(false, "That action doesn't make sense right now.");
    }
}