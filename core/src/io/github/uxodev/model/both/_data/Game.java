package io.github.uxodev.model.both._data;

import io.github.uxodev.libgdx.display.app.MapRender;
import io.github.uxodev.model.city.map.Map;
import io.github.uxodev.model.city.map.MapFactory;
import io.github.uxodev.model.city.map.Spawn;

public class Game {
    public static Time time = new Time(0, 30, 17, 0, 0, 0);

    public static Map currentMap;

    public static void init() {
        generateMap();
    }

    public static void step() {
        time.step();
        currentMap.step();
    }

    public static void generateMap() {
        MapFactory mapFactory = new MapFactory(MapRender.HEIGHT, MapRender.LENGTH, MapRender.WIDTH);
        currentMap = mapFactory.testMap().make();
        Spawn.spawn();
    }
}
