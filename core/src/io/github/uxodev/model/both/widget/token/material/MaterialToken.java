package io.github.uxodev.model.both.widget.token.material;

import io.github.uxodev.model.both.widget._data.token.PropertyToken;

import java.util.HashSet;

public class MaterialToken {
    public final String name;
    public final int multiplier;
    public final HashSet<PropertyToken> propertyTokens;

    public MaterialToken(String name, int multiplier, HashSet<PropertyToken> propertyTokens) {
        this.name = name;
        this.multiplier = multiplier;
        this.propertyTokens = propertyTokens;
    }

    @Override
    public String toString() {
        return name;
    }
}
