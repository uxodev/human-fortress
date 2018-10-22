package io.github.uxodev.model.both.widget.token.shape.dynamic.transport;

import io.github.uxodev.model.both.widget._data.token.ContainToken;
import io.github.uxodev.model.both.widget._data.token.UseToken;
import io.github.uxodev.model.both.widget.token.shape.dynamic.DynamicShapeToken;

import java.util.HashSet;

public abstract class TransportShapeToken extends DynamicShapeToken {
    public final HashSet<ContainToken> containTokens;
    public final int capacity;

    public TransportShapeToken(String name, int volume, int multiplier, HashSet<UseToken> useTokens,
                               HashSet<ContainToken> containTokens, int capacity) {
        super(name, volume, multiplier, useTokens);
        this.containTokens = containTokens;
        this.capacity = capacity;
    }
}
