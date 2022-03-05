import distance.*;
import game.board.*;
import game.play.*;
import game.players.*;


public class MainTest {
    public static void main(String[] args) throws Exception {

        Board board = new BoardFromFile(50, "data/hungarian/cmcl/boards_hu.csv", "data/hungarian/cmcl/board_colors.csv");
        board.printForGuessers();
        board.printForSpymasters();

        ColorCounts cc = new ColorCounts(10,10,0,0,0);
        board = new BoardRandom("data/hungarian/cmcl/board_words_hu.txt", cc);

        board.printForGuessers();
        board.printForSpymasters();

        BoardDistance bd = new BoardDistanceFromFile("/Volumes/my_passport/codenames/hungarian/huweb_normpmi_graph_sim.csv", board.getWords());

        Guesser guesser = new GuesserAgentSim(bd, board);
        Spymaster master = new SpymasterAgentSim(bd, board, 2, "scoreHarmonic");
        Clue clue1 = master.giveClue(2);
        guesser.guess(clue1);

        Clue clue2 = master.giveClue(3);
        guesser.guess(clue2);

        Clue clue3 = master.giveClue(-1);
        guesser.guess(clue3);

        Play coop = new PlayCooperative(board, bd, "scoreHarmonic", 2);
        coop.play();

        Play eval = new PlayForSpymasterEvaluation(100,
                "data/hungarian/cmcl/boards_hu.csv",
                "data/hungarian/cmcl/board_colors.csv",
                "data/hungarian/cmcl/clues_hu.csv");
        eval.play();

    }
}
