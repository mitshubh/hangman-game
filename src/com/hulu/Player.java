package com.hulu;

/**
 * Created by mitshubh on 11/6/16.
 */

public class Player {

    public String gameStatus;
    public String token;
    public int remaining;
    public String state;

    public String toString(){
        return "'status': " + gameStatus + ", 'token': " + token + ", 'remaining_guesses': " + remaining + ", 'state': " + state;
    }
}
