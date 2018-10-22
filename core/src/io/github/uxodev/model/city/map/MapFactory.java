package io.github.uxodev.model.city.map;

import io.github.uxodev.model.both.Transfer;
import io.github.uxodev.model.both._data.Coord;
import io.github.uxodev.model.both.widget._data.MaterialData;
import io.github.uxodev.model.both.widget._data.ShapeData;
import io.github.uxodev.model.both.widget._data.SourceData;
import io.github.uxodev.model.both.widget.instance.stable.terrain.TerrainFloor;
import io.github.uxodev.model.both.widget.instance.stable.terrain.TerrainWall;
import io.github.uxodev.model.city.map.voxel.Voxel;

public class MapFactory {
    private final Map map;
    private final int WIDTH;
    private final int LENGTH;
    private final int HEIGHT;

    public MapFactory(int height, int length, int width) {
        this.WIDTH = width;
        this.LENGTH = length;
        this.HEIGHT = height;

        map = new Map();
        Voxel[][][] voxels = new Voxel[WIDTH][LENGTH][HEIGHT];
        for (int i = 0; i < WIDTH; i++)
            for (int j = 0; j < LENGTH; j++)
                for (int k = 0; k < HEIGHT; k++)
                    voxels[i][j][k] = new Voxel(map, new Coord(i, j, k));
        map.init(voxels);
    }

    public MapFactory testMap() {
        for (int i = 0; i < WIDTH; i++)
            for (int j = 0; j < LENGTH; j++)
                for (int k = 0; k < HEIGHT; k++) {
                    if (k == 0)
                        Transfer.start(new TerrainFloor(
                                        SourceData.SOURCE_TEMPLATES.get("limestone").get("stone"),
                                        MaterialData.MATERIAL_TOKENS.get("stone"),
                                        ShapeData.TERRAIN_SHAPE_TOKENS.get("floor")),
                                map.getVoxelOrForbidden(i, j, k));
                    if (k == 0 && j > 15)
                        Transfer.start(new TerrainWall(
                                        SourceData.SOURCE_TEMPLATES.get("loam").get("soil"),
                                        MaterialData.MATERIAL_TOKENS.get("soil"),
                                        ShapeData.TERRAIN_SHAPE_TOKENS.get("wall")),
                                map.getVoxelOrForbidden(i, j, k));
                    if (k == 0 && j == 15)
                        Transfer.start(new TerrainWall(
                                        SourceData.SOURCE_TEMPLATES.get("loam").get("soil"),
                                        MaterialData.MATERIAL_TOKENS.get("soil"),
                                        ShapeData.TERRAIN_SHAPE_TOKENS.get("ramp")),
                                map.getVoxelOrForbidden(i, j, k));
                    if (k == 1 && j > 25)
                        Transfer.start(new TerrainWall(
                                        SourceData.SOURCE_TEMPLATES.get("clay").get("soil"),
                                        MaterialData.MATERIAL_TOKENS.get("soil"),
                                        ShapeData.TERRAIN_SHAPE_TOKENS.get("wall")),
                                map.getVoxelOrForbidden(i, j, k));
                    if (k == 1 && j == 25)
                        Transfer.start(new TerrainWall(
                                        SourceData.SOURCE_TEMPLATES.get("clay").get("soil"),
                                        MaterialData.MATERIAL_TOKENS.get("soil"),
                                        ShapeData.TERRAIN_SHAPE_TOKENS.get("ramp")),
                                map.getVoxelOrForbidden(i, j, k));
                }
        return this;
    }

    public Map make() {
        map.refresh();
        return map;
    }
}
