package io.github.uxodev.libgdx.input.mapmenu;

import com.badlogic.gdx.InputProcessor;
import io.github.uxodev.libgdx.display.ui.Ui;

public class MapMenuMultiplex implements InputProcessor {
    @Override
    public boolean keyDown(int keycode) {
        return MapMenuInput.keyDown(keycode);
    }

    @Override
    public boolean keyUp(int keycode) {
        return MapMenuInput.keyUp(keycode);
    }

    @Override
    public boolean keyTyped(char character) {
        return MapMenuInput.keyTyped(character);
    }

    @Override
    public boolean touchDown(int screenX, int screenY, int pointer, int button) {
        return Ui.stage.touchDown(screenX, screenY, pointer, button);
    }

    @Override
    public boolean touchUp(int screenX, int screenY, int pointer, int button) {
        return Ui.stage.touchUp(screenX, screenY, pointer, button);
    }

    @Override
    public boolean touchDragged(int screenX, int screenY, int pointer) {
        return Ui.stage.touchDragged(screenX, screenY, pointer);
    }

    @Override
    public boolean mouseMoved(int screenX, int screenY) {
        return Ui.stage.mouseMoved(screenX, screenY);
    }

    @Override
    public boolean scrolled(int amount) {
        return Ui.stage.scrolled(amount);
    }
}
