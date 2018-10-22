package io.github.uxodev.model.city.zone.match;

import io.github.uxodev.model.both.widget.instance.dynamic.Dynamic;
import io.github.uxodev.model.both.widget.instance.stable.Stable;

public class Matcher {
    public static boolean doesMatch(Dynamic dynamic, Match match) {
        if (match.source1 != null && !match.source1.equals(dynamic.source.sourceToken1.name))
            return false;
        if (match.source2 != null && !match.source2.equals(dynamic.source.sourceToken2.name))
            return false;
        if (match.material != null && !match.material.equals(dynamic.materialToken.name))
            return false;
        return match.shape == null || match.shape.equals(dynamic.getShapeToken().name);
    }

    public static boolean doesMatch(Stable stable, Match match) {
        if (match.source1 != null && !match.source1.equals(stable.source.sourceToken1.name))
            return false;
        if (match.source2 != null && !match.source2.equals(stable.source.sourceToken2.name))
            return false;
        if (match.material != null && !match.material.equals(stable.materialToken.name))
            return false;
        return match.shape == null || match.shape.equals(stable.getShapeToken().name);
    }
}
