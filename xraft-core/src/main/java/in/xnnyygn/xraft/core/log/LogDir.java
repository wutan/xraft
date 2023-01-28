package in.xnnyygn.xraft.core.log;

import java.io.File;

// 抽象化的获取指定文件地址的接口
public interface LogDir {

    void initialize();

    boolean exists();

    File getSnapshotFile();

    File getEntriesFile();

    File getEntryOffsetIndexFile();

    File get();

    // 重命名文件
    boolean renameTo(LogDir logDir);

}
