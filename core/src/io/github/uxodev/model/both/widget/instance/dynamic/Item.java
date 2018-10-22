package io.github.uxodev.model.both.widget.instance.dynamic;

import io.github.uxodev.model.both.widget._data.token.ContainToken;
import io.github.uxodev.model.both.widget.instance._objcomp.composite.Composite;
import io.github.uxodev.model.both.widget.instance._objcomp.composite.ICompositable;
import io.github.uxodev.model.both.widget.instance._objcomp.composite.IComposite;
import io.github.uxodev.model.both.widget.instance._objcomp.container.Containable;
import io.github.uxodev.model.both.widget.instance._objcomp.container.IContainable;
import io.github.uxodev.model.both.widget.instance.part.Part;
import io.github.uxodev.model.both.widget.token.material.MaterialToken;
import io.github.uxodev.model.both.widget.token.shape.dynamic.ItemShapeToken;
import io.github.uxodev.model.both.widget.token.source.Source;
import io.github.uxodev.model.city.map.voxel.Voxel;

import java.util.ArrayList;

public class Item extends Dynamic implements ICompositable, IComposite, IContainable {
    private final ItemShapeToken itemShapeToken;
    private final Composite composite;
    private final Containable containable;

    public Item(Source source, MaterialToken materialToken, ItemShapeToken itemShapeToken,
                ArrayList<Part> parts) {
        super(source, materialToken, itemShapeToken);
        this.itemShapeToken = itemShapeToken;
        composite = new Composite(parts);
        containable = new Containable(this, itemShapeToken.containToken);
    }

    @Override
    public void start(Voxel dest) {
        loc.map.enclosedWidgets.remove(this);
        dest.map.enclosedWidgets.add(this);
        move(dest);
    }

    @Override
    public void move(Voxel dest) {
        loc.supportedDynamics.remove(this);
        dest.supportedDynamics.add(this);
        loc = dest;
    }

    // ICompositable
    @Override
    public Part turnIntoPart() {
        return new Part(this);
    }

    // IComposite
    @Override
    public ArrayList<Part> getParts() {
        return composite.getParts();
    }

    @Override
    public int getVolume() {
        return composite.getVolume();
    }

    @Override
    public int getWeight() {
        return composite.getWeight();
    }

    @Override
    public int getValue() {
        return composite.getValue();
    }

    // IContainable
    @Override
    public ContainToken getContainToken() {
        return containable.getContainToken();
    }

    @Override
    public boolean isContainable() {
        return containable.isContainable();
    }

    @Override
    public ItemShapeToken getShapeToken() {
        return itemShapeToken;
    }
}
