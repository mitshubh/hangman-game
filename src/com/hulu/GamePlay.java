package com.hulu;

/**
 * Created by mitshubh on 11/6/16.
 */
import java.io.BufferedReader;
import java.io.IOException;
import java.util.regex.*;
import java.net.URL;
import java.util.regex.Matcher;
import java.io.InputStreamReader;

// Hit server to get all the parameters associated with a particular token
public class GamePlay {

    public static Player playGame(String s){
        Player player = new Player();

        try{
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(new URL("http://gallows.hulu.com/play?code=mitshubh@cs.ucla.edu" + s).openStream()));
            String playDetails = bufferedReader.readLine();

            Pattern pattern = null;
            Matcher statusM, tokenM, remainingM, stateM;

            // game status
            pattern = Pattern.compile("(ALIVE|DEAD|FREE)");
            statusM = pattern.matcher(playDetails);
            // token
            pattern = Pattern.compile("(\\d+)");
            tokenM = pattern.matcher(playDetails);
            // remaining guesses
            pattern = Pattern.compile("(\\d)(,)");
            remainingM = pattern.matcher(playDetails);
            // state
            pattern = Pattern.compile("([A-Z_'\\s]+)(\"})");
            stateM = pattern.matcher(playDetails);

            if(statusM.find() && tokenM.find() && remainingM.find() && stateM.find()){
                player.gameStatus = statusM.group();
                player.token = tokenM.group();
                player.remaining = Integer.parseInt(remainingM.group(1));
                player.state = stateM.group(1);
            }
        } catch (IOException e){
            e.printStackTrace();
        }
        return player;
    }

    // begin game -- empty string returs the token associated with a new game
    public static Player startGame(){
        return playGame("");
    }

    // keep on guessing until the player wins or loses
    public static Player guessChar(Player player, char guess) throws InterruptedException {
        //Thread.sleep(1000);   // To restrict bombardment of server with concurrent requests, slow down the system
        return playGame(String.format("&token=%s&guess=%s", player.token, guess));
    }
}
