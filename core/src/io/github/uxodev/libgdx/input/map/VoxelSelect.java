package io.github.uxodev.libgdx.input.map;

import io.github.uxodev.libgdx.display.ui.infopanel.InfoPanel;
import io.github.uxodev.model.both._data.Coord;
import io.github.uxodev.model.both._data.Game;
import io.github.uxodev.model.city.map.voxel.Voxel;

import java.util.HashSet;

public class VoxelSelect {
    private SelectShape selectShape = SelectShape.CUBE;
    private boolean isRestart = true;
    private boolean isAdditive = true;
    private boolean isLock = false;

    private HoverType hoverType = HoverType.SINGLE;
    private LockType lockType = LockType.NONE;
    private Voxel selectStart;
    public Voxel hovering;

    public HashSet<Voxel> volumeSelected = new HashSet<>();
    public HashSet<Voxel> volumeHovered = new HashSet<>();
    public HashSet<Voxel> volumeFuture = new HashSet<>();

    public enum SelectShape {
        LINE, CUBE, CYLINDER, CIRCLE, PYRAMID, HEXAGON, OCTAGON,;
    }

    public enum HoverType {
        SINGLE, DRAG, POINT,;
    }

    public enum LockType {
        NONE, PERFECT,
        X, Y, Z, DIAG_Q1, DIAG_Q2, DIAG_Q3, DIAG_Q4,;
    }

    public VoxelSelect(Voxel initSelect) {
        this.selectStart = initSelect;
        this.hovering = initSelect;
    }

    // output
    private void update() {
        volumeHovered.clear();
        switch (hoverType) {
            case SINGLE:
                volumeHovered.add(hovering);
                break;
            case DRAG:
            case POINT:
                volumeHovered.addAll(getShapePositions(selectStart, hovering));
                break;
        }
        if (hoverType != HoverType.SINGLE) {
            volumeFuture.clear();
            volumeFuture.addAll(volumeSelected);
            volumeIntersect(volumeFuture, hovering);
        }
        MapInput.updateMapHover();
        MapInput.updateMapSelect();
    }

    // input
    public void setShape(SelectShape selectShape) {
        this.selectShape = selectShape;
        update();
    }

    public void touchDown(Voxel positionSelected, int button) {
        if (button == 0) {
            if (hoverType != HoverType.SINGLE) {
                selectSecond(positionSelected);
                hoverType = HoverType.SINGLE;
            } else {
                selectFirst(positionSelected);
                hoverType = HoverType.DRAG;
            }
        } else if (button == 4) {

        } else if (button == 5) {

        }
        update();
    }

    public void touchDownOutsideBounds(int button) {
        touchDown(hovering, button);
    }

    public void touchUp(Voxel positionSelected, int button) {
        if (button == 0) {
            if (hoverType != HoverType.DRAG)
                return;
            if (selectStart.equals(positionSelected) && isLock)
                hoverType = HoverType.POINT;
            else {
                selectSecond(positionSelected);
                hoverType = HoverType.SINGLE;
            }
        }
        update();
    }

    public void touchUpOutsideBounds(int button) {
        touchUp(hovering, button);
    }

    public void mouseMove(Voxel positionMoved) {
        hovering = positionMoved;
        update();
    }

    public void setModKeys(boolean isShift, boolean isCtrl, boolean isAlt) {
        if (isShift) {
            isRestart = false;
            isAdditive = true;
        } else if (isCtrl) {
            isRestart = false;
            isAdditive = false;
        } else {
            isRestart = true;
            isAdditive = true;
        }
        isLock = isAlt;
        update();
    }

    public void clear() {
        hoverType = HoverType.SINGLE;
        volumeSelected.clear();
        volumeHovered.clear();
        volumeFuture.clear();
        update();
        InfoPanel.modflagInfoPanel = true;
    }

    public boolean undo() {

        return false;
    }

    public void redo() {

    }

    // internal
    private void selectFirst(Voxel positionSelected) {
        selectStart = positionSelected;
    }

    private void selectSecond(Voxel positionSelected) {
        volumeIntersect(volumeSelected, positionSelected);
        selectStart = positionSelected;
        InfoPanel.modflagInfoPanel = true;
    }

    private void volumeIntersect(HashSet<Voxel> volume, Voxel positionSelected) {
        if (isRestart) {
            volume.clear();
            volume.addAll(getShapePositions(selectStart, positionSelected));
        } else if (isAdditive) {
            volume.addAll(getShapePositions(selectStart, positionSelected));
        } else {
            volume.removeAll(getShapePositions(selectStart, positionSelected));
        }
    }

    // shapes
    private HashSet<Voxel> getShapePositions(Voxel start, Voxel end) {
        switch (selectShape) {
            case LINE:
                break;
            default:
            case CUBE:
                return getCubeVoxels(start, end, isLock);
            case CYLINDER:
                break;
            case CIRCLE:
                break;
            case PYRAMID:
                break;
            case HEXAGON:
                break;
            case OCTAGON:
                break;
        }
        return new HashSet<>();
    }

    private static HashSet<Voxel> getCubeVoxels(Voxel start, Voxel end, boolean isLock) {
        HashSet<Voxel> cubeVoxels = new HashSet<>();
        Coord low = new Coord(Math.min(start.position.x, end.position.x),
                Math.min(start.position.y, end.position.y), Math.min(start.position.z, end.position.z));
        Coord high = new Coord(Math.max(start.position.x, end.position.x),
                Math.max(start.position.y, end.position.y), Math.max(start.position.z, end.position.z));
        for (int k = low.z; k <= high.z; k++)
            for (int j = low.y; j <= high.y; j++)
                for (int i = low.x; i <= high.x; i++)
                    cubeVoxels.add(Game.currentMap.getVoxelOrForbidden(i, j, k));
        return cubeVoxels;
    }
}
