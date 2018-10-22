package io.github.uxodev.model.city.map.voxel.supported;

import io.github.uxodev.model.city.zone.Zone;
import io.github.uxodev.model.city.zone.area.Area;

import java.util.ArrayList;

public class SupportedZones {
    public final ArrayList<Zone> zones = new ArrayList<>();
    public final ArrayList<Area> areas = new ArrayList<>();

    public boolean hasZone() {
        return zones.size() > 0;
    }

    public boolean hasArea() {
        return areas.size() > 0;
    }

    public ArrayList<Zone> getZones() {
        return zones;
    }

    public void add(Zone zone) {
        zones.add(zone);
    }

    public void remove(Zone zone) {
        zones.remove(zone);
    }

    public void removeAllZones() {
        zones.clear();
    }
}
