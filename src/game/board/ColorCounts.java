package game.board;

public class ColorCounts {
    public int all;
    public int neutral;
    public int blue;
    public int red;
    public int purple;
    public int black;

    public ColorCounts() {
        this.neutral = 0;
        this.blue = 0;
        this.red = 0;
        this.purple = 0;
        this.black = 0;
        this.all = 0;
    }

    public ColorCounts(int neutral, int blue, int red, int purple, int black) {
        this.neutral = neutral;
        this.blue = blue;
        this.red = red;
        this.purple = purple;
        this.black = black;
        this.all = neutral + black + blue + red + purple;
    }
}
