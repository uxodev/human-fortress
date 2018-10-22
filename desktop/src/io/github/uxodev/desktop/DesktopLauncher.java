package io.github.uxodev.desktop;

import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import io.github.uxodev.libgdx.display.app.AppRenderer;

import static io.github.uxodev.model.both._data.Time.STEP_PER_RSEC;

public class DesktopLauncher {
    public static void main(String[] arg) {
        LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();
        config.foregroundFPS = STEP_PER_RSEC;
        config.backgroundFPS = STEP_PER_RSEC;
//        config.fullscreen = true;
        config.width = LwjglApplicationConfiguration.getDesktopDisplayMode().width;
        config.height = LwjglApplicationConfiguration.getDesktopDisplayMode().height - 70;
        config.x = 0;
        config.y = 0;
        new LwjglApplication(new AppRenderer(), config);
    }
}
