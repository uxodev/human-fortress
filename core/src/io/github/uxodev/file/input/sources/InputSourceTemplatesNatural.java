package io.github.uxodev.file.input.sources;

import io.github.uxodev.file.FileError;
import io.github.uxodev.file.input.Input;
import com.moandjiezana.toml.Toml;

import static io.github.uxodev.file.FileError.ErrorId.TEMPLATE_MISSING_ENTRY;

public class InputSourceTemplatesNatural {

    public static void parseNaturalSourceTemplates(String path) {
        Toml toml = Input.getToml(path);
        if (toml == null) return;

        for (int i = 0; i < toml.getList("source2").size(); i++) {
            Toml tomlSource2 = toml.getTable("source2[" + i + "]");
            if (FileError.isNull(tomlSource2, TEMPLATE_MISSING_ENTRY, path + " source2[" + i + "]"))
                continue;

            for (int j = 0; j < tomlSource2.getList("source1").size(); j++) {
                Toml tomlSource1 = tomlSource2.getTable("source1[" + j + "]");
                if (FileError.isNull(tomlSource1, TEMPLATE_MISSING_ENTRY, path + " source1[" + j + "]"))
                    continue;

                String source1Name = tomlSource1.getString("name");
                String source2Name = tomlSource2.getString("name");
                InputSources.parseSource(source1Name, source2Name, tomlSource1);
            }
        }
    }
}
