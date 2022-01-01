package game.players;

import distance.*;
import game.board.Board;
import game.board.BoardFromFile;

import java.io.*;

public class MainClueGenerator {
    public static void main(String[] args) throws Exception {

        BufferedWriter writer = new BufferedWriter(new FileWriter("generated.csv"));

        for (int i=0; i<100; i++){
            writer.write(i+"");

            Board board = new BoardFromFile(i, "data/generalt_szavak.csv", "data/generalt_szinek.csv");
            BoardDistance bd = new BoardDistanceFromFile("data/inverse_PMI_matrix.csv", board.getWords());

            int team = 2;
            Spymaster master1 = new SpymasterAgent(bd, board, team, "scoreRatio");

            Clue clue10 = master1.giveClue(-1);
            Clue clue12 = master1.giveClue(2);
            Clue clue13 = master1.giveClue(3);
            writer.write("," + clue10.word + "," + clue10.number + ","
                    + clue12.word + "," + clue12.number + "," + clue13.word + "," + clue13.number + "\n"
                    );
            writer.flush();
        }

        writer.close();
    }
}

