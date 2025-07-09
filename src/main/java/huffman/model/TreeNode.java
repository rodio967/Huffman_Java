package huffman.model;

public class TreeNode {
    public Byte content;
    public int weight;
    public TreeNode left;
    public TreeNode right;

    public TreeNode(Byte content, int weight) {
        this.content = content;
        this.weight = weight;
    }

    public TreeNode(Byte content, int weight, TreeNode left, TreeNode right) {
        this.content = content;
        this.weight = weight;
        this.left = left;
        this.right = right;
    }

}
