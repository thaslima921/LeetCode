
import java.util.*;

class Solution {
    static final int MOD = 1_000_000_007;
    long[][] comb;

    public int numOfWays(int[] nums) {
        int n = nums.length;

        comb = new long[n + 1][n + 1];

        for (int i = 0; i <= n; i++) {
            comb[i][0] = comb[i][i] = 1;

            for (int j = 1; j < i; j++) {
                comb[i][j] =
                    (comb[i - 1][j - 1] + comb[i - 1][j]) % MOD;
            }
        }

        return (int) ((countWays(nums) - 1 + MOD) % MOD);
    }

    private long countWays(int[] nums) {
        int n = nums.length;

        if (n <= 2) {
            return 1;
        }

        int root = nums[0];

        List<Integer> left = new ArrayList<>();
        List<Integer> right = new ArrayList<>();

        for (int i = 1; i < n; i++) {
            if (nums[i] < root) {
                left.add(nums[i]);
            } else {
                right.add(nums[i]);
            }
        }

        int[] leftArr = left.stream()
                            .mapToInt(Integer::intValue)
                            .toArray();

        int[] rightArr = right.stream()
                              .mapToInt(Integer::intValue)
                              .toArray();

        long leftWays = countWays(leftArr);
        long rightWays = countWays(rightArr);

        long interleavings = comb[n - 1][left.size()];

        return interleavings * leftWays % MOD * rightWays % MOD;
    }
}
