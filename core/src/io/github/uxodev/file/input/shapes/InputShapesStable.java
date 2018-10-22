package io.github.uxodev.file.input.shapes;

import io.github.uxodev.file.FileError;
import io.github.uxodev.file.input.Input;
import io.github.uxodev.model.both.widget._data.ShapeData;
import io.github.uxodev.model.both.widget._data.token.PathToken;
import io.github.uxodev.model.both.widget._data.token.UseToken;
import io.github.uxodev.model.both.widget.token.shape.stable.FurnitureShapeToken;
import io.github.uxodev.model.both.widget.token.shape.stable.terrain.TerrainShapeToken;
import com.moandjiezana.toml.Toml;

import java.util.ArrayList;
import java.util.EnumSet;
import java.util.HashSet;
import java.util.List;

import static io.github.uxodev.file.FileError.ErrorId.SHAPE_CLASS_NO_MATCHING_CLASS;
import static io.github.uxodev.file.FileError.ErrorId.SHAPE_NAME;
import static io.github.uxodev.model.both.widget._data.token.PathToken.*;

public class InputShapesStable {
    static void addHardcodedTerrain() {
        String name = "forbidden floor";
        TerrainShapeToken forbiddenFloor = new TerrainShapeToken(
                name, 0, 0,
                EnumSet.of(BLOCKS_SOLID, BLOCKS_UNIT, FORBIDDEN));
        ShapeData.TERRAIN_SHAPE_TOKENS.putIfAbsent(name, forbiddenFloor);

        name = "implied floor";
        TerrainShapeToken impliedFloor = new TerrainShapeToken(
                name, 0, 0,
                EnumSet.of(BLOCKS_SOLID, BLOCKS_UNIT, IMPLIED));
        ShapeData.TERRAIN_SHAPE_TOKENS.putIfAbsent(name, impliedFloor);

        name = "forbidden wall";
        TerrainShapeToken forbiddenWall = new TerrainShapeToken(
                name, 0, 0,
                EnumSet.of(BLOCKS_UNIT, BLOCKS_SOLID, BLOCKS_LIQUID, BLOCKS_GAS, BLOCKS_LIGHT,
                        IMPLIES_FLOOR_ABOVE, FORBIDDEN));
        ShapeData.TERRAIN_SHAPE_TOKENS.putIfAbsent(name, forbiddenWall);
    }

    static void parseStableShapes(String path) {
        Toml toml = Input.getToml(path);
        if (toml == null) return;

        for (int i = 0; i < toml.getList("shape").size(); i++) {
            Toml tomlShape = toml.getTable("shape[" + i + "]");

            String name = tomlShape.getString("name");
            String aClass = tomlShape.getString("class");
            Long volume = tomlShape.getLong("volume", 100L);
            Long multiplier = tomlShape.getLong("multiplier", 100L);
            List<String> use_tokens = tomlShape.getList("use_tokens", new ArrayList<>());
            List<String> path_tokens = tomlShape.getList("path_tokens", new ArrayList<>());

            if (FileError.isNull(name, SHAPE_NAME, path + " shape[" + i + "]"))
                continue;

            HashSet<UseToken> useTokens;
            EnumSet<PathToken> pathTokens;
            switch (aClass) {
                case "floor":
                case "wall":
                    pathTokens = InputShapes.registerPathTokens(path_tokens, name);
                    TerrainShapeToken terrainShapeToken = new TerrainShapeToken(
                            name,
                            Math.toIntExact(volume),
                            Math.toIntExact(multiplier),
                            pathTokens);
                    ShapeData.TERRAIN_SHAPE_TOKENS.putIfAbsent(name, terrainShapeToken);
                    break;
                case "furniture":
                    useTokens = InputShapes.registerUseTokens(use_tokens);
                    pathTokens = InputShapes.registerPathTokens(path_tokens, name);
                    FurnitureShapeToken furnitureShapeToken = new FurnitureShapeToken(
                            name,
                            Math.toIntExact(volume),
                            Math.toIntExact(multiplier),
                            useTokens,
                            pathTokens);
                    ShapeData.FURNITURE_SHAPES.putIfAbsent(name, furnitureShapeToken);
                    break;
                default:
                    FileError.throwError(SHAPE_CLASS_NO_MATCHING_CLASS, name, aClass);
                    break;
            }
        }
    }
}
