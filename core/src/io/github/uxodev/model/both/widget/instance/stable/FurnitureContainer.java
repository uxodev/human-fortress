package io.github.uxodev.model.both.widget.instance.stable;

import io.github.uxodev.model.both.widget._data.token.ContainToken;
import io.github.uxodev.model.both.widget.instance._objcomp.composite.Composite;
import io.github.uxodev.model.both.widget.instance._objcomp.composite.IComposite;
import io.github.uxodev.model.both.widget.instance._objcomp.composite.IStackable;
import io.github.uxodev.model.both.widget.instance._objcomp.container.Container;
import io.github.uxodev.model.both.widget.instance._objcomp.container.IContainable;
import io.github.uxodev.model.both.widget.instance._objcomp.container.IContainer;
import io.github.uxodev.model.both.widget.instance.part.Part;
import io.github.uxodev.model.both.widget.token.material.MaterialToken;
import io.github.uxodev.model.both.widget.token.shape.stable.FurnitureContainerShapeToken;
import io.github.uxodev.model.both.widget.token.source.Source;

import java.util.ArrayList;
import java.util.HashSet;

public class FurnitureContainer extends Stable implements IComposite, IContainer {
    private final FurnitureContainerShapeToken furnitureContainerShapeToken;
    private final Composite composite;
    private final Container container;

    public FurnitureContainer(Source source, MaterialToken materialToken, FurnitureContainerShapeToken furnitureContainerShapeToken,
                              ArrayList<Part> parts) {
        super(source, materialToken, furnitureContainerShapeToken);
        this.furnitureContainerShapeToken = furnitureContainerShapeToken;
        composite = new Composite(parts);
        container = new Container(furnitureContainerShapeToken.containTokens, furnitureContainerShapeToken.capacity);
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
    public FurnitureContainerShapeToken getShapeToken() {
        return furnitureContainerShapeToken;
    }
}
