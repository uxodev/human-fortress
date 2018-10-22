package io.github.uxodev.model.both._objcomp.force.gravity;

import io.github.uxodev.model.city.map.voxel.Voxel;

public abstract class Gravity implements IGravity {
    protected boolean isGravitating = false;
    protected int progress = 0;
    protected int bucket = 24;
    protected int speed = 0;

    // initGravity
    @Override
    public abstract void initGravity();

    protected abstract boolean isAllowEntryDest();

    protected abstract Voxel getDest();

    protected abstract void begin();

    // stepGravity
    protected abstract void start(int speed);

    @Override
    public abstract void stepGravity();

    protected abstract void stop();

    // gravity
    protected abstract void gravity();

    // end
    protected abstract void end(int speed);

    @Override
    public boolean isGravitating() {
        return isGravitating;
    }

    protected abstract void setGravitating(int speed);

    protected abstract void clearGravitating();
}
