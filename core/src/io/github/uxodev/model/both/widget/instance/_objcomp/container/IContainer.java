package io.github.uxodev.model.both.widget.instance._objcomp.container;

import io.github.uxodev.model.both.widget._data.token.ContainToken;
import io.github.uxodev.model.both.widget.instance._objcomp.composite.IStackable;

import java.util.ArrayList;
import java.util.HashSet;

public interface IContainer {
    int numCapacityFor(IContainable containable);
    int numCapacityFor(IStackable stackable);

    boolean add(IContainable containable);
    boolean add(IStackable stackable);

    boolean remove(IContainable containable);
    boolean remove(IStackable stackable);

    ArrayList<IContainable> getContained();

    HashSet<ContainToken> getContainTokens();
    int getCapacity();
}
