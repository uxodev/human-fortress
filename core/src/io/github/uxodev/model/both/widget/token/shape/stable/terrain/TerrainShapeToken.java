package io.github.uxodev.model.both.widget.token.shape.stable.terrain;

import io.github.uxodev.model.both.widget._data.token.PathToken;
import io.github.uxodev.model.both.widget.token.shape.stable.StableShapeToken;

import java.util.EnumSet;
import java.util.HashSet;

public class TerrainShapeToken extends StableShapeToken {
    public TerrainShapeToken(String name, int volume, int multiplier, EnumSet<PathToken> pathTokens) {
        super(name, volume, multiplier, new HashSet<>(), pathTokens);
    }
}
