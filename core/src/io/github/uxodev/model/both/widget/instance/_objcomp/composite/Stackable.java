package io.github.uxodev.model.both.widget.instance._objcomp.composite;

import io.github.uxodev.model.both.widget._data.token.ContainToken;
import io.github.uxodev.model.both.widget.instance._objcomp.container.Containable;

public class Stackable extends Containable implements IStackable {
    private final IStackable parent;

    private int quantity;

    public Stackable(IStackable parent, ContainToken containToken) {
        super(parent, containToken);
        this.parent = parent;
    }

    @Override
    public void increase(int quantityAdding) {
        quantity += quantityAdding;
    }

    @Override
    public void decrease(int quantityRemoving) {
        quantity -= quantityRemoving;
    }

    @Override
    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    @Override
    public int getQuantity() {
        return quantity;
    }

    @Override
    public IStackable copy() {
        return parent.copy();
    }
}
