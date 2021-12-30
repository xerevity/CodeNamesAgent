import game.play.PlayForSpymasterEvaluation;

import java.util.Scanner;

public class MainMSzNy {
    public static void main(String[] args) throws Exception {
        PlayForSpymasterEvaluation play = new PlayForSpymasterEvaluation(100,
                "data/generalt_szavak.csv",
                "data/generalt_szinek.csv",
                "data/generalt_utalasok.csv"
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
