package io.github.uxodev.model.city.pathfinder;

import io.github.uxodev.model.city.map.Map;
import io.github.uxodev.model.city.map.voxel.Voxel;

import java.util.ArrayList;

public class Pathfinder {
    public static boolean isConnectedToFoundation(Map map, Voxel startVoxel) {
        return GraphSearch.isPathToFoundation(map, startVoxel);
    }

    public static ArrayList<Voxel> getAllConnected(Voxel startVoxel) {
        return GraphSearch.getAllConnected(startVoxel);
    }

    public enum Matter {
        SOLID,
        LIQUID,
        GAS,
//        LIGHT,
    }

    public enum Method {
        CRAWL, WALK, WHEEL, LEAP,
        CLIMB,
        SWIM, FLY,
        BUOYANT,
    }
}
