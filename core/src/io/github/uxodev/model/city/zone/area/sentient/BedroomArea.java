package io.github.uxodev.model.city.zone.area.sentient;

import io.github.uxodev.model.city.map.voxel.Voxel;
import io.github.uxodev.model.city.zone.Zone;
import io.github.uxodev.model.city.zone.area.Area;

import java.util.HashSet;

public class BedroomArea extends Area {
    public BedroomArea(Zone parentZone, HashSet<Voxel> enclosedVoxels) {
        super(parentZone, AreaType.BEDROOM, enclosedVoxels);
    }
}
