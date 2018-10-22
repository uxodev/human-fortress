package io.github.uxodev.model.city.zone.match;

import io.github.uxodev.model.both.widget.instance.dynamic.Dynamic;
import io.github.uxodev.model.both.widget.instance.stable.Stable;

public class Match {
    public final String source1;
    public final String source2;
    public final String material;
    public final String shape;

    public Match(String source1, String source2, String material, String shape) {
        this.source1 = source1;
        this.source2 = source2;
        this.material = material;
        this.shape = shape;
    }

    public boolean matches(Dynamic dynamic) {
        if (source1 != null && !source1.equals(dynamic.source.sourceToken1.name))
            return false;
        if (source2 != null && !source2.equals(dynamic.source.sourceToken2.name))
            return false;
        if (material != null && !material.equals(dynamic.materialToken.name))
            return false;
        return shape == null || shape.equals(dynamic.getShapeToken().name);
    }

    public boolean matches(Stable stable) {
        if (source1 != null && !source1.equals(stable.source.sourceToken1.name))
            return false;
        if (source2 != null && !source2.equals(stable.source.sourceToken2.name))
            return false;
        if (material != null && !material.equals(stable.materialToken.name))
            return false;
        return shape == null || shape.equals(stable.getShapeToken().name);
    }
}
