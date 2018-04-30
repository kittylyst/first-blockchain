package kathik;

import org.junit.BeforeClass;
import org.junit.Test;

import java.security.NoSuchAlgorithmException;

import static org.junit.Assert.assertEquals;

public class TestSHA256 {

    private static SHA256Collision coll;

    @BeforeClass
    public static void setup() throws Exception {
        coll = new SHA256Collision();
    }

    @Test
    public void testSimple() {
        String s = "xxxxxxxx";
        assertEquals(64, coll.bytesToHex(s).length());
    }
}
