package io.github.uxodev.libgdx.display.ui.buttonPanel;

import io.github.uxodev.model.both.Transfer;
import io.github.uxodev.model.both._data.Game;
import io.github.uxodev.model.both.unit.instance.sentient.Sentient;
import io.github.uxodev.model.both.widget._data.MaterialData;
import io.github.uxodev.model.both.widget._data.ShapeData;
import io.github.uxodev.model.both.widget._data.SourceData;
import io.github.uxodev.model.both.widget.instance.dynamic.Item;
import io.github.uxodev.model.both.widget.instance.stable.Furniture;
import io.github.uxodev.model.both.widget.instance.stable.terrain.TerrainFloor;
import io.github.uxodev.model.both.widget.instance.stable.terrain.TerrainWall;
import io.github.uxodev.model.city.map.voxel.Voxel;
import io.github.uxodev.model.city.zone.area.sentient.StockArea;
import io.github.uxodev.model.city.zone.match.Match;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;

// static class
public class ButtonPanelEvents {
    public static void dig() {
        for (Voxel voxel : Game.currentMap.voxelSelect.volumeSelected) {
            voxel.supportedStables.dig();
        }
    }

    public static void addRamp() {
        for (Voxel voxel : Game.currentMap.voxelSelect.volumeSelected) {
            voxel.supportedStables.setTerrainWall(new TerrainWall(
                    SourceData.SOURCE_TEMPLATES.get("loam").get("soil"),
                    MaterialData.MATERIAL_TOKENS.get("soil"),
                    ShapeData.TERRAIN_SHAPE_TOKENS.get("ramp")));
        }
    }

    public static void addStair() {
        for (Voxel voxel : Game.currentMap.voxelSelect.volumeSelected) {
            voxel.supportedStables.setTerrainWall(new TerrainWall(
                    SourceData.SOURCE_TEMPLATES.get("ruby").get("gem"),
                    MaterialData.MATERIAL_TOKENS.get("gem"),
                    ShapeData.TERRAIN_SHAPE_TOKENS.get("stair")));
        }
    }

    public static void removeWall() {
        for (Voxel voxel : Game.currentMap.voxelSelect.volumeSelected) {
            voxel.supportedStables.removeTerrainWall();
        }
    }

    public static void addWall(int i) {
        for (Voxel voxel : Game.currentMap.voxelSelect.volumeSelected) {
            switch (i) {
                default:
                case 1:
                    Transfer.start(new TerrainWall(
                                    SourceData.SOURCE_TEMPLATES.get("granite").get("stone"),
                                    MaterialData.MATERIAL_TOKENS.get("stone"),
                                    ShapeData.TERRAIN_SHAPE_TOKENS.get("wall")),
                            voxel);
                    break;
                case 2:
                    Transfer.start(new TerrainWall(
                                    SourceData.SOURCE_TEMPLATES.get("marble").get("stone"),
                                    MaterialData.MATERIAL_TOKENS.get("stone"),
                                    ShapeData.TERRAIN_SHAPE_TOKENS.get("wall")),
                            voxel);
                    break;
                case 3:
                    Transfer.start(new TerrainWall(
                                    SourceData.SOURCE_TEMPLATES.get("loam").get("soil"),
                                    MaterialData.MATERIAL_TOKENS.get("soil"),
                                    ShapeData.TERRAIN_SHAPE_TOKENS.get("wall")),
                            voxel);
                    break;
                case 4:
                    Transfer.start(new TerrainWall(
                                    SourceData.SOURCE_TEMPLATES.get("clay").get("soil"),
                                    MaterialData.MATERIAL_TOKENS.get("soil"),
                                    ShapeData.TERRAIN_SHAPE_TOKENS.get("wall")),
                            voxel);
                    break;
                case 5:
                    Transfer.start(new TerrainWall(
                                    SourceData.SOURCE_TEMPLATES.get("sand").get("soil"),
                                    MaterialData.MATERIAL_TOKENS.get("soil"),
                                    ShapeData.TERRAIN_SHAPE_TOKENS.get("wall")),
                            voxel);
                    break;
            }
        }
    }

    public static void removeFloor() {
        for (Voxel voxel : Game.currentMap.voxelSelect.volumeSelected) {
            voxel.supportedStables.removeTerrainFloor();
        }
    }

    public static void addFloor(int i) {
        for (Voxel voxel : Game.currentMap.voxelSelect.volumeSelected) {
            switch (i) {
                default:
                case 1:
                    Transfer.start(new TerrainFloor(
                                    SourceData.SOURCE_TEMPLATES.get("granite").get("stone"),
                                    MaterialData.MATERIAL_TOKENS.get("stone"),
                                    ShapeData.TERRAIN_SHAPE_TOKENS.get("floor")),
                            voxel);
                    break;
                case 2:
                    Transfer.start(new TerrainFloor(
                                    SourceData.SOURCE_TEMPLATES.get("marble").get("stone"),
                                    MaterialData.MATERIAL_TOKENS.get("stone"),
                                    ShapeData.TERRAIN_SHAPE_TOKENS.get("floor")),
                            voxel);
                    break;
                case 3:
                    Transfer.start(new TerrainFloor(
                                    SourceData.SOURCE_TEMPLATES.get("loam").get("soil"),
                                    MaterialData.MATERIAL_TOKENS.get("soil"),
                                    ShapeData.TERRAIN_SHAPE_TOKENS.get("floor")),
                            voxel);
                    break;
                case 4:
                    Transfer.start(new TerrainFloor(
                                    SourceData.SOURCE_TEMPLATES.get("clay").get("soil"),
                                    MaterialData.MATERIAL_TOKENS.get("soil"),
                                    ShapeData.TERRAIN_SHAPE_TOKENS.get("floor")),
                            voxel);
                    break;
                case 5:
                    Transfer.start(new TerrainFloor(
                                    SourceData.SOURCE_TEMPLATES.get("sand").get("soil"),
                                    MaterialData.MATERIAL_TOKENS.get("soil"),
                                    ShapeData.TERRAIN_SHAPE_TOKENS.get("floor")),
                            voxel);
                    break;
            }
        }
    }

    public static void createTree() {

    }

    public static void addBranch() {

    }

    public static void addTrunk() {

    }

    public static void removeFurniture() {
        for (Voxel voxel : Game.currentMap.voxelSelect.volumeSelected) {
            voxel.supportedStables.removeFurniture();
        }
    }

    public static void addFurniture(int i) {
        for (Voxel voxel : Game.currentMap.voxelSelect.volumeSelected) {
            switch (i) {
                default:
                case 1:
                    Transfer.start(new Furniture(
                                    SourceData.SOURCE_TEMPLATES.get("marble").get("stone"),
                                    MaterialData.MATERIAL_TOKENS.get("stone"),
                                    ShapeData.FURNITURE_SHAPES.get("table"),
                                    new ArrayList<>()),
                            voxel);
                    break;
                case 2:
                    Transfer.start(new Furniture(
                                    SourceData.SOURCE_TEMPLATES.get("cat").get("skin"),
                                    MaterialData.MATERIAL_TOKENS.get("leather"),
                                    ShapeData.FURNITURE_SHAPES.get("bed"),
                                    new ArrayList<>()),
                            voxel);
                    break;
            }
        }
    }

    public static void removeItem() {
        Transfer.removeDynamics(Game.currentMap.voxelSelect.volumeSelected);
    }

    public static void addItem(int i) {
        for (Voxel voxel : Game.currentMap.voxelSelect.volumeSelected) {
            switch (i) {
                default:
                case 1:
                    Transfer.start(new Item(
                                    SourceData.SOURCE_TEMPLATES.get("dog").get("skin"),
                                    MaterialData.MATERIAL_TOKENS.get("leather"),
                                    ShapeData.ITEM_SHAPES.get("figurine"),
                                    new ArrayList<>()),
                            voxel);
                    break;
                case 2:
                    Transfer.start(new Item(
                                    SourceData.SOURCE_TEMPLATES.get("jade").get("stone"),
                                    MaterialData.MATERIAL_TOKENS.get("stone"),
                                    ShapeData.ITEM_SHAPES.get("figurine"),
                                    new ArrayList<>()),
                            voxel);
                    break;
                case 3:
                    Transfer.start(new Item(
                                    SourceData.SOURCE_TEMPLATES.get("copper").get("ore"),
                                    MaterialData.MATERIAL_TOKENS.get("metal"),
                                    ShapeData.ITEM_SHAPES.get("spear"),
                                    new ArrayList<>()),
                            voxel);
                    break;
                case 4:
                    Transfer.start(new Item(
                                    SourceData.SOURCE_TEMPLATES.get("silver").get("ore"),
                                    MaterialData.MATERIAL_TOKENS.get("metal"),
                                    ShapeData.ITEM_SHAPES.get("flute"),
                                    new ArrayList<>()),
                            voxel);
                    break;
                case 5:
                    Transfer.start(new Item(
                                    SourceData.SOURCE_TEMPLATES.get("gold").get("ore"),
                                    MaterialData.MATERIAL_TOKENS.get("metal"),
                                    ShapeData.ITEM_SHAPES.get("figurine"),
                                    new ArrayList<>()),
                            voxel);
                    break;
            }
        }
    }

    public static void removeUnit() {
        Transfer.removeUnits(Game.currentMap.voxelSelect.volumeSelected);
    }

    public static void addUnit(int i) {
        for (Voxel voxel : Game.currentMap.voxelSelect.volumeSelected) {
            switch (i) {
                default:
                case 1:
                    Transfer.start(new Sentient(), voxel);
                    break;
                case 2:

                    break;
            }
        }
    }

    public static void removeArea() {
        Transfer.removeAreas(Game.currentMap.voxelSelect.volumeSelected);
    }

    public static void addArea(int i) {
        HashSet<Voxel> voxels = new HashSet<>(Game.currentMap.voxelSelect.volumeSelected);
        StockArea area = Transfer.start(new StockArea(null, voxels, new ArrayList<>()), voxels);
        switch (i) {
            default:
            case 1:
                area.addMatches(new ArrayList<>(Arrays.asList(new Match(null, null, "leather", null))));
                break;
            case 2:
                area.addMatches(new ArrayList<>(Arrays.asList(new Match("jade", null, null, null))));
                break;
            case 3:
                area.addMatches(new ArrayList<>(Arrays.asList(new Match("copper", null, null, null))));
                break;
            case 4:
                area.addMatches(new ArrayList<>(Arrays.asList(new Match(null, null, null, "figurine"))));
                break;
            case 5:
                area.addMatches(new ArrayList<>(Arrays.asList(new Match(null, null, "metal", null))));
                break;
        }
    }
}
