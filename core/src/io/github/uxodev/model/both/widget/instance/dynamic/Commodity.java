package io.github.uxodev.model.both.widget.instance.dynamic;

import io.github.uxodev.model.both.widget._data.token.ContainToken;
import io.github.uxodev.model.both.widget.instance._objcomp.composite.ICompositable;
import io.github.uxodev.model.both.widget.instance._objcomp.composite.IStackable;
import io.github.uxodev.model.both.widget.instance._objcomp.composite.Stackable;
import io.github.uxodev.model.both.widget.instance.part.Part;
import io.github.uxodev.model.both.widget.token.material.MaterialToken;
import io.github.uxodev.model.both.widget.token.shape.dynamic.CommodityShapeToken;
import io.github.uxodev.model.both.widget.token.source.Source;
import io.github.uxodev.model.city.map.voxel.Voxel;

public class Commodity extends Dynamic implements ICompositable, IStackable {
    private final CommodityShapeToken commodityShapeToken;
    private final Stackable stackable;

    public Commodity(Source source, MaterialToken materialToken, CommodityShapeToken commodityShapeToken) {
        super(source, materialToken, commodityShapeToken);
        this.commodityShapeToken = commodityShapeToken;
        stackable = new Stackable(this, commodityShapeToken.containToken);
    }

    private Commodity(Commodity copy) {
        super(copy.source, copy.materialToken, copy.commodityShapeToken);
        this.commodityShapeToken = copy.commodityShapeToken;
        stackable = new Stackable(this, commodityShapeToken.containToken);
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

    // IStackable
    @Override
    public ContainToken getContainToken() {
        return stackable.getContainToken();
    }

    @Override
    public boolean isContainable() {
        return stackable.isContainable();
    }

    @Override
    public void increase(int quantityAdding) {
        stackable.increase(quantityAdding);
    }

    @Override
    public void decrease(int quantityRemoving) {
        stackable.decrease(quantityRemoving);
    }

    @Override
    public void setQuantity(int quantity) {
        stackable.setQuantity(quantity);
    }

    @Override
    public int getQuantity() {
        return stackable.getQuantity();
    }

    @Override
    public Commodity copy() {
        return new Commodity(this);
    }

    @Override
    public CommodityShapeToken getShapeToken() {
        return commodityShapeToken;
    }
}
