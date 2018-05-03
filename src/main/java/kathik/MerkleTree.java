package kathik;

import java.io.PrintStream;
import java.security.MessageDigest;

public final class MerkleTree {
    private TreeNode head = new TreeNode(this);
    private LeafNode lastLeaf;
    private MessageDigest digest;

    public MerkleTree(MessageDigest md) {
        digest = md;
    }


    public String append(final String next) {
        LeafNode n;

        if (lastLeaf == null) {
            // First append of tree
            n = new LeafNode(head, next);
            head.setLeft(n);
            lastLeaf = n;
        } else {
            final TreeNode parent = lastLeaf.getParent();

            if (parent.getLeft() == null) {
                n = new LeafNode(parent, next);
                parent.setLeft(n);
            } else if (parent.getRight() == null) {
                n = new LeafNode(parent, next);
                parent.setRight(n);
            } else {
                // New sub-block
                final TreeNode aunt = parent.rebalanceForSibling(1);
                n = new LeafNode(aunt, next);
                aunt.setLeft(n);
            }

            lastLeaf = n;
        }
        return n.computeHash();
    }


    public void prettyPrint() {
        head.prettyPrint(0, System.out);
    }

    public TreeNode getHead() {
        return head;
    }
}
