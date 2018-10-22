package io.github.uxodev.model.both;

import io.github.uxodev.model.both._data.Game;
import io.github.uxodev.model.both._objcomp.lock.Lockable;
import io.github.uxodev.model.both.unit.instance.Unit;
import io.github.uxodev.model.both.widget.instance.dynamic.Dynamic;
import io.github.uxodev.model.both.widget.instance.stable.Furniture;
import io.github.uxodev.model.both.widget.instance.stable.terrain.TerrainFloor;
import io.github.uxodev.model.both.widget.instance.stable.terrain.TerrainWall;
import io.github.uxodev.model.city.map.voxel.Voxel;
import io.github.uxodev.model.city.zone.area.Area;
import io.github.uxodev.model.city.zone.area.sentient.BedroomArea;
import io.github.uxodev.model.city.zone.area.sentient.StockArea;
import io.github.uxodev.model.city.zone.match.Match;

import java.util.HashSet;

public class Transfer {
    public static void start(TerrainWall terrainWall, Voxel dest) {
        dest.supportedStables.setTerrainWall(terrainWall);
    }

    public static void start(TerrainFloor terrainFloor, Voxel dest) {
        dest.supportedStables.setTerrainFloor(terrainFloor);
    }

    public static void start(Furniture furniture, Voxel dest) {
        Game.currentMap.enclosedWidgets.add(furniture);
        dest.supportedStables.setFurniture(furniture);
    }

    public static void start(Dynamic dynamic, Voxel dest) {
        dynamic.loc = dest.map.getVoxelOrForbidden(-1, -1, -1);
        dynamic.start(dest);
    }

    public static void move(Dynamic dynamic, Voxel dest) {
        dynamic.move(dest);
    }

    public static void pickUp(Unit unit, Dynamic dynamic) {
        if (unit.held == null) {
            unit.pickUp(dynamic);
            dynamic.move(dynamic.loc.map.getVoxelOrForbidden(-1, -1, -1));
            dynamic.pickUp();
            dynamic.isPiled = false;
            System.out.println(unit + " picked up " + dynamic);
        }
    }

    public static void putDown(Unit unit, Dynamic dynamic, Voxel dest) {
        if (unit.held == dynamic) {
            unit.putDown(dynamic);
            dynamic.move(dest);
            dynamic.putDown();
            for (Area area : dynamic.loc.supportedZones.areas) {
                for (Match match : area.getMatches()) {
                    if (match.matches(dynamic)) {
                        dynamic.isPiled = true;
                    }
                }
            }
            dynamic.removeLock(Lockable.LockReason.HELD);
            System.out.println(unit + " put down " + dynamic);
        }
    }

    public static void drop(Unit unit, Dynamic dynamic) {
        putDown(unit, dynamic, unit.loc);
    }

    public static void removeDynamics(HashSet<Voxel> voxels) {

    }

    public static void start(Unit unit, Voxel dest) {
        unit.loc = dest.map.getVoxelOrForbidden(-1, -1, -1);
        unit.start(dest);
    }

    public static void move(Unit unit, Voxel dest) {
        unit.move(dest);
    }

    public static void removeUnits(HashSet<Voxel> voxels) {

    }

    public static <T extends Area> T start(T t, HashSet<Voxel> voxels) {
        switch (t.areaType) {
            default:
            case STOCK_AREA:
                Game.currentMap.enclosedZones.add(((StockArea) t));
                break;
            case BEDROOM:
                Game.currentMap.enclosedZones.add(((BedroomArea) t));
                break;
        }
        for (Voxel voxel : voxels) {
            voxel.supportedZones.areas.add(t);
        }
        return t;
    }

    public static void removeAreas(HashSet<Voxel> voxels) {

    }
}

