package io.github.uxodev.model.both.widget.instance.dynamic.transport;

import io.github.uxodev.model.both.widget._data.token.ContainToken;
import io.github.uxodev.model.both.widget.instance._objcomp.composite.Compositable;
import io.github.uxodev.model.both.widget.instance._objcomp.composite.Composite;
import io.github.uxodev.model.both.widget.instance._objcomp.composite.IComposite;
import io.github.uxodev.model.both.widget.instance._objcomp.composite.IStackable;
import io.github.uxodev.model.both.widget.instance._objcomp.container.Container;
import io.github.uxodev.model.both.widget.instance._objcomp.container.IContainable;
import io.github.uxodev.model.both.widget.instance._objcomp.container.IContainer;
import io.github.uxodev.model.both.widget.instance._objcomp.interaction.IPushable;
import io.github.uxodev.model.both.widget.instance._objcomp.interaction.Pushable;
import io.github.uxodev.model.both.widget.instance.dynamic.Dynamic;
import io.github.uxodev.model.both.widget.instance.part.Part;
import io.github.uxodev.model.both.widget.token.material.MaterialToken;
import io.github.uxodev.model.both.widget.token.shape.dynamic.transport.TransportShapeToken;
import io.github.uxodev.model.both.widget.token.source.Source;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;

public abstract class Transport extends Dynamic implements IComposite, IContainer, IPushable {
    private final TransportShapeToken transportShapeToken;
    private final Composite composite;
    private final Container container;
    private final Pushable pushable;

    Transport(Source source, MaterialToken materialToken, TransportShapeToken transportShapeToken,
              ArrayList<Part> parts) {
        super(source, materialToken, transportShapeToken);
        this.transportShapeToken = transportShapeToken;
        composite = (parts == null || parts.isEmpty())
                ? new Composite(new ArrayList<>(Arrays.asList(Compositable.createMirrorPart(this))))
                : new Composite(parts);
        container = new Container(transportShapeToken.containTokens, transportShapeToken.capacity);
        pushable = new Pushable();
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

    // IPushable
    @Override
    public void startPushing() {
        pushable.startPushing();
    }

    @Override
    public void stopPushing() {
        pushable.stopPushing();
    }

    @Override
    public void release() {
        pushable.release();
    }

    @Override
    public TransportShapeToken getShapeToken() {
        return transportShapeToken;
    }
}
