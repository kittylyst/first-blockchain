package kathik;

import org.junit.BeforeClass;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class TestSHA256 {

    private static SHA256Finder coll;

    @BeforeClass
    public static void setup() throws Exception {
        coll = new SHA256Finder();
    }

    @Test
    public void testSimple() {
        String s = "xxxxxxxx";
        assertEquals(64, coll.sha256Hash(s).length());
    }
}
