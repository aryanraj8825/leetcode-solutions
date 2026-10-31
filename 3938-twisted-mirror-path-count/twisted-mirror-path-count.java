class Solution {
    static final int MOD = 1_000_000_007;

    int[][] grid;
    int m, n;

    // memo[r][c][dir]
    // dir = 0 means right, dir = 1 means down
    int[][][] memo;

    int resolve(int r, int c, int dir) {

        // Out of bounds
        if (r >= m || c >= n) {
            return -1;
        }

        // Already calculated
        if (memo[r][c][dir] != -2) {
            return memo[r][c][dir];
        }

        // Empty cell: robot can enter
        if (grid[r][c] == 0) {
            return memo[r][c][dir] = r * n + c;
        }

        // Mirror reflection
        if (dir == 0) {
            // Moving right -> turn down
            return memo[r][c][dir] = resolve(r + 1, c, 1);
        } else {
            // Moving down -> turn right
            return memo[r][c][dir] = resolve(r, c + 1, 0);
        }
    }

    public int uniquePaths(int[][] grid) {

        this.grid = grid;
        m = grid.length;
        n = grid[0].length;

        memo = new int[m][n][2];

        for (int i = 0; i < m; i++) {
            for (int j = 0; j < n; j++) {
                memo[i][j][0] = -2;
                memo[i][j][1] = -2;
            }
        }

        long[][] dp = new long[m][n];

        dp[0][0] = 1;

        for (int i = 0; i < m; i++) {
            for (int j = 0; j < n; j++) {

                if (dp[i][j] == 0) {
                    continue;
                }

                // Try moving right
                int right = resolve(i, j + 1, 0);

                if (right != -1) {
                    int r = right / n;
                    int c = right % n;

                    dp[r][c] = (dp[r][c] + dp[i][j]) % MOD;
                }

                // Try moving down
                int down = resolve(i + 1, j, 1);

                if (down != -1) {
                    int r = down / n;
                    int c = down % n;

                    dp[r][c] = (dp[r][c] + dp[i][j]) % MOD;
                }
            }
        }

        return (int) dp[m - 1][n - 1];
    }
}
