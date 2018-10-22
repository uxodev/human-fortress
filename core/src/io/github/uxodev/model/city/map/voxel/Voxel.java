package io.github.uxodev.model.city.map.voxel;

import io.github.uxodev.model.both._data.Coord;
import io.github.uxodev.model.both._objcomp.force.gravity.GravityCollapseStable;
import io.github.uxodev.model.both._objcomp.force.gravity.IGravity;
import io.github.uxodev.model.both._objcomp.force.propel.Trajectory;
import io.github.uxodev.model.both._objcomp.lock.ILockable;
import io.github.uxodev.model.both._objcomp.lock.Lockable;
import io.github.uxodev.model.city.map.Map;
import io.github.uxodev.model.city.map.voxel._objcomp.Appearance;
import io.github.uxodev.model.city.map.voxel._objcomp.Neighbors;
import io.github.uxodev.model.city.map.voxel._objcomp.Pathing;
import io.github.uxodev.model.city.map.voxel.supported.*;

import static io.github.uxodev.model.city.map.voxel._objcomp.Neighbors.Ordinal;
import static io.github.uxodev.model.city.map.voxel._objcomp.Neighbors.Plane;

public class Voxel implements ILockable, IGravity, IForceNotifier {
    public final Map map;
    public final Coord position;
    public final Neighbors neighbors = new Neighbors();
    private final Lockable lockable = new Lockable();

    public final SupportedStables supportedStables = new SupportedStables(this);
    public final GravityCollapseStable gravityCollapseStable = new GravityCollapseStable(this);
    public final Pathing pathing = new Pathing(this);

    public final SupportedFluids supportedFluids = new SupportedFluids(this);
    public final SupportedDynamics supportedDynamics = new SupportedDynamics(this);
    public final SupportedUnits supportedUnits = new SupportedUnits(this);
    public final SupportedZones supportedZones = new SupportedZones();

    // user input
    public boolean isSelected = false;
    public boolean isHovered = false;
    public final Appearance appearance = new Appearance(this);

    // temp
    public boolean flagAlert = false;

    // init
    public Voxel(Map map, Coord position) {
        this.map = map;
        this.position = position;
    }

    public void init(Map map) {
        neighbors.initNeighbors(map, position);
    }

    public void refresh() {
        supportedStables.refreshImpliedFloor();
    }

    public void initForbidden() {
        neighbors.initForbidden(this);
        supportedStables.setForbidden();
    }

    // middleman
    public Voxel getNeighbor(Plane plane, Ordinal ordinal) {
        return neighbors.get(plane, ordinal);
    }

    public boolean isVacant() {
        return supportedDynamics.isEmpty() && supportedUnits.isEmpty();
    }

    public boolean isEmpty() {
        return supportedDynamics.isEmpty() && pathing.wallIsBlank();
    }

    public int getElevation() {
        return position.z;
    }

    public void updateConnected() {
        pathing.forceUpdateConnected();
    }

    public void updatePathing() {
        pathing.forceUpdatePathing();
    }

    public void removeAllZones() {
        supportedZones.removeAllZones();
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

    // IGravity
    @Override
    public void initGravity() {
        gravityCollapseStable.initGravity();
    }

    @Override
    public void stepGravity() {
        gravityCollapseStable.stepGravity();
    }

    @Override
    public boolean isGravitating() {
        return gravityCollapseStable.isGravitating();
    }

    // IForceNotifier
    @Override
    public void allNotifyCollapseBegin(int speed) {
        supportedFluids.allNotifyCollapseBegin(speed);
        supportedDynamics.allNotifyCollapseBegin(speed);
        supportedUnits.allNotifyCollapseBegin(speed);
    }

    @Override
    public void allNotifyCollapseMove(Voxel below) {
        supportedFluids.allNotifyCollapseMove(below);
        supportedDynamics.allNotifyCollapseMove(below);
        supportedUnits.allNotifyCollapseMove(below);
    }

    @Override
    public void allNotifyCollapseEnd(int speed) {
        supportedFluids.allNotifyCollapseEnd(speed);
        supportedDynamics.allNotifyCollapseEnd(speed);
        supportedUnits.allNotifyCollapseEnd(speed);
    }

    @Override
    public void allNotifyCollapseCrushed() {
        supportedFluids.allNotifyCollapseCrushed();
        supportedDynamics.allNotifyCollapseCrushed();
        supportedUnits.allNotifyCollapseCrushed();
    }

    @Override
    public void allNotifyPropel(Trajectory trajectory) {
        supportedFluids.allNotifyPropel(trajectory);
        supportedDynamics.allNotifyPropel(trajectory);
        supportedUnits.allNotifyPropel(trajectory);
    }

    public String status() {
        return this +
//                "\n" +
//                "waterLevel=" + supportedFreePrimitives.waterLevel +
                "\n" +
//                "stateFloor=" + supportedTerrain.getStateFloor() +
                "floor=" + supportedStables.getTerrainFloor() +
                "\n" +
//                "stateWall=" + supportedTerrain.getStateWall() +
                "wall=" + supportedStables.getTerrainWall() +
                ", furniture=" + supportedStables.getFurniture() +
                ", furnitureContainer=" + supportedStables.getFurnitureContainer() +
                ", plant=" + supportedStables.getPlantInstance() +
                "\n" +
                "free=" + supportedFluids.fluidLevel +
                "\n" +
                "getDynamics=" + supportedDynamics.getDynamics() +
                "\n" +
                "supportedUnits=" + supportedUnits.units +
//                "connected =" + getIsConnectedTo() +
//                "\n" +
//                "walk =" + pathing.getCanPathToByMethod(Pathfinder.Method.WALK) +
//                "\n" +
//                "wheel=" + pathing.getCanPathToByMethod(Pathfinder.Method.WHEEL) +
//                "\n" +
//                "leap =" + pathing.getCanPathToByMethod(Pathfinder.Method.LEAP) +
//                "\n" +
//                "climb=" + pathing.canPathToViaClimb() +
//                "\n" +
//                "swim =" + pathing.getCanPathToByMethod(Pathfinder.Method.SWIM) +
//                "\n" +
//                "fly  =" + pathing.getCanPathToByMethod(Pathfinder.Method.FLY) +

//                "items=" + supportedMobiles.items +
//                "\n" +
//                "units=" + supportedMobiles.units +
//                "\n" +

//                "connected=" + path.getIsConnectedTo() +
//                "collapseflag=" + collapse.isFlaggedCollapse() +
//                "collapseStep=" + collapse.fallProgress +

                "";
    }

    @Override
    public String toString() {
        return position.toString();
    }
}
