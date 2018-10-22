package io.github.uxodev.model.both.widget.token.shape.stable;

import io.github.uxodev.model.both.widget._data.token.ContainToken;
import io.github.uxodev.model.both.widget._data.token.PathToken;
import io.github.uxodev.model.both.widget._data.token.UseToken;

import java.util.EnumSet;
import java.util.HashSet;

public class FurnitureContainerShapeToken extends StableShapeToken {
    public final HashSet<ContainToken> containTokens;
    public final int capacity;

    public FurnitureContainerShapeToken(String name, int volume, int multiplier, HashSet<UseToken> useTokens, EnumSet<PathToken> pathTokens,
                                        HashSet<ContainToken> containTokens, int capacity) {
        super(name, volume, multiplier, useTokens, pathTokens);
        this.containTokens = containTokens;
        this.capacity = capacity;
    }
}
