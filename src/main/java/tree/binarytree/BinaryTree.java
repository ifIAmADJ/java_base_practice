package tree.binarytree;

import java.util.LinkedList;
import java.util.Queue;

public class BinaryTree {
    /**
     * 树是一个概念，内部是由每个结点构成的。
     * 每个结点具备：值域，左子节点的引用，右子结点的引用。
     */
    static class Node {
        private int val;
        private Node left;
        private Node right;

        private Node(int val) {
            this.val = val;
            left = null;
            right = null;
        }

        public int getVal() {
            return val;
        }

        public void setVal(int val) {
            this.val = val;
        }

        public Node getLeft() {
            return left;
        }

        public void setLeft(Node left) {
            this.left = left;
        }

        public Node getRight() {
            return right;
        }

        public void setRight(Node right) {
            this.right = right;
        }
    }

    private Node root;

    /**
     * 先序遍历。对外暴露空括号函数。
     */
    public void preOrder() {
        preOrder$(root);
    }

    /**
     * 先序遍历。首次调用时将根结点传入到函数内部，随后进行迭代。
     * @param node 传入结点
     */
    private void preOrder$(Node node) {

        if (node == null) {
            return;
        }

        System.out.print(node.val + " ");
        preOrder$(node.left);
        preOrder$(node.right);

    }

    /**
     * 中序遍历。对外暴露空括号函数。
     */
    public void inOrder() {
        inOrder$(root);
    }

    /**
     * 中序遍历。首次调用时将根结点传入到函数内部，随后进行迭代。
     * @param node 传入结点
     */
    private void inOrder$(Node node) {

        if (node == null) {
            return;
        }

        inOrder$(node.left);

        System.out.print(node.val + " ");

        inOrder$(node.right);

    }

    /**
     * 后序遍历。对外暴露空括号函数。
     */
    public void postOrder() {
        postOrder$(root);
    }

    /**
     * 后序遍历。首次调用时将根结点传入到函数内部，随后进行迭代。
     * @param node 传入结点。
     */
    private void postOrder$(Node node) {
        if (node == null) {
            return;
        }

        postOrder$(node.left);
        postOrder$(node.right);

        System.out.print(node.val + " ");
    }

    /**
     * 根据函数的先序遍历序列，中序遍历序列重构一个唯一的树结构。
     * 默认，每个结点内部的val值是唯一的。
     * @param pre 外部输入先序遍历数组。
     * @param in 外部输入中序遍历数组。
     * @return 返回一个具有根节点的树。
     */
    public static BinaryTree rebuildBinaryTree(int[] pre, int[] in) {

       return new BinaryTree(rebuildBinaryTree$(pre,0,pre.length-1,in,0,in.length-1));
    }

    /**
     * rebuildBinaryTree的底层实现，通过递归进行。
     * 主要思路为:
     *  1.从先序遍历中获取第"1"个元素,找到"根"结点。（这里的第"1"，"根"均相对于每次递归过程而言。）
     *  2.从中序遍历中找到"根"结点，并且划分出左右子树。
     *  3.对每个左右子树，重复此递归过程。
     * @param pre 先序遍历数组。
     * @param preStart 此轮递归中，先序遍历的下标起始位
     * @param preEnd 此轮递归中，先序遍历的下标终止位
     * @param in 中序遍历数组。
     * @param inStart 此轮递归中，中序遍历的下标起始位
     * @param inEnd 此轮递归中，中序遍历的下标终止位
     * @return 将构造好的"根"结点返回给上一次递归做左/右子树。
     */
    private static Node rebuildBinaryTree$(int[] pre, int preStart, int preEnd, int[] in, int inStart, int inEnd) {

        if (preStart > preEnd || inStart > inEnd) {
            return null;
        }

        //获取根结点。
        Node node = new Node(pre[preStart]);

        for (int i = inStart; i <= inEnd; i++) {

            if (in[i] == pre[preStart]) {

                /*
                * pre[preStart] pre[preStart+1] ... pre[x] pre[x+1] ... pre[preEnd]
                * ^root         |----------left----------| |--------right---------|
                *
                * in[inStart] ... in[i-1] in[i] in[i+1] ... in[inEnd]
                * |---------left--------| ^root |--------right------|
                *
                * preEnd - x = inEnd -i
                * x = preEnd - inEnd + i
                *
                * */
                node.left = rebuildBinaryTree$(pre,preStart+1, preEnd - inEnd + i, in , inStart , i- 1);
                node.right = rebuildBinaryTree$(pre,preEnd -inEnd +i +1 ,preEnd,in,i+1,inEnd);
                break;
            }
        }

        return node;

    }

    /**
     * 层序遍历，使用队列进行。每次出队时，若有左右子结点，则将此结点的左右子结点按顺序放入到队列当中。
     */
    public void layerOrder() {

        Queue<Node> queue = new LinkedList<>();

        if (root != null) {
            queue.offer(root);
        } else return;

        while (!queue.isEmpty()) {

            Node t = queue.poll();

            System.out.print(t.val + " ");

            if (t.left != null) queue.offer(t.left);
            if (t.right != null) queue.offer(t.right);

        }

    }


    /**
     * 插入新的结点，默认先插入左子结点，若左子结点有值，则插入到右子结点。
     * @param node 传入挂载的结点。
     * @param val 新结点的值。
     * @return 若插入成功，则返回true，可用于检测插入是否成功。
     */
    public boolean insertTo(Node node, int val) {

        if (node == null) return false;

        if (node.left == null) {
            node.left = new Node(val);
            return true;
        }

        if (node.right == null) {
            node.right = new Node(val);
            return true;
        }

        return false;
    }

    /**
     * 对外部公开的求树高（深度）。
     * @return 返回最终的高度，隐藏迭代过程。
     */
    public int getDepth() {
        return getDepth$(root);
    }

    /**
     * 求深度的递归实现。首次调用时需要将根结点传入进去。
     *  1.求出左子树的高度。
     *  2.求右子树的高度。
     *  3.取1，2步骤的最大值，并向上级返回此最大值+1.
     * @param node 结点
     * @return 返回每次迭代的高度值。
     */
    private int getDepth$(Node node) {

        int leftHeight, rightHeight, maxHeight;

        if (node != null) {

            leftHeight = getDepth$(node.left);
            rightHeight = getDepth$(node.right);

            maxHeight = leftHeight > rightHeight ? leftHeight : rightHeight;

            return maxHeight + 1;

        } else return 0;

    }

    private BinaryTree(int val) {
        root = new Node(val);
    }

    private BinaryTree(Node node) {
        root = node;
    }

    public static BinaryTree BuildBinaryTree(int val) {
        return new BinaryTree(val);
    }

    public Node getRoot() {
        return root;
    }

    public void setRoot(Node root) {
        this.root = root;
    }

}
