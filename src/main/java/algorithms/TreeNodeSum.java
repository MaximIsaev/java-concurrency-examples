package algorithms;

public class TreeNodeSum {


    public boolean hasPathSum(TreeNode root, int targetSum) {
        if (root == null) return false;
        int newTarget = targetSum - root.val;
        if (root.left == null && root.right == null && newTarget == 0) return true;

        return hasPathSum(root.left, newTarget) || hasPathSum(root.right, newTarget);
    }

    public boolean isSameTree(TreeNode p, TreeNode q) {
        if (p == null && q == null) return true;
        if (p != null && q == null) return false;
        if (p == null && q != null) return false;

        return (p.val == q.val) && isSameTree(p.left, q.left) && isSameTree(p.right, q.right);
    }
}


class TreeNode {
    int val;
    TreeNode left;
    TreeNode right;

    TreeNode() {
    }

    TreeNode(int val) {
        this.val = val;
    }

    TreeNode(int val, TreeNode left, TreeNode right) {
        this.val = val;
        this.left = left;
        this.right = right;
    }
}
