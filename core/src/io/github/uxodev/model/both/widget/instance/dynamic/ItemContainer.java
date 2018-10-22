package io.github.uxodev.model.both.widget.instance.dynamic;

import io.github.uxodev.model.both.widget._data.token.ContainToken;
import io.github.uxodev.model.both.widget.instance._objcomp.composite.Composite;
import io.github.uxodev.model.both.widget.instance._objcomp.composite.IComposite;
import io.github.uxodev.model.both.widget.instance._objcomp.composite.IStackable;
import io.github.uxodev.model.both.widget.instance._objcomp.container.Container;
import io.github.uxodev.model.both.widget.instance._objcomp.container.IContainable;
import io.github.uxodev.model.both.widget.instance._objcomp.container.IContainer;
import io.github.uxodev.model.both.widget.instance.part.Part;
import io.github.uxodev.model.both.widget.token.material.MaterialToken;
import io.github.uxodev.model.both.widget.token.shape.dynamic.ItemContainerShapeToken;
import io.github.uxodev.model.both.widget.token.source.Source;
import io.github.uxodev.model.city.map.voxel.Voxel;

import java.util.ArrayList;
import java.util.HashSet;

public class ItemContainer extends Dynamic implements IComposite, IContainer {
    private final ItemContainerShapeToken itemContainerShapeToken;
    private final Composite composite;
    private final Container container;

    public ItemContainer(Source source, MaterialToken materialToken, ItemContainerShapeToken itemContainerShapeToken,
                         ArrayList<Part> parts) {
        super(source, materialToken, itemContainerShapeToken);
        this.itemContainerShapeToken = itemContainerShapeToken;
        composite = new Composite(parts);
        container = new Container(itemContainerShapeToken.containTokens, itemContainerShapeToken.capacity);
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

    // IContainer
    @Override
    public int numCapacityFor(IContainable containable) {
        return container.numCapacityFor(containable);
    }

    @Override
    public int numCapacityFor(IStackable stackable) {
        return container.numCapacityFor(stackable);
    }

    @Override
    public boolean add(IContainable containable) {
        return container.add(containable);
    }

    @Override
    public boolean add(IStackable stackable) {
        return container.add(stackable);
    }

    @Override
    public boolean remove(IContainable containable) {
        return container.remove(containable);
    }

    @Override
    public boolean remove(IStackable stackable) {
        return container.remove(stackable);
    }

    @Override
    public ArrayList<IContainable> getContained() {
        return container.getContained();
    }

    @Override
    public HashSet<ContainToken> getContainTokens() {
        return container.getContainTokens();
    }

    @Override
    public int getCapacity() {
        return container.getCapacity();
    }

    @Override
    public ItemContainerShapeToken getShapeToken() {
        return itemContainerShapeToken;
    }
}
