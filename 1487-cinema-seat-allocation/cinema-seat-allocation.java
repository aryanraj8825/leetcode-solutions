import java.util.*;

class Solution {
    public int maxNumberOfFamilies(int n, int[][] reservedSeats) {

        // Store reserved seats for only the affected rows
        Map<Integer, Integer> map = new HashMap<>();

        for (int[] seat : reservedSeats) {
            int row = seat[0];
            int s = seat[1];

            // Only seats 2-9 matter for 4-person groups
            if (s >= 2 && s <= 9) {
                map.put(row, map.getOrDefault(row, 0) | (1 << s));
            }
        }

        // Every completely empty row can accommodate 2 groups
        long ans = (long) (n - map.size()) * 2;

        for (int mask : map.values()) {

            // seats 2,3,4,5
            boolean left = (mask & 0b00111100) == 0;

            // seats 4,5,6,7
            boolean middle = (mask & 0b11110000) == 0;

            // seats 6,7,8,9
            boolean right = (mask & 0b1111000000) == 0;

            if (left && right) {
                // Both can coexist
                ans += 2;
            } else if (left || middle || right) {
                // At least one block is available
                ans += 1;
            }
        }

        return (int) ans;
    }
}