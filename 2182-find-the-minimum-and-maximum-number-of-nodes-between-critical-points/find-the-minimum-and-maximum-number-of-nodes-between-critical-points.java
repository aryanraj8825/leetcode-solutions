class Solution {
    public int[] nodesBetweenCriticalPoints(ListNode head) {
        int minDist = Integer.MAX_VALUE;
        int maxDist = -1;

        int first = -1;
        int last = -1;
        int pos = 1;

        ListNode prev = head;
        ListNode curr = head.next;

        while (curr.next != null) {
            boolean isMax = curr.val > prev.val && curr.val > curr.next.val;
            boolean isMin = curr.val < prev.val && curr.val < curr.next.val;

            if (isMax || isMin) {
                if (first == -1) {
                    // First critical point
                    first = pos;
                } else {
                    // Distance from previous critical point
                    minDist = Math.min(minDist, pos - last);

                    // Distance from first critical point
                    maxDist = Math.max(maxDist, pos - first);
                }

                last = pos;
            }

            prev = curr;
            curr = curr.next;
            pos++;
        }

        if (first == -1 || first == last) {
            return new int[]{-1, -1};
        }

        return new int[]{minDist, maxDist};
    }
}