package io.github.uxodev.model.both._objcomp.lock;

import java.util.EnumSet;

public class Lockable implements ILockable {
    private final EnumSet<LockReason> lockReasons = EnumSet.noneOf(LockReason.class);

    @Override
    public void addLock(LockReason lockReason) {
        lockReasons.add(lockReason);
    }

    @Override
    public void removeLock(LockReason lockReason) {
        lockReasons.remove(lockReason);
    }

    @Override
    public boolean isLocked() {
        return !lockReasons.isEmpty();
    }

    public enum LockReason {
        COLLAPSING, FALLING, PROPELLING,
        RESERVED, HELD,;
    }
}
