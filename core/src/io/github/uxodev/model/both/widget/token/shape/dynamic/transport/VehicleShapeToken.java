package io.github.uxodev.model.both.widget.token.shape.dynamic.transport;

import io.github.uxodev.model.both.widget._data.token.ContainToken;
import io.github.uxodev.model.both.widget._data.token.UseToken;

import java.util.HashSet;

public class VehicleShapeToken extends TransportShapeToken {
    public VehicleShapeToken(String name, int volume, int multiplier, HashSet<UseToken> useTokens,
                             HashSet<ContainToken> containTokens, int capacity) {
        super(name, volume, multiplier, useTokens, containTokens, capacity);
    }
}
