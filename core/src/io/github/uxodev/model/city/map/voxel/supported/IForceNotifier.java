package io.github.uxodev.model.city.map.voxel.supported;

import io.github.uxodev.model.both._objcomp.force.propel.Trajectory;
import io.github.uxodev.model.city.map.voxel.Voxel;

public interface IForceNotifier {
    void allNotifyCollapseBegin(int speed);
    void allNotifyCollapseMove(Voxel below);
    void allNotifyCollapseEnd(int speed);
    void allNotifyCollapseCrushed();
    void allNotifyPropel(Trajectory trajectory);
}
