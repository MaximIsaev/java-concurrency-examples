//package algorithms;
//
//public class DFS {
//
//    public static void main(String[] args) {
//
//        TreeNode root = new TreeNode("5");
//        root.addLeft(
//                new TreeNode("10").addLeft(
//                        new TreeNode("1").addLeft(
//                                new TreeNode("77")
//                        )
//                )
//        );
//
//        root.addRight(
//                new TreeNode("123").addRight(
//                        new TreeNode("999").addRight(
//                                new TreeNode("746")
//                        )
//                )
//        );
//
//        boolean result = findDFS(root, "999");
//        System.out.println("Found " + result);
//
//    }
//
//    private static boolean findDFS(TreeNode node, String value) {
//        if (node == null) return false;
//        System.out.println(node.getValue() + " ");
//        if (node.getValue().equals(value)) return true;
//
//        return findDFS(node.getLeft(), value) || findDFS(node.getRight(), value);
//    }
//}
//
//
//class TreeNode {
//    private String value;
//    private TreeNode left;
//    private TreeNode right;
//
//    public TreeNode(String value) {
//        this.value = value;
//    }
//
//    public TreeNode addLeft(TreeNode node) {
//        this.left = node;
//        return left;
//    }
//
//    public TreeNode addRight(TreeNode node) {
//        this.right = node;
//        return right;
//    }
//
//    public String getValue() {
//        return value;
//    }
//
//    public TreeNode getLeft() {
//        return left;
//    }
//
//    public TreeNode getRight() {
//        return right;
//    }
//}
//
//
