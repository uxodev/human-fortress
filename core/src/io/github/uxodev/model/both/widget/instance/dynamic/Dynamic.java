package io.github.uxodev.model.both.widget.instance.dynamic;

import io.github.uxodev.model.both.Transfer;
import io.github.uxodev.model.both._objcomp.force.gravity.GravityFallDynamic;
import io.github.uxodev.model.both._objcomp.force.gravity.IGravityLock;
import io.github.uxodev.model.both._objcomp.force.propel.IPropellable;
import io.github.uxodev.model.both._objcomp.force.propel.PropellableDynamic;
import io.github.uxodev.model.both.widget.instance.Widget;
import io.github.uxodev.model.both.widget.instance._objcomp.interaction.Holdable;
import io.github.uxodev.model.both.widget.instance._objcomp.interaction.IHoldable;
import io.github.uxodev.model.both.widget.token.material.MaterialToken;
import io.github.uxodev.model.both.widget.token.shape.dynamic.DynamicShapeToken;
import io.github.uxodev.model.both.widget.token.source.Source;
import io.github.uxodev.model.city.map.voxel.Voxel;

import static io.github.uxodev.model.both._objcomp.lock.Lockable.LockReason.*;

public abstract class Dynamic extends Widget implements IGravityLock, IPropellable, IHoldable {
    private final DynamicShapeToken dynamicShapeToken;
    private final GravityFallDynamic gravityFallDynamic;
    private final PropellableDynamic propellableDynamic;
    private final Holdable holdable;
    public boolean isPiled = false;

    public Dynamic(Source source, MaterialToken materialToken, DynamicShapeToken dynamicShapeToken) {
        super(source, materialToken, dynamicShapeToken);
        this.dynamicShapeToken = dynamicShapeToken;
        gravityFallDynamic = new GravityFallDynamic(this);
        propellableDynamic = new PropellableDynamic(this);
        holdable = new Holdable();
    }

    public abstract void start(Voxel dest);

    public abstract void move(Voxel dest);

    // IGravityLock
    @Override
    public void notifyBegin(int speed) {
        addLock(COLLAPSING);
    }

    @Override
    public void forceMove(Voxel below) {
        Transfer.move(this, below);
    }

    @Override
    public void notifyEnd(int speed) {
        removeLock(COLLAPSING);
        removeLock(FALLING);
        removeLock(PROPELLING);
        damage(speed);
    }

    @Override
    public void forceCrushed() {
        destroy();
    }

    @Override
    public void initGravity() {
        gravityFallDynamic.initGravity();
    }

    @Override
    public void stepGravity() {
        gravityFallDynamic.stepGravity();
    }

    @Override
    public boolean isGravitating() {
        return gravityFallDynamic.isGravitating();
    }

    // IPropellable
    @Override
    public void propel() {
        propellableDynamic.propel();
    }

    // IHoldable
    @Override
    public void pickUp() {
        addLock(HELD);
    }

    @Override
    public void putDown() {
        removeLock(HELD);
    }

    @Override
    public void drop() {
        removeLock(HELD);
    }

    @Override
    public DynamicShapeToken getShapeToken() {
        return dynamicShapeToken;
    }
}
