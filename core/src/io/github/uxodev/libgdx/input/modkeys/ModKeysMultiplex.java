package io.github.uxodev.libgdx.input.modkeys;

import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputProcessor;

public class ModKeysMultiplex implements InputProcessor {

    @Override
    public boolean keyDown(int keycode) {
        switch (keycode) {
            case Input.Keys.SHIFT_LEFT:
            case Input.Keys.SHIFT_RIGHT:
                ModKeysInput.setShift(true);
                return true;
            case Input.Keys.CONTROL_LEFT:
            case Input.Keys.CONTROL_RIGHT:
                ModKeysInput.setCtrl(true);
                return true;
            case Input.Keys.ALT_LEFT:
            case Input.Keys.ALT_RIGHT:
                ModKeysInput.setAlt(true);
                return true;
        }
        return false;
    }

    public boolean keyUp(int keycode) {
        switch (keycode) {
            case Input.Keys.SHIFT_LEFT:
            case Input.Keys.SHIFT_RIGHT:
                ModKeysInput.setShift(false);
                return true;
            case Input.Keys.CONTROL_LEFT:
            case Input.Keys.CONTROL_RIGHT:
                ModKeysInput.setCtrl(false);
                return true;
            case Input.Keys.ALT_LEFT:
            case Input.Keys.ALT_RIGHT:
                ModKeysInput.setAlt(false);
                return true;
        }
        return false;
    }

    @Override
    public boolean keyTyped(char character) {
        return false;
    }

    @Override
    public boolean touchDown(int screenX, int screenY, int pointer, int button) {
        return false;
    }

    @Override
    public boolean touchUp(int screenX, int screenY, int pointer, int button) {
        return false;
    }

    @Override
    public boolean touchDragged(int screenX, int screenY, int pointer) {
        return false;
    }

    @Override
    public boolean mouseMoved(int screenX, int screenY) {
        return false;
    }

    @Override
    public boolean scrolled(int amount) {
        return false;
    }
}
