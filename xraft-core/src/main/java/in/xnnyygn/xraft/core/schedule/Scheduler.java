package in.xnnyygn.xraft.core.schedule;

import javax.annotation.Nonnull;

/**
 * Scheduler.
 */
// TODO optimize
public interface Scheduler {

    /**
     * Schedule log replication task.
     *  创建日志复制定时任务
     * @param task task
     * @return log replication task
     */
    @Nonnull
    LogReplicationTask scheduleLogReplicationTask(@Nonnull Runnable task);

    /**
     * Schedule election timeout.
     *  创建选举超时器
     * @param task task
     * @return election timeout
     */
    @Nonnull
    ElectionTimeout scheduleElectionTimeout(@Nonnull Runnable task);

    /**
     * Stop scheduler.
     *  关闭定时器
     * @throws InterruptedException if interrupted
     */
    void stop() throws InterruptedException;

}
