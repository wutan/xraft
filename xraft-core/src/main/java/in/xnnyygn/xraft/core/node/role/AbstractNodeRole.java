package in.xnnyygn.xraft.core.node.role;

import in.xnnyygn.xraft.core.node.NodeId;

/**
 * 节点角色 公共类
 */
public abstract class AbstractNodeRole {

    // 角色名
    private final RoleName name;
    protected final int term;

    AbstractNodeRole(RoleName name, int term) {
        this.name = name;
        this.term = term;
    }

    public RoleName getName() {
        return name;
    }

    public int getTerm() {
        return term;
    }

    public RoleNameAndLeaderId getNameAndLeaderId(NodeId selfId) {
        return new RoleNameAndLeaderId(name, getLeaderId(selfId));
    }

    public abstract NodeId getLeaderId(NodeId selfId);

    // 取消选举超时或日志复制定时任务 (每个角色至多对应一个超时或定时任务,当一个角色转到另一个角色,必须取消当前超时或定时任务,然后创建新的超时或定时任务)
    public abstract void cancelTimeoutOrTask();

    public abstract RoleState getState();

    public boolean stateEquals(AbstractNodeRole that) {
        if (this.name != that.name || this.term != that.term) {
            return false;
        }
        return doStateEquals(that);
    }

    protected abstract boolean doStateEquals(AbstractNodeRole role);

}
