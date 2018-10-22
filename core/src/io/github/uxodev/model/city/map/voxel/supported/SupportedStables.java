package io.github.uxodev.model.city.map.voxel.supported;

import io.github.uxodev.model.both.widget._data.MaterialData;
import io.github.uxodev.model.both.widget._data.ShapeData;
import io.github.uxodev.model.both.widget._data.SourceData;
import io.github.uxodev.model.both.widget._data.token.PathToken;
import io.github.uxodev.model.both.widget.instance.stable.Furniture;
import io.github.uxodev.model.both.widget.instance.stable.FurnitureContainer;
import io.github.uxodev.model.both.widget.instance.stable.PlantInstance;
import io.github.uxodev.model.both.widget.instance.stable.Stable;
import io.github.uxodev.model.both.widget.instance.stable.terrain.TerrainFloor;
import io.github.uxodev.model.both.widget.instance.stable.terrain.TerrainWall;
import io.github.uxodev.model.both.widget.token.material.MaterialToken;
import io.github.uxodev.model.both.widget.token.source.Source;
import io.github.uxodev.model.city.map.voxel.Voxel;
import io.github.uxodev.model.city.register.CollapseRegister;

import java.util.EnumSet;
import java.util.Optional;

import static io.github.uxodev.model.city.map.voxel._objcomp.Neighbors.Ordinal.C;
import static io.github.uxodev.model.city.map.voxel._objcomp.Neighbors.Plane.ABOVE;
import static io.github.uxodev.model.city.map.voxel._objcomp.Neighbors.Plane.BELOW;

public class SupportedStables {
    private final Voxel parent;

    private TerrainFloor terrainFloor;
    private TerrainWall terrainWall;
    private Furniture furniture;
    private FurnitureContainer furnitureContainer;
    private PlantInstance plantInstance;

    private StateFloor stateFloor = StateFloor.BLANK;
    private StateWall stateWall = StateWall.BLANK;

    public SupportedStables(Voxel parent) {
        this.parent = parent;
    }

    // floor
    public boolean validSetTerrainFloor() {
        return parent.pathing.canReachFloor();
    }

    public boolean setTerrainFloor(TerrainFloor terrainFloor) {
        if (validSetTerrainFloor()) {
            forceSetTerrainFloor(terrainFloor);
            registerCheckCollapseNeighbors();
            return true;
        }
        return false;
    }

    private void forceSetTerrainFloor(TerrainFloor terrainFloor) {
        this.terrainFloor = terrainFloor;
        changeStateFloor(StateFloor.TERRAIN);
    }

    public boolean validRemoveTerrainFloor() {
        return (stateFloor == StateFloor.TERRAIN) && parent.pathing.canReachFloor();
    }

    public boolean removeTerrainFloor() {
        if (validRemoveTerrainFloor()) {
            forceRemoveTerrainFloor();
            refreshImpliedFloor();
            registerCheckCollapseNeighbors();
            return true;
        }
        return false;
    }

    private void forceRemoveTerrainFloor() {
        this.terrainFloor = null;
        changeStateFloor(StateFloor.BLANK);
    }

    // implied floor
    private boolean validSetImpliedFloorSame() {
        return (stateFloor == StateFloor.BLANK) || (stateFloor == StateFloor.IMPLIED);
    }

    private boolean isWallImplyingFloorSame() {
        return parent.pathing.wallImpliesFloorSame();
    }

    private boolean isWallImplyingFloorAbove() {
        return parent.pathing.wallImpliesFloorAbove();
    }

    private boolean isBelowWallImplyingFloorAbove() {
        return parent.getNeighbor(BELOW, C).pathing.wallImpliesFloorAbove();
    }

    public void refreshImpliedFloor() {
        if (validSetImpliedFloorSame() && isWallImplyingFloorSame()) { // if this implying same
            forceSetImpliedFloor(terrainWall.source, terrainWall.materialToken);
        } else if (validSetImpliedFloorSame() && isBelowWallImplyingFloorAbove()) { // if is implied from below
            forceSetImpliedFloor(parent.getNeighbor(BELOW, C).supportedStables.terrainWall.source,
                    parent.getNeighbor(BELOW, C).supportedStables.terrainWall.materialToken);
        } else if (stateFloor == StateFloor.IMPLIED) { // else is not implied same
            forceRemoveTerrainFloor();
        }
        Voxel above = parent.getNeighbor(ABOVE, C);
        if (above.supportedStables.validSetImpliedFloorSame() && above.supportedStables.isWallImplyingFloorSame()) {
            above.supportedStables.forceSetImpliedFloor(above.supportedStables.terrainWall.source, above.supportedStables.terrainWall.materialToken);
        } else if (above.supportedStables.validSetImpliedFloorSame() && isWallImplyingFloorAbove()) {
            above.supportedStables.forceSetImpliedFloor(terrainWall.source, terrainWall.materialToken);
        } else if (above.supportedStables.stateFloor == StateFloor.IMPLIED) {
            above.supportedStables.forceRemoveTerrainFloor();
        }
    }

    private void forceSetImpliedFloor(Source source, MaterialToken materialToken) {
        terrainFloor = createImpliedFloor(source, materialToken);
        changeStateFloor(StateFloor.IMPLIED);
    }

    private static TerrainFloor createImpliedFloor(Source source, MaterialToken materialToken) {
        return new TerrainFloor(source, materialToken, ShapeData.TERRAIN_SHAPE_TOKENS.get("implied floor"));
    }

    // wall
    public boolean validSetTerrainWall() {
        return (stateWall == StateWall.BLANK) || (stateWall == StateWall.TERRAIN);
    }

    public boolean setTerrainWall(TerrainWall terrainWall) {
        if (validSetTerrainWall() && parent.isVacant()) {
            forceSetTerrainWall(terrainWall);
            refreshImpliedFloor();
            registerCheckCollapseNeighbors();
            return true;
        }
        return false;
    }

    private void forceSetTerrainWall(TerrainWall terrainWall) {
        this.terrainWall = terrainWall;
        changeStateWall(StateWall.TERRAIN);
    }

    public boolean validRemoveTerrainWall() {
        return stateWall == StateWall.TERRAIN;
    }

    public boolean removeTerrainWall() {
        if (validRemoveTerrainWall()) {
            forceRemoveTerrainWall();
            refreshImpliedFloor();
            registerCheckCollapseNeighbors();
            return true;
        }
        return false;
    }

    private void forceRemoveTerrainWall() {
        terrainWall = null;
        changeStateWall(StateWall.BLANK);
    }

    public boolean validSetFurniture() {
        return (stateWall == StateWall.BLANK) && parent.pathing.floorSupportsSolid();
    }

    public boolean setFurniture(Furniture furniture) {
        if (validSetFurniture() && parent.isVacant()) {
            forceSetFurniture(furniture);
            return true;
        }
        return false;
    }

    private void forceSetFurniture(Furniture furniture) {
        this.furniture = furniture;
        changeStateWall(StateWall.FURNITURE);
    }

    public boolean validRemoveFurniture() {
        return stateWall == StateWall.FURNITURE;
    }

    public boolean removeFurniture() {
        if (validRemoveFurniture()) {
            furniture = null;
            changeStateWall(StateWall.BLANK);
            return true;
        }
        return false;
    }

    public boolean validSetFurnitureContainer() {
        return (stateWall == StateWall.BLANK) && parent.pathing.floorSupportsSolid();
    }

    public boolean setFurnitureContainer(FurnitureContainer furnitureContainer) {
        if (validSetFurnitureContainer() && parent.isVacant()) {
            forceSetFurnitureContainer(furnitureContainer);
            return true;
        }
        return false;
    }

    private void forceSetFurnitureContainer(FurnitureContainer furnitureContainer) {
        this.furnitureContainer = furnitureContainer;
        changeStateWall(StateWall.FURNITURE_CONTAINER);
    }

    public boolean validRemoveFurnitureContainer() {
        return stateWall == StateWall.FURNITURE_CONTAINER;
    }

    public boolean removeFurnitureContainer() {
        if (validRemoveFurnitureContainer()) {
            furnitureContainer = null;
            changeStateWall(StateWall.BLANK);
            return true;
        }
        return false;
    }

    public boolean validSetPlant() {
        return (stateWall == StateWall.BLANK) && parent.pathing.floorSupportsPlant();
    }

    public boolean setPlant(PlantInstance plantInstance) {
        if (validSetPlant() && parent.isVacant()) {
            this.plantInstance = plantInstance;
            changeStateWall(StateWall.PLANT);
            return true;
        }
        return false;
    }

    public boolean validRemovePlant() {
        return stateWall == StateWall.PLANT;
    }

    public boolean removePlant() {
        if (validRemovePlant()) {
            plantInstance = null;
            changeStateWall(StateWall.BLANK);
            return true;
        }
        return false;
    }

    // dig
    public boolean validDig() {
        return (stateFloor == StateFloor.TERRAIN) || (stateFloor == StateFloor.IMPLIED);
    }

    public boolean dig() {
        if (validDig()) {
            removeTerrainWall();
            if (!parent.getNeighbor(BELOW, C).pathing.voxelIsForbidden())
                parent.getNeighbor(BELOW, C).supportedStables.removeTerrainWall();
            removeTerrainFloor();
            return true;
        }
        return false;
    }

    // for material cost from changes
    public boolean willCreateImpliedFloorSame() {
        return true;
    }

    public boolean willCreateImpliedFloorAbove() {
        return true;
    }

    public boolean willDigRemoveWallSame() {
        return true;
    }

    public boolean willDigRemoveWallBelow() {
        return true;
    }

    // change voxel
    private void changeStateFloor(StateFloor stateFloor) {
        this.stateFloor = stateFloor;
        switch (stateFloor) {
            case BLANK:
                parent.pathing.updatePathTokensFloor(EnumSet.noneOf(PathToken.class));
                break;
            case TERRAIN:
            case IMPLIED:
                parent.pathing.updatePathTokensFloor(terrainFloor.getPathTokens());
                break;
        }
    }

    private void changeStateWall(StateWall stateWall) {
        this.stateWall = stateWall;
        switch (stateWall) {
            case BLANK:
                parent.pathing.updatePathTokensWall(EnumSet.noneOf(PathToken.class));
                break;
            case TERRAIN:
                parent.pathing.updatePathTokensWall(terrainWall.getPathTokens());
                break;
            case FURNITURE:
                parent.pathing.updatePathTokensWall(furniture.getPathTokens());
                break;
            case FURNITURE_CONTAINER:
                parent.pathing.updatePathTokensWall(furnitureContainer.getPathTokens());
                break;
//            case PLANT:
//                parent.pathing.updatePathTokensWall(plantInstance.getPathTokens());
//                break;
        }
    }

    public void collapseReplaceState(Voxel below) {
        switch (stateFloor) {
            case BLANK:
                break;
            case TERRAIN:
            case IMPLIED:
                below.supportedStables.forceSetTerrainFloor(terrainFloor);
                below.supportedStables.registerCheckCollapseNeighbors();
                break;
        }
        switch (stateWall) {
            case BLANK:
                // error if would replace, as this means a terrain/empty "fell into" a terrain/terrain
                break;
            case TERRAIN:
                below.supportedStables.forceSetTerrainWall(terrainWall);
                break;
            case FURNITURE:
                below.supportedStables.forceSetFurniture(furniture);
                break;
            case FURNITURE_CONTAINER:
                below.supportedStables.forceSetFurnitureContainer(furnitureContainer);
                break;
//            case PLANT:
//                below.supportedStables.forceSetTerrainWall(plantInstance);
//                break;
        }
    }

    private void registerCheckCollapseNeighbors() {
        CollapseRegister.registerCheckCollapse(parent.neighbors.getAll());
    }

    public void setForbidden() {
        TerrainFloor forbiddenTerrainFloor = new TerrainFloor(
                SourceData.SOURCE_TEMPLATES.get("forbidden").get("forbidden"),
                MaterialData.MATERIAL_TOKENS.get("forbidden"),
                ShapeData.TERRAIN_SHAPE_TOKENS.get("forbidden wall"));
        TerrainWall forbiddenTerrainWall = new TerrainWall(
                SourceData.SOURCE_TEMPLATES.get("forbidden").get("forbidden"),
                MaterialData.MATERIAL_TOKENS.get("forbidden"),
                ShapeData.TERRAIN_SHAPE_TOKENS.get("forbidden wall"));

        forceSetTerrainFloor(forbiddenTerrainFloor);
        forceSetTerrainWall(forbiddenTerrainWall);
        stateFloor = StateFloor.FORBIDDEN;
        stateWall = StateWall.FORBIDDEN;
    }

    public void clearAll() {
        removePlant();
        removeFurnitureContainer();
        removeFurniture();
        forceRemoveTerrainWall();
        forceRemoveTerrainFloor();
    }

    public StateFloor getStateFloor() {
        return stateFloor;
    }

    public StateWall getStateWall() {
        return stateWall;
    }

    public Optional<Stable> getFloor() {
        return Optional.ofNullable(terrainFloor);
    }

    public Optional<Stable> getWall() {
        switch (stateWall) {
            default:
            case BLANK:
                return Optional.empty();
            case TERRAIN:
                return Optional.of(terrainWall);
            case FURNITURE:
                return Optional.of(furniture);
            case FURNITURE_CONTAINER:
                return Optional.of(furnitureContainer);
//            case PLANT:
//                return Optional.of(plantInstance);
        }
    }

    public boolean hasTerrainFloor() {
        return stateFloor == StateFloor.TERRAIN;
    }

    public TerrainFloor getTerrainFloor() {
        return terrainFloor;
    }

    public boolean hasTerrainWall() {
        return stateWall == StateWall.TERRAIN;
    }

    public TerrainWall getTerrainWall() {
        return terrainWall;
    }

    public boolean hasFurniture() {
        return stateWall == StateWall.FURNITURE;
    }

    public Furniture getFurniture() {
        return furniture;
    }

    public boolean hasFurnitureContainer() {
        return stateWall == StateWall.FURNITURE_CONTAINER;
    }

    public FurnitureContainer getFurnitureContainer() {
        return furnitureContainer;
    }

    public boolean hasPlantInstance() {
        return stateWall == StateWall.PLANT;
    }

    public PlantInstance getPlantInstance() {
        return plantInstance;
    }

    public enum StateFloor {
        BLANK,
        TERRAIN,
        IMPLIED,
        FORBIDDEN,
    }

    public enum StateWall {
        BLANK,
        TERRAIN,
        FURNITURE,
        FURNITURE_CONTAINER,
        PLANT,
        FORBIDDEN,
    }
}
