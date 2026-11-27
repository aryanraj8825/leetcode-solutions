class Solution {
    public int maximumSaleItems(int[][] items, int budget) {
        int n = items.length;

        int[] bonus = new int[n];

        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                if (i != j && items[j][0] % items[i][0] == 0) {
                    bonus[i]++;
                }
            }
        }

        int[] dp = new int[budget + 1];

        for (int i = 0; i < n; i++) {
            int price = items[i][1];

            int[] curr = dp.clone();

            for (int b = price; b <= budget; b++) {
                curr[b] = Math.max(
                    curr[b],
                    dp[b - price] + 1 + bonus[i]
                );

                curr[b] = Math.max(
                    curr[b],
                    curr[b - price] + 1
                );
            }

            dp = curr;
        }

        int ans = 0;

        for (int value : dp) {
            ans = Math.max(ans, value);
        }

        return ans;
    }
}
