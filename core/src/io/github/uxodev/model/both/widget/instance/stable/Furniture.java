package io.github.uxodev.model.both.widget.instance.stable;

import io.github.uxodev.model.both.widget.instance._objcomp.composite.Composite;
import io.github.uxodev.model.both.widget.instance._objcomp.composite.IComposite;
import io.github.uxodev.model.both.widget.instance.part.Part;
import io.github.uxodev.model.both.widget.token.material.MaterialToken;
import io.github.uxodev.model.both.widget.token.shape.stable.FurnitureShapeToken;
import io.github.uxodev.model.both.widget.token.source.Source;

import java.util.ArrayList;

public class Furniture extends Stable implements IComposite {
    private final FurnitureShapeToken furnitureShapeToken;
    private final Composite composite;

    public Furniture(Source source, MaterialToken materialToken, FurnitureShapeToken furnitureShapeToken,
                     ArrayList<Part> parts) {
        super(source, materialToken, furnitureShapeToken);
        this.furnitureShapeToken = furnitureShapeToken;
        composite = new Composite(parts);
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

    @Override
    public FurnitureShapeToken getShapeToken() {
        return furnitureShapeToken;
    }
}
