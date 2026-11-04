import java.util.*;

class Solution {

    class MedianCost {
        TreeMap<Integer, Integer> left = new TreeMap<>();
        TreeMap<Integer, Integer> right = new TreeMap<>();

        long leftSum = 0, rightSum = 0;
        int leftSize = 0, rightSize = 0;

        void addLeft(int val) {
            left.put(val, left.getOrDefault(val, 0) + 1);
            leftSum += val;
            leftSize++;
        }

        void addRight(int val) {
            right.put(val, right.getOrDefault(val, 0) + 1);
            rightSum += val;
            rightSize++;
        }

        void removeLeft(int val) {
            int count = left.get(val);
            if (count == 1) left.remove(val);
            else left.put(val, count - 1);

            leftSum -= val;
            leftSize--;
        }

        void removeRight(int val) {
            int count = right.get(val);
            if (count == 1) right.remove(val);
            else right.put(val, count - 1);

            rightSum -= val;
            rightSize--;
        }

        void add(int val) {
            if (leftSize == 0 || val <= left.lastKey()) {
                addLeft(val);
            } else {
                addRight(val);
            }
            balance();
        }

        void remove(int val) {
            if (left.containsKey(val)) {
                removeLeft(val);
            } else {
                removeRight(val);
            }
            balance();
        }

        void balance() {
            while (leftSize > rightSize + 1) {
                int val = left.lastKey();
                removeLeft(val);
                addRight(val);
            }

            while (leftSize < rightSize) {
                int val = right.firstKey();
                removeRight(val);
                addLeft(val);
            }
        }

        long getCost() {
            int median = left.lastKey();

            long leftCost = (long) median * leftSize - leftSum;
            long rightCost = rightSum - (long) median * rightSize;

            return leftCost + rightCost;
        }
    }

    public long minOperations(int[] nums, int x, int k) {

        int n = nums.length;

        // Step 1: Calculate minimum cost for each window
        long[] cost = new long[n - x + 1];

        MedianCost window = new MedianCost();

        for (int i = 0; i < x; i++) {
            window.add(nums[i]);
        }

        cost[0] = window.getCost();

        for (int i = 1; i <= n - x; i++) {

            window.remove(nums[i - 1]);
            window.add(nums[i + x - 1]);

            cost[i] = window.getCost();
        }

        // Step 2: Dynamic Programming
        long INF = Long.MAX_VALUE / 4;

        long[] prev = new long[n + 1];
        long[] curr = new long[n + 1];

        // prev[i] = minimum cost using 0 subarrays
        Arrays.fill(prev, 0);

        for (int t = 1; t <= k; t++) {

            Arrays.fill(curr, INF);

            for (int i = 1; i <= n; i++) {

                // Option 1: Skip current element
                curr[i] = curr[i - 1];

                // Option 2: Select a window of size x
                if (i >= x) {
                    curr[i] = Math.min(
                        curr[i],
                        prev[i - x] + cost[i - x]
                    );
                }
            }

            long[] temp = prev;
            prev = curr;
            curr = temp;
        }

        return prev[n];
    }
}
