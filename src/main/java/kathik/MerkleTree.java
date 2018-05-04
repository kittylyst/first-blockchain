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
            final TreeNode lastParent = lastLeaf.getParent();

            if (lastParent.getLeft() == null) {
                n = new LeafNode(lastParent, next);
                lastParent.setLeft(n);
            } else if (lastParent.getRight() == null) {
                n = new LeafNode(lastParent, next);
                lastParent.setRight(n);
            } else {
                // New sub-block
                final TreeNode aunt = lastParent.rebalanceForSibling(1);
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

    public void setHead(TreeNode newHead) {
        head = newHead;
    }
}
