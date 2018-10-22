package io.github.uxodev.model.both._objcomp.force.gravity;

import io.github.uxodev.model.both.Transfer;
import io.github.uxodev.model.both._objcomp.lock.Lockable;
import io.github.uxodev.model.both.widget.instance.dynamic.Dynamic;
import io.github.uxodev.model.city.map.voxel.Voxel;
import io.github.uxodev.model.city.register.FallRegister;

import static io.github.uxodev.model.city.map.voxel._objcomp.Neighbors.Ordinal.C;
import static io.github.uxodev.model.city.map.voxel._objcomp.Neighbors.Plane.BELOW;

public class GravityFallDynamic extends Gravity {
    private final Dynamic parent;

    public GravityFallDynamic(Dynamic parent) {
        this.parent = parent;
    }

    // initGravity
    @Override
    public void initGravity() {
        if (!isGravitating() && isAllowEntryDest())
            begin();
    }

    @Override
    protected boolean isAllowEntryDest() {
        return !getDest().pathing.wallImpliesFloorAbove() && parent.loc.pathing.floorAllowsSolid();
    }

    @Override
    protected Voxel getDest() {
        return parent.loc.getNeighbor(BELOW, C);
    }

    @Override
    protected void begin() {
        start(0);
        parent.notifyBegin(0);
    }

    // stepGravity
    @Override
    protected void start(int speed) {
        setGravitating(speed);
    }

    @Override
    public void stepGravity() {
        progress++;
        if (progress > bucket)
            stop();
    }

    @Override
    protected void stop() {
        clearGravitating();
        gravity();
    }

    // gravity
    @Override
    protected void gravity() {
        // parent fell
        Voxel below = getDest();
        if (isAllowEntryDest()) {
            // parent fell into below
            Transfer.move(parent, below);
            if (isAllowEntryDest()) {
                // below started falling again
                start(++speed);
            } else {
                // below stopped on top of voxel below himself
                end(++speed);
            }
        } else {
            // tried to fall but could not enter, so stopped in place
            end(speed);
        }
    }

    // end
    @Override
    protected void end(int speed) {
        // parent stopped distance speed
        parent.notifyEnd(speed);
    }

    @Override
    protected void setGravitating(int speed) {
        progress = 0;
        this.speed = speed;
        isGravitating = true;
        FallRegister.registerStepFall(parent);
        parent.addLock(Lockable.LockReason.FALLING);
    }

    @Override
    protected void clearGravitating() {
        isGravitating = false;
        FallRegister.removeStepFall(parent);
        parent.removeLock(Lockable.LockReason.FALLING);
    }
}
