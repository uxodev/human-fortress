package io.github.uxodev.model.both.widget.token.shape.stable;

import io.github.uxodev.model.both.widget._data.token.PathToken;
import io.github.uxodev.model.both.widget._data.token.UseToken;

import java.util.EnumSet;
import java.util.HashSet;

public class FurnitureShapeToken extends StableShapeToken {
    public FurnitureShapeToken(String name, int volume, int multiplier, HashSet<UseToken> useTokens, EnumSet<PathToken> pathTokens) {
        super(name, volume, multiplier, useTokens, pathTokens);
    }
}
