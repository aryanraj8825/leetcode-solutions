import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

class Solution {
    public int maxSelectedElements(int[] nums) {
        Arrays.sort(nums);
        Map<Integer, Integer> dp = new HashMap<>();
        int ans = 0;

        for (int num : nums) {
            // Update (num + 1) first so it reads the previous value of dp.get(num)
            int nextLen = dp.getOrDefault(num, 0) + 1;
            int currLen = dp.getOrDefault(num - 1, 0) + 1;

            dp.put(num + 1, nextLen);
            dp.put(num, currLen);

            ans = Math.max(ans, Math.max(currLen, nextLen));
        }

        return ans;
    }
}