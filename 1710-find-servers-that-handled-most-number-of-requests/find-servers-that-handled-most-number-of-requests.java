import java.util.*;

class Solution {
    public List<Integer> busiestServers(int k, int[] arrival, int[] load) {

        TreeSet<Integer> available = new TreeSet<>();

        // Stores {finishTime, serverId}
        PriorityQueue<int[]> busy = new PriorityQueue<>(
            (a, b) -> Integer.compare(a[0], b[0])
        );

        int[] count = new int[k];

        for (int i = 0; i < k; i++) {
            available.add(i);
        }

        for (int i = 0; i < arrival.length; i++) {

            // Release servers whose requests are completed
            while (!busy.isEmpty() && busy.peek()[0] <= arrival[i]) {
                int[] server = busy.poll();
                available.add(server[1]);
            }

            // Drop request if all servers are busy
            if (available.isEmpty()) {
                continue;
            }

            // Find the first available server >= i % k
            int target = i % k;

            Integer server = available.ceiling(target);

            // Wrap around if necessary
            if (server == null) {
                server = available.first();
            }

            // Assign request
            available.remove(server);
            count[server]++;

            // Mark server as busy
            busy.offer(new int[]{
                arrival[i] + load[i],
                server
            });
        }

        // Find maximum requests handled
        int max = 0;

        for (int c : count) {
            max = Math.max(max, c);
        }

        // Collect busiest servers
        List<Integer> result = new ArrayList<>();

        for (int i = 0; i < k; i++) {
            if (count[i] == max) {
                result.add(i);
            }
        }

        return result;
    }
}
