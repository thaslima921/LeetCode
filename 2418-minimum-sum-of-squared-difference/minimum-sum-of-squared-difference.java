
import java.util.Arrays;

class Solution {
    public long minSumSquareDiff(int[] nums1, int[] nums2, int k1, int k2) {
        int n = nums1.length;
        long operations = (long) k1 + k2;
        int[] diff = new int[n];
        int max = 0;

        for (int i = 0; i < n; i++) {
            diff[i] = Math.abs(nums1[i] - nums2[i]);
            max = Math.max(max, diff[i]);
        }

        if (operations >= Arrays.stream(diff).asLongStream().sum()) {
            return 0L;
        }

        int left = 0, right = max;

        while (left < right) {
            int mid = left + (right - left) / 2;
            long needed = 0;

            for (int d : diff) {
                if (d > mid) {
                    needed += d - mid;
                }
            }

            if (needed <= operations) {
                right = mid;
            } else {
                left = mid + 1;
            }
        }

        int threshold = left;
        long remaining = operations;
        long answer = 0;

        for (int d : diff) {
            if (d > threshold) {
                remaining -= d - threshold;
                answer += (long) threshold * threshold;
            } else {
                answer += (long) d * d;
            }
        }

        // Raise some differences from threshold - 1 to threshold.
        // The remaining operations are used to reduce them by one.
        if (threshold > 0) {
            answer -= remaining * (2L * threshold - 1);
        }

        return answer;
    }
}
