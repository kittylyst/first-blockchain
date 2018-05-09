package kathik;

import java.util.HashMap;
import java.util.Map;

public final class SimpleBlockMain {

    private final Map<String, SimpleBlock> storage = new HashMap<>();

    public static void main(String[] args) {
        final SimpleBlockMain m = new SimpleBlockMain();
        m.initialise();
        if (!validateChain(m)) {
            throw new RuntimeException("Chain is invalid...");
        }
        System.out.println(m.storage);
        attackChain(m);
        if (!validateChain(m)) {
            throw new RuntimeException("Chain has been tampered with...");
        }

    }

    private static boolean validateChain(SimpleBlockMain m) {
        return true;
    }

    private static void attackChain(SimpleBlockMain m) {
        // Attack
    }

    private void initialise() {
        final SimpleBlock genesis = SimpleBlock.genesis();
        // Initialise a sample chain...
    }
}
