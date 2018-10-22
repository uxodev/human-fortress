package io.github.uxodev.file.input.shapes;

import io.github.uxodev.file.FileError;
import io.github.uxodev.file.input.Input;
import io.github.uxodev.model.both.widget._data.ShapeData;
import io.github.uxodev.model.both.widget._data.token.ContainToken;
import io.github.uxodev.model.both.widget._data.token.UseToken;
import io.github.uxodev.model.both.widget.token.shape.dynamic.CommodityShapeToken;
import io.github.uxodev.model.both.widget.token.shape.dynamic.ItemShapeToken;
import io.github.uxodev.model.both.widget.token.shape.fluid.FluidShapeToken;
import io.github.uxodev.model.both.widget.token.shape.part.PartShapeToken;
import com.moandjiezana.toml.Toml;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

import static io.github.uxodev.file.FileError.ErrorId.SHAPE_CLASS_NO_MATCHING_CLASS;
import static io.github.uxodev.file.FileError.ErrorId.SHAPE_NAME;

public class InputShapesDynamic {

    static void parseDynamicShapes(String path) {
        Toml toml = Input.getToml(path);
        if (toml == null) return;

        for (int i = 0; i < toml.getList("shape").size(); i++) {
            Toml tomlShape = toml.getTable("shape[" + i + "]");

            String name = tomlShape.getString("name");
            String aClass = tomlShape.getString("class");
            Long volume = tomlShape.getLong("volume", 100L);
            Long multiplier = tomlShape.getLong("multiplier", 100L);
            List<String> use_tokens = tomlShape.getList("use_tokens", new ArrayList<>());
            String containable = tomlShape.getString("containable", "");

            if (FileError.isNull(name, SHAPE_NAME, path + " shape[" + i + "]"))
                continue;

            HashSet<UseToken> useTokens;
            ContainToken containableToken;
            switch (aClass) {
                case "fluid":
                    useTokens = InputShapes.registerUseTokens(use_tokens);
                    containableToken = InputShapes.registerContainableToken(containable);
                    FluidShapeToken fluidShapeToken = new FluidShapeToken(
                            name,
                            Math.toIntExact(volume),
                            Math.toIntExact(multiplier),
                            useTokens,
                            containableToken);
                    ShapeData.FLUID_SHAPES.putIfAbsent(name, fluidShapeToken);
                    break;
                case "commodity":
                    useTokens = InputShapes.registerUseTokens(use_tokens);
                    containableToken = InputShapes.registerContainableToken(containable);
                    CommodityShapeToken commodityShapeToken = new CommodityShapeToken(
                            name,
                            Math.toIntExact(volume),
                            Math.toIntExact(multiplier),
                            useTokens,
                            containableToken);
                    ShapeData.COMMODITY_SHAPES.putIfAbsent(name, commodityShapeToken);
                    PartShapeToken partShapeToken = new PartShapeToken(
                            name,
                            Math.toIntExact(volume),
                            Math.toIntExact(multiplier));
                    ShapeData.PART_SHAPE_TOKENS.putIfAbsent(name, partShapeToken);
                    break;
                case "item":
                    useTokens = InputShapes.registerUseTokens(use_tokens);
                    containableToken = InputShapes.registerContainableToken(containable);
                    ItemShapeToken itemShapeToken = new ItemShapeToken(
                            name,
                            Math.toIntExact(volume),
                            Math.toIntExact(multiplier),
                            useTokens,
                            containableToken);
                    ShapeData.ITEM_SHAPES.putIfAbsent(name, itemShapeToken);
                    partShapeToken = new PartShapeToken(
                            name,
                            Math.toIntExact(volume),
                            Math.toIntExact(multiplier));
                    ShapeData.PART_SHAPE_TOKENS.putIfAbsent(name, partShapeToken);
                    break;
                default:
                    FileError.throwError(SHAPE_CLASS_NO_MATCHING_CLASS, name, aClass);
                    break;
            }
        }
    }
}
