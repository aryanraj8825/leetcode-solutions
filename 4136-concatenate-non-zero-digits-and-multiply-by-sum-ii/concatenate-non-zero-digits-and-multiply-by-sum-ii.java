class Solution {
    static final int MOD = 1_000_000_007;

    public int[] sumAndMultiply(String s, int[][] queries) {
        int n = s.length();

        int[] cnt = new int[n + 1];
        int total = 0;

        for (int i = 0; i < n; i++) {
            cnt[i + 1] = cnt[i];
            if (s.charAt(i) != '0') {
                cnt[i + 1]++;
                total++;
            }
        }

        int[] digits = new int[total];
        int idx = 0;
        for (int i = 0; i < n; i++) {
            if (s.charAt(i) != '0') {
                digits[idx++] = s.charAt(i) - '0';
            }
        }

        long[] pow10 = new long[total + 1];
        pow10[0] = 1;
        for (int i = 1; i <= total; i++) {
            pow10[i] = (pow10[i - 1] * 10) % MOD;
        }

        long[] prefVal = new long[total + 1];
        long[] prefSum = new long[total + 1];

        for (int i = 0; i < total; i++) {
            prefVal[i + 1] = (prefVal[i] * 10 + digits[i]) % MOD;
            prefSum[i + 1] = prefSum[i] + digits[i];
        }

        int[] ans = new int[queries.length];

        for (int i = 0; i < queries.length; i++) {
            int l = queries[i][0];
            int r = queries[i][1];

            int a = cnt[l];
            int b = cnt[r + 1];
            int len = b - a;

            long x = (prefVal[b] - (prefVal[a] * pow10[len]) % MOD + MOD) % MOD;
            long sum = prefSum[b] - prefSum[a];

            ans[i] = (int) ((x * (sum % MOD)) % MOD);
        }

        return ans;
    }
}