package io.github.uxodev.model.both.unit.search;

import io.github.uxodev.model.both.widget.instance.dynamic.Dynamic;
import io.github.uxodev.model.city.zone.area.sentient.StockArea;

import java.util.Optional;

public class AreaAndDynamic {
    public Optional<StockArea> stockArea;
    public Optional<Dynamic> dynamic;

    public AreaAndDynamic(StockArea stockArea, Dynamic dynamic) {
        this.stockArea = Optional.ofNullable(stockArea);
        this.dynamic = Optional.ofNullable(dynamic);
    }
}
