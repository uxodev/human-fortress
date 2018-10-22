package io.github.uxodev.model.city.map.voxel._objcomp;

import io.github.uxodev.model.both.widget._data.token.PathToken;
import io.github.uxodev.model.city.map.voxel.Voxel;
import io.github.uxodev.model.city.register.CollapseRegister;

import java.util.ArrayList;
import java.util.EnumSet;
import java.util.HashMap;
import java.util.HashSet;

import static io.github.uxodev.model.city.map.voxel._objcomp.Neighbors.Ordinal;
import static io.github.uxodev.model.city.map.voxel._objcomp.Neighbors.Ordinal.C;
import static io.github.uxodev.model.city.map.voxel._objcomp.Neighbors.Ordinal.valuesExceptC;
import static io.github.uxodev.model.city.map.voxel._objcomp.Neighbors.Plane.*;
import static io.github.uxodev.model.city.pathfinder.Pathfinder.Matter;
import static io.github.uxodev.model.city.pathfinder.Pathfinder.Method;

public class Pathing {
    private final Voxel parent;

    private EnumSet<PathToken> pathTokensFloor = EnumSet.noneOf(PathToken.class);
    private EnumSet<PathToken> pathTokensWall = EnumSet.noneOf(PathToken.class);

    private HashSet<Voxel> isConnectedTo = new HashSet<>();
    private HashMap<Matter, ArrayList<Voxel>> canPathToMatter = new HashMap<>();
    private HashMap<Method, ArrayList<Voxel>> canPathToByMethod = new HashMap<>();

    public Pathing(Voxel parent) {
        this.parent = parent;
    }

    // neighbors connected to, for collapse check
    public HashSet<Voxel> getIsConnectedTo() {
        return isConnectedTo;
    }

    private void updateIsConnectedTo() {
        isConnectedTo.clear();
        if (parent.isLocked() || voxelIsBlank()) {
            return;
        }
        Voxel belowNeighbor = parent.getNeighbor(BELOW, C);
        if (belowNeighbor.pathing.wallImpliesFloorAbove()
                && !belowNeighbor.pathing.voxelIsForbidden()
                && !belowNeighbor.isLocked()) { // below
            isConnectedTo.add(belowNeighbor);
        }
        Voxel aboveNeighbor = parent.getNeighbor(ABOVE, C);
        if (wallImpliesFloorAbove()
                && !aboveNeighbor.pathing.voxelIsForbidden()
                && !aboveNeighbor.isLocked()) { // above
            isConnectedTo.add(aboveNeighbor);
        }
        for (Ordinal ordinal : valuesExceptC()) { // horiz
            Voxel horizNeighbor = parent.getNeighbor(HORIZ, ordinal);
            if (!horizNeighbor.pathing.voxelIsForbidden()
                    && !horizNeighbor.isLocked()) {
                if (floorSupportsSolid() && horizNeighbor.pathing.floorSupportsSolid()) {
                    isConnectedTo.add(horizNeighbor);
                } else if (wallIsSticky() && horizNeighbor.pathing.wallIsSticky()) {
                    isConnectedTo.add(horizNeighbor);
                }
            }
        }
    }

    // neighbors that can be pathed to by gait method, for pathfinder
    // should be by voxel, then for each voxel, get all of the methods i could use and use the fastest
    public HashMap<Method, ArrayList<Voxel>> getCanPathToByMethod() {
        return canPathToByMethod;
    }

    public ArrayList<Voxel> getCanPathToByMethod(Method method) {
        return canPathToByMethod.get(method);
    }

    private void updateCanPathToByMethod() {
        for (Method method : Method.values()) {
            canPathToByMethod.put(method, canPathTo(method));
        }
    }

    // get list of neighbors that can be pathed to for each gait method
    private ArrayList<Voxel> canPathTo(Method method) {
        ArrayList<Voxel> canPathTo = new ArrayList<>();
        switch (method) {
            case CRAWL:
            case WALK:
                return canPathToViaWalk();
            case WHEEL:
                return canPathToViaWheel();
            case LEAP:
                return canPathToViaLeap();
            case CLIMB:
                return canPathToViaClimb();
            case SWIM:
                return canPathToViaSwim();
            case FLY:
                return canPathToViaFly();
            case BUOYANT:

                break;
        }
        return canPathTo;
    }

    // stair pattern
    private ArrayList<Voxel> canPathToViaWalk() {
        ArrayList<Voxel> canPathTo = new ArrayList<>();

        Voxel belowNeighbor = parent.getNeighbor(BELOW, C);
        Voxel aboveNeighbor = parent.getNeighbor(ABOVE, C);
        for (Ordinal ordinal : valuesExceptC()) {
            Voxel horizNeighbor = parent.getNeighbor(HORIZ, ordinal);
            Voxel horizBelowNeighbor = parent.getNeighbor(BELOW, ordinal);
            if (horizNeighbor.pathing.supports(Method.WALK)) {
                canPathTo.add(horizNeighbor);
            } else if (belowNeighbor.pathing.wallImpliesFloorAbove() // stair rule
                    && horizBelowNeighbor.pathing.wallEnablesStairing() // stair rule
                    && horizNeighbor.pathing.voxelAllowsUnit() // L rule allow passthrough
                    && horizBelowNeighbor.pathing.supports(Method.WALK)) { // L rule support destination
                canPathTo.add(horizBelowNeighbor);
            }
            Voxel horizAboveNeighbor = parent.getNeighbor(ABOVE, ordinal);
            if (horizNeighbor.pathing.wallImpliesFloorAbove() // stair rule
                    && wallEnablesStairing() // stair rule
                    && aboveNeighbor.pathing.voxelAllowsUnit() // L rule allow passthrough
                    && horizAboveNeighbor.pathing.supports(Method.WALK)) { // L rule support destination
                canPathTo.add(horizAboveNeighbor);
            }
        }
        return canPathTo;
    }

    // stair pattern
    private ArrayList<Voxel> canPathToViaWheel() {
        ArrayList<Voxel> canPathTo = new ArrayList<>();

        Voxel belowNeighbor = parent.getNeighbor(BELOW, C);
        Voxel aboveNeighbor = parent.getNeighbor(ABOVE, C);
        for (Ordinal ordinal : valuesExceptC()) {
            Voxel horizNeighbor = parent.getNeighbor(HORIZ, ordinal);
            Voxel horizBelowNeighbor = parent.getNeighbor(BELOW, ordinal);
            if (horizNeighbor.pathing.supports(Method.WHEEL)) {
                canPathTo.add(horizNeighbor);
            } else if (belowNeighbor.pathing.wallImpliesFloorAbove() // stair rule
                    && horizBelowNeighbor.pathing.wallEnablesRamping() // stair rule
                    && horizNeighbor.pathing.voxelAllowsUnit() // L rule allow passthrough
                    && horizBelowNeighbor.pathing.supports(Method.WHEEL)) { // L rule support destination
                canPathTo.add(horizBelowNeighbor);
            }
            Voxel horizAboveNeighbor = parent.getNeighbor(ABOVE, ordinal);
            if (horizNeighbor.pathing.wallImpliesFloorAbove() // stair rule
                    && wallEnablesRamping() // stair rule
                    && aboveNeighbor.pathing.voxelAllowsUnit() // L rule allow passthrough
                    && horizAboveNeighbor.pathing.supports(Method.WHEEL)) { // L rule support destination
                canPathTo.add(horizAboveNeighbor);
            }
        }
        return canPathTo;
    }

    private ArrayList<Voxel> canPathToViaLeap() {
        ArrayList<Voxel> canPathTo = new ArrayList<>();

        for (Ordinal ordinal : valuesExceptC()) {
            Voxel horizNeighbor = parent.getNeighbor(HORIZ, ordinal);
            if (horizNeighbor.pathing.supports(Method.LEAP)
                    && horizNeighbor.getNeighbor(HORIZ, ordinal).pathing.supports(Method.LEAP)) {
                canPathTo.add(horizNeighbor.getNeighbor(HORIZ, ordinal));
            }
        }
        return canPathTo;
    }

    // open pattern with additional ability to climb up or up-over the side of a wall,
    // if the horiz implies floor above, and down or over-down the side
    ArrayList<Voxel> canPathToViaClimb() {
        HashSet<Voxel> canPathTo = new HashSet<>();

        // wall climb special rule for straight up/down
        Voxel belowNeighbor = parent.getNeighbor(BELOW, C);
        Voxel aboveNeighbor = parent.getNeighbor(ABOVE, C);
        if (neighborWallEnablesClimb()) {
            if (belowNeighbor.pathing.wallAllowsUnit() // ==supports wall climb
                    && belowNeighbor.pathing.neighborWallEnablesClimb()) {
                canPathTo.add(belowNeighbor);
            }
            if (aboveNeighbor.pathing.wallAllowsUnit() // ==supports wall climb
                    && aboveNeighbor.pathing.floorAllowsUnit()
                    && aboveNeighbor.pathing.neighborWallEnablesClimb()) {
                canPathTo.add(aboveNeighbor);
            }
        }
        // wall climb stair pattern
        for (Ordinal ordinal : valuesExceptC()) {
            Voxel horizNeighbor = parent.getNeighbor(HORIZ, ordinal);
            Voxel horizBelowNeighbor = parent.getNeighbor(BELOW, ordinal);
            if (belowNeighbor.pathing.wallImpliesFloorAbove() // stair rule
                    && horizNeighbor.pathing.voxelAllowsUnit() // L rule allow passthrough
                    // = supports wall climb
                    && horizBelowNeighbor.pathing.wallAllowsUnit()) { // L rule support destination
                canPathTo.add(horizBelowNeighbor);
            }
            Voxel horizAboveNeighbor = parent.getNeighbor(ABOVE, ordinal);
            if (horizNeighbor.pathing.wallImpliesFloorAbove() // stair rule
                    && aboveNeighbor.pathing.voxelAllowsUnit() // L rule allow passthrough
                    // = supports wall climb
                    && horizAboveNeighbor.pathing.wallAllowsUnit()) { // L rule support destination
                canPathTo.add(horizAboveNeighbor);
            }
        }

        // climb open pattern
        if (floorAllowsUnit() && belowNeighbor.pathing.supports(Method.CLIMB)) {
            canPathTo.add(belowNeighbor);
        }
        if (aboveNeighbor.pathing.floorAllowsUnit() && aboveNeighbor.pathing.supports(Method.CLIMB)) {
            canPathTo.add(aboveNeighbor);
        }
        for (Ordinal ordinal : valuesExceptC()) {
            Voxel horizNeighbor = parent.getNeighbor(HORIZ, ordinal);
            Voxel horizBelowNeighbor = parent.getNeighbor(BELOW, ordinal);
            if (horizNeighbor.pathing.supports(Method.CLIMB)) { // L rule allow passthrough
                canPathTo.add(horizNeighbor);
                if (horizNeighbor.pathing.floorAllowsUnit() // L rule allow passthrough
                        && horizBelowNeighbor.pathing.supports(Method.CLIMB)) { // L rule support destination
                    canPathTo.add(horizBelowNeighbor);
                }
            }
            Voxel horizAboveNeighbor = parent.getNeighbor(ABOVE, ordinal);
            if (aboveNeighbor.pathing.floorAllowsUnit() // L rule allow passthrough
                    && aboveNeighbor.pathing.supports(Method.CLIMB) // L rule allow passthrough
                    && horizAboveNeighbor.pathing.supports(Method.CLIMB)) { // L rule support destination
                canPathTo.add(horizAboveNeighbor);
            }
        }

        return new ArrayList<>(canPathTo);
    }

    // open pattern
    private ArrayList<Voxel> canPathToViaSwim() {
        ArrayList<Voxel> canPathTo = new ArrayList<>();

        Voxel belowNeighbor = parent.getNeighbor(BELOW, C);
        if (floorAllowsUnit() && belowNeighbor.pathing.supports(Method.SWIM)) {
            canPathTo.add(belowNeighbor);
        }
        Voxel aboveNeighbor = parent.getNeighbor(ABOVE, C);
        if (aboveNeighbor.pathing.floorAllowsUnit() && aboveNeighbor.pathing.supports(Method.SWIM)) {
            canPathTo.add(aboveNeighbor);
        }
        for (Ordinal ordinal : valuesExceptC()) {
            Voxel horizNeighbor = parent.getNeighbor(HORIZ, ordinal);
            Voxel horizBelowNeighbor = parent.getNeighbor(BELOW, ordinal);
            if (horizNeighbor.pathing.supports(Method.SWIM)) { // L rule allow passthrough
                canPathTo.add(horizNeighbor);
                if (horizNeighbor.pathing.floorAllowsUnit() // L rule allow passthrough
                        && horizBelowNeighbor.pathing.supports(Method.SWIM)) { // L rule support destination
                    canPathTo.add(horizBelowNeighbor);
                }
            }
            Voxel horizAboveNeighbor = parent.getNeighbor(ABOVE, ordinal);
            if (aboveNeighbor.pathing.floorAllowsUnit() // L rule allow passthrough
                    && aboveNeighbor.pathing.supports(Method.SWIM) // L rule allow passthrough
                    && horizAboveNeighbor.pathing.supports(Method.SWIM)) { // L rule support destination
                canPathTo.add(horizAboveNeighbor);
            }
        }
        return canPathTo;
    }

    // open pattern
    private ArrayList<Voxel> canPathToViaFly() {
        ArrayList<Voxel> canPathTo = new ArrayList<>();

        Voxel belowNeighbor = parent.getNeighbor(BELOW, C);
        if (floorAllowsUnit() && (belowNeighbor.pathing.supports(Method.FLY)
                || belowNeighbor.pathing.wallAllowsUnit())) { // fliers can land straight down
            canPathTo.add(belowNeighbor);
        }
        Voxel aboveNeighbor = parent.getNeighbor(ABOVE, C);
        if (aboveNeighbor.pathing.floorAllowsUnit() && aboveNeighbor.pathing.supports(Method.FLY)) {
            canPathTo.add(aboveNeighbor);
        }
        for (Ordinal ordinal : valuesExceptC()) {
            Voxel horizNeighbor = parent.getNeighbor(HORIZ, ordinal);
            Voxel horizBelowNeighbor = parent.getNeighbor(BELOW, ordinal);
            if (horizNeighbor.pathing.supports(Method.FLY)) { // L rule allow passthrough
                canPathTo.add(horizNeighbor);
                if (horizNeighbor.pathing.floorAllowsUnit() // L rule allow passthrough
                        && horizBelowNeighbor.pathing.supports(Method.FLY)) { // L rule support destination
                    canPathTo.add(horizBelowNeighbor);
                }
            }
            Voxel horizAboveNeighbor = parent.getNeighbor(ABOVE, ordinal);
            if (aboveNeighbor.pathing.floorAllowsUnit() // L rule allow passthrough
                    && aboveNeighbor.pathing.supports(Method.FLY) // L rule allow passthrough
                    && horizAboveNeighbor.pathing.supports(Method.FLY)) { // L rule support destination
                canPathTo.add(horizAboveNeighbor);
            }
        }
        return canPathTo;
    }

    // whether this voxel allows entry by gait method
    private boolean supports(Method method) {
        switch (method) {
            case CRAWL:
            case WALK:
            case WHEEL:
            case LEAP:
                return supportsStand();
            case CLIMB:
                return supportsClimb();
            case SWIM:
                return supportsSwim();
            case FLY:
                return supportsFly();
            case BUOYANT:
                return supportsBuoyant();
        }
        return false;
    }

    private boolean supportsStand() {
        return floorSupportsUnit()
                && wallAllowsUnit()
                && parent.supportedFluids.isLowLiquid();
    }

    private boolean supportsClimb() {
        return wallSupportsClimb()
                && parent.supportedFluids.isLowLiquid();
    }

    private boolean supportsSwim() {
        return wallAllowsUnit()
                && parent.supportedFluids.isHighLiquid();
    }

    private boolean supportsFly() {
        return voxelIsBlank()
//                && parent.getNeighbor(BELOW, C).pathing.voxelIsBlank()
                && parent.supportedFluids.isNoLiquid();
    }

    private boolean supportsBuoyant() {
        return floorAllowsUnit() // or IsLiquidFloor
                && wallAllowsUnit()
                && parent.supportedFluids.isLowLiquid()
                && parent.getNeighbor(HORIZ, C).supportedFluids.isHighLiquid();
    }

    // neighbors that can be pathed to by matter state, for propel
    public boolean canPathToSolid(Voxel destination) {
        return canPathToMatter.get(Matter.SOLID).contains(destination);
    }

    public boolean canPathToLiquid(Voxel destination) {
        return canPathToMatter.get(Matter.LIQUID).contains(destination);
    }

    public boolean canPathToGas(Voxel destination) {
        return canPathToMatter.get(Matter.GAS).contains(destination);
    }

    private void updateCanPathToMatter() {
        for (Matter matter : Matter.values()) {
            canPathToMatter.put(matter, canPathTo(matter));
        }
    }

    private ArrayList<Voxel> canPathTo(Matter matter) {
        ArrayList<Voxel> canPathTo = new ArrayList<>();
        switch (matter) {
            case SOLID:
                return canPathToViaSolid();
            case LIQUID:
                return canPathToViaLiquid();
            case GAS:
                return canPathToViaGas();
        }
        return canPathTo;
    }

    private ArrayList<Voxel> canPathToViaSolid() {
        return new ArrayList<>();
    }

    private ArrayList<Voxel> canPathToViaLiquid() {
        return new ArrayList<>();
    }

    private ArrayList<Voxel> canPathToViaGas() {
        return new ArrayList<>();
    }

    // floor path logic
    public boolean floorIsForbidden() { return pathTokensFloor.contains(PathToken.FORBIDDEN); }

    // implies floorAllowsUnit()
    public boolean floorIsBlank() { return pathTokensFloor.isEmpty(); }

    public boolean floorAllowsUnit() { return !pathTokensFloor.contains(PathToken.BLOCKS_UNIT); }

    public boolean floorSupportsUnit() { return pathTokensFloor.contains(PathToken.BLOCKS_UNIT); }

    public boolean floorSupportsSolid() { return pathTokensFloor.contains(PathToken.BLOCKS_SOLID); }

    public boolean floorSupportsPlant() { return pathTokensFloor.contains(PathToken.SUPPORTS_PLANT); }

    public boolean floorAllowsSolid() { return !pathTokensFloor.contains(PathToken.BLOCKS_SOLID); }

    // wall path logic
    public boolean wallIsForbidden() { return pathTokensWall.contains(PathToken.FORBIDDEN); }

    public boolean wallIsBlank() { return pathTokensWall.isEmpty(); }

    public boolean wallAllowsUnit() { return !pathTokensWall.contains(PathToken.BLOCKS_UNIT); }

    public boolean canReachFloor() { return wallAllowsUnit(); }

    public boolean wallBlocksSolid() { return pathTokensWall.contains(PathToken.BLOCKS_SOLID); }

    public boolean wallAllowsSolid() { return !pathTokensWall.contains(PathToken.BLOCKS_SOLID); }

    public boolean wallImpliesFloorSame() { return pathTokensWall.contains(PathToken.IMPLIES_FLOOR_SAME); }

    public boolean wallImpliesFloorAbove() { return pathTokensWall.contains(PathToken.IMPLIES_FLOOR_ABOVE); }

    public boolean wallEnablesRamping() { return pathTokensWall.contains(PathToken.ENABLES_RAMPING); }

    public boolean wallEnablesStairing() {
        return pathTokensWall.contains(PathToken.ENABLES_STAIRING) || wallEnablesRamping();
    }

    public boolean wallSupportsClimb() { return pathTokensWall.contains(PathToken.SUPPORTS_CLIMB); }

    public boolean neighborWallEnablesClimb() {
        return parent.neighbors.getHorizPlusC().stream()
                .anyMatch(voxel -> voxel.pathing.wallImpliesFloorAbove());
    }

    public boolean wallIsSticky() { return pathTokensWall.contains(PathToken.STICKY); }

    public boolean wallCanOpen() { return pathTokensWall.contains(PathToken.OPENABLE); }

    // voxel path logic
    public boolean voxelIsForbidden() { return wallIsForbidden(); }

    public boolean voxelIsBlank() { return floorIsBlank() && wallIsBlank(); }

    public boolean voxelAllowsUnit() { return floorAllowsUnit() && wallAllowsUnit(); }

    public boolean voxelAllowsSolid() { return floorAllowsSolid() && wallAllowsSolid(); }

    // refresh path tokens
    public void updatePathTokensFloor(EnumSet<PathToken> tokens) {
        pathTokensFloor.clear();
        pathTokensFloor.addAll(tokens);
        CollapseRegister.registerUpdateConnected(parent.neighbors.getAll());
        CollapseRegister.registerUpdatePathing(parent.neighbors.getAllPlusHorizLeap());
    }

    public void updatePathTokensWall(EnumSet<PathToken> tokens) {
        pathTokensWall.clear();
        pathTokensWall.addAll(tokens);
        CollapseRegister.registerUpdateConnected(parent.neighbors.getAll());
        CollapseRegister.registerUpdatePathing(parent.neighbors.getAllPlusHorizLeap());
    }

    public void forceUpdateConnected() {
        updateIsConnectedTo();
    }

    public void forceUpdatePathing() {
        updateCanPathToByMethod();
        updateCanPathToMatter();
    }
}
