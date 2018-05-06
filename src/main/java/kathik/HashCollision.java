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

    private void findCollisionRandomly(int length, char c) {
        final StringBuilder sb = new StringBuilder();
        for (int j = 0; j < length; j++) {
            sb.append(c);
        }
        final String original = sb.toString();
        final int target = original.hashCode();
        System.out.println("Length: " + length + " ; target: " + target);
        long attempts = 0;
        boolean progressBar = false;

        // Search indefinitely for a match
        FOREVER:
        while (true) {
            String candidate = Utils.makeRandomString(length);
            if (candidate.hashCode() == target) {
                break FOREVER;
            }
            if (++attempts % 10_000_000 == 0) {
                progressBar = true;
                System.out.print(".");
            }
        }
        if (progressBar)
            System.out.println();
        System.out.println("Length: " + length + " took attempts: " + attempts);
        System.out.println();
    }

}
