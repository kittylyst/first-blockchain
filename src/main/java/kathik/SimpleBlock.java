package kathik;

import java.time.LocalDateTime;

public final class SimpleBlock {

    private final int index;
    private final LocalDateTime timestamp;
    private final String previousHash;
    private String data;
    private String hash;

    /**
     *
     * @param index
     * @param timestamp
     * @param data
     * @param previous
     */
    public SimpleBlock(int index, LocalDateTime timestamp, String data, String previous) {
        this.index = index;
        this.timestamp = timestamp;
        this.data = data;
        this.previousHash = previous;
        this.hash = hashBlock();
    }

    public String hashBlock() {
        final String initial = String.valueOf(index) + String.valueOf(timestamp) + String.valueOf(data) + String.valueOf(previousHash);
        return ""+ initial.hashCode();
    }

    public static SimpleBlock genesis() {
        return new SimpleBlock(0, LocalDateTime.now(), "Genesis", "{}");
    }

    public SimpleBlock next() {
        final int index = getIndex() + 1;
        final SimpleBlock b = new SimpleBlock(index, LocalDateTime.now(), "Block #" + index, getHash());
        b.setHash(b.hashBlock());
        return b;
    }


    public int getIndex() {
        return index;
    }

    public String getData() {
        return data;
    }

    public String getPreviousHash() {
        return previousHash;
    }

    public String getHash() {
        return hash;
    }

    public void setHash(String hash) {
        this.hash = hash;
    }

    public void setData(String data) {
        this.data = data;
    }


    @Override
    public String toString() {
        return "SimpleBlock{" +
                "index=" + index +
                ", timestamp=" + timestamp +
                ", data='" + data + '\'' +
                ", previousHash='" + previousHash + '\'' +
                ", hash='" + hash + '\'' +
                '}';
    }
}
