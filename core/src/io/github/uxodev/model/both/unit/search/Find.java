package io.github.uxodev.model.both.unit.search;

import io.github.uxodev.model.both.Verify;
import io.github.uxodev.model.both._data.Game;
import io.github.uxodev.model.both.widget.instance.dynamic.Dynamic;
import io.github.uxodev.model.both.widget.instance.stable.Stable;
import io.github.uxodev.model.city.map.Map;
import io.github.uxodev.model.city.map.voxel.Voxel;
import io.github.uxodev.model.city.pathfinder.GraphSearch;
import io.github.uxodev.model.city.pathfinder.Pathfinder;
import io.github.uxodev.model.city.zone.area.Area;
import io.github.uxodev.model.city.zone.area.sentient.StockArea;
import io.github.uxodev.model.city.zone.match.Match;

import java.util.Optional;
import java.util.Stack;

public class Find {

    public static AreaAndDynamic findAreaWithSpaceAndMatchingDynamic(Voxel source) {
        for (StockArea stockArea : source.map.enclosedZones.stockAreas) {
            if (
//                    stockArea.isActive() &&
                    stockArea.pile.hasSpace()) {
                for (Dynamic dynamic : source.map.enclosedWidgets.dynamics) {
                    if (!dynamic.isPiled && !dynamic.isLocked() &&
                            Verify.verifyCanPathTo(pathToLocHorizPlusC(null, source, dynamic.loc), Pathfinder.Method.WALK)) {
                        for (Match match : stockArea.pile.matches) {
                            if (match.matches(dynamic)) {
                                return new AreaAndDynamic(stockArea, dynamic);
                            }
                        }
                    }
                }
            }
        }
        return new AreaAndDynamic(null, null);
    }

    public static Optional<Area> findActiveArea(Area.AreaType areaType) {
        for (Area area : Game.currentMap.enclosedZones.areas) {
            if (area.areaType == areaType && area.isActive()) {
                return Optional.of(area);
            }
        }
        return Optional.empty();
    }

    public static Optional<Stable> findMatchedFurniture(Area area, Match match) {
        for (Voxel enclosedVoxel : area.enclosedVoxels) {
            if (enclosedVoxel.supportedStables.getWall().isPresent())
                if (match.matches(enclosedVoxel.supportedStables.getWall().get())) {
                    return Optional.of(enclosedVoxel.supportedStables.getWall().get());
                }
        }
        return Optional.empty();
    }

    public static int findMoveBucketLowest(Voxel dest, Pathfinder.Method method) {
        return 3;
    }

    public static Stack<Voxel> pathToLocOnlyC(Map map, Voxel source, Voxel destination) {
        return GraphSearch.getPathBreathFirst(source, destination);
    }

    public static Stack<Voxel> pathToLocHorizPlusC(Map map, Voxel source, Voxel destination) {
        return GraphSearch.getPathBreathFirst(source, destination);
    }

    public static Stack<Voxel> pathToLocHorizMinusC(Map map, Voxel source, Voxel destination) {
        return GraphSearch.getPathBreathFirst(source, destination);
    }
}

