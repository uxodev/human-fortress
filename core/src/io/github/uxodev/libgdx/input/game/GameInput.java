package io.github.uxodev.libgdx.input.game;

public class GameInput {
    public static boolean isPaused = false;
    public static boolean oneStep = false;

    public static void switchPaused() {
        isPaused = !isPaused;
    }

    public static void oneStep() {
        isPaused = true;
        oneStep = true;
    }
}
