package game.board;

public class Card {
    private final String word;
    private final int color;
    private boolean revealed;

    public Card(String word, int color) {
        this.word = word;
        this.color = color;
        this.revealed = false;
    }

    public String getWord() {
        return word;
    }

    public int getColor() {
        return color;
    }

    public boolean isRevealed() {
        return revealed;
    }

    public void setRevealed(){
        this.revealed = true;
    }

    public String[] markColor() {
        // braces around the word mark color when the board is printed
        return switch (color) {
            case 0 -> new String[]{"!!", "!!"}; // assassin
            case 1 -> new String[]{"((", "))"}; // neutral
            case 2 -> new String[]{"[[", "]]"}; // blue team
            case 3 -> new String[]{"{{", "}}"}; // red team
            case 4 -> new String[]{"//", "//"}; // purple team
            default -> null;
        };
    }

    public String toStringGuessers() {
        // for guessers: when revealed, color is visible
        return revealed ? this.markColor()[0] + word + this.markColor()[1] : word;
    }

    public String toStringSpymasters() {
        // for spymasters: when revealed, word is not visible
        return revealed ?
                this.markColor()[0] + "XXXX" + this.markColor()[1] :
                this.markColor()[0] + word + this.markColor()[1];
    }
}
