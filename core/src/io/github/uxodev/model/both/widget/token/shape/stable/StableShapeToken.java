package io.github.uxodev.model.both.widget.token.shape.stable;

import io.github.uxodev.model.both.widget._data.token.PathToken;
import io.github.uxodev.model.both.widget._data.token.UseToken;
import io.github.uxodev.model.both.widget.token.shape.WidgetShapeToken;

import java.util.EnumSet;
import java.util.HashSet;

public abstract class StableShapeToken extends WidgetShapeToken {
    public final EnumSet<PathToken> pathTokens;

    public StableShapeToken(String name, int volume, int multiplier, HashSet<UseToken> useTokens,
                            EnumSet<PathToken> pathTokens) {
        super(name, volume, multiplier, useTokens);
        this.pathTokens = pathTokens;
    }
}
