package io.github.uxodev.model.both.widget.token.shape;

import io.github.uxodev.model.both.widget._data.token.UseToken;

import java.util.HashSet;

public abstract class WidgetShapeToken {
    public final String name;
    public final int volume;
    public final int multiplier;
    public final HashSet<UseToken> useTokens;

    public WidgetShapeToken(String name, int volume, int multiplier, HashSet<UseToken> useTokens) {
        this.name = name;
        this.volume = volume;
        this.multiplier = multiplier;
        this.useTokens = useTokens;
    }

    @Override
    public String toString() {
        return name;
    }
}
