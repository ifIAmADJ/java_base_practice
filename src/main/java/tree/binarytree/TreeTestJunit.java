package tree.binarytree;

import org.junit.Test;

public class TreeTestJunit {

    /**
     * 测试tree.binaryTree的基本操作
     */
    @Test
    public void testBinaryTree() {

        BinaryTree tree = BinaryTree.BuildBinaryTree(0);

        BinaryTree.Node root = tree.getRoot();

        tree.insertTo(root, 1);
        tree.insertTo(root, 2);

        tree.insertTo(root.getLeft(), 3);
        tree.insertTo(root.getLeft(), 4);

        System.out.println(tree.getDepth());

        tree.preOrder();
        System.out.println("\ninOrder");
        tree.inOrder();
        System.out.println("\npostOrder");
        tree.postOrder();

        System.out.println("\nlayerOrder");
        tree.layerOrder();

        int[] pre = {0, 1, 3, 4, 2};
        int[] in = {3, 1, 4, 0, 2};

        BinaryTree binaryTree = BinaryTree.rebuildBinaryTree(pre, in);

        System.out.println("\ncheck : ");
        binaryTree.postOrder();
    }
}
