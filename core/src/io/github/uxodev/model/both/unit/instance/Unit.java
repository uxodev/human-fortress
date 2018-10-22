package io.github.uxodev.model.both.unit.instance;

import com.badlogic.gdx.graphics.Color;
import io.github.uxodev.model.both.Transfer;
import io.github.uxodev.model.both._objcomp.force.gravity.GravityFallUnit;
import io.github.uxodev.model.both._objcomp.force.gravity.IGravityLock;
import io.github.uxodev.model.both._objcomp.force.propel.IPropellable;
import io.github.uxodev.model.both._objcomp.force.propel.PropellableUnit;
import io.github.uxodev.model.both._objcomp.lock.ILockable;
import io.github.uxodev.model.both._objcomp.lock.Lockable;
import io.github.uxodev.model.both.unit.instance._objcomp.decisionTree.Schedule;
import io.github.uxodev.model.both.widget.instance.dynamic.Dynamic;
import io.github.uxodev.model.city.map.voxel.Voxel;

public abstract class Unit implements ILockable, IGravityLock, IPropellable {
    public Voxel loc;
    public Dynamic held;

    private final Lockable lockable;
    private final GravityFallUnit gravityFallUnit;
    private final PropellableUnit propellableUnit;

    public String name;
    public static int unitNameCounter = 1;
    public Schedule roleSchedule = new Schedule(this);

    public Unit() {
        lockable = new Lockable();
        gravityFallUnit = new GravityFallUnit(this);
        propellableUnit = new PropellableUnit(this);

        name = "Unit " + unitNameCounter;
        unitNameCounter++;
    }

    public abstract void start(Voxel dest);

    public abstract void move(Voxel dest);

    public void pickUp(Dynamic dynamic) {
        this.held = dynamic;
    }

    public void putDown(Dynamic dynamic) {
        this.held = null;
    }

    public void drop(Dynamic dynamic) {
        this.held = null;
    }

    public void damage(int distance) {
        System.out.println(this + " was damaged distance " + distance);
    }

    public void destroy() {
        System.out.println(this + " was destroyed");
    }

    public void step() {

    }

    // ILockable
    @Override
    public void addLock(Lockable.LockReason lockReason) {
        lockable.addLock(lockReason);
    }

    @Override
    public void removeLock(Lockable.LockReason lockReason) {
        lockable.removeLock(lockReason);
    }

    @Override
    public boolean isLocked() {
        return lockable.isLocked();
    }

    // IGravityLock
    @Override
    public void notifyBegin(int speed) {
        addLock(Lockable.LockReason.COLLAPSING);
    }

    @Override
    public void forceMove(Voxel below) {
        Transfer.move(this, below);
    }

    @Override
    public void notifyEnd(int speed) {
        removeLock(Lockable.LockReason.COLLAPSING);
        removeLock(Lockable.LockReason.FALLING);
        removeLock(Lockable.LockReason.PROPELLING);
        damage(speed);
    }

    @Override
    public void forceCrushed() {
        destroy();
    }

    @Override
    public void initGravity() {
        gravityFallUnit.initGravity();
    }

    @Override
    public void stepGravity() {
        gravityFallUnit.stepGravity();
    }

    @Override
    public boolean isGravitating() {
        return gravityFallUnit.isGravitating();
    }

    // IPropellable
    @Override
    public void propel() {
        propellableUnit.propel();
    }

    public Color getColor() {
        return Color.GOLD;
    }

    @Override
    public String toString() {
        return "Unit{" + name + '}';
    }
}
