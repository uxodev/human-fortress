package io.github.uxodev.model.city.map;

import io.github.uxodev.libgdx.display.ui.buttonPanel.ButtonPanelEvents;
import io.github.uxodev.libgdx.input.game.GameInput;
import io.github.uxodev.libgdx.input.map.MapInput;
import io.github.uxodev.model.both._data.Game;

public class Spawn {
    public static void spawn() {
        // units
        Game.currentMap.voxelSelect.touchDown(
                Game.currentMap.getVoxelOrForbidden(0, 0, 0), 0);
        Game.currentMap.voxelSelect.touchUp(
                Game.currentMap.getVoxelOrForbidden(3, 3, 0), 0);
        ButtonPanelEvents.addUnit(1);
        MapInput.clear();

        // items
        Game.currentMap.voxelSelect.touchDown(
                Game.currentMap.getVoxelOrForbidden(5, 0, 0), 0);
        Game.currentMap.voxelSelect.touchUp(
                Game.currentMap.getVoxelOrForbidden(9, 5, 0), 0);
        ButtonPanelEvents.addItem(1);
        MapInput.clear();
        Game.currentMap.voxelSelect.touchDown(
                Game.currentMap.getVoxelOrForbidden(10, 0, 0), 0);
        Game.currentMap.voxelSelect.touchUp(
                Game.currentMap.getVoxelOrForbidden(14, 5, 0), 0);
        ButtonPanelEvents.addItem(2);
        MapInput.clear();
        Game.currentMap.voxelSelect.touchDown(
                Game.currentMap.getVoxelOrForbidden(15, 0, 0), 0);
        Game.currentMap.voxelSelect.touchUp(
                Game.currentMap.getVoxelOrForbidden(19, 5, 0), 0);
        ButtonPanelEvents.addItem(3);
        MapInput.clear();
        Game.currentMap.voxelSelect.touchDown(
                Game.currentMap.getVoxelOrForbidden(25, 0, 0), 0);
        Game.currentMap.voxelSelect.touchUp(
                Game.currentMap.getVoxelOrForbidden(29, 5, 0), 0);
        ButtonPanelEvents.addItem(4);
        MapInput.clear();
        Game.currentMap.voxelSelect.touchDown(
                Game.currentMap.getVoxelOrForbidden(30, 0, 0), 0);
        Game.currentMap.voxelSelect.touchUp(
                Game.currentMap.getVoxelOrForbidden(37, 5, 0), 0);
        ButtonPanelEvents.addItem(5);
        MapInput.clear();

        // areas
        Game.currentMap.voxelSelect.touchDown(
                Game.currentMap.getVoxelOrForbidden(5, 18, 1), 0);
        Game.currentMap.voxelSelect.touchUp(
                Game.currentMap.getVoxelOrForbidden(7, 20, 1), 0);
        ButtonPanelEvents.addArea(1);
        MapInput.clear();
        Game.currentMap.voxelSelect.touchDown(
                Game.currentMap.getVoxelOrForbidden(10, 18, 1), 0);
        Game.currentMap.voxelSelect.touchUp(
                Game.currentMap.getVoxelOrForbidden(12, 20, 1), 0);
        ButtonPanelEvents.addArea(2);
        MapInput.clear();
        Game.currentMap.voxelSelect.touchDown(
                Game.currentMap.getVoxelOrForbidden(15, 18, 1), 0);
        Game.currentMap.voxelSelect.touchUp(
                Game.currentMap.getVoxelOrForbidden(17, 20, 1), 0);
        ButtonPanelEvents.addArea(3);
        MapInput.clear();
        Game.currentMap.voxelSelect.touchDown(
                Game.currentMap.getVoxelOrForbidden(25, 18, 1), 0);
        Game.currentMap.voxelSelect.touchUp(
                Game.currentMap.getVoxelOrForbidden(37, 23, 1), 0);
        ButtonPanelEvents.addArea(4);
        MapInput.clear();
        Game.currentMap.voxelSelect.touchDown(
                Game.currentMap.getVoxelOrForbidden(25, 30, 2), 0);
        Game.currentMap.voxelSelect.touchUp(
                Game.currentMap.getVoxelOrForbidden(37, 34, 2), 0);
        ButtonPanelEvents.addArea(5);
        MapInput.clear();
    }
}
