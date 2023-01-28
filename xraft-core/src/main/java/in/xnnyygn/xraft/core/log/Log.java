package in.xnnyygn.xraft.core.log;

import in.xnnyygn.xraft.core.log.entry.*;
import in.xnnyygn.xraft.core.log.statemachine.StateMachine;
import in.xnnyygn.xraft.core.node.NodeEndpoint;
import in.xnnyygn.xraft.core.node.NodeId;
import in.xnnyygn.xraft.core.rpc.message.AppendEntriesRpc;
import in.xnnyygn.xraft.core.rpc.message.InstallSnapshotRpc;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.List;
import java.util.Set;

/**
 * Log.
 *
 * @see in.xnnyygn.xraft.core.log.sequence.EntrySequence
 * @see in.xnnyygn.xraft.core.log.snapshot.Snapshot
 */
public interface Log {

    int ALL_ENTRIES = -1;

    /**
     * Get meta of last entry.
     *
     * 获取最后一条日志的元信息
     * @return entry meta
     */
    @Nonnull
    EntryMeta getLastEntryMeta();

    /**
     * Create append entries rpc from log.
     *  创建AppendEntries消息
     *
     * @param term       current term
     * @param selfId     self node id
     * @param nextIndex  next index
     * @param maxEntries max entries
     * @return append entries rpc
     */
    AppendEntriesRpc createAppendEntriesRpc(int term, NodeId selfId, int nextIndex, int maxEntries);

    /**
     * Create install snapshot rpc from log.
     *
     * @param term   current term
     * @param selfId self node id
     * @param offset data offset
     * @param length data length
     * @return install snapshot rpc
     */
    InstallSnapshotRpc createInstallSnapshotRpc(int term, NodeId selfId, int offset, int length);

    /**
     * Get last uncommitted group config entry.
     *
     * @return last committed group config entry, maybe {@code null}
     */
    @Nullable
    GroupConfigEntry getLastUncommittedGroupConfigEntry();

    /**
     * Get next log index.
     *  获取下一条日志的索引
     *
     * @return next log index
     */
    int getNextIndex();

    /**
     * Get commit index.
     * 获取当前的commitIndex
     * @return commit index
     */
    int getCommitIndex();

    /**
     * Test if last log self is new than last log of leader.
     *  判断对象的lastLogIndex和lastLogTerm是否比自己新
     * @param lastLogIndex last log index
     * @param lastLogTerm  last log term
     * @return true if last log self is newer than last log of leader, otherwise false
     */
    boolean isNewerThan(int lastLogIndex, int lastLogTerm);

    /**
     * Append a NO-OP log entry.
     *  增加一条NO_OP日志
     * @param term current term
     * @return no-op entry
     */
    NoOpEntry appendEntry(int term);

    /**
     * Append a general log entry.
     *  增加一条普通日志
     * @param term    current term
     * @param command command in bytes
     * @return general entry
     */
    GeneralEntry appendEntry(int term, byte[] command);

    /**
     * Append a log entry for adding node.
     * 追加来自leader的日志条目
     *
     * @param term            current term
     * @param nodeEndpoints   current node configs
     * @param newNodeEndpoint new node config
     * @return add node entry
     */
    AddNodeEntry appendEntryForAddNode(int term, Set<NodeEndpoint> nodeEndpoints, NodeEndpoint newNodeEndpoint);

    /**
     * Append a log entry for removing node.
     *
     * @param term          current term
     * @param nodeEndpoints current node configs
     * @param nodeToRemove  node to remove
     * @return remove node entry
     */
    RemoveNodeEntry appendEntryForRemoveNode(int term, Set<NodeEndpoint> nodeEndpoints, NodeId nodeToRemove);

    /**
     * Append entries to log.
     *
     * @param prevLogIndex expected index of previous log entry
     * @param prevLogTerm  expected term of previous log entry
     * @param entries      entries to append
     * @return true if success, false if previous log check failed
     */
    boolean appendEntriesFromLeader(int prevLogIndex, int prevLogTerm, List<Entry> entries);

    /**
     * Advance commit index.
     *  推进
     * <p>
     * The log entry with new commit index must be the same term as the one in parameter,
     * otherwise commit index will not change.
     * </p>
     *
     * @param newCommitIndex new commit index
     * @param currentTerm    current term
     */
    void advanceCommitIndex(int newCommitIndex, int currentTerm);

    /**
     * Install snapshot.
     *
     * @param rpc rpc
     * @return install snapshot state
     */
    InstallSnapshotState installSnapshot(InstallSnapshotRpc rpc);

    /**
     * Generate snapshot.
     *
     * @param lastIncludedIndex last included index
     * @param groupConfig       group config
     */
    void generateSnapshot(int lastIncludedIndex, Set<NodeEndpoint> groupConfig);

    /**
     * Set state machine.
     * <p>
     * It will be called when
     * <ul>
     * <li>apply the log entry</li>
     * <li>generate snapshot</li>
     * <li>apply snapshot</li>
     * </ul>
     *
     * @param stateMachine state machine
     */
    void setStateMachine(StateMachine stateMachine);

    /**
     * Close log files.
     */
    void close();

}
