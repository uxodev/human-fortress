package io.github.uxodev.model.both.widget._data.token;

import java.util.HashSet;

public enum PathToken {
    FORBIDDEN,

    BLOCKS_UNIT,
    BLOCKS_SOLID,
    BLOCKS_LIQUID,
    BLOCKS_GAS,
    BLOCKS_LIGHT,

    // floor only
    IMPLIED,
    SUPPORTS_PLANT,

    // wall only
    IMPLIES_FLOOR_SAME,
    IMPLIES_FLOOR_ABOVE,
    ENABLES_RAMPING,
    ENABLES_STAIRING,
    SUPPORTS_CLIMB,
    STICKY,

    OPENABLE,;

    public static final HashSet<String> map = new HashSet<>();

    static {
        for (PathToken pathToken : PathToken.values()) map.add(pathToken.name());
    }
}
