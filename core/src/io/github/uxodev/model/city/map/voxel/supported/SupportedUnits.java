package io.github.uxodev.model.city.map.voxel.supported;

import io.github.uxodev.model.both._objcomp.force.propel.Trajectory;
import io.github.uxodev.model.both.unit.instance.Unit;
import io.github.uxodev.model.city.map.voxel.Voxel;

import java.util.ArrayList;

public class SupportedUnits implements IForceNotifier {
    private final Voxel parent;
    public final ArrayList<Unit> units = new ArrayList<>();

    public SupportedUnits(Voxel parent) {
        this.parent = parent;
    }

    // IGravityLock
    @Override
    public void allNotifyCollapseBegin(int speed) {
        units.forEach(unit -> unit.notifyBegin(speed));
    }

    @Override
    public void allNotifyCollapseMove(Voxel below) {
        if (!units.isEmpty()) {
            ArrayList<Unit> copy = new ArrayList<>(units);
            for (Unit unit : copy) {
                unit.forceMove(below);
            }
        }
    }

    @Override
    public void allNotifyCollapseEnd(int speed) {
        units.forEach(unit -> unit.notifyEnd(speed));
    }

    @Override
    public void allNotifyCollapseCrushed() {
        if (!units.isEmpty()) {
            ArrayList<Unit> copy = new ArrayList<>(units);
            for (Unit unit : copy) {
                unit.forceCrushed();
            }
        }
    }

    // IPropellable
    @Override
    public void allNotifyPropel(Trajectory trajectory) {

    }

    public boolean isEmpty() {
        return units.isEmpty();
    }

    public void add(Unit unit) {
        units.add(unit);
    }

    public void remove(Unit unit) {
        units.remove(unit);
    }

    public ArrayList<Unit> getUnits() {
        return units;
    }
}
