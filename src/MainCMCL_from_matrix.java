import distance.*;
import game.board.*;
import game.play.*;
import java.util.Scanner;

public class MainCMCL_from_matrix {
    public static void main(String[] args) throws Exception {

        String matrixFile;
        String scoreFunction;
        int clueNum;

        try {
            matrixFile = args[0];
            scoreFunction = args[1];
            clueNum = java.lang.Integer.parseInt(args[2]);
        } catch (ArrayIndexOutOfBoundsException e) {
            System.err.println("Required arguments are: [distance matrix file] [name of scoring function] [number of target words]");
            throw new Exception("Some of the arguments are missing!");
        }


        DistanceMatrix dm = new DistanceMatrix(matrixFile);
        Board board = new BoardRandom(
                "data/english/all_board_words_en.txt",
                new ColorCounts(10, 10, 0, 0, 0)
        );
        BoardDistance bd = new BoardDistanceFromMatrix(dm, dm.getTargetWords());

        PlayCooperative play = new PlayCooperative(
                board, bd, scoreFunction, clueNum
        );

        play.play();
        String option = "";
        Scanner sc = new Scanner(System.in);

        while (true) {
            while (!option.equals("Y") && !option.equals("N")) {
                System.out.println("Play again?");
                System.out.println("Y/N");
                option = sc.nextLine();
            }
            if (option.equals("N")) break;

            option = "";
            play.play();
        }


    }
}
