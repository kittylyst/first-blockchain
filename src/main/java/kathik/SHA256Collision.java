package kathik;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public final class SHA256Collision {

    private MessageDigest digest;

    public SHA256Collision() throws NoSuchAlgorithmException {
        digest = MessageDigest.getInstance("SHA-256");
    }


    public static void main(String[] args) throws NoSuchAlgorithmException {
        SHA256Collision s = new SHA256Collision();
        s.run();
    }

    void run() {

    }

    String bytesToHex(String s) {
        final byte[] hash = digest.digest(s.getBytes(StandardCharsets.UTF_8));

        final StringBuffer sb = new StringBuffer();
        for (int i = 0; i < hash.length; i++) {
            String hex = Integer.toHexString(0xff & hash[i]);
            if(hex.length() == 1) sb.append('0');
            sb.append(hex);
        }
        return sb.toString();
    }
}
