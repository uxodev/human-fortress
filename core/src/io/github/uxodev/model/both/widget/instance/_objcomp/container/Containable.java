package io.github.uxodev.model.both.widget.instance._objcomp.container;

import io.github.uxodev.model.both.widget._data.token.ContainToken;

public class Containable implements IContainable {
    private final IContainable parent;
    private final ContainToken containToken;

    public Containable(IContainable parent, ContainToken containToken) {
        this.parent = parent;
        this.containToken = containToken;
    }

    @Override
    public ContainToken getContainToken() {
        return containToken;
    }

    @Override
    public int getVolume() {
        return parent.getVolume();
    }

    @Override
    public boolean isContainable() {
        return !containToken.name.equals("");
    }
}
