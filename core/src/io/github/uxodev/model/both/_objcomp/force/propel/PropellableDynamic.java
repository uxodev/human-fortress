package io.github.uxodev.model.both._objcomp.force.propel;

import io.github.uxodev.model.both.widget.instance.dynamic.Dynamic;

public class PropellableDynamic implements IPropellable {
    private final Dynamic parent;

    public PropellableDynamic(Dynamic parent) {
        this.parent = parent;
    }

    @Override
    public void propel() {

    }
}
