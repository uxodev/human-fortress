package io.github.uxodev.model.both._objcomp.force.propel;

import io.github.uxodev.model.both.unit.instance.Unit;

public class PropellableUnit implements IPropellable {
    private final Unit parent;

    public PropellableUnit(Unit parent) {
        this.parent = parent;
    }

    @Override
    public void propel() {

    }
}
