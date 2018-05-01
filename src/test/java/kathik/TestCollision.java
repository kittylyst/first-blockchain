package kathik;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class TestCollision {

    @Test
    public void testRandom() {
        HashCollision h = new HashCollision();
        String s  = Utils.makeRandomString(1);
        assertEquals("Length should be 1", 1, s.length());
    }
}
