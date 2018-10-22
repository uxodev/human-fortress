package io.github.uxodev.model.city.map.voxel._objcomp;

import io.github.uxodev.model.both._data.Coord;
import io.github.uxodev.model.city.map.Map;
import io.github.uxodev.model.city.map.voxel.Voxel;

import java.util.Arrays;
import java.util.EnumMap;
import java.util.HashSet;

public class Neighbors {
    private final EnumMap<Ordinal, Voxel> horiz = new EnumMap<>(Ordinal.class);
    private final EnumMap<Ordinal, Voxel> below = new EnumMap<>(Ordinal.class);
    private final EnumMap<Ordinal, Voxel> above = new EnumMap<>(Ordinal.class);

    private final HashSet<Voxel> all = new HashSet<>();
    private final HashSet<Voxel> allMinusC = new HashSet<>();
    private final HashSet<Voxel> allPlusHorizLeap = new HashSet<>();
    private final HashSet<Voxel> horizPlusC = new HashSet<>();

    public void initNeighbors(Map map, Coord position) {
        horiz.put(Ordinal.C, map.getVoxelOrForbidden(position.x, position.y, position.z));
        horiz.put(Ordinal.N, map.getVoxelOrForbidden(position.x, position.y - 1, position.z));
        horiz.put(Ordinal.E, map.getVoxelOrForbidden(position.x + 1, position.y, position.z));
        horiz.put(Ordinal.S, map.getVoxelOrForbidden(position.x, position.y + 1, position.z));
        horiz.put(Ordinal.W, map.getVoxelOrForbidden(position.x - 1, position.y, position.z));
        horiz.put(Ordinal.NE, map.getVoxelOrForbidden(position.x + 1, position.y - 1, position.z));
        horiz.put(Ordinal.SE, map.getVoxelOrForbidden(position.x + 1, position.y + 1, position.z));
        horiz.put(Ordinal.SW, map.getVoxelOrForbidden(position.x - 1, position.y + 1, position.z));
        horiz.put(Ordinal.NW, map.getVoxelOrForbidden(position.x - 1, position.y - 1, position.z));

        below.put(Ordinal.C, map.getVoxelOrForbidden(position.x, position.y, position.z - 1));
        below.put(Ordinal.N, map.getVoxelOrForbidden(position.x, position.y - 1, position.z - 1));
        below.put(Ordinal.E, map.getVoxelOrForbidden(position.x + 1, position.y, position.z - 1));
        below.put(Ordinal.S, map.getVoxelOrForbidden(position.x, position.y + 1, position.z - 1));
        below.put(Ordinal.W, map.getVoxelOrForbidden(position.x - 1, position.y, position.z - 1));
        below.put(Ordinal.NE, map.getVoxelOrForbidden(position.x + 1, position.y - 1, position.z - 1));
        below.put(Ordinal.SE, map.getVoxelOrForbidden(position.x + 1, position.y + 1, position.z - 1));
        below.put(Ordinal.SW, map.getVoxelOrForbidden(position.x - 1, position.y + 1, position.z - 1));
        below.put(Ordinal.NW, map.getVoxelOrForbidden(position.x - 1, position.y - 1, position.z - 1));

        above.put(Ordinal.C, map.getVoxelOrForbidden(position.x, position.y, position.z + 1));
        above.put(Ordinal.N, map.getVoxelOrForbidden(position.x, position.y - 1, position.z + 1));
        above.put(Ordinal.E, map.getVoxelOrForbidden(position.x + 1, position.y, position.z + 1));
        above.put(Ordinal.S, map.getVoxelOrForbidden(position.x, position.y + 1, position.z + 1));
        above.put(Ordinal.W, map.getVoxelOrForbidden(position.x - 1, position.y, position.z + 1));
        above.put(Ordinal.NE, map.getVoxelOrForbidden(position.x + 1, position.y - 1, position.z + 1));
        above.put(Ordinal.SE, map.getVoxelOrForbidden(position.x + 1, position.y + 1, position.z + 1));
        above.put(Ordinal.SW, map.getVoxelOrForbidden(position.x - 1, position.y + 1, position.z + 1));
        above.put(Ordinal.NW, map.getVoxelOrForbidden(position.x - 1, position.y - 1, position.z + 1));

        all.addAll(horiz.values());
        all.addAll(below.values());
        all.addAll(above.values());
        all.remove(map.getVoxelOrForbidden(-1, -1, -1));

        allMinusC.addAll(horiz.values());
        allMinusC.remove(horiz.get(Ordinal.C));
        allMinusC.addAll(below.values());
        allMinusC.addAll(above.values());
        allMinusC.remove(map.getVoxelOrForbidden(-1, -1, -1));

        // plus secondary neighbors on horiz plane
        allPlusHorizLeap.addAll(all);
        allPlusHorizLeap.add(map.getVoxelOrForbidden(position.x, position.y - 2, position.z));
        allPlusHorizLeap.add(map.getVoxelOrForbidden(position.x + 2, position.y, position.z));
        allPlusHorizLeap.add(map.getVoxelOrForbidden(position.x, position.y + 2, position.z));
        allPlusHorizLeap.add(map.getVoxelOrForbidden(position.x - 2, position.y, position.z));
        allPlusHorizLeap.add(map.getVoxelOrForbidden(position.x + 2, position.y - 2, position.z));
        allPlusHorizLeap.add(map.getVoxelOrForbidden(position.x + 2, position.y + 2, position.z));
        allPlusHorizLeap.add(map.getVoxelOrForbidden(position.x - 2, position.y + 2, position.z));
        allPlusHorizLeap.add(map.getVoxelOrForbidden(position.x - 2, position.y - 2, position.z));
        allPlusHorizLeap.remove(map.getVoxelOrForbidden(-1, -1, -1));

        horizPlusC.addAll(horiz.values());
        horizPlusC.remove(map.getVoxelOrForbidden(-1, -1, -1));
    }

    public void initForbidden(Voxel parent) {
        for (Ordinal ordinal : Ordinal.values()) {
            horiz.put(ordinal, parent);
            below.put(ordinal, parent);
            above.put(ordinal, parent);
        }
        all.add(parent);
    }

    public Voxel get(Plane plane, Ordinal ordinal) {
        switch (plane) {
            case HORIZ:
                return horiz.get(ordinal);
            case BELOW:
                return below.get(ordinal);
            case ABOVE:
                return above.get(ordinal);
        }
        return null;
    }

    public HashSet<Voxel> getAll() {
        return all;
    }

    public HashSet<Voxel> getAllMinusC() {
        return allMinusC;
    }

    public HashSet<Voxel> getAllPlusHorizLeap() {
        return allPlusHorizLeap;
    }

    public HashSet<Voxel> getHorizPlusC() {
        return horizPlusC;
    }

    public enum Plane {
        HORIZ, BELOW, ABOVE,
    }

    public enum Ordinal {
        C,
        N, E, S, W,
        NE, SE, SW, NW,;

        public static final Ordinal[] valuesExceptC;

        static {
            valuesExceptC = Arrays.copyOfRange(Ordinal.values(), 1, Ordinal.values().length);
        }

        public static Ordinal[] valuesExceptC() {
            return valuesExceptC;
        }
    }
}
