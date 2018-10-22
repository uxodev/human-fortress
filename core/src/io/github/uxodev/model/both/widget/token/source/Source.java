package io.github.uxodev.model.both.widget.token.source;

import com.badlogic.gdx.graphics.Color;
import io.github.uxodev.model.both.widget._data.token.PropertyToken;

import java.util.HashSet;

public class Source {
    public final SourceToken sourceToken1;
    public final SourceToken sourceToken2;
    public final Color color;
    public final int density;
    public final int value;
    public final HashSet<PropertyToken> propertyTokens;
    public final String name;

    public Source(SourceToken sourceToken1, SourceToken sourceToken2, Color color,
                  int density, int value, HashSet<PropertyToken> propertyTokens) {
        this.sourceToken1 = sourceToken1;
        this.sourceToken2 = sourceToken2;
        this.color = color;
        this.density = density;
        this.value = value;
        this.propertyTokens = propertyTokens;
        name = this.sourceToken1.name + " " + this.sourceToken2.name;
    }

    @Override
    public String toString() {
        return name + " " + value;
    }
}
