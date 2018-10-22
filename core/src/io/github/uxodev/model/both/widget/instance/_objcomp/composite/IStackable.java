package io.github.uxodev.model.both.widget.instance._objcomp.composite;

import io.github.uxodev.model.both.widget.instance._objcomp.container.IContainable;

public interface IStackable extends IContainable {
    void increase(int quantityAdding);
    void decrease(int quantityRemoving);
    void setQuantity(int quantity);
    int getQuantity();
    IStackable copy();
}
