package io.github.uxodev.model.both.widget.instance.fluid;

import io.github.uxodev.model.both.widget._data.token.ContainToken;
import io.github.uxodev.model.both.widget.instance.Widget;
import io.github.uxodev.model.both.widget.instance._objcomp.composite.IStackable;
import io.github.uxodev.model.both.widget.instance._objcomp.composite.Stackable;
import io.github.uxodev.model.both.widget.instance._objcomp.flow.Flowable;
import io.github.uxodev.model.both.widget.instance._objcomp.flow.IFlowable;
import io.github.uxodev.model.both.widget.token.material.MaterialToken;
import io.github.uxodev.model.both.widget.token.shape.fluid.FluidShapeToken;
import io.github.uxodev.model.both.widget.token.source.Source;

public class Fluid extends Widget implements IStackable, IFlowable {
    private final FluidShapeToken fluidShapeToken;
    private final Stackable stackable;
    private final Flowable flowable;

    public Fluid(Source source, MaterialToken materialToken, FluidShapeToken fluidShapeToken) {
        super(source, materialToken, fluidShapeToken);
        this.fluidShapeToken = fluidShapeToken;
        stackable = new Stackable(this, fluidShapeToken.containToken);
        flowable = new Flowable();
    }

    private Fluid(Fluid copy) {
        super(copy.source, copy.materialToken, copy.fluidShapeToken);
        this.fluidShapeToken = copy.fluidShapeToken;
        stackable = new Stackable(this, fluidShapeToken.containToken);
        flowable = new Flowable();
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
    public Fluid copy() {
        return new Fluid(this);
    }

    // IFlowable
    //

    @Override
    public FluidShapeToken getShapeToken() {
        return fluidShapeToken;
    }
}
