import java.util.Arrays;

class Solution {
    public int minimizeSum(int[] nums) {
        Arrays.sort(nums);
        int n = nums.length;

        int dropTwoLargest = nums[n - 3] - nums[0];
        int dropTwoSmallest = nums[n - 1] - nums[2];
        int dropOneEach = nums[n - 2] - nums[1];

        return Math.min(dropTwoLargest, Math.min(dropTwoSmallest, dropOneEach));
    }
}