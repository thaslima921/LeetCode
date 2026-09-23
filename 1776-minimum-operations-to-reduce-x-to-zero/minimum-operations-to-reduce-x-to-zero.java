class Solution {
    public int minOperations(int[] nums, int x) {
        int n = nums.length;
        int sum = 0;

        for (int a : nums)
            sum += a;

        int target = sum - x;

        if (target < 0)
            return -1;

        if (target == 0)
            return n;

        int l = 0, cur = 0;
        int maxLen = -1;

        for (int r = 0; r < n; r++) {
            cur += nums[r];

            while (cur > target && l <= r) {
                cur -= nums[l++];
            }

            if (cur == target) {
                maxLen = Math.max(maxLen, r - l + 1);
            }
        }

        return maxLen == -1 ? -1 : n - maxLen;
    }
}