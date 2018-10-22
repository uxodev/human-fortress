package io.github.uxodev.libgdx.display.app;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.GL30;
import com.badlogic.gdx.graphics.g3d.Material;
import com.badlogic.gdx.graphics.g3d.Model;
import com.badlogic.gdx.graphics.g3d.ModelInstance;
import com.badlogic.gdx.graphics.g3d.attributes.BlendingAttribute;
import com.badlogic.gdx.graphics.g3d.attributes.ColorAttribute;
import com.badlogic.gdx.graphics.g3d.utils.MeshPartBuilder;
import com.badlogic.gdx.graphics.g3d.utils.ModelBuilder;
import com.badlogic.gdx.graphics.g3d.utils.shapebuilders.BoxShapeBuilder;
import com.badlogic.gdx.math.Intersector;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.math.collision.BoundingBox;
import com.badlogic.gdx.math.collision.Ray;
import io.github.uxodev.model.both._data.Coord;
import io.github.uxodev.model.both._data.Game;

import static com.badlogic.gdx.graphics.VertexAttributes.Usage;

// static class
public class MapRender {
    public static final int WIDTH = 40;
    public static final int LENGTH = 40;
    public static final int HEIGHT = 3;
    private static final int CUBE_TOTAL = WIDTH * LENGTH * HEIGHT;

    static final int MODEL_WIDTH = 4;
    static final int MODEL_LENGTH = 4;
    private static final int MODEL_HEIGHT_BOT = 2;
    private static final int MODEL_HEIGHT_TOP = 6;
    private static final int MODEL_HEIGHT = MODEL_HEIGHT_TOP + MODEL_HEIGHT_BOT;
    private static final float MODEL_HEIGHT_TOP_OFFSET = MODEL_HEIGHT_TOP / 2f + MODEL_HEIGHT_BOT / 2f;

    static Cube[][][] cubes3d;
    private static Cube[] cubes1d;

    // outline color
    private static ColorAttribute clear = ColorAttribute.createDiffuse(Color.CLEAR);
    private static ColorAttribute black = ColorAttribute.createDiffuse(Color.BLACK);

    public static class Cube {
        final ModelInstance bot;
        final ModelInstance top;
        final Coord coordinates;

        private final BoundingBox bounds = new BoundingBox();
        final Vector3 center = new Vector3();
        final Vector3 dimensions = new Vector3();

        final float radius;
        boolean isClickableBot = false;
        boolean isClickableTop = false;

        Cube(ModelInstance bot, ModelInstance top, Coord coordinates) {
            this.bot = bot;
            this.top = top;
            this.coordinates = coordinates;

            bot.calculateBoundingBox(bounds);
            top.extendBoundingBox(bounds);
            bounds.getCenter(center);
            bounds.getDimensions(dimensions);
            radius = dimensions.len() / 2f;
        }
    }

    public static int getCube(int screenX, int screenY) {
        Vector3 coordinates = new Vector3();
        Ray ray = AppRenderer.camera.getPickRay(screenX, screenY);
        int cubes1dIndex = -1;
        float distance = -1;
        for (int i = 0; i < cubes1d.length; i++) {
            final Cube cube = cubes1d[i];
            if (!cube.isClickableBot) continue;
            cube.top.transform.getTranslation(coordinates);
            coordinates.add(cube.center);
            float distance2 = ray.origin.dst2(coordinates);
            if (distance >= 0f && distance2 > distance) continue;
            if (Intersector.intersectRaySphere(ray, coordinates, cube.radius, null)) {
                cubes1dIndex = i;
                distance = distance2;
            }
        }
        return cubes1dIndex;
    }

    public static Coord indexToPosition(int index) {
        return cubes1d[index].coordinates;
    }

    public static void init() {
        cubes3d = createCubes();

        cubes1d = new Cube[CUBE_TOTAL];
        int n = 0;
        for (int i = 0; i < WIDTH; i++)
            for (int j = 0; j < LENGTH; j++)
                for (int k = 0; k < HEIGHT; k++) {
                    cubes1d[n] = cubes3d[i][j][k];
                    n++;
                }
    }

    private static Cube[][][] createCubes() {
        Material material = new Material();
        material.set(new ColorAttribute(ColorAttribute.Diffuse, 1f, 1f, 1f, 1f));
        material.set(new BlendingAttribute(GL30.GL_SRC_ALPHA, GL30.GL_ONE_MINUS_SRC_ALPHA, 1f));

        Model botCube;
        {
            ModelBuilder modelBuilderBot = new ModelBuilder();
            modelBuilderBot.begin();
            MeshPartBuilder meshPartBuilder;
            meshPartBuilder = modelBuilderBot.part("box", GL20.GL_TRIANGLES, Usage.Position | Usage.Normal,
                    new Material(new ColorAttribute(ColorAttribute.Diffuse, 1f, 1f, 1f, 1f),
                            new BlendingAttribute(GL30.GL_SRC_ALPHA, GL30.GL_ONE_MINUS_SRC_ALPHA, 1f)));
            BoxShapeBuilder.build(meshPartBuilder, MODEL_WIDTH, MODEL_LENGTH, MODEL_HEIGHT_BOT);
            meshPartBuilder = modelBuilderBot.part("outline", GL20.GL_LINES, Usage.Position | Usage.Normal,
                    new Material(new ColorAttribute(ColorAttribute.Diffuse, 1f, 1f, 1f, 1f),
                            new BlendingAttribute(GL30.GL_SRC_ALPHA, GL30.GL_ONE_MINUS_SRC_ALPHA, 1f)));
            BoxShapeBuilder.build(meshPartBuilder, MODEL_WIDTH + .01f, MODEL_LENGTH + .01f, MODEL_HEIGHT_BOT + .01f);
            botCube = modelBuilderBot.end();
        }
        Model topCube;
        {
            ModelBuilder modelBuilderTop = new ModelBuilder();
            modelBuilderTop.begin();
            MeshPartBuilder meshPartBuilder;
            meshPartBuilder = modelBuilderTop.part("box", GL20.GL_TRIANGLES, Usage.Position | Usage.Normal,
                    new Material(new ColorAttribute(ColorAttribute.Diffuse, 1f, 1f, 1f, 1f),
                            new BlendingAttribute(GL30.GL_SRC_ALPHA, GL30.GL_ONE_MINUS_SRC_ALPHA, 1f)));
            BoxShapeBuilder.build(meshPartBuilder, MODEL_WIDTH, MODEL_LENGTH, MODEL_HEIGHT_TOP);
            meshPartBuilder = modelBuilderTop.part("outline", GL20.GL_LINES, Usage.Position | Usage.Normal,
                    new Material(new ColorAttribute(ColorAttribute.Diffuse, 1f, 1f, 1f, 1f),
                            new BlendingAttribute(GL30.GL_SRC_ALPHA, GL30.GL_ONE_MINUS_SRC_ALPHA, 1f)));
            BoxShapeBuilder.build(meshPartBuilder, MODEL_WIDTH + .01f, MODEL_LENGTH + .01f, MODEL_HEIGHT_TOP + .01f);
            topCube = modelBuilderTop.end();
        }

        Cube[][][] cubes = new Cube[WIDTH][LENGTH][HEIGHT];
        for (int i = 0; i < WIDTH; i++)
            for (int j = 0; j < LENGTH; j++)
                for (int k = 0; k < HEIGHT; k++) {
                    ModelInstance botModel = new ModelInstance(botCube, i * MODEL_WIDTH, j * MODEL_LENGTH, k * MODEL_HEIGHT);
                    ModelInstance topModel = new ModelInstance(topCube, i * MODEL_WIDTH, j * MODEL_LENGTH, k * MODEL_HEIGHT + MODEL_HEIGHT_TOP_OFFSET);
                    cubes[i][j][k] = new Cube(botModel, topModel, new Coord(i, j, k));
                }

        return cubes;
    }

    public static void update() {
        updateCubesFromVoxels();
    }

    private static void updateCubesFromVoxels() {
        for (int i = 0; i < WIDTH; i++)
            for (int j = 0; j < LENGTH; j++)
                for (int k = 0; k < HEIGHT; k++) {
                    setColorBot(i, j, k, Game.currentMap.getVoxelOrForbidden(i, j, k).appearance.getDrawFloor());
                    setColorTop(i, j, k, Game.currentMap.getVoxelOrForbidden(i, j, k).appearance.getDrawWall());
                }
    }

    private static void setColorBot(int x, int y, int z, Color color) {
        cubes3d[x][y][z].bot.materials.get(0).set(ColorAttribute.createDiffuse(color));
        cubes3d[x][y][z].bot.materials.get(1).set(color == Color.CLEAR ? clear : black);
        cubes3d[x][y][z].isClickableBot = (color != Color.CLEAR);
    }

    private static void setColorTop(int x, int y, int z, Color color) {
        cubes3d[x][y][z].top.materials.get(0).set(ColorAttribute.createDiffuse(color));
        cubes3d[x][y][z].top.materials.get(1).set(color == Color.CLEAR ? clear : black);
        cubes3d[x][y][z].isClickableTop = (color != Color.CLEAR);
    }
}
