class Solution {
    public int maximumLength(int[] nums) {
        HashMap<Long, Integer> freq = new HashMap<>();

        for (int x : nums) {
            freq.put((long) x, freq.getOrDefault((long) x, 0) + 1);
        }

        int ans = 1;

        if (freq.containsKey(1L)) {
            int c = freq.get(1L);
            ans = Math.max(ans, (c % 2 == 1) ? c : c - 1);
        }

        for (long start : freq.keySet()) {
            if (start == 1L) continue;

            long cur = start;
            int len = 1;

            while (true) {
                if (freq.getOrDefault(cur, 0) < 2) break;

                if (cur > 1000000000L / cur) break;
                long next = cur * cur;

                if (!freq.containsKey(next)) break;

                len += 2;
                cur = next;
            }

            ans = Math.max(ans, len);
        }

        return ans;
    }
}