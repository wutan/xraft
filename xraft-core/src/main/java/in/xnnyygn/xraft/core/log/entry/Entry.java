package in.xnnyygn.xraft.core.log.entry;

//  日志条目
public interface Entry {
    // 空日志条目
    int KIND_NO_OP = 0;
    // 普通日志条目
    int KIND_GENERAL = 1;
    int KIND_ADD_NODE = 3;
    int KIND_REMOVE_NODE = 4;

    int getKind();

    int getIndex();

    int getTerm();

    EntryMeta getMeta();

    byte[] getCommandBytes();

}
