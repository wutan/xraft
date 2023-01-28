package in.xnnyygn.xraft.core.rpc.message;

import java.io.Serializable;

// 日志复制响应
public class AppendEntriesResult implements Serializable {
    // 消息ID
    private final String rpcMessageId;
    // 选举term
    private final int term;
    // 追加是否成功
    private final boolean success;

    public AppendEntriesResult(String rpcMessageId, int term, boolean success) {
        this.rpcMessageId = rpcMessageId;
        this.term = term;
        this.success = success;
    }

    public String getRpcMessageId() {
        return rpcMessageId;
    }

    public int getTerm() {
        return term;
    }

    public boolean isSuccess() {
        return success;
    }

    @Override
    public String toString() {
        return "AppendEntriesResult{" +
                "rpcMessageId='" + rpcMessageId + '\'' +
                ", success=" + success +
                ", term=" + term +
                '}';
    }

}
