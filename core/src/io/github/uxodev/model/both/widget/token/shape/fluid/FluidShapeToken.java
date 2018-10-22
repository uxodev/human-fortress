package io.github.uxodev.model.both.widget.token.shape.fluid;

import io.github.uxodev.model.both.widget._data.token.ContainToken;
import io.github.uxodev.model.both.widget._data.token.UseToken;
import io.github.uxodev.model.both.widget.token.shape.WidgetShapeToken;

import java.util.HashSet;

public class FluidShapeToken extends WidgetShapeToken {
    public final ContainToken containToken;

    public FluidShapeToken(String name, int volume, int multiplier, HashSet<UseToken> useTokens,
                           ContainToken containToken) {
        super(name, volume, multiplier, useTokens);
        this.containToken = containToken;
    }
}
