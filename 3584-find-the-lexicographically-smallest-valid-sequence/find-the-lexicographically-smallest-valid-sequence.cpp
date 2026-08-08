class Solution {
public:
    vector<int> validSequence(string word1, string word2) {
        int n = word1.size();
        int m = word2.size();

        vector<int> right(m, -1);

        int i = n - 1;
        for (int j = m - 1; j >= 0; j--) {
            while (i >= 0 && word1[i] != word2[j])
                i--;
            if (i < 0)
                break;
            right[j] = i;
            i--;
        }

        if (right[0] == -1) {
            // Exact match impossible, but one mismatch may help.
            // Continue normally; right[] still tells which suffixes exist.
        }

        vector<int> ans;
        int pos = 0;
        bool used = false;

        for (int j = 0; j < m; j++) {
            while (pos < n) {

                if (word1[pos] == word2[j]) {
                    ans.push_back(pos);
                    pos++;
                    break;
                }

                if (!used) {
                    bool ok;

                    if (j == m - 1)
                        ok = true;
                    else
                        ok = (right[j + 1] != -1 && right[j + 1] > pos);

                    if (ok) {
                        used = true;
                        ans.push_back(pos);
                        pos++;
                        break;
                    }
                }

                pos++;
            }

            if ((int)ans.size() != j + 1)
                return {};
        }

        return ans;
    }
};