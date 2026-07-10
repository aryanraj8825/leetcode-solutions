class Solution {
    public int[] pathExistenceQueries(int n, int[] nums, int maxDiff, int[][] queries) {
        int[][] arr = new int[n][2];
        for (int i = 0; i < n; i++) {
            arr[i][0] = nums[i];
            arr[i][1] = i;
        }

        java.util.Arrays.sort(arr, (a, b) -> a[0] - b[0]);

        int[] pos = new int[n];
        int[] comp = new int[n];
        int cid = 0;

        for (int i = 0; i < n; i++) {
            pos[arr[i][1]] = i;
            if (i > 0 && arr[i][0] - arr[i - 1][0] > maxDiff) cid++;
            comp[i] = cid;
        }

        int[] next = new int[n];
        int r = 0;
        for (int i = 0; i < n; i++) {
            while (r + 1 < n && arr[r + 1][0] - arr[i][0] <= maxDiff) r++;
            next[i] = r;
        }

        int LOG = 18;
        int[][] up = new int[LOG][n];
        up[0] = next;

        for (int k = 1; k < LOG; k++) {
            int[] prev = up[k - 1];
            int[] cur = up[k];
            for (int i = 0; i < n; i++) {
                cur[i] = prev[prev[i]];
            }
        }

        int[] ans = new int[queries.length];

        for (int i = 0; i < queries.length; i++) {
            int u = pos[queries[i][0]];
            int v = pos[queries[i][1]];

            if (u == v) {
                ans[i] = 0;
                continue;
            }

            if (comp[u] != comp[v]) {
                ans[i] = -1;
                continue;
            }

            int l = Math.min(u, v);
            int rr = Math.max(u, v);

            int cur = l;
            int steps = 0;

            for (int k = LOG - 1; k >= 0; k--) {
                int nxt = up[k][cur];
                if (nxt < rr) {
                    cur = nxt;
                    steps += 1 << k;
                }
            }

            ans[i] = steps + 1;
        }

        return ans;
    }
}