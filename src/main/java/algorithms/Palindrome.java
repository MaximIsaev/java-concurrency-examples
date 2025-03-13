package algorithms;

import java.util.HashMap;

/*
LONGEST PALINDROME
Given a string s which consists of lowercase or uppercase letters, return the length of the longest palindrome that can be built with those letters.
Letters are case sensitive, for example, "Aa" is not considered a palindrome.
 */
public class Palindrome {
    public int longestPalindrome(String s) {
        if (s.length() == 1) return 1;

        int maxLength = 0;
        HashMap<Character, Integer> letters = new HashMap<>();

        for (int i = 0; i < s.length(); i++) {
            char letter = s.charAt(i);
            if (letters.containsKey(letter)) {
                maxLength += 2;
                letters.remove(letter);
            } else {
                letters.put(letter, 1);
            }
        }
        if (!letters.isEmpty()) {
            maxLength += 1;
        }

        return maxLength;
    }

    public int longestPalindromeV2(String s) {
        if (s.length() == 1) return 1;

        int maxLength = 0;
        HashMap<Character, Integer> letters = new HashMap<>();

        for (int i = 0; i < s.length(); i++) {
            char letter = s.charAt(i);
            if (letters.remove(letter) != null) {
                maxLength += 2;
            } else {
                letters.put(letter, 1);
            }
        }
        if (!letters.isEmpty()) {
            maxLength += 1;
        }

        return maxLength;
    }

    public int longestPalindromeV3(String s) {
        int maxLength = 0;
        HashMap<Character, Integer> letters = new HashMap<>();

        for (int i = 0; i < s.length(); i++) {
            char letter = s.charAt(i);
            if (letters.remove(letter) != null) {
                maxLength += 2;
            } else {
                letters.put(letter, 1);
            }
        }
        if (!letters.isEmpty()) {
            maxLength += 1;
        }

        return maxLength;
    }
}
