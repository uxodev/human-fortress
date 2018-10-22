package io.github.uxodev.model.both.widget.instance.stable;

import io.github.uxodev.model.both.widget._data.token.PathToken;
import io.github.uxodev.model.both.widget.instance.Widget;
import io.github.uxodev.model.both.widget.instance._objcomp.token.IHasPathTokens;
import io.github.uxodev.model.both.widget.token.material.MaterialToken;
import io.github.uxodev.model.both.widget.token.shape.stable.StableShapeToken;
import io.github.uxodev.model.both.widget.token.source.Source;

import java.util.EnumSet;

public abstract class Stable extends Widget implements IHasPathTokens {
    private final StableShapeToken stableShapeToken;

    public Stable(Source source, MaterialToken materialToken, StableShapeToken stableShapeToken) {
        super(source, materialToken, stableShapeToken);
        this.stableShapeToken = stableShapeToken;
    }

    // IHasPathTokens
    @Override
    public EnumSet<PathToken> getPathTokens() {
        return stableShapeToken.pathTokens;
    }

    @Override
    public StableShapeToken getShapeToken() {
        return stableShapeToken;
    }
}
