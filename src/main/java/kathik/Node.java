package kathik;

import java.io.PrintStream;

public abstract class Node {
    private Node parent = null;

    public Node(Node from) {
        parent = from;
    }

    public void changeParent(Node newParent) {
        parent = newParent;
    }

    // Chase up through parents
    public abstract String computeHash();

    public Node getParent() {
        return parent;
    }

    public abstract Node getLeft();

    public abstract Node getRight();

    public void prettyPrint(final int indent, final PrintStream out) {
        for (int i = 0; i < indent; i++) {
            out.print(" ");
        }
        final Node left = getLeft();
        final Node right = getRight();

        // Head case
        if (parent == null) {
            out.println("Head: " + computeHash() + " : ");

            if (left != null) {
                left.prettyPrint(indent + 1, out);
            }
            out.print("     ");
            if (right != null) {
                right.prettyPrint(indent + 1, out);
            }
            out.println();
            return;
        }

        // Leaves
        if (left == null) {
            if (right != null || !(this instanceof LeafNode)) {
                out.println(); // Flush the printer JIC
                throw new IllegalStateException("Leaf in illegal state...");
            }

            out.print(computeHash());
            return;
        }

        // Children are tree nodes
        left.prettyPrint(indent + 1, out);
        right.prettyPrint(indent + 1, out);
        out.println();
    }


}
