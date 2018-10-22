package io.github.uxodev.model.both._objcomp.lock;

public interface ILockable {
    void addLock(Lockable.LockReason lockReason);
    void removeLock(Lockable.LockReason lockReason);
    boolean isLocked();
}
