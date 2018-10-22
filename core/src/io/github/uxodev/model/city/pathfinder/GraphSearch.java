package io.github.uxodev.model.city.pathfinder;

import io.github.uxodev.model.both._data.Coord;
import io.github.uxodev.model.city.map.Map;
import io.github.uxodev.model.city.map.voxel.Voxel;

import java.util.*;

public class GraphSearch {
    public static ArrayList<Voxel> getAllConnected(Voxel startVoxel) {
        ArrayList<Voxel> visited = new ArrayList<>();
        visitConnected(visited, startVoxel);
        return visited;
    }

    private static void visitConnected(ArrayList<Voxel> visited, Voxel voxel) {
        visited.add(voxel);
        for (Voxel neighbor : voxel.neighbors.getAllMinusC()) {
            if (!visited.contains(neighbor) && voxel.pathing.getIsConnectedTo().contains(neighbor))
                visitConnected(visited, neighbor);
        }
    }

    public static boolean isPathToFoundation(Map map, Voxel startVoxel) {
        Voxel endVoxel = map.getVoxelOrForbidden(0, 0, 0);

        Queue<Voxel> frontier = new LinkedList<>();
        HashMap<Voxel, Voxel> cameFrom = new HashMap<>();

        frontier.add(startVoxel);
        cameFrom.put(startVoxel, null);

        Voxel currentVoxel = startVoxel;

        while (!frontier.isEmpty()) {
            currentVoxel = frontier.poll();

            if (currentVoxel.equals(endVoxel)) {
                break;
            }

            for (Voxel next : currentVoxel.pathing.getIsConnectedTo()) {
                if (!cameFrom.containsKey(next)) {
                    frontier.add(next);
                    cameFrom.put(next, currentVoxel);
                }
            }
        }

        return currentVoxel.equals(endVoxel);
    }

    public static Stack<Voxel> getPathConnected(Voxel startVoxel, Voxel endVoxel) {
        Queue<Voxel> frontier = new LinkedList<>();
        HashMap<Voxel, Voxel> cameFrom = new HashMap<>();

        frontier.add(startVoxel);
        cameFrom.put(startVoxel, null);

        Voxel currentVoxel = startVoxel;

        while (!frontier.isEmpty()) {
            currentVoxel = frontier.poll();

            if (currentVoxel.equals(endVoxel)) {
                break;
            }

            for (Voxel next : currentVoxel.pathing.getIsConnectedTo()) {
                if (!cameFrom.containsKey(next)) {
                    frontier.add(next);
                    cameFrom.put(next, currentVoxel);
                }
            }
        }

        if (!currentVoxel.equals(endVoxel)) {
            return new Stack<>();
        }

        Stack<Voxel> path = new Stack<>();
        while (!currentVoxel.equals(startVoxel)) {
            path.add(currentVoxel);
            currentVoxel = cameFrom.get(currentVoxel);
        }
        return path;
    }

    public static Stack<Voxel> getPathBreathFirst(Map map, Coord start, Coord destination) {
        Voxel startVoxel = map.getVoxelOrForbidden(start);
        Voxel endVoxel = map.getVoxelOrForbidden(destination);
        return getPathBreathFirst(startVoxel, endVoxel);
    }

    public static boolean pathExists(Voxel source, Voxel dest) {
        return GraphSearch.getPathBreathFirst(source, dest) != null;
    }

    public static Stack<Voxel> getPathBreathFirst(Voxel startVoxel, Voxel endVoxel) {
        Queue<Voxel> frontier = new LinkedList<>();
        HashMap<Voxel, Voxel> cameFrom = new HashMap<>();

        frontier.add(startVoxel);
        cameFrom.put(startVoxel, null);

        Voxel currentVoxel = startVoxel;

        while (!frontier.isEmpty()) {
            currentVoxel = frontier.poll();

            if (currentVoxel.equals(endVoxel)) {
                break;
            }

            for (Voxel next : currentVoxel.pathing.getCanPathToByMethod().get(Pathfinder.Method.WALK)) {
                if (!cameFrom.containsKey(next)) {
                    frontier.add(next);
                    cameFrom.put(next, currentVoxel);
                }
            }
        }

        if (!currentVoxel.equals(endVoxel)) { // no path found from startVoxel to endVoxel
            return null;
        }

        Stack<Voxel> path = new Stack<>();
        while (!currentVoxel.equals(startVoxel)) {
            path.add(currentVoxel);
            currentVoxel = cameFrom.get(currentVoxel);
        }
        return path;
    }
}
