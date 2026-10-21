
class Solution {
    public int minOperations(String s) {
        int n = s.length();

        boolean sorted = true;
        for (int i = 1; i < n; i++) {
            if (s.charAt(i - 1) > s.charAt(i)) {
                sorted = false;
                break;
            }
        }

        if (sorted) return 0;

        char minChar = 'z';
        char maxChar = 'a';

        for (int i = 0; i < n; i++) {
            char c = s.charAt(i);
            minChar = (char) Math.min(minChar, c);
            maxChar = (char) Math.max(maxChar, c);
        }

        if (s.charAt(0) == minChar ||
            s.charAt(n - 1) == maxChar) {
            return 1;
        }

        if (n < 3) return -1;

        if (s.indexOf(minChar) == n - 1 &&
            s.lastIndexOf(maxChar) == 0) {
            return 3;
        }

        return 2;
    }
}
