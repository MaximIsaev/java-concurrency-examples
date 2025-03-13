package algorithms;

/*
Longest Common Prefix
Write a function to find the longest common prefix string amongst an array of strings.
If there is no common prefix, return an empty string "".

Input: strs = ["flower","flow","flight"]
Output: "fl"
 */
public class LongestCommonPrefix {

    /* O(n * m) */
    public String longestCommonPrefix(String[] strs) {
        if (strs.length == 0) return "";
        if (strs.length == 1) return strs[0];

        StringBuilder prefix = new StringBuilder();
        String alpha = strs[0];
        for (int i = 0; i < alpha.length(); i++) {
            char letter = alpha.charAt(i);
            boolean same = true;
            for (int j = 1; j < strs.length; j++) {
                if (i > (strs[j].length() - 1) || strs[j].charAt(i) != letter) {
                    same = false;
                    break;
                }
            }

            if (same) {
                prefix.append(letter);
            } else {
                return prefix.toString();
            }
        }

        return prefix.toString();
    }
}
