package kathik;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import static kathik.Utils.thresholdMask;

public final class SHA256Finder {

    private MessageDigest digest;

    public SHA256Finder() throws NoSuchAlgorithmException {
        digest = MessageDigest.getInstance("SHA-256");
    }


    public static void main(String[] args) throws Exception {
        SHA256Finder s = new SHA256Finder();
        s.findMatcher(1024, 20);
    }

    void findMatcher(int length, int target) {
        System.out.println("Length: " + length + " ; target: " + target);
        long attempts = 0;

        // Search indefinitely for a match
        String candidate = null;
        FOREVER:
        while (true) {
            candidate = Utils.makeRandomString(length);
            if (belowThreshold(candidate, target)) {
                break FOREVER;
            }
            if (++attempts % 100_000 == 0) {
                System.out.print(".");
            }
        }
        System.out.println();
        System.out.println("Length: " + length + " took attempts: " + attempts +" to find a "+ target +" prefix");
        System.out.println("Winner: " + candidate);
        System.out.println("Winning hash: " + sha256Hash(candidate));
    }

    public boolean belowThreshold(final String s, final int numBits) {
        final byte[] hash = digest.digest(s.getBytes(StandardCharsets.UTF_8));
        int bitsCounted = 0;
        final int fullBytes = numBits / 8;
        final int remBits = numBits % 8;

        // Check full bytes first
        for (int i = 0; i < fullBytes; i++) {
            // Each hash byte contains 8 bits, so use an int to store them unsigned
            int compare = 0xff & hash[i];
            if (compare == 0) {
                bitsCounted += 8;
                if (bitsCounted >= numBits) {
                    return true;
                }
            } else {
                return false;
            }
        }

        // Now check the bits using one final byte
        return thresholdMask[remBits] > (hash[fullBytes] & 0xff);

    }

    String sha256Hash(String s) {
        final byte[] hash = digest.digest(s.getBytes(StandardCharsets.UTF_8));

        final StringBuilder sb = new StringBuilder();
        for (byte aHash : hash) {
            String hex = Integer.toHexString(0xff & aHash);
            // We are converting an unsigned byte to a hex string.
            // This will give either 1 or 2 hex digits e.g. 'a' or '23' (35 dec).
            // If we only get 1 back, we need to include the zero byte so we stay fixed-width as a string.
            if (hex.length() == 1) sb.append('0');
            sb.append(hex);
        }
        return sb.toString();
    }
}
