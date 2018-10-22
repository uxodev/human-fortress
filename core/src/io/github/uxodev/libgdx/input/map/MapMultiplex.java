package io.github.uxodev.libgdx.input.map;

import com.badlogic.gdx.InputProcessor;
import io.github.uxodev.libgdx.display.app.MapRender;

public class MapMultiplex implements InputProcessor {
    private boolean isButton1Down = false;
    private boolean isButton2Down = false;

    @Override
    public boolean keyDown(int keycode) {
        return MapInput.keyDown(keycode);
    }

    @Override
    public boolean keyUp(int keycode) {
        return MapInput.keyUp(keycode);
    }

    @Override
    public boolean keyTyped(char character) {
        return MapInput.keyTyped(character);
    }

    @Override
    public boolean touchDown(int screenX, int screenY, int pointer, int button) {
        if (button == 1) isButton1Down = true;
        else if (button == 2) isButton2Down = true;
        if (button == 1 || button == 2) return false; // let right and middle button pass through to camera
        return MapInput.touchDown(MapRender.getCube(screenX, screenY), button);
    }

    @Override
    public boolean touchUp(int screenX, int screenY, int pointer, int button) {
        if (button == 1) isButton1Down = false;
        else if (button == 2) isButton2Down = false;
        if (button == 1 || button == 2) return false;
        return MapInput.touchUp(MapRender.getCube(screenX, screenY), button);
    }

    @Override
    public boolean touchDragged(int screenX, int screenY, int pointer) {
        MapInput.mouseMoved(MapRender.getCube(screenX, screenY));
        return !isButton1Down && !isButton2Down;
    }

    @Override
    public boolean mouseMoved(int screenX, int screenY) {
        MapInput.mouseMoved(MapRender.getCube(screenX, screenY));
        return !isButton1Down && !isButton2Down;
    }

    @Override
    public boolean scrolled(int amount) {
        return false;
    }
}
