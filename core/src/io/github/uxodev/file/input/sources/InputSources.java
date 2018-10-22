package io.github.uxodev.file.input.sources;

import com.badlogic.gdx.graphics.Color;
import com.moandjiezana.toml.Toml;
import io.github.uxodev.file.FileError;
import io.github.uxodev.model.both.widget._data.MaterialData;
import io.github.uxodev.model.both.widget._data.SourceData;
import io.github.uxodev.model.both.widget._data.token.PropertyToken;
import io.github.uxodev.model.both.widget.token.source.Source;
import io.github.uxodev.model.both.widget.token.source.SourceToken;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;

import static io.github.uxodev.file.FileError.ErrorId.SOURCE_MISSING_ENTRY;
import static io.github.uxodev.file.FileError.ErrorId.SOURCE_NAME;

public class InputSources {
    public static void parseSources() {
        addHardcodedSources();
        InputSourceTemplatesNatural.parseNaturalSourceTemplates("data\\widget\\source\\natural source templates.toml");
    }

    private static void addHardcodedSources() {
        SourceData.SOURCE1_TOKENS.putIfAbsent("", new SourceToken(""));
        String name = "forbidden";
        SourceData.SOURCE1_TOKENS.putIfAbsent(name, new SourceToken(name));
        SourceData.SOURCE2_TOKENS.putIfAbsent(name, new SourceToken(name));
        createSource(name, name, new ArrayList<>(), new ArrayList<>(),
                "FFFFFFFF", 0L, 0L);
    }

    public static void parseSource(String nameSource1, String nameSource2, Toml tomlSource) {
        String color = tomlSource.getString("color");
        Long density = tomlSource.getLong("density", 1L);
        Long value = tomlSource.getLong("value", 0L);
        List<String> property_tokens1 = tomlSource.getList("property_tokens", new ArrayList<>());

        if (FileError.isNull(nameSource1, SOURCE_NAME, tomlSource.toString()))
            return;
        if (FileError.isNull(nameSource2, SOURCE_NAME, tomlSource.toString()))
            return;

        SourceToken sourceToken = new SourceToken(nameSource1);
        SourceData.SOURCE1_TOKENS.putIfAbsent(nameSource1, sourceToken);

        sourceToken = new SourceToken(nameSource2);
        SourceData.SOURCE2_TOKENS.putIfAbsent(nameSource2, sourceToken);

        if (FileError.isNull(color, SOURCE_MISSING_ENTRY, "todo"))
            return;

        createSource(nameSource1, nameSource2, property_tokens1, property_tokens1, color, density, value);
    }

    public static void createSource(String nameSource1, String nameSource2,
                                    List<String> property_tokens1, List<String> property_tokens2,
                                    String color, Long density, Long value) {
        HashSet<PropertyToken> propertyTokens = InputSources.registerPropertyTokens(property_tokens1);
        propertyTokens.addAll(InputSources.registerPropertyTokens(property_tokens2));

        Source source = new Source(
                SourceData.SOURCE1_TOKENS.get(nameSource1),
                SourceData.SOURCE2_TOKENS.get(nameSource2),
                Color.valueOf(color),
                Math.toIntExact(density),
                Math.toIntExact(value),
                propertyTokens);
        SourceData.SOURCE_TEMPLATES.putIfAbsent(nameSource1, new HashMap<>());
        SourceData.SOURCE_TEMPLATES.get(nameSource1).putIfAbsent(nameSource2, source);
    }

    public static HashSet<PropertyToken> registerPropertyTokens(List<String> property_tokens) {
        HashSet<PropertyToken> propertyTokens = new HashSet<>();
        for (String token : property_tokens) {
            MaterialData.PROPERTY_TOKENS.putIfAbsent(token, new PropertyToken(token));
            propertyTokens.add(MaterialData.PROPERTY_TOKENS.get(token));
        }
        return propertyTokens;
    }
}
