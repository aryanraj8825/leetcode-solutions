import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

class Solution {
    private List<List<Integer>> origChildren;
    private List<List<Integer>> newChildren;
    private int[] newParent;
    private int[] lastSeen;
    private int[] ans;
    private String s;

    public int[] findSubtreeSizes(int[] parent, String s) {
        int n = parent.length;
        this.s = s;

        origChildren = new ArrayList<>(n);
        for (int i = 0; i < n; i++) {
            origChildren.add(new ArrayList<>());
        }
        for (int i = 1; i < n; i++) {
            origChildren.get(parent[i]).add(i);
        }

        newParent = Arrays.copyOf(parent, n);
        lastSeen = new int[26];
        Arrays.fill(lastSeen, -1);

        dfsReparent(0);

        newChildren = new ArrayList<>(n);
        for (int i = 0; i < n; i++) {
            newChildren.add(new ArrayList<>());
        }
        for (int i = 1; i < n; i++) {
            newChildren.get(newParent[i]).add(i);
        }

        ans = new int[n];
        dfsSize(0);
        return ans;
    }

    private void dfsReparent(int u) {
        int idx = s.charAt(u) - 'a';
        int prev = lastSeen[idx];

        if (u > 0 && prev != -1) {
            newParent[u] = prev;
        }

        lastSeen[idx] = u;
        for (int v : origChildren.get(u)) {
            dfsReparent(v);
        }
        lastSeen[idx] = prev;
    }

    private int dfsSize(int u) {
        int size = 1;
        for (int v : newChildren.get(u)) {
            size += dfsSize(v);
        }
        ans[u] = size;
        return size;
    }
}