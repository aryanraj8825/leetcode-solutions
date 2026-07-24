class Solution {
    public int uniqueXorTriplets(int[] nums) {
        final int MAX = 2048;

        boolean[][] dp = new boolean[4][MAX];
        dp[0][0] = true;

        for (int v : nums) {
            for (int repeat = 0; repeat < 3; repeat++) {

                for (int cnt = 2; cnt >= 0; cnt--) {

                    for (int x = 0; x < MAX; x++) {
                        if (dp[cnt][x]) {
                            dp[cnt + 1][x ^ v] = true;
                        }
                    }
                }
            }
        }

        int ans = 0;
        for (int x = 0; x < MAX; x++) {
            if (dp[3][x]) ans++;
        }

        return ans;
    }
}