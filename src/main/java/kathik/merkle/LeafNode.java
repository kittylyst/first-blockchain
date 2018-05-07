package kathik.merkle;

public class LeafNode extends Node {
    private final String payload;

    public LeafNode(Node from, String next) {
        super(from);
        payload = next;
    }

    @Override
    public void changeParent(Node newParent) {
        throw new UnsupportedOperationException("Can't change the parent of a leaf");
    }

    // Chase up through parents
    public String computeHash() {
        return ""+ payload.hashCode();
    }

    public TreeNode getParent() {
        return (TreeNode)super.getParent();
    }

    @Override
    public Node getLeft() {
        return null;
    }

    @Override
    public Node getRight() {
        return null;
    }

}
