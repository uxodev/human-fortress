package io.github.uxodev.model.both.widget.token.shape.dynamic;

import io.github.uxodev.model.both.widget._data.token.ContainToken;
import io.github.uxodev.model.both.widget._data.token.UseToken;

import java.util.HashSet;

public class ItemContainerShapeToken extends DynamicShapeToken {
    public final HashSet<ContainToken> containTokens;
    public final int capacity;

    public ItemContainerShapeToken(String name, int volume, int multiplier, HashSet<UseToken> useTokens,
                                   HashSet<ContainToken> containTokens, int capacity) {
        super(name, volume, multiplier, useTokens);
        this.containTokens = containTokens;
        this.capacity = capacity;
    }
}
