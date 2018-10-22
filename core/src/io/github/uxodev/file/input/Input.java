package io.github.uxodev.file.input;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;
import com.moandjiezana.toml.Toml;
import io.github.uxodev.file.FileError;
import io.github.uxodev.file.input.materials.InputMaterials;
import io.github.uxodev.file.input.shapes.InputShapes;
import io.github.uxodev.file.input.sources.InputSources;
import io.github.uxodev.file.input.templates.InputTemplates;
import io.github.uxodev.model.both.widget._data.MaterialData;
import io.github.uxodev.model.both.widget._data.ShapeData;
import io.github.uxodev.model.both.widget._data.SourceData;

import java.io.File;

import static io.github.uxodev.file.FileError.ErrorId.*;

public class Input {
    public static void initFileData() {
        InputTemplates.parseTemplates();

        InputSources.parseSources();
        System.out.println("SOURCE1_TOKENS: " + SourceData.SOURCE1_TOKENS);
        System.out.println("SOURCE2_TOKENS: " + SourceData.SOURCE2_TOKENS);
        System.out.println("SOURCE_TEMPLATES: " + SourceData.SOURCE_TEMPLATES.values());

        InputMaterials.parseMaterials();
        System.out.println("MATERIAL_TOKENS: " + MaterialData.MATERIAL_TOKENS);

        InputShapes.parseShapes();
        System.out.println("SHAPES: ");
        System.out.println(ShapeData.COMMODITY_SHAPES.values());
        System.out.println(ShapeData.ITEM_SHAPES.values());
        System.out.println(ShapeData.ITEM_CONTAINER_SHAPES.values());
        System.out.println(ShapeData.TERRAIN_SHAPE_TOKENS.values());
        System.out.println(ShapeData.FURNITURE_SHAPES.values());
        System.out.println(ShapeData.FURNITURE_CONTAINER_SHAPES.values());
    }

    public static Toml getToml(String path) {
//        FileHandle classpath = Gdx.files.local(Gdx.files.getLocalStoragePath() + path);
//        File file = classpath.file();
        File file = new File(System.getProperty("user.dir") + "\\" + path);
        if (!file.exists()) {
            FileError.throwError(TOML_MISSING, file.toString());
            return null;
        }
        Toml toml;
        try {
            toml = new Toml().read(file);
        } catch (RuntimeException e) {
            FileError.throwError(TOML_INVALID, file.toString());
            return null;
        }
        if (toml.isEmpty()) {
            FileError.throwError(TOML_EMPTY, file.toString());
            return null;
        }
        return toml;
    }
}
