package algorithms;

/*
Valid Palindrome
A phrase is a palindrome if, after converting all uppercase letters into lowercase letters and removing all non-alphanumeric characters,
it reads the same forward and backward. Alphanumeric characters include letters and numbers.

Given a string s, return true if it is a palindrome, or false otherwise.

Input: s = "A man, a plan, a canal: Panama"
Output: true
Explanation: "amanaplanacanalpanama" is a palindrome.

"rac a car"
"0P"
 */
public class ValidPalindrome {
    public boolean isPalindrome(String s) {
        if (s.isBlank() || s.trim().length() == 1) return true;

        int left = 0;
        int right = s.length() - 1;

        String upperCase = s.toUpperCase();
        while (left <= right) {
            char leftLetter = upperCase.charAt(left);
            char rightLetter = upperCase.charAt(right);
            if (!Character.isLetterOrDigit(leftLetter) && !Character.isLetterOrDigit(rightLetter)) {
                left++;
                right--;
                continue;
            }
            if (!Character.isLetterOrDigit(leftLetter)) {
                left++;
                continue;
            }
            if (!Character.isLetterOrDigit(rightLetter)) {
                right--;
                continue;
            }
            if (leftLetter != rightLetter) {
                return false;
            }
            left++;
            right--;
        }

        return true;
    }
}
