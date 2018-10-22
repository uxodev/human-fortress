package io.github.uxodev.model.city.map.voxel.supported;

import io.github.uxodev.model.both._objcomp.force.propel.Trajectory;
import io.github.uxodev.model.both.widget.instance.dynamic.Dynamic;
import io.github.uxodev.model.city.map.voxel.Voxel;

import java.util.ArrayList;

public class SupportedDynamics implements IForceNotifier {
    private final Voxel parent;
    public final ArrayList<Dynamic> dynamics = new ArrayList<>();

    public SupportedDynamics(Voxel parent) {
        this.parent = parent;
    }

    // IGravityLock
    @Override
    public void allNotifyCollapseBegin(int speed) {
        dynamics.forEach(dynamic -> dynamic.notifyBegin(speed));
    }

    @Override
    public void allNotifyCollapseMove(Voxel below) {
        if (!dynamics.isEmpty()) {
            ArrayList<Dynamic> copy = new ArrayList<>(dynamics);
            for (Dynamic dynamic : copy) {
                dynamic.forceMove(below);
            }
        }
    }

    @Override
    public void allNotifyCollapseEnd(int speed) {
        dynamics.forEach(dynamic -> dynamic.notifyEnd(speed));
    }

    @Override
    public void allNotifyCollapseCrushed() {
        if (!dynamics.isEmpty()) {
            ArrayList<Dynamic> copy = new ArrayList<>(dynamics);
            for (Dynamic dynamic : copy) {
                dynamic.forceCrushed();
            }
        }
    }

    // IPropellable
    @Override
    public void allNotifyPropel(Trajectory trajectory) {

    }

    public boolean isEmpty() {
        return dynamics.isEmpty();
    }

    public void add(Dynamic dynamic) {
        dynamics.add(dynamic);
    }

    public void remove(Dynamic dynamic) {
        dynamics.remove(dynamic);
    }

    public ArrayList<Dynamic> getDynamics() {
        return dynamics;
    }
}
