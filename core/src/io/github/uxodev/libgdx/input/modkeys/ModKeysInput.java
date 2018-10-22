package io.github.uxodev.libgdx.input.modkeys;

import io.github.uxodev.libgdx.input.map.MapInput;
import io.github.uxodev.libgdx.input.world.WorldInput;

public class ModKeysInput {
    public static boolean isAlt = false;
    public static boolean isShift = false;
    public static boolean isCtrl = false;

    public static void setShift(boolean isShift) {
        ModKeysInput.isShift = isShift;
        sendModKeys();
    }

    public static void setCtrl(boolean isCtrl) {
        ModKeysInput.isCtrl = isCtrl;
        sendModKeys();
    }

    public static void setAlt(boolean isAlt) {
        ModKeysInput.isAlt = isAlt;
        sendModKeys();
    }

    private static void sendModKeys() {
        MapInput.setModKeys(isShift, isCtrl, isAlt);
        WorldInput.setModKeys(isShift, isCtrl, isAlt);
    }
}
