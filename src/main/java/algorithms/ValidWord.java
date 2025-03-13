package algorithms;

import java.util.Arrays;
import java.util.List;

/*
Valid Word
A word is considered valid if:

It contains a minimum of 3 characters.
It contains only digits (0-9), and English letters (uppercase and lowercase).
It includes at least one vowel.
It includes at least one consonant.
You are given a string word.

Return true if word is valid, otherwise, return false.

Notes:

'a', 'e', 'i', 'o', 'u', and their uppercases are vowels.
A consonant is an English letter that is not a vowel.
 */
public class ValidWord {
    public boolean isValid(String word) {
        List<Character> vowels = Arrays.asList('a', 'e', 'i', 'o', 'u');

        if (word.length() < 3) return false;

        boolean hasVowel = false;
        boolean hasConsonant = false;
        String lowerCaseWord = word.toLowerCase();
        for (int i = 0; i < lowerCaseWord.length(); i++) {
            char symbol = lowerCaseWord.charAt(i);
            if (!Character.isLetterOrDigit(symbol)) {
                return false;
            }
            if (!hasVowel && vowels.contains(symbol)) {
                hasVowel = true;
            }
            if (!hasConsonant && Character.isLetter(symbol) && !vowels.contains(symbol)) {
                hasConsonant = true;
            }
        }

        return hasVowel && hasConsonant;
    }
}
