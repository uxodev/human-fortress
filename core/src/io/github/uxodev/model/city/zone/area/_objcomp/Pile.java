package io.github.uxodev.model.city.zone.area._objcomp;

import io.github.uxodev.model.both.widget.instance.dynamic.Dynamic;
import io.github.uxodev.model.city.map.voxel.Voxel;
import io.github.uxodev.model.city.zone.area.Area;
import io.github.uxodev.model.city.zone.match.Match;

import java.util.ArrayList;

public class Pile implements IPile {
    private final Area parentArea;

    public final ArrayList<Dynamic> enclosedDynamics = new ArrayList<>();
    public ArrayList<Match> matches = new ArrayList<>();
    private boolean hasSpace = true;
    public int value = 0;

    public Pile(Area parentArea) {
        this.parentArea = parentArea;
    }

    public boolean hasSpace() {
        for (Voxel enclosedVoxel : parentArea.enclosedVoxels) {
            if (enclosedVoxel.isEmpty() && !enclosedVoxel.isLocked()) {
                hasSpace = true;
                return hasSpace;
            }
        }
        hasSpace = false;
        return hasSpace;
    }

    @Override
    public Voxel getSpace() {
        for (Voxel enclosedVoxel : parentArea.enclosedVoxels) {
            if (enclosedVoxel.isEmpty() && !enclosedVoxel.isLocked()) {
                hasSpace = true;
                return enclosedVoxel;
            }
        }
        hasSpace = false;
        return null;
    }

    @Override
    public ArrayList<Match> getMatches() {
        return matches;
    }
}
