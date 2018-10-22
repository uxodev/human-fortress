package io.github.uxodev.model.both.widget.instance.part;

import com.badlogic.gdx.graphics.Color;
import io.github.uxodev.model.both.widget._data.ShapeData;
import io.github.uxodev.model.both.widget._data.token.PropertyToken;
import io.github.uxodev.model.both.widget.instance.dynamic.Commodity;
import io.github.uxodev.model.both.widget.instance.dynamic.Item;
import io.github.uxodev.model.both.widget.token.material.MaterialToken;
import io.github.uxodev.model.both.widget.token.shape.part.PartShapeToken;
import io.github.uxodev.model.both.widget.token.source.Source;

import java.util.HashSet;

public class Part {
    public final Source source;
    public final MaterialToken materialToken;
    private final PartShapeToken partShapeToken;
    public final int quantity;
    public final String name;
    public final Color color;
    public Decoration decoration;

    public Part(Source source, MaterialToken materialToken, PartShapeToken partShapeToken) {
        this(source, materialToken, partShapeToken, 1);
    }

    public Part(Source source, MaterialToken materialToken, PartShapeToken partShapeToken, int quantity) {
        this.source = source;
        this.materialToken = materialToken;
        this.partShapeToken = partShapeToken;
        this.quantity = quantity;
        if (source.sourceToken2.name.equals(materialToken.name)) name = source.name + " " + partShapeToken.name;
        else name = source.name + " " + materialToken.name + " " + partShapeToken.name;
        color = source.color;
    }

    public Part(Commodity commodity) {
        this(commodity.source, commodity.materialToken,
                ShapeData.PART_SHAPE_TOKENS.get(commodity.getShapeToken().name), commodity.getQuantity());
    }

    public Part(Item item) {
        this(item.source, item.materialToken,
                ShapeData.PART_SHAPE_TOKENS.get(item.getShapeToken().name));
    }

    // internal
    public Source getSource() {
        return source;
    }

    public MaterialToken getMaterialToken() {
        return materialToken;
    }

    public PartShapeToken getShapeToken() {
        return partShapeToken;
    }

    public int getQuantity() {
        return quantity;
    }

    public String getName() {
        return name;
    }

    public Color getColor() {
        return color;
    }

    public int getVolume() {
        return getShapeToken().volume * quantity;
    }

    public int getWeight() {
        return source.density * getShapeToken().volume * quantity;
    }

    public int getValue() {
        return ((source.value * materialToken.multiplier * getShapeToken().multiplier) / 10000) * quantity;
    }

    public HashSet<PropertyToken> getPropertyTokens() {
        return source.propertyTokens;
    }

    @Override
    public String toString() {
        return name;
    }
}

