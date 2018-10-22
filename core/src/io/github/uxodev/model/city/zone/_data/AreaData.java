package io.github.uxodev.model.city.zone._data;

import io.github.uxodev.model.city.zone.area.Area;
import io.github.uxodev.model.city.zone.match.Match;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.EnumMap;

import static io.github.uxodev.model.city.zone.area.Area.AreaType.BEDROOM;
import static io.github.uxodev.model.city.zone.area.Area.AreaType.STOCK_AREA;

public class AreaData {
    public static final EnumMap<Area.AreaType, ArrayList<Match>> AREA_REQ_DISPLAY_ORDER = new EnumMap<>(Area.AreaType.class);

    static {
        AREA_REQ_DISPLAY_ORDER.put(STOCK_AREA,
                new ArrayList<>(Arrays.asList(
                        new Match(null, null, null, "table")
                )));
        AREA_REQ_DISPLAY_ORDER.put(BEDROOM,
                new ArrayList<>(Arrays.asList(
                        new Match(null, null, null, "bed")
                )));
    }
}
