import distance.BoardDistance;
import game.board.Board;
import game.board.BoardFromFile;
import game.board.BoardRandom;
import game.board.ColorCounts;
import game.players.Spymaster;
import game.players.SpymasterAgent;

import java.io.IOException;

public class MainTest {
    public static void main(String[] args) throws IOException {
        ColorCounts cc = new ColorCounts(5,5,5,5,5);
        Board board = new BoardRandom("data/tablara_valo_szavak.txt", cc);

        board.printForGuessers();
        board.printForSpymasters();

        board = new BoardFromFile(50, "data/generalt_szavak.csv", "data/generalt_szinek.csv");
        board.printForGuessers();
        board.printForSpymasters();

        Spymaster master = new SpymasterAgent(new BoardDistance("data/MSzNy_graf_matrix.csv"), board, 3);
        master.giveClue(2);
        master.giveClue(3);
        master.giveClue(-1);
    }
}
