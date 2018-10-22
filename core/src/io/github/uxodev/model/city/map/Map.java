package io.github.uxodev.model.city.map;

import io.github.uxodev.libgdx.input.map.VoxelSelect;
import io.github.uxodev.model.both._data.Coord;
import io.github.uxodev.model.city.map.enclosed.EnclosedUnits;
import io.github.uxodev.model.city.map.enclosed.EnclosedWidgets;
import io.github.uxodev.model.city.map.enclosed.EnclosedZones;
import io.github.uxodev.model.city.map.voxel.Voxel;
import io.github.uxodev.model.city.register.CollapseRegister;
import io.github.uxodev.model.city.register.FallRegister;

import java.util.ArrayList;
import java.util.HashSet;

public class Map {
    private Voxel[][][] voxels;
    private Voxel voxelForbidden;
    private int WIDTH;
    private int LENGTH;
    private int HEIGHT;

    public VoxelSelect voxelSelect;
    private ArrayList<Voxel> voxelsSelected = new ArrayList<>();
    private ArrayList<Voxel> voxelsHovered = new ArrayList<>();

    public final EnclosedWidgets enclosedWidgets = new EnclosedWidgets();
    public final EnclosedUnits enclosedUnits = new EnclosedUnits();
    public final EnclosedZones enclosedZones = new EnclosedZones();

    // factory
    void init(Voxel[][][] voxels) {
        this.voxels = voxels;

        this.WIDTH = this.voxels.length;
        this.LENGTH = this.voxels[0].length;
        this.HEIGHT = this.voxels[0][0].length;

        voxelForbidden = new Voxel(this, new Coord(-1, -1, -1));
        voxelForbidden.initForbidden();
        for (int i = 0; i < WIDTH; i++)
            for (int j = 0; j < LENGTH; j++)
                for (int k = 0; k < HEIGHT; k++)
                    voxels[i][j][k].init(this);

        voxelSelect = new VoxelSelect(getVoxelOrForbidden(0, 0, 0));
    }

    void refresh() {
        HashSet<Voxel> all = new HashSet<>();
        voxelForbidden.refresh();
        for (int i = 0; i < WIDTH; i++)
            for (int j = 0; j < LENGTH; j++)
                for (int k = 0; k < HEIGHT; k++) {
                    voxels[i][j][k].refresh();
                    all.add(voxels[i][j][k]);
                }
        CollapseRegister.registerUpdateConnected(all);
        CollapseRegister.registerCheckCollapse(all);
        CollapseRegister.registerUpdatePathing(all);

        CollapseRegister.allUpdateConnected();
        CollapseRegister.allCheckCollapse();
        CollapseRegister.allUpdatePathing();
    }

    public void step() {
        CollapseRegister.allUpdateConnected();
        CollapseRegister.allCheckCollapse();
        CollapseRegister.allStepCollapse();

        FallRegister.allCheckFall();
        FallRegister.stepFall();

        CollapseRegister.allUpdatePathing();

        enclosedUnits.step();
    }

    public String count() {
        return enclosedUnits.count() + "\n"
                + enclosedWidgets.count() + "\n"
                + enclosedZones.count();
    }

    public Voxel getVoxelOrForbidden(Coord coord) {
        return getVoxelOrForbidden(coord.x, coord.y, coord.z);
    }

    public Voxel getVoxelOrForbidden(int x, int y, int z) {
        return isValidCoord(x, y, z) ? voxels[x][y][z] : voxelForbidden;
    }

    private boolean isValidCoord(int x, int y, int z) {
        return (x >= 0 && x < WIDTH) && (y >= 0 && y < LENGTH) && (z >= 0 && z < HEIGHT);
    }

    public void setSelect(HashSet<Voxel> volumeSelected) {
        voxelsSelected.forEach(voxel -> voxel.isSelected = false);
        voxelsSelected = new ArrayList<>(volumeSelected);
        voxelsSelected.forEach(voxel -> voxel.isSelected = true);
    }

    public void setHover(HashSet<Voxel> volumeHovered) {
        voxelsHovered.forEach(voxel -> voxel.isHovered = false);
        voxelsHovered = new ArrayList<>(volumeHovered);
        voxelsHovered.forEach(voxel -> voxel.isHovered = true);
    }
}
