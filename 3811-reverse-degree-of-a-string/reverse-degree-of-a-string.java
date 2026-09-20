class Solution {
    public int reverseDegree(String s) {
        int sum = 0;

        for (int i = 0; i < s.length(); i++) {
            int x = s.charAt(i) - 'a' + 1;
            int y = 27 - x;
            sum += y * (i + 1);
        }

        return sum;
    }
}