class Solution {
    public String smallestSubsequence(String s) {
        int[] freq = new int[26];
        boolean[] vis = new boolean[26];

        for (char c : s.toCharArray()) {
            freq[c - 'a']++;
        }

        StringBuilder st = new StringBuilder();

        for (char c : s.toCharArray()) {
            freq[c - 'a']--;

            if (vis[c - 'a']) continue;

            while (st.length() > 0 &&
                   st.charAt(st.length() - 1) > c &&
                   freq[st.charAt(st.length() - 1) - 'a'] > 0) {
                vis[st.charAt(st.length() - 1) - 'a'] = false;
                st.deleteCharAt(st.length() - 1);
            }

            st.append(c);
            vis[c - 'a'] = true;
        }

        return st.toString();
    }
}