package com.hulu;

/**
 * Created by mitshubh on 11/6/16.
 */

public class HangmanEntry {

    // Entry Point for the Hangman Program
    public static void main(String[] args) throws InterruptedException {

        float winRate = 0;
        long totalWins = 0;
        long matchesPlayed = 0;

        //Break execution on user hard exit
        while (true) {
            AI ai = new AI();
            Player player = GamePlay.startGame();   // TODO -- Place a check for server unresponsive/down instances
            while (player.gameStatus.equals(GameStatus.ALIVE)) {
                System.out.println(player.state);
                char guessedChar = ai.guess(player.state); // guess the character
                Player updatedPlayer = GamePlay.guessChar(player, guessedChar); // hit server with the new guess and get back the state
                if (!updatedPlayer.gameStatus.equals(GameStatus.DEAD)) {
                    if (player.state.equals(updatedPlayer.state)) {
                        ai.updateSets(guessedChar, false);
                        System.out.println(guessedChar + " is " + false + ", No. of tries left : " + updatedPlayer.remaining);
                    } else {
                        ai.updateSets(guessedChar, true);
                        System.out.println(guessedChar + " is " + true + ", New status: " + updatedPlayer.gameStatus);
                    }
                } else {
                    System.out.println(guessedChar + " is " + false + ", No. of tries left : 0");
                }
                player = updatedPlayer;
            }
            System.out.println("The challenge was : " + player.state);
            if (player.gameStatus.equals(GameStatus.DEAD)) {
                System.out.println("Player Lost! Better luck next time.");
            }
            else if (player.gameStatus.equals(GameStatus.FREE)) {
                System.out.println("Player Won! Hurray!!");
                totalWins++;
            }
            matchesPlayed++;
            winRate=(((float) totalWins/(float) matchesPlayed) * 100);
            System.out.println("========================================================================");
            System.out.println("Total Matches played " + matchesPlayed + " \t Matches Won : " + totalWins + "\tWinRate is: " + winRate + "%");
            System.out.println("========================================================================");
            System.out.println("\n\n");
        }
    }
}

