package io.github.uxodev.model.city.zone.area._objcomp;

import io.github.uxodev.model.city.map.voxel.Voxel;
import io.github.uxodev.model.city.zone.match.Match;

import java.util.ArrayList;

public interface IPile {
    Voxel getSpace();
    ArrayList<Match> getMatches();
}
