package edu.brown.cs.student.main.User;

import java.util.ArrayList;
import java.util.List;

public class UserCreator {



    public String createFirstYear(){
        return null;
    }






    public List<String> convertStringToList(String input) {
        // Check if the input string is not null or empty
        if (input == null || input.isEmpty()) {
            throw new IllegalArgumentException("Input string cannot be null or empty");
        }

        // Split the input string based on commas
        String[] wordsArray = input.split(",");

        // Create a list to store the words
        List<String> wordList = new ArrayList<>();

        // Add each word to the list (trimming leading/trailing spaces)
        for (String word : wordsArray) {
            wordList.add(word.trim());
        }

        return wordList;



    }


}
