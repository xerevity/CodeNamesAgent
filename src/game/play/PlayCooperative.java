package game.play;

import distance.BoardDistance;
import game.board.Board;
import game.board.ColorCounts;
import game.players.*;

import java.util.List;
import java.util.Scanner;

public class PlayCooperative extends Play{
    private final Spymaster spymaster;
    private final Guesser guesser;
    private final int team;
    private final Board board;
    private int clueNum;

    public PlayCooperative(Board board, BoardDistance bd, String scoreFunction, int clueNum) throws Exception {
        this.board = board;
        this.clueNum = clueNum;

        List<String> vocab = bd.getVocab();

        // check if board is correct
        ColorCounts cc = board.getCounts();
        if (cc.blue == 0 && cc.red == 0 && cc.purple > 0) this.team = 4;
        else if (cc.blue == 0 && cc.red > 0 && cc.purple == 0) this.team = 3;
        else if (cc.blue > 0 && cc.red == 0 && cc.purple == 0) this.team = 2;
        else throw new Exception("The board is not a cooperative game board.");

        // ask user about roles, assign attributes
        Scanner sc = new Scanner(System.in);
        String option = "";

        while (!option.equals("1") && !option.equals("2")){
            System.out.println("Choose one of the following options:");
            System.out.println("1 - I play as a guesser with a spymaster agent.");
            System.out.println("2 - I play as a spymaster with a guesser agent.");
            System.out.println("Please type 1 or 2.");
            option = sc.nextLine();
        }

        this.spymaster = option.equals("1") ?  new SpymasterAgentSim(bd, board, team, scoreFunction) : new SpymasterUser(board, vocab);
        this.guesser = option.equals("1") ? new GuesserUser(board) : new GuesserAgentDist(bd, board);
    }

    public void play() throws Exception {
        do {
            Clue clue = spymaster.giveClue(clueNum);
            guesserRound(clue, guesser, board, team);
        } while (!super.end);
    }
}
