package kathik;

public final class HashCollision {

    public static void main(String[] args) {
        HashCollision h = new HashCollision();
        h.run();
    }

    public void run() {
        for (int i = 1; i < 1000; i++) {
            findCollisionRandomly(i, 'a');
        }
    }

    void findCollisionRandomly(int length, char c) {
        final StringBuilder sb = new StringBuilder();
        for (int j = 0; j < length; j++) {
            sb.append(c);
        }
        final String original = sb.toString();
        final int target = original.hashCode();
        System.out.println("Length: "+ length +" ; target: "+ target);
        long attempts = 0;

        // Search indefinitely for a match
        FOREVER: while (true) {
            String candidate = makeRandomString(length);
            if (candidate.hashCode() == target) {
                break FOREVER;
            }
            if (attempts++ % 10_000_000 == 0) {
                System.out.print(".");
            }
        }
        System.out.println();
        System.out.println("Length: "+ length +" took attempts: "+ attempts);
    }

    String makeRandomString(int length) {
        final StringBuilder sb = new StringBuilder();
        for (int j = 0; j < length; j++) {
            // Select a random char ch >= 32 && ch < 127
            char c = (char)(32 + Math.random()*96);
            sb.append(c);
        }
        return sb.toString();
    }
}
