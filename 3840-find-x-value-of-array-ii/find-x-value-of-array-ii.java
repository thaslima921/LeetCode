class Solution {
    static class Node {
        int prod;
        int[] cnt;
        Node(int k) {
            cnt = new int[k];
        }
    }
    int k;
    Node[] tree;
    int[] nums;
    Node merge(Node a, Node b) {
        Node c = new Node(k);
        c.prod = (a.prod * b.prod) % k;
        for (int i = 0; i < k; i++) {
            c.cnt[i] += a.cnt[i];
        }
        for (int i = 0; i < k; i++) {
            int rem = (a.prod * i) % k;
            c.cnt[rem] += b.cnt[i];
        }
        return c;
    }
    void build(int p, int l, int r) {
        if (l == r) {
            tree[p] = new Node(k);
            int rem = nums[l] % k;
            tree[p].prod = rem;
            tree[p].cnt[rem] = 1;
            return;
        }
        int m = (l + r) / 2;
        build(p * 2, l, m);
        build(p * 2 + 1, m + 1, r);
        tree[p] = merge(tree[p * 2], tree[p * 2 + 1]);
    }
    void update(int p, int l, int r, int idx, int val) {
        if (l == r) {
            tree[p] = new Node(k);
            int rem = val % k;
            tree[p].prod = rem;
            tree[p].cnt[rem] = 1;
            return;
        }
        int m = (l + r) / 2;
        if (idx <= m)
            update(p * 2, l, m, idx, val);
        else
            update(p * 2 + 1, m + 1, r, idx, val);
        tree[p] = merge(tree[p * 2], tree[p * 2 + 1]);
    }
    Node query(int p, int l, int r, int ql, int qr) {
        if (ql <= l && r <= qr)
            return tree[p];
        int m = (l + r) / 2;
        if (qr <= m)
            return query(p * 2, l, m, ql, qr);
        if (ql > m)
            return query(p * 2 + 1, m + 1, r, ql, qr);
        Node left = query(p * 2, l, m, ql, qr);
        Node right = query(p * 2 + 1, m + 1, r, ql, qr);
        return merge(left, right);
    }
    public int[] resultArray(int[] nums, int k, int[][] queries) {
        this.nums = nums;
        this.k = k;

        int n = nums.length;
        tree = new Node[4 * n];

        build(1, 0, n - 1);

        int[] ans = new int[queries.length];

        for (int i = 0; i < queries.length; i++) {
            int idx = queries[i][0];
            int val = queries[i][1];
            int start = queries[i][2];
            int x = queries[i][3];

            // Persistent update
            nums[idx] = val;
            update(1, 0, n - 1, idx, val);

            // All possible remaining arrays are non-empty prefixes
            // of nums[start ... n-1].
            Node res = query(1, 0, n - 1, start, n - 1);

            ans[i] = res.cnt[x];
        }

        return ans;
    }
}      