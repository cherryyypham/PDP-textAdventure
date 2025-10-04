import java.util.LinkedHashMap;
import java.util.ArrayList;
import java.util.List;

/**
 * InputParser - Handles parsing of user input
 * Detects verbs and nouns from input strings
 */
public class InputParser {
    private final LinkedHashMap<String, String> VERB_SYNONYMS;
    private final LinkedHashMap<String, String> NOUN_SYNONYMS;
    private final List<String> WORD_ORDER;

    public InputParser() {
        this.VERB_SYNONYMS = new LinkedHashMap<>();
        this.NOUN_SYNONYMS = new LinkedHashMap<>();
        this.WORD_ORDER = new ArrayList<>();
        initVerbs();
        initNouns();
    }

    /**
     * Initialize verb synonyms
     */
    private void initVerbs() {
        // Call/Talk verbs
        putVerbSyn("call", "call");
        putVerbSyn("shout", "call");
        putVerbSyn("yell", "call");
        putVerbSyn("talk", "talk");
        putVerbSyn("speak", "talk");
        putVerbSyn("converse", "talk");

        // Ask verbs
        putVerbSyn("ask", "ask");
        putVerbSyn("question", "ask");
        putVerbSyn("inquire", "ask");

        // Introduce verbs
        putVerbSyn("introduce", "introduce");
        putVerbSyn("present", "introduce");
        putVerbSyn("identify", "introduce");
        putVerbSyn("state", "introduce");

        // Threaten verbs
        putVerbSyn("threaten", "threaten");
        putVerbSyn("intimidate", "threaten");
        putVerbSyn("coerce", "threaten");

        // Fight verbs
        putVerbSyn("fight", "fight");
        putVerbSyn("attack", "fight");
        putVerbSyn("strike", "fight");
        putVerbSyn("hit", "fight");

        // Pass verbs
        putVerbSyn("pass", "pass");
        putVerbSyn("cross", "pass");
        putVerbSyn("walk", "pass");
        putVerbSyn("go", "pass");

        // Run verbs
        putVerbSyn("run", "run");
        putVerbSyn("flee", "run");
        putVerbSyn("escape", "run");
        putVerbSyn("retreat", "run");

        // Plan verbs
        putVerbSyn("plan", "plan");
        putVerbSyn("strategize", "plan");
        putVerbSyn("scheme", "plan");

        // Cut verbs
        putVerbSyn("cut", "cut");
        putVerbSyn("chop", "cut");
        putVerbSyn("slice", "cut");
    }

    /**
     * Initialize noun synonyms
     */
    private void initNouns() {
        // People
        putNounSyn("man", "man");
        putNounSyn("woman", "woman");
        putNounSyn("peasant", "peasant");
        putNounSyn("person", "peasant");

        // Castle
        putNounSyn("castle", "castle");
        putNounSyn("fort", "castle");
        putNounSyn("fortress", "castle");

        // Knight
        putNounSyn("knight", "knight");
        putNounSyn("warrior", "knight");
        putNounSyn("fighter", "knight");

        // Bridge
        putNounSyn("bridge", "bridge");

        // French
        putNounSyn("french", "french");
        putNounSyn("guard", "french");
        putNounSyn("frenchman", "french");

        // Grail
        putNounSyn("grail", "grail");

        // Knights Ni
        putNounSyn("ni", "ni");
        putNounSyn("knights", "ni");

        // Shrubbery
        putNounSyn("shrubbery", "shrubbery");
        putNounSyn("shrub", "shrubbery");
        putNounSyn("bush", "shrubbery");

        // Tree
        putNounSyn("tree", "tree");
        putNounSyn("plant", "tree");
    }

    /**
     * Add verb synonym to the map
     */
    private void putVerbSyn(String synonym, String official) {
        VERB_SYNONYMS.put(synonym.toLowerCase(), official);
        if (!WORD_ORDER.contains(synonym.toLowerCase())) {
            WORD_ORDER.add(synonym.toLowerCase());
        }
    }

    /**
     * Add noun synonym to the map
     */
    private void putNounSyn(String synonym, String official) {
        NOUN_SYNONYMS.put(synonym.toLowerCase(), official);
        if (!WORD_ORDER.contains(synonym.toLowerCase())) {
            WORD_ORDER.add(synonym.toLowerCase());
        }
    }

    /**
     * Process input string and extract verb and noun
     * Returns String[2] where [0] is verb and [1] is noun
     * Returns null if parsing fails
     */
    public String[] processInput(String inputRaw) {
        if (inputRaw == null || inputRaw.trim().isEmpty()) {
            return null;
        }

        // Clean input
        String cleaned = cleanInput(inputRaw);

        // Split into words manually (no regex split)
        List<String> words = splitIntoWords(cleaned);

        if (words.isEmpty()) {
            return null;
        }

        // Find verb and noun
        String verb = null;
        String noun = null;

        for (String word : words) {
            if (verb == null && VERB_SYNONYMS.containsKey(word)) {
                verb = VERB_SYNONYMS.get(word);
            } else if (noun == null && NOUN_SYNONYMS.containsKey(word)) {
                noun = NOUN_SYNONYMS.get(word);
            }

            // Found a verb and a noun!
            if (verb != null && noun != null) {
                break;
            }
        }

        // If only verb found, some commands don't need nouns
        if (verb != null && noun == null) {
            if (verb.equals("introduce") || verb.equals("run") ||
                    verb.equals("plan") || verb.equals("fight")) {
                noun = "self";
            }
        }

        if (verb != null && noun != null) {
            return new String[]{verb, noun};
        }

        return null;
    }

    /**
     * Clean input - remove punctuation and extra spaces
     * Custom implementation without regex
     */
    private String cleanInput(String input) {
        StringBuilder cleaned = new StringBuilder();

        for (int i = 0; i < input.length(); i++) {
            char c = input.charAt(i);

            // Keep letters, numbers, and spaces
            if (isAlphanumeric(c) || c == ' ') {
                cleaned.append(c);
            } else if (c == '!' || c == '?' || c == '.') {
                // Replace punctuation with space
                cleaned.append(' ');
            }
        }

        return cleaned.toString().toLowerCase();
    }

    /**
     * Check if character is alphanumeric
     */
    private boolean isAlphanumeric(char c) {
        return (c >= 'a' && c <= 'z') ||
                (c >= 'A' && c <= 'Z') ||
                (c >= '0' && c <= '9');
    }

    /**
     * Split string into words manually
     */
    private List<String> splitIntoWords(String text) {
        List<String> words = new ArrayList<>();
        StringBuilder currentWord = new StringBuilder();

        for (int i = 0; i < text.length(); i++) {
            char c = text.charAt(i);

            if (c == ' ') {
                // End of word
                if (!currentWord.isEmpty()) {
                    words.add(currentWord.toString());
                    currentWord = new StringBuilder();
                }
            } else {
                currentWord.append(c);
            }
        }

        // Add last word if exists
        if (!currentWord.isEmpty()) {
            words.add(currentWord.toString());
        }

        return words;
    }
}