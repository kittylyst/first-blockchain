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

        // Head case
        if (parent == null) {
            out.println("Node digest: " + computeHash());
            return;
        }

        if (getRight() != null && getLeft() != null) {
            // Children are leaf nodes, so indent an extra space
            for (int i = 0; i < indent + 1; i++) {
                out.print(" ");
            }

            out.println("[: " + getLeft().toString() +
                    " : " + getRight().toString() +" :]");
            return;

        }

        // Children are tree nodes
        getLeft().prettyPrint(indent + 1, out);
        getRight().prettyPrint(indent + 1, out);
    }


}
