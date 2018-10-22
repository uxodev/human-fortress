package io.github.uxodev.file.input.templates;

import io.github.uxodev.file.FileError;
import io.github.uxodev.file.input.Input;
import io.github.uxodev.file.input.sources.InputSources;
import io.github.uxodev.model.both.widget._data.SourceData;
import io.github.uxodev.model.both.widget.instance.dynamic.Dynamic;
import io.github.uxodev.model.both.widget.instance.part.Part;
import io.github.uxodev.model.both.widget.token.source.SourceToken;
import com.moandjiezana.toml.Toml;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import static io.github.uxodev.file.FileError.ErrorId.SOURCE_MISSING_ENTRY;
import static io.github.uxodev.file.FileError.ErrorId.TEMPLATE_MISSING_ENTRY;

public class InputTemplatesUnit {
    private static HashMap<String, HashMap<String, Toml>> partTemplatesToPartsToSource = new HashMap<>();

    static void parsePartTemplates(String path) {
        Toml toml = Input.getToml(path);
        if (toml == null) return;

        for (int i = 0; i < toml.getList("part_template").size(); i++) {
            Toml part_template = toml.getTable("part_template[" + i + "]");

            String nameTemplate = part_template.getString("name");
            partTemplatesToPartsToSource.putIfAbsent(nameTemplate, new HashMap<>());

            for (int j = 0; j < part_template.getList("part").size(); j++) {
                Toml part = part_template.getTable("part[" + j + "]");
                Toml source = part.getTable("source");
                String nameSource2 = source.getString("name");

                partTemplatesToPartsToSource.get(nameTemplate).putIfAbsent(nameSource2, source);
            }
        }
    }

    static void parseAnimalTemplates(String path) {
        Toml toml = Input.getToml(path);
        if (toml == null) return;

        for (int i = 0; i < toml.getList("animal").size(); i++) {
            Toml tomlAnimal = toml.getTable("animal[" + i + "]");
            if (FileError.isNull(tomlAnimal, TEMPLATE_MISSING_ENTRY, toml.toString())) continue;

            parseUnitTemplate(tomlAnimal);
        }
    }

    static void parseSentientTemplates(String path) {
        Toml toml = Input.getToml(path);
        if (toml == null) return;

        for (int i = 0; i < toml.getList("sentient").size(); i++) {
            Toml tomlSentient = toml.getTable("sentient[" + i + "]");
            if (FileError.isNull(tomlSentient, TEMPLATE_MISSING_ENTRY, toml.toString())) continue;

            parseUnitTemplate(tomlSentient);
        }
    }

    private static void parseUnitTemplate(Toml tomlUnit) {
        ArrayList<Dynamic> dropItems = new ArrayList<>();
        for (int i = 0; i < tomlUnit.getList("drop.item").size(); i++) {
            Toml tomlDropItem = tomlUnit.getTable("drop.item[" + i + "]");

            dropItems.add(parseUnitDropItem(tomlUnit, tomlDropItem));
        }
    }

    private static Dynamic parseUnitDropItem(Toml tomlUnit, Toml tomlDropItem) {
        Toml tomlSource = tomlDropItem.getTable("source");
        if (FileError.isNull(tomlSource, TEMPLATE_MISSING_ENTRY, "?")) return null;

        String partTemplateName = tomlDropItem.getString("part_template");
        parseSource(tomlUnit, tomlSource, partTemplateName);

        ArrayList<Part> parts = new ArrayList<>();
        for (int k = 0; k < tomlDropItem.getList("part").size(); k++) {
            Toml tomlDropItemPart = tomlDropItem.getTable("part[" + k + "]");
            if (FileError.isNull(tomlDropItemPart, TEMPLATE_MISSING_ENTRY, "?")) return null;

            Toml tomlDropItemPartSource = tomlDropItemPart.getTable("source");
            if (FileError.isNull(tomlDropItemPartSource, TEMPLATE_MISSING_ENTRY, "?")) return null;

            parseSource(tomlUnit, tomlDropItemPartSource, partTemplateName);
        }
        partTemplatesToPartsToSource.get(partTemplateName).forEach((s, toml) ->
                parseSource(tomlUnit, toml, partTemplateName));

        return null;
    }

    private static void parseSource(Toml unit, Toml source2, String partTemplateName) {
        String nameSource1 = unit.getString("name");
        String nameSource2 = source2.getString("name");

        String color = source2.getString("color");
        if (color.equals("body"))
            color = unit.getTable("body").getString("color");
        else if (color.equals("default"))
            color = partTemplatesToPartsToSource.get(partTemplateName).get(nameSource2).getString("color");
        Long density = source2.getLong("density", 1L);
        Long value = source2.getLong("value", 0L);
        List<String> property_tokens2 = source2.getList("property_tokens", new ArrayList<>());

        SourceToken sourceToken = new SourceToken(nameSource1);
        SourceData.SOURCE1_TOKENS.putIfAbsent(nameSource1, sourceToken);

        sourceToken = new SourceToken(nameSource2);
        SourceData.SOURCE2_TOKENS.putIfAbsent(nameSource2, sourceToken);

        if (FileError.isNull(color, SOURCE_MISSING_ENTRY, "todo"))
            return;

        InputSources.createSource(nameSource1, nameSource2, property_tokens2, property_tokens2, color, density, value);
    }
}
