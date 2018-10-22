package io.github.uxodev.model.both.widget._data;

import io.github.uxodev.model.both.widget._data.token.ContainToken;
import io.github.uxodev.model.both.widget._data.token.UseToken;
import io.github.uxodev.model.both.widget.token.shape.dynamic.CommodityShapeToken;
import io.github.uxodev.model.both.widget.token.shape.dynamic.ItemContainerShapeToken;
import io.github.uxodev.model.both.widget.token.shape.dynamic.ItemShapeToken;
import io.github.uxodev.model.both.widget.token.shape.dynamic.transport.TransportShapeToken;
import io.github.uxodev.model.both.widget.token.shape.dynamic.transport.VehicleShapeToken;
import io.github.uxodev.model.both.widget.token.shape.fluid.FluidShapeToken;
import io.github.uxodev.model.both.widget.token.shape.part.PartShapeToken;
import io.github.uxodev.model.both.widget.token.shape.stable.FurnitureContainerShapeToken;
import io.github.uxodev.model.both.widget.token.shape.stable.FurnitureShapeToken;
import io.github.uxodev.model.both.widget.token.shape.stable.terrain.TerrainFloorShapeToken;
import io.github.uxodev.model.both.widget.token.shape.stable.terrain.TerrainShapeToken;
import io.github.uxodev.model.both.widget.token.shape.stable.terrain.TerrainWallShapeToken;

import java.util.HashMap;

public class ShapeData {
    public static final HashMap<String, FluidShapeToken> FLUID_SHAPES = new HashMap<>();

    public static final HashMap<String, CommodityShapeToken> COMMODITY_SHAPES = new HashMap<>();
    public static final HashMap<String, ItemShapeToken> ITEM_SHAPES = new HashMap<>();
    public static final HashMap<String, ItemContainerShapeToken> ITEM_CONTAINER_SHAPES = new HashMap<>();

    public static final HashMap<String, TransportShapeToken> TRANSPORT_SHAPES = new HashMap<>();
    public static final HashMap<String, VehicleShapeToken> VEHICLE_SHAPES = new HashMap<>();

    public static final HashMap<String, FurnitureShapeToken> FURNITURE_SHAPES = new HashMap<>();
    public static final HashMap<String, FurnitureContainerShapeToken> FURNITURE_CONTAINER_SHAPES = new HashMap<>();

    public static final HashMap<String, TerrainShapeToken> TERRAIN_SHAPE_TOKENS = new HashMap<>();
    public static final HashMap<String, TerrainFloorShapeToken> FLOOR_SHAPE_TOKENS = new HashMap<>();
    public static final HashMap<String, TerrainWallShapeToken> WALL_SHAPE_TOKENS = new HashMap<>();

    public static final HashMap<String, PartShapeToken> PART_SHAPE_TOKENS = new HashMap<>();

    public static final HashMap<String, UseToken> USE_TOKENS = new HashMap<>();
    public static final HashMap<String, ContainToken> CONTAIN_TOKENS = new HashMap<>();
}
