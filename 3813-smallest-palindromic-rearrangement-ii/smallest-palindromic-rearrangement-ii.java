class Solution {

    private static final long LIMIT = 1_000_001L;

    public String smallestPalindrome(String s, int k) {
        int[] freq = new int[26];

        for (char c : s.toCharArray()) {
            freq[c - 'a']++;
        }

        int[] half = new int[26];
        int halfLen = 0;
        char middle = 0;

        for (int i = 0; i < 26; i++) {
            half[i] = freq[i] / 2;
            halfLen += half[i];
            if ((freq[i] & 1) == 1) {
                middle = (char) ('a' + i);
            }
        }

        if (countWays(half) < k) {
            return "";
        }

        StringBuilder left = new StringBuilder();

        for (int pos = 0; pos < halfLen; pos++) {

            for (int ch = 0; ch < 26; ch++) {

                if (half[ch] == 0) continue;

                half[ch]--;

                long ways = countWays(half);

                if (ways >= k) {
                    left.append((char) ('a' + ch));
                    break;
                } else {
                    k -= ways;
                    half[ch]++;
                }
            }
        }

        StringBuilder ans = new StringBuilder();
        ans.append(left);

        if (middle != 0) {
            ans.append(middle);
        }

        ans.append(new StringBuilder(left).reverse());

        return ans.toString();
    }

    private long countWays(int[] cnt) {
        int total = 0;
        for (int x : cnt) total += x;

        long res = 1;

        int remaining = total;

        for (int c : cnt) {
            if (c == 0) continue;

            long choose = combination(remaining, c);

            if (res > LIMIT / choose) return LIMIT;

            res *= choose;

            if (res > LIMIT) return LIMIT;

            remaining -= c;
        }

        return res;
    }

    private long combination(int n, int r) {
        if (r < 0 || r > n) return 0;

        r = Math.min(r, n - r);

        long res = 1;

        for (int i = 1; i <= r; i++) {

            long num = n - r + i;
            long den = i;

            long g = gcd(num, den);
            num /= g;
            den /= g;

            g = gcd(res, den);
            res /= g;
            den /= g;

            if (res > LIMIT / num) return LIMIT;

            res *= num;

            if (res > LIMIT) return LIMIT;
        }

        return res;
    }

    private long gcd(long a, long b) {
        while (b != 0) {
            long t = a % b;
            a = b;
            b = t;
        }
        return a;
    }
}