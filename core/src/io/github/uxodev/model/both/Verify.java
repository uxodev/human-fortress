package io.github.uxodev.model.both;

import io.github.uxodev.model.both.unit.instance.Unit;
import io.github.uxodev.model.both.widget.instance.dynamic.Dynamic;
import io.github.uxodev.model.both.widget.token.material.MaterialToken;
import io.github.uxodev.model.city.map.voxel.Voxel;
import io.github.uxodev.model.city.pathfinder.Pathfinder;

import java.util.Stack;

public class Verify {
    public static boolean verifyCanPathTo(Stack<Voxel> path, Pathfinder.Method method) {
        if (path == null) return false;
        for (int i = 0; i < path.size() - 1; i++)
            if (!verifyCanPathToNext(path.get(i), path.get(i + 1), method))
                return false;
        return true;
    }

    // todo: gait method enum set
    public static boolean verifyCanPathToNext(Voxel source, Voxel dest, Pathfinder.Method method) {
        return source.pathing.getCanPathToByMethod(method).contains(dest);
    }

    public static boolean verifyStandingOn(Voxel source, Voxel dest) {
        return source.equals(dest);
    }

    public static boolean verifyStandingOnOrNextToHoriz(Voxel source, Voxel dest) {
        return source.neighbors.getHorizPlusC().contains(dest);
    }

    public static boolean verifyCanReachFloor(Voxel dest) {
        return dest.pathing.canReachFloor();
    }

    public static boolean verifyItemInExpectedLoc(Dynamic dynamic, Voxel expectedLoc) {
        return dynamic.loc == expectedLoc;
    }

    public static boolean verifyIsTerrainFloorExpectedMaterial(Voxel locTerrain, MaterialToken materialToken) {
        return locTerrain.supportedStables.getTerrainFloor().materialToken
                .equals(materialToken);
    }

//    public static boolean verifyPlantInExpectedLoc(Plant targetPlant, Voxel expectedLocTargetPlant) {
//        return targetPlant.loc.equals(expectedLocTargetPlant);
//    }

//    public static boolean verifyItemDestHasSpace(Item item, Voxel dest) {
//        return true;
//    }

    public static boolean verifyCanCarry(Unit unit, Dynamic dynamic) {
        return unit.held == null;
    }

    public static boolean verifyStillCarrying(Unit unit, Dynamic dynamic) {
        return unit.held == dynamic;
    }
}
