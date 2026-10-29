import java.util.*;

class Solution {
    List<Integer>[] adj;
    int[] tin, tout, depth, parent;
    int[][] up;
    int[] bit, mask;
    int timer, LOG;

    public List<Boolean> palindromePath(int n, int[][] edges, String s, String[] queries) {
        adj = new ArrayList[n];
        for (int i = 0; i < n; i++) adj[i] = new ArrayList<>();

        for (int[] e : edges) {
            adj[e[0]].add(e[1]);
            adj[e[1]].add(e[0]);
        }

        tin = new int[n];
        tout = new int[n];
        depth = new int[n];
        parent = new int[n];
        mask = new int[n];
        timer = 0;

        LOG = 1;
        while ((1 << LOG) <= n) LOG++;
        up = new int[LOG][n];

        bit = new int[n + 2];
        dfs(0, 0, 0);

        for (int j = 1; j < LOG; j++)
            for (int i = 0; i < n; i++)
                up[j][i] = up[j - 1][up[j - 1][i]];

        for (int i = 0; i < n; i++) {
            mask[i] = 1 << (s.charAt(i) - 'a');
            updateBIT(tin[i], mask[i]);
            updateBIT(tout[i] + 1, mask[i]);
        }

        List<Boolean> ans = new ArrayList<>();

        for (String q : queries) {
            String[] p = q.split(" ");
            int u = Integer.parseInt(p[1]);

            if (p[0].equals("update")) {
                int newMask = 1 << (p[2].charAt(0) - 'a');
                int delta = mask[u] ^ newMask;

                updateBIT(tin[u], delta);
                updateBIT(tout[u] + 1, delta);
                mask[u] = newMask;
            } else {
                int v = Integer.parseInt(p[2]);
                int lca = getLCA(u, v);

                int pathMask = queryBIT(tin[u]) ^ queryBIT(tin[v])
                             ^ mask[lca];

                ans.add((pathMask & (pathMask - 1)) == 0);
            }
        }

        return ans;
    }

    void dfs(int u, int p, int d) {
        parent[u] = p;
        depth[u] = d;
        up[0][u] = p;
        tin[u] = ++timer;

        for (int v : adj[u]) {
            if (v != p) dfs(v, u, d + 1);
        }

        tout[u] = timer;
    }

    void updateBIT(int i, int val) {
        for (; i < bit.length; i += i & -i)
            bit[i] ^= val;
    }

    int queryBIT(int i) {
        int res = 0;
        for (; i > 0; i -= i & -i)
            res ^= bit[i];
        return res;
    }

    int getLCA(int u, int v) {
        if (depth[u] < depth[v]) {
            int t = u;
            u = v;
            v = t;
        }

        int diff = depth[u] - depth[v];

        for (int j = 0; j < LOG; j++)
            if ((diff & (1 << j)) != 0)
                u = up[j][u];

        if (u == v) return u;

        for (int j = LOG - 1; j >= 0; j--) {
            if (up[j][u] != up[j][v]) {
                u = up[j][u];
                v = up[j][v];
            }
        }

        return parent[u];
    }
}