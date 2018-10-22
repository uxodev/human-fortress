package io.github.uxodev.libgdx.display.app;

import com.badlogic.gdx.ApplicationListener;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputMultiplexer;
import com.badlogic.gdx.graphics.GL30;
import com.badlogic.gdx.graphics.PerspectiveCamera;
import com.badlogic.gdx.graphics.g3d.ModelBatch;
import com.badlogic.gdx.graphics.g3d.utils.CameraInputController;
import com.badlogic.gdx.math.Vector3;
import io.github.uxodev.file.input.Input;
import io.github.uxodev.libgdx.display.ui.Ui;
import io.github.uxodev.libgdx.input.game.GameInput;
import io.github.uxodev.libgdx.input.map.MapMultiplex;
import io.github.uxodev.libgdx.input.mapmenu.MapMenuMultiplex;
import io.github.uxodev.libgdx.input.modkeys.ModKeysMultiplex;
import io.github.uxodev.libgdx.input.world.WorldMultiplex;
import io.github.uxodev.libgdx.input.worldmenu.WorldMenuMultiplex;
import io.github.uxodev.model.both._data.Game;

// static class
public class AppRenderer implements ApplicationListener {
    static PerspectiveCamera camera;
    private static CameraInputController cameraInputController;
    private static ModelBatch modelBatch;

    @Override
    public void create() {
        MapRender.init();
        Ui.init();

        Input.initFileData();
        Game.init();

        int MID_X = (MapRender.WIDTH * MapRender.MODEL_WIDTH) / 2;
        int MID_Y = (MapRender.LENGTH * MapRender.MODEL_LENGTH) / 2;
        camera = new PerspectiveCamera(20, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        camera.position.set(MID_X, MID_Y, 300);
        camera.rotateAround(new Vector3(0, 0, 0), new Vector3(1, 0, 0), 65);
        camera.rotateAround(new Vector3(MID_X, MID_Y, 0), new Vector3(0, 0, 1), 45);
        camera.rotateAround(new Vector3(0, 0, 0), new Vector3(0, 0, 1), -20);
        camera.lookAt(MID_X + 17, MID_Y - 37, 0);
        camera.near = 1f;
        camera.far = 1000f;
        camera.update(true);
        cameraInputController = new CameraInputController(camera) {
            private final Vector3 tmpV1 = new Vector3();
            private final Vector3 tmpV2 = new Vector3();

            @Override
            public boolean process(float deltaX, float deltaY, int button) {
                if (button == rotateButton) {
                    tmpV1.set(camera.direction).crs(camera.up).y = 0f;
                    camera.rotateAround(target, Vector3.Z, deltaX * -rotateAngle);
                } else if (button == translateButton) {
                    camera.translate(tmpV1.set(camera.direction).crs(camera.up).nor().scl(-deltaX * translateUnits));
                    camera.translate(tmpV2.set(camera.up).scl(-deltaY * translateUnits));
                    if (translateTarget) target.add(tmpV1).add(tmpV2);
                } else if (button == forwardButton) {
                    camera.translate(tmpV1.set(camera.direction).scl(deltaY * translateUnits));
                    if (forwardTarget) target.add(tmpV1);
                }
                if (autoUpdate) camera.update();
                return true;
            }

            @Override
            public void update() {
                if (rotateRightPressed || rotateLeftPressed || forwardPressed || backwardPressed) {
                    if (rotateRightPressed) process(.01f, 0, translateButton);
                    if (rotateLeftPressed) process(-.01f, 0, translateButton);
                    if (forwardPressed) process(0, -.01f, translateButton);
                    if (backwardPressed) process(0, .01f, translateButton);
                    if (autoUpdate) camera.update();
                }
            }
        };
        cameraInputController.forwardTarget = false;
        cameraInputController.translateTarget = false;
        cameraInputController.target = new Vector3(MID_X, MID_Y, 0);
        cameraInputController.translateButton = 2;
        cameraInputController.rotateButton = 1;
        cameraInputController.rotateAngle = 180;
        cameraInputController.scrollFactor = -.5f;
        cameraInputController.translateUnits = 200;

        modelBatch = new ModelBatch();
        Gdx.gl.glClearColor(.5f, .5f, .5f, 0);
        Gdx.gl.glViewport(0, 0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());

        InputMultiplexer inputMultiplexer = new InputMultiplexer();
        inputMultiplexer.addProcessor(new ModKeysMultiplex());
        inputMultiplexer.addProcessor(new WorldMenuMultiplex());
        inputMultiplexer.addProcessor(new WorldMultiplex());
        inputMultiplexer.addProcessor(new MapMenuMultiplex());
        inputMultiplexer.addProcessor(new MapMultiplex());
        inputMultiplexer.addProcessor(cameraInputController);
        Gdx.input.setInputProcessor(inputMultiplexer);
    }

    @Override
    public void render() {
        if (!GameInput.isPaused) {
            Game.step();
        } else if (GameInput.oneStep) {
            GameInput.oneStep = false;
            Game.step();
        }

        cameraInputController.update();
        MapRender.update();
        Ui.update();

        Gdx.gl.glClear(GL30.GL_COLOR_BUFFER_BIT | GL30.GL_DEPTH_BUFFER_BIT);
        modelBatch.begin(camera);
        for (int i = 0; i < MapRender.WIDTH; i++)
            for (int j = 0; j < MapRender.LENGTH; j++)
                for (int k = 0; k < MapRender.HEIGHT; k++) {
                    modelBatch.render(MapRender.cubes3d[i][j][k].bot);
                    modelBatch.render(MapRender.cubes3d[i][j][k].top);
                }
        modelBatch.end();

        Ui.stage.act();
        Ui.stage.draw();
    }

    @Override
    public void resize(int width, int height) {
        Ui.stage.getViewport().update(width, height, true);
    }

    @Override
    public void pause() {
    }

    @Override
    public void resume() {
    }

    @Override
    public void dispose() {
        modelBatch.dispose();
    }
}
