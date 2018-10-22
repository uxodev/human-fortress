package io.github.uxodev.model.both.widget.instance._objcomp.container;

import io.github.uxodev.model.both.widget._data.token.ContainToken;
import io.github.uxodev.model.both.widget.instance._objcomp.composite.IStackable;
import io.github.uxodev.model.both.widget.instance.dynamic.Item;

import java.util.ArrayList;
import java.util.HashSet;

public class Container implements IContainer {
    private final HashSet<ContainToken> containTokens;
    private final int capacity;

    private int remainingCapacity;
    private ArrayList<IContainable> contained = new ArrayList<>();

    public Container(HashSet<ContainToken> containTokens, int capacity) {
        this.containTokens = containTokens;
        this.capacity = capacity;
        remainingCapacity = capacity;
    }

    @Override
    public int numCapacityFor(IContainable containable) {
        if ((contained.size() == 0 || contained.get(0).getClass().equals(Item.class)) // empty or not stack
                && containTokens.contains(containable.getContainToken())) // and accepts token
            return remainingCapacity / containable.getVolume();
        return 0;
    }

    @Override
    public int numCapacityFor(IStackable stackable) {
        if ((contained.size() == 0 || contained.get(0).equals(stackable)) // empty or same stack
                && containTokens.contains(stackable.getContainToken())) // and accepts token
            return remainingCapacity / stackable.getVolume();
        return 0;
    }

    @Override
    public boolean add(IContainable containable) {
        if (numCapacityFor(containable) > 0) {
            contained.add(containable);
            remainingCapacity -= containable.getVolume();
            return true;
        } else return false;
    }

    @Override
    public boolean add(IStackable stackable) {
        if (numCapacityFor(stackable) > 0) {
            // actual quantity that will get added is min of as many as can fit or as many as are in stackable
            int quantityAdding = Math.min(numCapacityFor(stackable), remainingCapacity);
            // if empty, copy an empty stackable before increasing
            if (contained.size() == 0) contained.add(stackable.copy());
            ((IStackable) contained.get(0)).increase(quantityAdding); // this IContainable must be an IStackable because it passed numCapacityFor(IStackable)
            stackable.decrease(quantityAdding);
            remainingCapacity -= quantityAdding * stackable.getVolume();
            return true;
        } else return false;
    }

    @Override
    public boolean remove(IContainable containable) {
        if (contained.contains(containable)) {
            contained.remove(containable);
            remainingCapacity += containable.getVolume();
            return true;
        } else return false;
    }

    // if moving into another container, use add(IStackable), which also decreases what was added from source IStackable
    @Override
    public boolean remove(IStackable stackable) {
        if (contained.get(0).equals(stackable)) {
            contained.remove(stackable);
            remainingCapacity += stackable.getVolume();
            return true;
        } else return false;
    }

    @Override
    public ArrayList<IContainable> getContained() {
        return contained;
    }

    @Override
    public HashSet<ContainToken> getContainTokens() {
        return containTokens;
    }

    @Override
    public int getCapacity() {
        return capacity;
    }
}
