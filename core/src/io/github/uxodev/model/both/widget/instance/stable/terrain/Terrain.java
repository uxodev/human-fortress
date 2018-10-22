package io.github.uxodev.model.both.widget.instance.stable.terrain;

import io.github.uxodev.model.both.widget.instance.stable.Stable;
import io.github.uxodev.model.both.widget.token.material.MaterialToken;
import io.github.uxodev.model.both.widget.token.shape.stable.terrain.TerrainShapeToken;
import io.github.uxodev.model.both.widget.token.source.Source;

public abstract class Terrain extends Stable {
    private final TerrainShapeToken terrainShapeToken;

    public Terrain(Source source, MaterialToken materialToken, TerrainShapeToken terrainShapeToken) {
        super(source, materialToken, terrainShapeToken);
        this.terrainShapeToken = terrainShapeToken;
    }

    @Override
    public TerrainShapeToken getShapeToken() {
        return terrainShapeToken;
    }
}
