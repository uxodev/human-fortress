package io.github.uxodev.model.city.map.enclosed;

import io.github.uxodev.model.city.zone.Zone;
import io.github.uxodev.model.city.zone.area.Area;
import io.github.uxodev.model.city.zone.area.sentient.BedroomArea;
import io.github.uxodev.model.city.zone.area.sentient.StockArea;

import java.util.ArrayList;

public class EnclosedZones {
    public final ArrayList<Zone> zones = new ArrayList<>();

    public final ArrayList<Area> areas = new ArrayList<>();
    public final ArrayList<StockArea> stockAreas = new ArrayList<>();
    public final ArrayList<BedroomArea> bedroomAreas = new ArrayList<>();

    public void add(Zone zone) {
        zones.add(zone);
    }

    public void remove(Zone zone) {
        zones.remove(zone);
    }

    public void add(StockArea stockArea) {
        areas.add(stockArea);
        stockAreas.add(stockArea);
    }

    public void add(BedroomArea bedroomArea) {
        areas.add(bedroomArea);
        bedroomAreas.add(bedroomArea);
    }

    public String count() {
        return "Areas:" + "\n" +
                "areas:" + areas.size() + "\n";
    }
}
