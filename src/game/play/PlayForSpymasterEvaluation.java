package game.play;

import game.board.Board;
import game.board.BoardFromFile;
import game.players.*;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class PlayForSpymasterEvaluation extends Play{
    private final List<Integer> order;
    private int status;
    private final String wordsFile;
    private final String colorsFile;
    private final String cluesFile;

    public PlayForSpymasterEvaluation(int allBoards, String wordsFile, String colorsFile, String cluesFile) {
        List<Integer> order = new ArrayList<>();
        for (int i=0; i < allBoards; i++){
            order.add(i);
        }
        Collections.shuffle(order);

        this.order = order;
        this.status = 0;

        this.wordsFile = wordsFile;
        this.colorsFile = colorsFile;
        this.cluesFile = cluesFile;
    }

    public void play() throws Exception {
        Board board = new BoardFromFile(order.get(status), wordsFile, colorsFile);
        Clue clue = new SpymasterFromFile(cluesFile, order.get(status)).giveClue(-1);
        guesserRound(clue, new GuesserUser(board), board, 2);
        status++;
    }
}
