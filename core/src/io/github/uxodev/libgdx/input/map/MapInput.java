package io.github.uxodev.libgdx.input.map;

import com.badlogic.gdx.Input;
import io.github.uxodev.libgdx.display.app.MapRender;
import io.github.uxodev.libgdx.input.game.GameInput;
import io.github.uxodev.libgdx.input.modkeys.ModKeysInput;
import io.github.uxodev.model.both._data.Game;

public class MapInput {

    public static void setShape(VoxelSelect.SelectShape selectShape) {
        Game.currentMap.voxelSelect.setShape(selectShape);
    }

    static void updateMapSelect() {
        Game.currentMap.setSelect(Game.currentMap.voxelSelect.volumeSelected);
    }

    static void updateMapHover() {
        Game.currentMap.setHover(Game.currentMap.voxelSelect.volumeHovered);
    }

    // key input
    public static void setModKeys(boolean isShift, boolean isCtrl, boolean isAlt) {
        Game.currentMap.voxelSelect.setModKeys(isShift, isCtrl, isAlt);
    }

    public static void undo() {
        Game.currentMap.voxelSelect.undo();
    }

    public static void redo() {
        Game.currentMap.voxelSelect.redo();
    }

    public static void clear() {
        Game.currentMap.voxelSelect.clear();
    }

    public static boolean keyDown(int keycode) {
        switch (keycode) {
            case Input.Keys.SPACE:
                GameInput.switchPaused();
                return true;
            case Input.Keys.NUM_1:
                GameInput.oneStep();
                return true;
            case Input.Keys.NUM_2:

                return true;
            case Input.Keys.NUM_3:

                return true;
            case Input.Keys.ESCAPE:
                clear();
                return true;
            case Input.Keys.Z:
                if (ModKeysInput.isShift && ModKeysInput.isCtrl) {
                    redo();
                    return true;
                } else if (ModKeysInput.isCtrl) {
                    undo();
                    return true;
                }
                break;
        }
        return false;
    }

    public static boolean keyUp(int keycode) {
        return false;
    }

    public static boolean keyTyped(char character) {
        return false;
    }

    // mouse input
    public static boolean touchDown(int indexSelected, int button) {
        if (indexSelected >= 0)
            Game.currentMap.voxelSelect.touchDown(Game.currentMap.getVoxelOrForbidden(
                    MapRender.indexToPosition(indexSelected)), button);
        else
            Game.currentMap.voxelSelect.touchDownOutsideBounds(button);
        return true;
    }

    public static boolean touchUp(int indexSelected, int button) {
        if (indexSelected >= 0)
            Game.currentMap.voxelSelect.touchUp(Game.currentMap.getVoxelOrForbidden(
                    MapRender.indexToPosition(indexSelected)), button);
        else
            Game.currentMap.voxelSelect.touchUpOutsideBounds(button);
        return true;
    }

    public static boolean mouseMoved(int indexMoved) {
        if (indexMoved >= 0)
            Game.currentMap.voxelSelect.mouseMove(Game.currentMap.getVoxelOrForbidden(
                    MapRender.indexToPosition(indexMoved)));
        return true;
    }
}
