package huffman.model;

import java.util.Comparator;
import java.util.Map;
import java.util.PriorityQueue;

public class HuffmanTree {
    private static final Comparator<TreeNode> comparator = Comparator
            .comparingInt((TreeNode node) -> node.weight)
            .thenComparing(node -> node.content, Comparator.nullsLast(Comparator.naturalOrder()));


    public static TreeNode buildHuffmanTree(Map<Byte, Integer> frequencies) {
        PriorityQueue<TreeNode> codeTreeNodes = BuildQueue(frequencies);


        while (codeTreeNodes.size() > 1) {
            TreeNode left = codeTreeNodes.poll();
            TreeNode right = codeTreeNodes.poll();

            TreeNode parent = new TreeNode(null, left.weight + right.weight, left, right);
            codeTreeNodes.add(parent);
        }

        TreeNode root = codeTreeNodes.poll();
        if (root == null) {
            throw new IllegalStateException("Ошибка при построении дерева Хаффмана.");
        }

        return root;
    }

    private static PriorityQueue<TreeNode> BuildQueue(Map<Byte, Integer> frequencies) {
        PriorityQueue<TreeNode> codeTreeNodes = new PriorityQueue<>(comparator);

        for (Map.Entry<Byte, Integer> entry : frequencies.entrySet()) {
            byte b = entry.getKey();
            int count = entry.getValue();
            TreeNode newNode = new TreeNode(b, count);
            codeTreeNodes.add(newNode);
        }

        return codeTreeNodes;
    }


}
