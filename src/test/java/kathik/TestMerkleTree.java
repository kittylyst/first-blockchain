package kathik;

import org.junit.Test;

import static junit.framework.TestCase.assertEquals;

public class TestMerkleTree {

    @Test
    public void testSimpleTree() {
        MerkleTree t = new MerkleTree(null);
        t.append("cat");
        assertEquals("54358767", t.getHead().computeHash());
        t.append("dog");
        assertEquals("-1636958073", t.getHead().computeHash());
        t.prettyPrint();
        t = new MerkleTree(null);
        t.append("dog");
        assertEquals("54392342", t.getHead().computeHash());
        t.append("cat");
        assertEquals("1809046169", t.getHead().computeHash());
        t.prettyPrint();
    }

    @Test
    public void testSiblingFlow() {
        MerkleTree t = new MerkleTree(null);
        t.append("cat");
        t.append("dog");
        t.append("rabbit");
        t.append("parrot");
        t.append("goblin");
//        assertEquals("1809046169", t.getHead().computeHash());
        t.prettyPrint();
    }

}
