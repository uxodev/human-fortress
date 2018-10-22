package io.github.uxodev.model.city.register;

import io.github.uxodev.model.city.map.voxel.Voxel;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashSet;

public class CollapseRegister {
    private static HashSet<Voxel> updateConnected = new HashSet<>();
    private static HashSet<Voxel> checkCollapse = new HashSet<>();
    private static HashSet<Voxel> stepCollapse = new HashSet<>();
    private static HashSet<Voxel> stepFlowables = new HashSet<>();
    private static HashSet<Voxel> updatePathing = new HashSet<>();

    // update connections
    public static void registerUpdateConnected(HashSet<Voxel> voxels) {
        updateConnected.addAll(voxels);
    }

    public static void allUpdateConnected() {
        updateConnected.forEach(Voxel::updateConnected);
        updateConnected.clear();
    }

    // check, step collapse
    public static void registerCheckCollapse(HashSet<Voxel> voxels) {
        checkCollapse.addAll(voxels);
    }

    public static void allCheckCollapse() {
        checkCollapse.forEach(Voxel::initGravity);
        checkCollapse.clear();
    }

    public static void registerStepCollapse(Voxel voxel) {
        stepCollapse.add(voxel);
    }

    public static void removeStepCollapse(Voxel voxel) {
        stepCollapse.remove(voxel);
    }

    public static void allStepCollapse() {
        if (!stepCollapse.isEmpty()) {
            ArrayList<Voxel> sorted = new ArrayList<>(stepCollapse);
            sorted.sort(Comparator.comparingInt(Voxel::getElevation));
            sorted.forEach(Voxel::stepGravity);
        }
    }

    // update pathing
    public static void registerUpdatePathing(HashSet<Voxel> voxels) {
        updatePathing.addAll(voxels);
    }

    public static void allUpdatePathing() {
        updatePathing.forEach(Voxel::updatePathing);
        updatePathing.clear();
    }
}
