package io.github.uxodev.model.city.zone.area.sentient;

import io.github.uxodev.model.city.map.voxel.Voxel;
import io.github.uxodev.model.city.zone.Zone;
import io.github.uxodev.model.city.zone.area.Area;
import io.github.uxodev.model.city.zone.area._objcomp.IPile;
import io.github.uxodev.model.city.zone.area._objcomp.Pile;
import io.github.uxodev.model.city.zone.match.Match;

import java.util.ArrayList;
import java.util.HashSet;

public class StockArea extends Area implements IPile {
    public final Pile pile;

    public StockArea(Zone parentZone, HashSet<Voxel> enclosedVoxels, ArrayList<Match> matches) {
        super(parentZone, Area.AreaType.STOCK_AREA, enclosedVoxels);
        pile = new Pile(this);
        pile.matches = matches;
    }

    public void addMatches(ArrayList<Match> matches) {
        pile.matches.addAll(matches);
    }

    @Override
    public ArrayList<Match> getMatches() {
        return pile.getMatches();
    }

    @Override
    public Voxel getSpace() {
        return pile.getSpace();
    }
}
