package in.xnnyygn.xraft.core.log.entry;

abstract class AbstractEntry implements Entry {

    // 日志类型
    private final int kind;
    // 日志索引
    protected final int index;
    // 日志term
    protected final int term;

    AbstractEntry(int kind, int index, int term) {
        this.kind = kind;
        this.index = index;
        this.term = term;
    }

    @Override
    public int getKind() {
        return this.kind;
    }

    @Override
    public int getIndex() {
        return index;
    }

    @Override
    public int getTerm() {
        return term;
    }

    @Override
    public EntryMeta getMeta() {
        return new EntryMeta(kind, index, term);
    }

}
