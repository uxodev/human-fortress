package io.github.uxodev.model.both.widget.token.shape.dynamic;

import io.github.uxodev.model.both.widget._data.token.ContainToken;
import io.github.uxodev.model.both.widget._data.token.UseToken;

import java.util.HashSet;

public class CommodityShapeToken extends DynamicShapeToken {
    public final ContainToken containToken;

    public CommodityShapeToken(String name, int volume, int multiplier, HashSet<UseToken> useTokens,
                               ContainToken containToken) {
        super(name, volume, multiplier, useTokens);
        this.containToken = containToken;
    }
}
