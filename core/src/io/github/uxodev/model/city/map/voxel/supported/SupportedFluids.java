package io.github.uxodev.model.city.map.voxel.supported;

import io.github.uxodev.model.both._objcomp.force.propel.Trajectory;
import io.github.uxodev.model.both.widget.instance.fluid.Fluid;
import io.github.uxodev.model.city.map.voxel.Voxel;

import java.util.ArrayList;

public class SupportedFluids implements IForceNotifier {
    private final Voxel parent;
    public final ArrayList<Fluid> fluids = new ArrayList<>();
    public int fluidLevel = 0;

    public SupportedFluids(Voxel parent) {
        this.parent = parent;
    }

    // IGravityLock
    public void allNotifyCollapseBegin(int speed) {
        for (Fluid fluid : fluids) {

        }
    }

    public void allNotifyCollapseMove(Voxel below) {
        if (!fluids.isEmpty()) {
            ArrayList<Fluid> copy = new ArrayList<>(fluids);
            for (Fluid fluid : copy) {

            }
        }
    }

    @Override
    public void allNotifyCollapseEnd(int speed) {
        for (Fluid fluid : fluids) {

        }
    }

    @Override
    public void allNotifyCollapseCrushed() {
        if (!fluids.isEmpty()) {
            ArrayList<Fluid> copy = new ArrayList<>(fluids);
            for (Fluid fluid : copy) {

            }
        }
    }

    // IPropellable
    public void allNotifyPropel(Trajectory trajectory) {

    }

    public boolean isEmpty() {
        return fluids.isEmpty();
    }

    public void add(Fluid fluid) {
        fluids.add(fluid);
    }

    public void remove(Fluid fluid) {
        fluids.remove(fluid);
    }

    public ArrayList<Fluid> getFluids() {
        return fluids;
    }

    public boolean isNoLiquid() {
        return fluidLevel == 0;
    }

    public boolean isLowLiquid() {
        return fluidLevel <= 5;
    }

    public boolean isHighLiquid() {
        return fluidLevel >= 6;
    }
}
