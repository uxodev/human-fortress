package io.github.uxodev.file.input.materials;

import io.github.uxodev.file.FileError;
import io.github.uxodev.file.input.Input;
import io.github.uxodev.file.input.sources.InputSources;
import io.github.uxodev.model.both.widget._data.MaterialData;
import io.github.uxodev.model.both.widget._data.token.PropertyToken;
import io.github.uxodev.model.both.widget.token.material.MaterialToken;
import com.moandjiezana.toml.Toml;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

import static io.github.uxodev.file.FileError.ErrorId.MATERIAL_NAME;

public class InputMaterials {
    public static void parseMaterials() {
        addHardcodedMaterials();
        parseMaterials("data\\widget\\material\\default materials.toml");
        parseMaterials("data\\widget\\material\\artificial materials.toml");
    }

    private static void addHardcodedMaterials() {
        String name = "forbidden";
        MaterialToken materialToken = new MaterialToken(name, 0, new HashSet<>());
        MaterialData.MATERIAL_TOKENS.putIfAbsent(name, materialToken);
    }

    private static void parseMaterials(String path) {
        Toml toml = Input.getToml(path);
        if (toml == null) return;

        for (int i = 0; i < toml.getList("material").size(); i++) {
            Toml tomlMaterial = toml.getTable("material[" + i + "]");

            String name = tomlMaterial.getString("name");
            Long multiplier = tomlMaterial.getLong("multiplier", 100L);
            List<String> property_tokens = tomlMaterial.getList("property_tokens", new ArrayList<>());

            if (FileError.isNull(name, MATERIAL_NAME, path + " material[" + i + "]"))
                continue;

            HashSet<PropertyToken> propertyTokens = InputSources.registerPropertyTokens(property_tokens);

            MaterialToken materialToken = new MaterialToken(name, Math.toIntExact(multiplier), propertyTokens);
            MaterialData.MATERIAL_TOKENS.putIfAbsent(name, materialToken);
        }
    }
}
