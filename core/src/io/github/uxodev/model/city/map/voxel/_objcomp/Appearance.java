package io.github.uxodev.model.city.map.voxel._objcomp;

import com.badlogic.gdx.graphics.Color;
import io.github.uxodev.model.city.map.voxel.Voxel;

public class Appearance {
    private final Voxel parent;

    public Appearance(Voxel parent) {
        this.parent = parent;
    }

    public Color getDrawFloor() {
//        if (parent.flagAlert) return Color.GREEN;
        if (parent.supportedZones.hasArea()) return Color.CORAL;
        if (parent.isHovered) return Color.LIGHT_GRAY;
        else if (parent.isSelected) return Color.DARK_GRAY;
        else
            switch (parent.supportedStables.getStateFloor()) {
                default:
                case BLANK:
                    return Color.CLEAR;
                case TERRAIN:
                case IMPLIED:
                    if (parent.gravityCollapseStable.isGravitating()) return Color.PINK;
                    return parent.supportedStables.getTerrainFloor().color;
            }
    }

    public Color getDrawWall() {
//        if (zonesSystem.hasZone()) return Color.PINK;
        if (parent.isHovered) return Color.LIGHT_GRAY;
        else if (parent.isSelected) return Color.DARK_GRAY;
        else if (!parent.supportedUnits.units.isEmpty()) return parent.supportedUnits.units.get(0).getColor();
        else if (!parent.supportedDynamics.isEmpty()) return parent.supportedDynamics.getDynamics().get(0).color;
        else
            switch (parent.supportedStables.getStateWall()) {
                default:
                case BLANK:
                    return Color.CLEAR;
                case TERRAIN:
                    return parent.supportedStables.getTerrainWall().color;
                case FURNITURE:
                    return parent.supportedStables.getFurniture().color;
                case FURNITURE_CONTAINER:
                    return parent.supportedStables.getFurnitureContainer().color;
                case PLANT:
                    return parent.supportedStables.getPlantInstance().getColor();
            }
//        if (!sentientInstances.isEmpty()) return sentientInstances.get(0).getColor();
//        if (!machineInstances.isEmpty()) return machineInstances.get(0).getColor();
//        if (!containers.isEmpty()) return containers.get(0).getColor();
    }
}
