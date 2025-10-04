import java.util.HashMap;
import java.util.Map;

/**
 * Model class that stores game state and data
 * Manages scenes, inventory, and game progression
 */
public class Model {
    private String currentScene;
    private final Map<String, Integer> sceneProgress;
    private boolean playerDead;
    private int blackKnightLimbs;
    private boolean hasShrubbery;

    public Model() {
        this.currentScene = "DENNIS";
        this.sceneProgress = new HashMap<>();
        this.playerDead = false;
        this.blackKnightLimbs = 4;
        this.hasShrubbery = false;
        initializeSceneProgress();
    }

    private void initializeSceneProgress() {
        sceneProgress.put("DENNIS", 0);
        sceneProgress.put("BLACK_KNIGHT", 0);
        sceneProgress.put("FRENCH_CASTLE", 0);
        sceneProgress.put("KNIGHTS_NI", 0);
    }

    // Getters and Setters
    public String getCurrentScene() {
        return currentScene;
    }

    public void setCurrentScene(String scene) {
        this.currentScene = scene;
    }

    public int getSceneProgress(String scene) {
        return sceneProgress.getOrDefault(scene, 0);
    }

    public void incrementSceneProgress(String scene) {
        int current = sceneProgress.getOrDefault(scene, 0);
        sceneProgress.put(scene, current + 1);
    }

    public void setSceneProgress(String scene, int progress) {
        sceneProgress.put(scene, progress);
    }

    public boolean isPlayerDead() {
        return playerDead;
    }

    public void setPlayerDead(boolean dead) {
        this.playerDead = dead;
    }

    public int getBlackKnightLimbs() {
        return blackKnightLimbs;
    }

    public void removeBlackKnightLimb() {
        if (blackKnightLimbs > 0) {
            blackKnightLimbs--;
        }
    }

    public boolean hasShrubbery() {
        return hasShrubbery;
    }

    public void setHasShrubbery(boolean has) {
        this.hasShrubbery = has;
    }

    /**
     * Returns narration for the current scene state
     */
    public String getCurrentSceneNarration() {
        return switch (currentScene) {
            case "DENNIS" -> "Ahead, you see a silhouette of a hunching person. " +
                    "At this point, you see a castle ahead and it should be wise to converse " +
                    "with this person for more information before approaching the castle.";
            case "BLACK_KNIGHT" -> "You saw a knight, donned in heavy black armor, gallantly swing his sword " +
                    "to behead a defeated fighter.";
            case "FRENCH_CASTLE" -> "[French accent] You have arrived at the castle of my master, Guy de Loimbard.";
            case "KNIGHTS_NI" -> "You came across the Knights Who Say Ni, who are the keepers of the sacred words. " +
                    "Those who saw them rarely live to tell the tale. The Knights Who Say Ni demand a sacrifice.";
            default -> "";
        };
    }

    /**
     * Returns hint for current scene state
     */
    public String getCurrentHint() {
        switch (currentScene) {
            case "DENNIS":
                int dennisProgress = getSceneProgress("DENNIS");
                if (dennisProgress == 0) {
                    return "You should try and call out to that person. They seem to be an old peasant.";
                } else if (dennisProgress == 1) {
                    return "You should ask about the castle.";
                } else if (dennisProgress == 2) {
                    return "You should state your authority.";
                } else if (dennisProgress == 3) {
                    return "You should coerce the information out of them, or walk away.";
                }
                break;
            case "BLACK_KNIGHT":
                int knightProgress = getSceneProgress("BLACK_KNIGHT");
                if (knightProgress == 0) {
                    return "You should try to talk to him.";
                } else if (knightProgress == 1) {
                    return "You should try to pass him.";
                } else if (blackKnightLimbs > 0) {
                    return "You should try to defeat him.";
                } else {
                    return "You should try to pass him.";
                }
            case "FRENCH_CASTLE":
                int frenchProgress = getSceneProgress("FRENCH_CASTLE");
                if (frenchProgress == 0) {
                    return "You should try asking for food and shelter and/or introduce the task of the Holy Grail.";
                } else if (frenchProgress == 1) {
                    return "You should ask to come into the castle and take a look of their Grail.";
                } else if (frenchProgress == 2) {
                    return "You should threaten to take their castle by force.";
                } else if (frenchProgress >= 3 && frenchProgress < 6) {
                    return "You should run away and regroup with a genius plan.";
                } else {
                    return "You should let Sir Bedivere come up with a genius plan.";
                }
            case "KNIGHTS_NI":
                int niProgress = getSceneProgress("KNIGHTS_NI");
                if (niProgress == 0) {
                    return "You should try and introduce yourself.";
                } else if (niProgress < 3) {
                    return "Find them a shrubbery!";
                } else if (niProgress == 3) {
                    return "You should contest the Knights Who Recently Say Ni.";
                }
                break;
        }
        return "What will you do next?";
    }
}