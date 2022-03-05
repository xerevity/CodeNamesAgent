import game.play.PlayForSpymasterEvaluation;

import java.util.Scanner;

public class MainCMCL {
    public static void main(String[] args) throws Exception {
        PlayForSpymasterEvaluation play = new PlayForSpymasterEvaluation(100,
                "data/english/boards_en.csv",
                "data/english/board_colors.csv",
                "data/english/clues.csv"
        );

        play.play();
        String option = "";
        Scanner sc = new Scanner(System.in);

        while (true){
            while (!option.equals("Y") && !option.equals("N")){
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
