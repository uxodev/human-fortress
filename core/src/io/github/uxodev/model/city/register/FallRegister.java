package io.github.uxodev.model.city.register;

import io.github.uxodev.model.both._objcomp.force.gravity.IGravity;

import java.util.ArrayList;
import java.util.HashSet;

public class FallRegister {
    private static HashSet<IGravity> checkFall = new HashSet<>();
    private static HashSet<IGravity> stepFall = new HashSet<>();

    public static void registerCheckFall(IGravity fallable) {
        checkFall.add(fallable);
    }

    public static void allCheckFall() {
        checkFall.forEach(IGravity::initGravity);
        checkFall.clear();
    }

    public static void registerStepFall(IGravity fallable) {
        stepFall.add(fallable);
    }

    public static void removeStepFall(IGravity fallable) {
        stepFall.remove(fallable);
    }

    public static void stepFall() {
        if (!stepFall.isEmpty()) {
            ArrayList<IGravity> sorted = new ArrayList<>(stepFall);
//            sorted.sort(Comparator.comparingInt(IGravity::getElevation));
            sorted.forEach(IGravity::stepGravity);
        }
    }
}
