package io.github.uxodev.model.both.widget.token.shape.dynamic;

import io.github.uxodev.model.both.widget._data.token.UseToken;
import io.github.uxodev.model.both.widget.token.shape.WidgetShapeToken;

import java.util.HashSet;

public abstract class DynamicShapeToken extends WidgetShapeToken {
    public DynamicShapeToken(String name, int volume, int multiplier, HashSet<UseToken> useTokens) {
        super(name, volume, multiplier, useTokens);
    }
}
