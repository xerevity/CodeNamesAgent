import distance.BoardDistance;
import distance.BoardDistanceFromFile;
import game.board.Board;
import game.board.BoardFromFile;
import game.players.*;

import java.io.BufferedWriter;
import java.io.FileWriter;

public class MainAgentClueTargets {
    public static void main(String[] args) throws Exception {
        String boardsWordsFile;
        String boardsColorsFile;
        String distancesFile;

        try {
            boardsWordsFile = args[0];
            boardsColorsFile = args[1];
            distancesFile = args[2];
        } catch (ArrayIndexOutOfBoundsException e) {
            System.err.println("Required arguments are: [board words csv] [board colors csv] [distance matrix file]");
            throw new Exception("Some of the arguments are missing!");
        }

        BufferedWriter bw = new BufferedWriter(new FileWriter("clues_generated.csv"));

        for (int i=0; i<100; i++){

            Board board = new BoardFromFile(i, boardsWordsFile, boardsColorsFile);
            board.printForSpymasters();

            BoardDistance bd = new BoardDistanceFromFile(distancesFile, board.getWords());
            Spymaster spymaster1 = new SpymasterAgentSim(bd, board, 2, "scoreKoyyalagunta");
            Spymaster spymaster2 = new SpymasterAgentSim(bd, board, 2, "scoreKoyyalaguntaRestrict");
            Spymaster spymaster3 = new SpymasterAgentSim(bd, board, 2, "scoreHarmonic");
            Spymaster spymaster4 = new SpymasterAgentSim(bd, board, 2, "scoreHarmonicDivide");

            Clue clue12 = spymaster1.giveClue(2);
            Clue clue13 = spymaster1.giveClue(3);
            Clue clue22 = spymaster2.giveClue(2);
            Clue clue23 = spymaster2.giveClue(3);
            Clue clue32 = spymaster3.giveClue(2);
            Clue clue33 = spymaster3.giveClue(3);
            Clue clue42 = spymaster4.giveClue(2);
            Clue clue43 = spymaster4.giveClue(3);

            bw.write(i + "," + "Koyyalagunta," + clue12.word + "," + clue12.number + "\n");
            bw.write(i + "," + "Koyyalagunta," + clue13.word + "," + clue13.number + "\n");
            bw.write(i + "," + "KoyyalaguntaRestrict," + clue22.word + "," + clue22.number + "\n");
            bw.write(i + "," + "KoyyalaguntaRestrict," + clue23.word + "," + clue23.number + "\n");
            bw.write(i + "," + "Harmonic," + clue32.word + "," + clue32.number + "\n");
            bw.write(i + "," + "Harmonic," + clue33.word + "," + clue33.number + "\n");
            bw.write(i + "," + "HarmonicDivide," + clue42.word + "," + clue42.number + "\n");
            bw.write(i + "," + "HarmonicDivide," + clue43.word + "," + clue43.number + "\n");

            bw.flush();

        }

        bw.close();
    }
}
