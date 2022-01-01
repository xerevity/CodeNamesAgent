package game.players;

import distance.BoardDistance;
import game.board.Board;
import game.board.Card;

import java.util.*;

public class SpymasterAgent extends Spymaster{
    private final Board board;
    private final Map<String, Map<String, Double>> distances;
    private final int team;
    private final String scoreFunction;
    private final List<String> used;

    public SpymasterAgent(BoardDistance bd, Board board, int team, String scoreFunction) {
        this.board = board;
        this.distances = bd.getBoardDistances();
        this.team = team;
        this.scoreFunction = scoreFunction;
        this.used = new ArrayList<>();
    }

    /**
     * map a score and a number to each possible word in the vocab,
     * based on the differences of distance from the clue word,
     * @param num number of targeted board words, -1 if unspecified
     * @return a map with each word to [score, matching number]
     */
    private Map<String, Double[]> scoreDifference(int num){
        Map<String, Double[]> wordScores = new HashMap<>();

        for (Map.Entry<String, Map<String, Double>> entry : distances.entrySet()) {
            String word = entry.getKey();
            Map<String, Double> map = entry.getValue();

            // for each possible clue word, find the minimal distance of bad words,
            // and the distances of good words closer than the closest bad word
            List<Double> goodWordsDistances = new LinkedList<>();
            Double minBadDistance = Double.POSITIVE_INFINITY;

            for (Card card : board.getCards()){
                if (!card.isRevealed()) {
                    String cWord = card.getWord();
                    if (card.getColor() == team) {
                        goodWordsDistances.add((map.get(cWord) == null) ? Double.POSITIVE_INFINITY : map.get(cWord));
                    }
                    else if (map.get(cWord) != null && minBadDistance > map.get(cWord))
                        minBadDistance = map.get(cWord);
                }
            }

            Double finalMinBadDistance = minBadDistance;
            double score = 0.0;
            int number;

            if (num == -1){
                // score function:
                // the sum of (minBadDistance - goodDistance) differences,
                // where minBadDistance is the minimum distance of bad words from the possible clue word,
                // and goodDistance are distances of all good words closer than minBadDistance

                goodWordsDistances.removeIf(dist -> dist > finalMinBadDistance - 0.02);
                number = goodWordsDistances.size();
                for (Double dist : goodWordsDistances)
                    score += java.lang.Math.min(minBadDistance - dist, 0.3);
            } else {
                // score function:
                // the minimum of (minBadDistance - goodDistance) differences,
                // where minBadDistance is the minimum distance of bad words from the possible clue word,
                // and goodDistance is the distance of the @param num closest good word

                Collections.sort(goodWordsDistances);
                number = Math.min(num, goodWordsDistances.size());
                score = number == 0 ? Double.NEGATIVE_INFINITY : minBadDistance - goodWordsDistances.get(number-1);
            }

            wordScores.put(word, new Double[] {score, (double) number});
        }

        return wordScores;
    }

    /**
     * map a score and a number to each possible word in the vocab,
     * based on the ratio of distance from the clue word,
     * @param num number of targeted board words, -1 if unspecified
     * @return a map with each word to [score, matching number]
     */
    private Map<String, Double[]> scoreRatio(int num){
        Map<String, Double[]> wordScores = new HashMap<>();

        for (Map.Entry<String, Map<String, Double>> entry : distances.entrySet()) {
            String word = entry.getKey();
            Map<String, Double> map = entry.getValue();

            // for each possible clue word, find the minimal distance of bad words,
            // and the distances of good words closer than the closest bad word
            List<Double> goodWordsDistances = new LinkedList<>();
            Double minBadDistance = Double.POSITIVE_INFINITY;

            for (Card card : board.getCards()){
                if (!card.isRevealed()) {
                    String cWord = card.getWord();
                    if (card.getColor() == team) {
                        goodWordsDistances.add((map.get(cWord) == null) ? Double.POSITIVE_INFINITY : map.get(cWord));
                    }
                    else if (map.get(cWord) != null && minBadDistance > map.get(cWord))
                        minBadDistance = map.get(cWord);
                }
            }

            Double finalMinBadDistance = minBadDistance;
            double score = 0.0;
            int number;

            if (num == -1){
                // score function:
                // the sum of (minBadDistance / goodDistance) ratios,
                // where minBadDistance is the minimum distance of bad words from the possible clue word,
                // and goodDistance are distances of all good words closer than minBadDistance

                goodWordsDistances.removeIf(dist -> dist > finalMinBadDistance - 0.02);
                number = goodWordsDistances.size();
                for (Double dist : goodWordsDistances)
                    score += java.lang.Math.min(minBadDistance / dist, 5);
            } else {
                // score function:
                // the minimum of (minBadDistance - goodDistance) differences,
                // where minBadDistance is the minimum distance of bad words from the possible clue word,
                // and goodDistance is the distance of the @param num closest good word

                Collections.sort(goodWordsDistances);
                number = Math.min(num, goodWordsDistances.size());
                score = number == 0 ? Double.NEGATIVE_INFINITY : minBadDistance / goodWordsDistances.get(number-1);
            }

            wordScores.put(word, new Double[] {score, (double) number});
        }

        return wordScores;
    }

    @Override
    public Clue giveClue(int num) {
        // remove board words from possible clue words
        for (String word : board.getWords()) distances.remove(word);

        Map<String, Double[]> clueMap = scoreFunction.equals("scoreRatio") ? scoreRatio(num) : scoreDifference(num);

        // return the word with the best score and the matching number
        String bestWord = null;
        double maxScore = 0;
        for (Map.Entry<String, Double[]> entry : clueMap.entrySet())
            if (entry.getValue()[0] > maxScore && !(this.used.contains(entry.getKey()))){
                maxScore = entry.getValue()[0];
                bestWord = entry.getKey();
            }

        int clueNum = clueMap.get(bestWord)[1].intValue();
        System.out.println("The spymaster's message is " + bestWord + ", " + clueNum);
        this.used.add(bestWord);
        return new Clue(bestWord, clueNum);
    }
}
