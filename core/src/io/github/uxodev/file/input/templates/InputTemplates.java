package io.github.uxodev.file.input.templates;

public class InputTemplates {
    public static void parseTemplates() {
        InputTemplatesUnit.parsePartTemplates("data\\template\\body\\unit\\part templates.toml");

        InputTemplatesUnit.parseAnimalTemplates("data\\template\\body\\unit\\animal\\animal templates.toml");
        InputTemplatesUnit.parseSentientTemplates("data\\template\\body\\unit\\sentient\\sentient templates.toml");
    }
}
