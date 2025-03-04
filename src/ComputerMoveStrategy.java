//AI useage

import java.util.Random;

public class ComputerMoveStrategy implements Strategy {
    private static final Random random = new Random();

    @Override
    public String determineMove(String playerLastMove, int[] moveCounts) {
        int strategy = random.nextInt(5);

        switch (strategy) {
            case 1: return mostUsed(moveCounts);
            case 2: return leastUsed(moveCounts);
            case 3: return (playerLastMove != null) ? playerLastMove : getRandomMove();
            case 4: return cheatMove(playerLastMove);
            default: return getRandomMove();
        }
    }

    private static String getRandomMove() {
        String[] moves = {"Rock", "Paper", "Scissors"};
        return moves[random.nextInt(3)];
    }

    private static String mostUsed(int[] moveCounts) {
        int maxIndex = 0;
        for (int i = 1; i < moveCounts.length; i++) {
            if (moveCounts[i] > moveCounts[maxIndex]) maxIndex = i;
        }
        return getWinningMove(maxIndex);
    }

    private static String leastUsed(int[] moveCounts) {
        int minIndex = 0;
        for (int i = 1; i < moveCounts.length; i++) {
            if (moveCounts[i] < moveCounts[minIndex]) minIndex = i;
        }
        return getWinningMove(minIndex);
    }

    private static String getWinningMove(int index) {
        return switch (index) {
            case 0 -> "Paper";
            case 1 -> "Scissors";
            default -> "Rock";
        };
    }

    //cheating computer
    private static String cheatMove(String playerMove) {
        if (random.nextDouble() < 0.10) {
            return getWinningMove(switch (playerMove) {
                case "Rock" -> 0;
                case "Paper" -> 1;
                default -> 2;
            });
        }
        return getRandomMove();
    }
}
