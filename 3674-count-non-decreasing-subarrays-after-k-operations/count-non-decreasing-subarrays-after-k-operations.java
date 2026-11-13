import java.util.ArrayDeque;
import java.util.Deque;

class Solution {
    public long countNonDecreasingSubarrays(int[] nums, long k) {
        int n = nums.length;
        long ans = 0;
        long cost = 0;
        Deque<Integer> dq = new ArrayDeque<>(); // Stores indices of running maximums (strictly decreasing values)
        
        int r = n - 1;
        for (int l = n - 1; l >= 0; --l) {
            // 1. Add nums[l] to the left of the window
            while (!dq.isEmpty() && nums[dq.peekFirst()] <= nums[l]) {
                int idx = dq.pollFirst();
                // Where idx's dominance ended
                int nextIdx = dq.isEmpty() ? (r + 1) : dq.peekFirst();
                // Remove idx's contribution; nums[l] will replace it
                cost -= 1L * (nextIdx - idx) * nums[idx];
            }
            
            int nextIdx = dq.isEmpty() ? (r + 1) : dq.peekFirst();
            // Add nums[l]'s dominance over [l, nextIdx - 1]
            cost += 1L * (nextIdx - l) * nums[l];
            // Subtract the original value of nums[l]
            cost -= nums[l];
            dq.addFirst(l);
            
            // 2. Shrink window from the right if cost exceeds k
            while (cost > k) {
                // nums[dq.peekLast()] is the running max at index r
                cost -= (nums[dq.peekLast()] - nums[r]);
                if (dq.peekLast() == r) {
                    dq.pollLast();
                }
                --r;
            }
            
            // All subarrays starting at l and ending in [l, r] are valid
            ans += (r - l + 1);
        }
        
        return ans;
    }
}