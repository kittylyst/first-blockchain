package kathik.merkle;

public final class TreeNode extends Node {
    private MerkleTree forWhenHeadChanges;
    private Node left;
    private Node right;

    public TreeNode(Node from) {
        super(from);
    }

    @Override
    public void changeParent(Node newParent) {
        super.changeParent(newParent);
    }

    public TreeNode(MerkleTree merkleTree) {
        super(null);
        // This TreeNode knows it's the head
        forWhenHeadChanges = merkleTree;
    }

    // Chase up through children
    public String computeHash() {
        if (left == null) {
            return "{empty}";
        } else if (right == null) {
            return "" + left.computeHash().hashCode();
        }

        return "" + (left.computeHash() + right.computeHash()).hashCode();
    }

    public Node getLeft() {
        return left;
    }

    public Node getRight() {
        return right;
    }


    public void setLeft(Node left) {
        this.left = left;
    }

    public void setRight(Node right) {
        this.right = right;
    }

    public TreeNode getParent() {
        return (TreeNode) super.getParent();
    }

    public TreeNode rebalanceForSibling(final int count) {
        final TreeNode parent = getParent();
        if (parent == null) {
            // Change head
            final TreeNode newHead = new TreeNode(forWhenHeadChanges);
            forWhenHeadChanges.setHead(newHead);
            forWhenHeadChanges = null;
            this.changeParent(newHead);
            newHead.setLeft(this);

            // Make top right
            final TreeNode topRight = new TreeNode(newHead);
            newHead.setRight(topRight);

            // Make skeleton left branch
            return topRight.makeSkeleton(count - 1);
        }
        if (parent.getRight() != null) {
            return parent.rebalanceForSibling(count + 1);
        }

        return parent;
    }

    private TreeNode makeSkeleton(int count) {
        if (count == 0) {
            return this;
        }
        TreeNode nextLeft = new TreeNode(this);
        setLeft(nextLeft);
        return nextLeft.makeSkeleton(count - 1);
    }
}
