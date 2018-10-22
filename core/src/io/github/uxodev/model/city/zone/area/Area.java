package io.github.uxodev.model.city.zone.area;

import io.github.uxodev.model.both.widget.instance.stable.Stable;
import io.github.uxodev.model.city.map.voxel.Voxel;
import io.github.uxodev.model.city.zone.Zone;
import io.github.uxodev.model.city.zone._data.AreaData;
import io.github.uxodev.model.city.zone.match.Match;
import io.github.uxodev.model.city.zone.match.Matcher;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Optional;

public abstract class Area {
    public final Zone parentZone;
    public final AreaType areaType;
    public HashSet<Voxel> enclosedVoxels;

    private final ArrayList<Stable> enclosedStables = new ArrayList<>();
    private final ArrayList<Match> reqDisplayOrder;
    private AreaStatus areaStatus = AreaStatus.MISSING;
    public int value = 0;

    public final ArrayList<String> reqDisplay = new ArrayList<>();
    public final ArrayList<String> addlDisplay = new ArrayList<>();
    public final ArrayList<String> extraDisplay = new ArrayList<>();

    public Area(Zone parentZone, AreaType areaType, HashSet<Voxel> enclosedVoxels) {
        this.parentZone = parentZone;
        this.areaType = areaType;

        reqDisplayOrder = AreaData.AREA_REQ_DISPLAY_ORDER.get(areaType);
        setEnclosedVoxels(enclosedVoxels);
    }

    public void setEnclosedVoxels(HashSet<Voxel> enclosedVoxels) {
        this.enclosedVoxels = new HashSet<>(enclosedVoxels);
        updateAllStables();
    }

    private void updateAllStables() {
        enclosedStables.clear();
        value = 0;
        for (Voxel enclosedVoxel : enclosedVoxels) {
            Optional<Stable> wall = enclosedVoxel.supportedStables.getWall();
            if (wall.isPresent()) {
                enclosedStables.add(wall.get());
                value += wall.get().getValue();
            }
//            Optional<Stable> floor = enclosedVoxel.supportedStables.getFloor();
//            if (floor.isPresent()) {
//                enclosedStables.add(floor.get());
//                value += floor.get().getValue();
//            }
        }
        updateAllIsActiveDisplay();
    }

    private void updateAllIsActiveDisplay() {
        reqDisplay.clear();
        addlDisplay.clear();
        extraDisplay.clear();

        // add green or red match button for match or no match to reqDisplay
        ArrayList<Stable> checklist = new ArrayList<>(enclosedStables);
        markActive();
        finding:
        for (Match match : reqDisplayOrder) {
            for (Iterator<Stable> iterator = checklist.iterator(); iterator.hasNext(); ) {
                Stable stable = iterator.next();
                if (Matcher.doesMatch(stable, match)) {
                    reqDisplay.add(match.shape + " " + "(matched)");
                    iterator.remove();
                    continue finding;
                }
            }
            reqDisplay.add(match.shape + " " + "(not matched)");
            clearActive();
        }

        // add all matching stable buttons to addlDisplay
        checklist = new ArrayList<>(enclosedStables);
        for (Match match : reqDisplayOrder) {
            for (Iterator<Stable> iterator = checklist.iterator(); iterator.hasNext(); ) {
                Stable stable = iterator.next();
                if (Matcher.doesMatch(stable, match)) {
                    addlDisplay.add(stable.name);
                    iterator.remove();
                }
            }
        }
        // add remaining stable buttons to extraDisplay
        for (Stable stable : checklist) {
            extraDisplay.add(stable.name);
        }
    }

    public void addStable(Stable stable) {
        if (enclosedStables.add(stable)) {
            value += stable.getValue();
            addIsActiveDisplay(stable);
        }
    }

    private void addIsActiveDisplay(Stable stable) {
        updateAllIsActiveDisplay();
    }

    public void removeStable(Stable stable) {
        if (enclosedStables.remove(stable)) {
            value -= stable.getValue();
            removeIsActive(stable);
        }
    }

    private void removeIsActive(Stable stable) {
        updateAllIsActiveDisplay();
    }

    public void markActive() {
        areaStatus = AreaStatus.ACTIVE;
    }

    public void clearActive() {
        areaStatus = AreaStatus.INACTIVE;
    }

    public boolean isActive() {
        return areaStatus == AreaStatus.ACTIVE;
    }

    public boolean isMissing() {
        return areaStatus == AreaStatus.MISSING;
    }

    public ArrayList<Match> getMatches() {
        return new ArrayList<>();
    }

    public enum AreaStatus {
        ACTIVE, INACTIVE, MISSING,
    }

    public enum AreaType {
        NEST,
        BARN, FIELD, PASTURE,
        STOREFRONT, STOCK_AREA, INVENTORY_OFFICE, REPOSITORY, REPOSITORY_OFFICE,
        BEDROOM, KITCHEN, VESTIBULE, ATRIUM, DINING_ROOM, GARDEN, WASHROOM, PERSONAL_OFFICE, SERVANT_ROOM,
        FLAT_OPEN_SPACE, WELL, FOUNTAIN, MONUMENT,
        PUBLIC_BATH,
        DOCK, LOADING_AREA, UNLOADING_AREA,
        WORKSHOP, MANAGER_OFFICE,
        STALLS_OPEN_SPACE, BUSINESS_OFFICE,
        SERVING_AREA, EATING_AREA, ENTERTAINMENT_PLATFORM, DORMITORY, PRIVATE_ROOM,
        SEATING_AREA, STAGE, ARENA,
        VIEWING_AREA, VALUATION_OFFICE, READING_AREA, COPY_OFFICE,;

        public static final HashSet<String> map = new HashSet<>();

        static {
            for (AreaType areaType : AreaType.values()) map.add(areaType.name());
        }
    }

    @Override
    public String toString() {
        return "Area{" + areaType.name() + " " + isActive() + " " + value + " " + reqDisplay + '}';
    }
}
