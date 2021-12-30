package game.players;

import game.board.Board;

import java.util.List;

public abstract class Spymaster {
    public abstract Clue giveClue(int num) throws Exception;

    /**
     * clear previous prints
     */
    public static void clearScreen() {
        for (int i = 0; i < 15; i++) System.out.println();
        System.out.flush();
    }
}
