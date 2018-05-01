package kathik;

public final class Utils {

    // Threshold mask for remaining bits
    public static final int[] thresholdMask = {128, 64, 32, 16, 8, 4, 2, 1};

    public static String makeRandomString(int length) {
        final StringBuilder sb = new StringBuilder();
        for (int j = 0; j < length; j++) {
            // Select a random char ch >= 32 && ch < 127
            char c = (char)(32 + Math.random()*96);
            sb.append(c);
        }
        return sb.toString();
    }
}
