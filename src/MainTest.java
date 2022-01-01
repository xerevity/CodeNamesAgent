import distance.*;
import game.board.*;
import game.play.*;
import game.players.*;


public class MainTest {
    public static void main(String[] args) throws Exception {
        ColorCounts cc = new ColorCounts(5,5,5,5,5);
        Board board = new BoardRandom("data/tablara_valo_szavak.txt", cc);

        board.printForGuessers();
        board.printForSpymasters();

        board = new BoardFromFile(50, "data/generalt_szavak.csv", "data/generalt_szinek.csv");
        board.printForGuessers();
        board.printForSpymasters();
        BoardDistance bd = new BoardDistanceFromFile("data/inverse_PMI_matrix.csv", board.getWords());

        Spymaster master = new SpymasterAgent(bd, board, 2, "scoreRatio");
        Clue clue1 = master.giveClue(2);
        Clue clue2 = master.giveClue(3);
        Clue clue3 = master.giveClue(-1);

        Guesser guesser = new GuesserAgent(bd, board);
        guesser.guess(clue1);
        guesser.guess(clue2);
        guesser.guess(clue3);

        Play coop = new PlayCooperative(board, bd);
        coop.play();

        Play eval = new PlayForSpymasterEvaluation(100,
                "data/generalt_szavak.csv",
                "data/generalt_szinek.csv",
                "data/generalt_utalasok.csv");
        eval.play();

    }
}
