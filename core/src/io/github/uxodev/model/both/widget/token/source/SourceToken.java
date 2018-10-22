package io.github.uxodev.model.both.widget.token.source;

public class SourceToken {
    public final String name;

    public SourceToken(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return name;
    }
}
