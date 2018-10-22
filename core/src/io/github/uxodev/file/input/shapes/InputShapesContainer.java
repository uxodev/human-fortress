package io.github.uxodev.file.input.shapes;

import io.github.uxodev.file.FileError;
import io.github.uxodev.file.input.Input;
import io.github.uxodev.model.both.widget._data.ShapeData;
import io.github.uxodev.model.both.widget._data.token.ContainToken;
import io.github.uxodev.model.both.widget._data.token.PathToken;
import io.github.uxodev.model.both.widget._data.token.UseToken;
import io.github.uxodev.model.both.widget.token.shape.dynamic.ItemContainerShapeToken;
import io.github.uxodev.model.both.widget.token.shape.stable.FurnitureContainerShapeToken;
import com.moandjiezana.toml.Toml;

import java.util.ArrayList;
import java.util.EnumSet;
import java.util.HashSet;
import java.util.List;

import static io.github.uxodev.file.FileError.ErrorId.*;

public class InputShapesContainer {
    static void parseContainerShapes(String path) {
        Toml toml = Input.getToml(path);
        if (toml == null) return;

        HashSet<String> containingShapeTokens = new HashSet<>();
        for (int i = 0; i < toml.getList("shape").size(); i++) {
            Toml tomlShape = toml.getTable("shape[" + i + "]");

            String name = tomlShape.getString("name");
            String aClass = tomlShape.getString("class");
            Long volume = tomlShape.getLong("volume", 100L);
            Long multiplier = tomlShape.getLong("multiplier", 100L);
            List<String> use_tokens = tomlShape.getList("use_tokens", new ArrayList<>());
            List<String> path_tokens = tomlShape.getList("path_tokens", new ArrayList<>());
            List<String> contains = tomlShape.getList("contains");
            Long capacity = tomlShape.getLong("capacity");

            if (FileError.isNull(name, SHAPE_NAME, path + " shape[" + i + "]"))
                continue;
            else if (FileError.isNull(contains, SHAPE_CONTAINER_MISSING_CONTAINS, path + " shape[" + i + "]")
                    || FileError.isNull(capacity, SHAPE_CONTAINER_MISSING_CONTAINS, path + " shape[" + i + "]"))
                continue;

            HashSet<UseToken> useTokens;
            EnumSet<PathToken> pathTokens;
            HashSet<ContainToken> containsTokens;
            switch (aClass) {
                case "item container":
                    useTokens = InputShapes.registerUseTokens(use_tokens);
                    containsTokens = InputShapes.registerContainsTokens(contains, containingShapeTokens, name);
                    ItemContainerShapeToken itemContainerShapeToken = new ItemContainerShapeToken(
                            name,
                            Math.toIntExact(volume),
                            Math.toIntExact(multiplier),
                            useTokens,
                            containsTokens,
                            Math.toIntExact(capacity));
                    ShapeData.ITEM_CONTAINER_SHAPES.putIfAbsent(name, itemContainerShapeToken);
                    break;
                case "furniture container":
                    useTokens = InputShapes.registerUseTokens(use_tokens);
                    pathTokens = InputShapes.registerPathTokens(path_tokens, name);
                    containsTokens = InputShapes.registerContainsTokens(contains, containingShapeTokens, name);
                    FurnitureContainerShapeToken furnitureContainerShapeToken = new FurnitureContainerShapeToken(
                            name,
                            Math.toIntExact(volume),
                            Math.toIntExact(multiplier),
                            useTokens,
                            pathTokens,
                            containsTokens,
                            Math.toIntExact(capacity));
                    ShapeData.FURNITURE_CONTAINER_SHAPES.putIfAbsent(name, furnitureContainerShapeToken);
                    break;
                default:
                    FileError.throwError(SHAPE_CLASS_NO_MATCHING_CLASS, name, aClass);
                    break;
            }
        }
        // check for contain tokens that don't have a container
        containingShapeTokens.add(""); // pretend there is a container for "", which means not containable
        for (String containToken : ShapeData.CONTAIN_TOKENS.keySet())
            if (!containingShapeTokens.contains(containToken))
                FileError.throwError(CONTAIN_TOKEN_NO_MATCHING_CONTAINER, containToken);
    }
}
