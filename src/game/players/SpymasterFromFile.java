package game.players;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;

public class SpymasterFromFile extends Spymaster{
    private final int boardId;
    private final Map<Integer, List<Clue>> cluesMap;

    public SpymasterFromFile(String cluesFilename, int boardId) throws IOException {
        this.boardId = boardId;

        BufferedReader br = new BufferedReader(new FileReader(cluesFilename));
        br.readLine(); // first line is a header
        String line = br.readLine();
        Map<Integer, List<Clue>> cluesMap = new HashMap<>();

        while(line != null) {
            String[] elements = line.split(";");
            List<Clue> row = new ArrayList<>();

            int id = Integer.parseInt(elements[0]);
            for (int i = 1; i < elements.length; i += 2) {
                row.add(new Clue(elements[i], Integer.parseInt(elements[i + 1])));
            }
            cluesMap.put(id, row);
            line = br.readLine();
        }

        this.cluesMap = cluesMap;
    }

    @Override
    public Clue giveClue(int num) throws Exception {
        Random random = new Random();
        List<Clue> clues = cluesMap.get(boardId);
        if (num == -1) {
            return clues.get(random.nextInt(clues.size()));
        } else {
            List<Clue> good = new ArrayList<>();
            for (Clue clue: clues){
                if (clue.number == num) good.add(clue);
            }
            if (good.isEmpty()) throw new Exception("The requested clue number is not present in the given row of the database.");
            return good.get(random.nextInt(clues.size()));
        }
    }
}
