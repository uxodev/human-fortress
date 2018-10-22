package io.github.uxodev.model.both._objcomp.force.gravity;

import io.github.uxodev.model.both._objcomp.lock.Lockable;
import io.github.uxodev.model.city.map.voxel.Voxel;
import io.github.uxodev.model.city.pathfinder.Pathfinder;
import io.github.uxodev.model.city.register.CollapseRegister;

import static io.github.uxodev.model.city.map.voxel._objcomp.Neighbors.Ordinal.C;
import static io.github.uxodev.model.city.map.voxel._objcomp.Neighbors.Plane.BELOW;

public class GravityCollapseStable extends Gravity {
    private final Voxel parent;

    public GravityCollapseStable(Voxel parent) {
        this.parent = parent;
    }

    @Override
    public void initGravity() {
        if (!isGravitating() && canGravity() && !isConnectedToFoundation())
            begin();
    }

    private boolean canGravity() {
        return isValidOrigin() && isAllowEntryDest();
    }

    private boolean isValidOrigin() {
        return !parent.pathing.voxelIsBlank();
    }

    @Override
    protected boolean isAllowEntryDest() {
        return !getDest().pathing.wallImpliesFloorAbove();
    }

    private boolean isConnectedToFoundation() {
        return Pathfinder.isConnectedToFoundation(parent.map, parent);
    }

    @Override
    protected Voxel getDest() {
        return parent.getNeighbor(BELOW, C);
    }

    @Override
    protected void begin() {
        for (Voxel voxel : Pathfinder.getAllConnected(parent)) {
            voxel.gravityCollapseStable.start(0);
            parent.allNotifyCollapseBegin(0);
        }
    }

    // stepGravity
    @Override
    protected void start(int speed) {
        setGravitating(speed);
        // was notifying "starting to fall through a voxel (again)", with current cumulative speed
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
//        System.out.println(parent + " collapsed");
        // parent collapsed
        Voxel below = getDest();
        if (below.gravityCollapseStable.isFollowingCollapsing()) {
//            System.out.println(parent + " followed voxel that was already collapsing, into " + below);
            // parent followed voxel that was already collapsing, into below
            replace(below);
            below.gravityCollapseStable.start(++speed);
        } else if (canGravity()) {
//            System.out.println(parent + " collapsed into " + below);
            // parent collapsed into below
            replace(below);
            if (below.gravityCollapseStable.canGravity()) {
//                System.out.println(below + " started collapsing again");
                // below started collapsing again
                below.gravityCollapseStable.start(++speed);
            } else {
//                System.out.println(below + " stopped on top of voxel below himself");
                // below stopped on top of voxel below himself
                below.gravityCollapseStable.end(++speed);
            }
        } else {
//            System.out.println("tried to collapse but could not enter, so stopped in place");
            // tried to collapse but could not enter, so stopped in place
            below.gravityCollapseStable.end(speed);
        }
    }

    private boolean isFollowingCollapsing() {
        return getDest().gravityCollapseStable.isGravitating();
    }

    private void replace(Voxel below) {
        if (willDestroySomething()) {
            System.out.println("collapse destroyed something in voxel below");
        }
        below.allNotifyCollapseCrushed();
        parent.supportedStables.collapseReplaceState(below);
        parent.allNotifyCollapseMove(below);
        parent.supportedStables.clearAll();
    }

    // end
    @Override
    protected void end(int speed) {
        System.out.println(parent + " stopped distance " + speed);
        parent.allNotifyCollapseEnd(speed);
    }

    // create dust and notify that it hit something but continued
    private boolean willDestroySomething() {
        return !getDest().pathing.voxelIsBlank();
    }

    @Override
    protected void setGravitating(int speed) {
        progress = 0;
        this.speed = speed;
        isGravitating = true;
        CollapseRegister.registerStepCollapse(parent);
        parent.addLock(Lockable.LockReason.COLLAPSING);
    }

    @Override
    protected void clearGravitating() {
        isGravitating = false;
        CollapseRegister.removeStepCollapse(parent);
        parent.removeLock(Lockable.LockReason.COLLAPSING);
    }
}
