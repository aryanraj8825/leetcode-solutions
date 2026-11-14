import java.util.List;

class Solution {
    public int minimumSumSubarray(List<Integer> nums, int l, int r) {
        int n = nums.size();
        // Compute prefix sums: prefix[i] stores the sum of nums[0...i-1]
        long[] prefix = new long[n + 1];
        for (int i = 0; i < n; i++) {
            prefix[i + 1] = prefix[i] + nums.get(i);
        }

        long minSum = Long.MAX_VALUE;

        // Iterate over all possible ending indices of the subarray
        for (int j = l; j <= n; j++) {
            // Check all valid starting indices k such that length is between l and r
            // j - r <= k <= j - l
            int start = Math.max(0, j - r);
            int end = j - l;

            for (int k = start; k <= end; k++) {
                long currentSum = prefix[j] - prefix[k];
                if (currentSum > 0) {
                    minSum = Math.min(minSum, currentSum);
                }
            }
        }

        return minSum == Long.MAX_VALUE ? -1 : (int) minSum;
    }
}