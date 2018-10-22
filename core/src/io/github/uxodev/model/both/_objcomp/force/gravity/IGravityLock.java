package io.github.uxodev.model.both._objcomp.force.gravity;

import io.github.uxodev.model.city.map.voxel.Voxel;

public interface IGravityLock extends IGravity {
    void notifyBegin(int speed);
    void forceMove(Voxel below);
    void notifyEnd(int speed);
    void forceCrushed();
}
