class Solution {
    public boolean uniformArray(int[] nums1) {
        int minOdd = Integer.MAX_VALUE;
        int minEven = Integer.MAX_VALUE;

        for (int x : nums1) {
            if (x % 2 == 0) {
                minEven = Math.min(minEven, x);
            } else {
                minOdd = Math.min(minOdd, x);
            }
        }

        // All elements are already even
        if (minOdd == Integer.MAX_VALUE) {
            return true;
        }

        // All elements are already odd
        if (minEven == Integer.MAX_VALUE) {
            return true;
        }

        // We can make every element odd
        if (minOdd < minEven) {
            return true;
        }

        return false;
    }
}