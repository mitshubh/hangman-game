package com.hulu;

/**
 * Created by mitshubh on 11/6/16.
 */
import java.io.*;
import java.util.*;
import java.util.regex.*;

public class AI {

    private Map<Integer, List<String>> wordList = new HashMap<Integer, List<String>>();
    private Set<Character> correctSet = new HashSet<Character>();
    private Set<Character> incorrectSet = new HashSet<Character>();

    // index all the list of words from SCOWL (http://wordlist.aspell.net/)
    AI() {
        try {
            for (File f : new File("word_list/").listFiles()) {
                BufferedReader bufferedReader = new BufferedReader(new FileReader(f));
                for (String str; (str = bufferedReader.readLine()) != null;) {
                    str = str.trim();
                    if (!wordList.containsKey(str.length())) {
                        wordList.put(str.length(), new ArrayList<String>());
                    }
                    wordList.get(str.length()).add(str);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    // make a guess based on the known & unknown words (input state)
    public char guess(String state) {
        List<String> stateList = new ArrayList<String>(Arrays.asList(state
                .split("[^A-Z_']+")));
        StringBuilder exclude = new StringBuilder();
        for (Iterator<Character> ex = incorrectSet.iterator(); ex.hasNext();) {
            exclude.append(ex.next());
        }
        List<String> possibleWords = new ArrayList<String>();

        int count = 0;
        String guessedWord = null;
        int underscore_counter = 100;
        int minBlankCount = Integer.MAX_VALUE;
        for (String word : stateList) { //guess word with the minimum unknown letters or max known letters
            int blankCount = word.length() - word.replace("_", "").length();
            if (blankCount>0 && blankCount<minBlankCount) {
                minBlankCount=blankCount;
                guessedWord=word;
            }
        }

        //if the length is 1, then most likely letters are 'a' and 'i' -- but there could be instances where the hangman screws with us to check our algo
        if (guessedWord==null) {return 'A';}
        if(guessedWord.length() == 1){
            if ((correctSet.contains('A') || incorrectSet.contains('A')) && (correctSet.contains('I') || incorrectSet.contains('I'))) {
                // both a & i cannot be the guessed
            } else {
                return (!correctSet.contains('A') && !incorrectSet.contains('A')) ? 'A' : 'I';
            }
        }
        // make a guess on the word found
        String word = guessedWord.toLowerCase();
        Pattern regex = Pattern.compile(word.replace(
                "_",
                (exclude.length() > 0) ? String.format("[a-z&&[^%s]]",
                        exclude) : "[a-z]"));
        if (wordList.containsKey(word.length())) {
            for (String guess : wordList.get(word.length())) {
                Matcher matcher = regex.matcher(guess);
                if (matcher.find()) {
                    possibleWords.add(guess); // get all words that match the state
                }
            }
        }
        // count frequency of each character within the possible words
        Map<Character, Integer> charFreq = new HashMap<Character, Integer>();
        for (String possWord : possibleWords) {
            Set<String> chars = new HashSet<String>();
            for (char character : possWord.toCharArray()) {
                if (!chars.contains(character)) {
                    if (!charFreq.containsKey(character)) {
                        charFreq.put(character, 1);
                    } else {
                        charFreq.put(character, charFreq.get(character) + 1);
                    }
                }
            }
        }
        //find the character with highest frequency
        char guessLetter = 'a';
        int freq = 0;
        boolean noChar = true;
        for (char c = 'a'; c <= 'z'; c++) {
            if (!correctSet.contains(Character.toUpperCase(c)) && !incorrectSet.contains(Character.toUpperCase(c))) {
                if (charFreq.get(c) != null && charFreq.get(c) > freq) {
                    guessLetter = c;
                    freq = charFreq.get(c);
                    noChar = false;
                }
            }
        }
        // when none of the characters can be guessed
        if (noChar) {
            for (char c = 'a'; c <= 'z'; ++c) {
                if (!(correctSet.contains(Character.toUpperCase(c)) || incorrectSet.contains(Character.toUpperCase(c)))) {
                    return c;
                }
            }
        }
        return guessLetter;
    }

    // put the guessed characters into appropriate group
    public void updateSets(char guess, boolean success) {
        if (success) {
            correctSet.add(Character.toUpperCase(guess));
        } else {
            incorrectSet.add(Character.toUpperCase(guess));
        }
    }
}

