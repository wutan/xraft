package in.xnnyygn.xraft.core.log.entry;

// 空文件条目
public class NoOpEntry extends AbstractEntry {

    public NoOpEntry(int index, int term) {
        super(KIND_NO_OP, index, term);
    }

    @Override
    public byte[] getCommandBytes() {
        return new byte[0];
    }

    @Override
    public String toString() {
        return "NoOpEntry{" +
                "index=" + index +
                ", term=" + term +
                '}';
    }

}
