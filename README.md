# Human Fortress

A basic clone of the game Dwarf Fortress in 3D. Uses the [libGDX framework](https://github.com/libgdx/libgdx).

#### Units path through a 3D grid of floors, walls, and stairs to find items and deliver them to matching areas.
![](https://i.imgur.com/k1kDAmB.gif)
![](https://i.imgur.com/atWFcuL.gif)

#### Players can modify the terrain grid tiles and create new units, items, and areas without pausing the game.
![](https://i.imgur.com/8HbC5eJ.gif)

#### Grid tiles collapse if they are no longer attached to the ground after a change.
![](https://i.imgur.com/P5xIDrQ.gif)

#### Items are created from TOML config files, allowing the user to define new items by name and color.
```
[[source2]]
    name = "soil"
    property_tokens = []
    [[source2.source1]]
        name = "loam"
        color = "a0522d"
        density = 10
        value = 1
        property_tokens = []
    [[source2.source1]]
        name = "clay"
        color = "d2b48c"
        density = 10
        value = 1
        property_tokens = []
    [[source2.source1]]
        name = "sand"
        color = "f0e68c"
        density = 10
        value = 1
        property_tokens = []
```

To start: run DesktopLauncher class, change run configuration working directory to ...\human-fortress\core\assets
