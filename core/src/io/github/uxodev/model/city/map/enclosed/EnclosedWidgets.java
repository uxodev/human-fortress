package io.github.uxodev.model.city.map.enclosed;

import io.github.uxodev.model.both.widget.instance._objcomp.container.IContainer;
import io.github.uxodev.model.both.widget.instance.dynamic.Commodity;
import io.github.uxodev.model.both.widget.instance.dynamic.Dynamic;
import io.github.uxodev.model.both.widget.instance.dynamic.Item;
import io.github.uxodev.model.both.widget.instance.dynamic.ItemContainer;
import io.github.uxodev.model.both.widget.instance.dynamic.transport.Transport;
import io.github.uxodev.model.both.widget.instance.dynamic.transport.Vehicle;
import io.github.uxodev.model.both.widget.instance.fluid.Fluid;
import io.github.uxodev.model.both.widget.instance.stable.Furniture;
import io.github.uxodev.model.both.widget.instance.stable.FurnitureContainer;
import io.github.uxodev.model.both.widget.instance.stable.Stable;
import io.github.uxodev.model.both.widget.instance.stable.terrain.Terrain;

import java.util.ArrayList;

public class EnclosedWidgets {
    public final ArrayList<IContainer> containers = new ArrayList<>();
    public final ArrayList<Fluid> fluids = new ArrayList<>();

    public final ArrayList<Dynamic> dynamics = new ArrayList<>();
    public final ArrayList<Commodity> commodities = new ArrayList<>();
    public final ArrayList<Item> items = new ArrayList<>();
    public final ArrayList<ItemContainer> itemContainers = new ArrayList<>();

    public final ArrayList<Transport> transports = new ArrayList<>();
    public final ArrayList<Vehicle> vehicles = new ArrayList<>();

    public final ArrayList<Stable> stables = new ArrayList<>();
    public final ArrayList<Terrain> terrains = new ArrayList<>();
    public final ArrayList<Furniture> furnitures = new ArrayList<>();
    public final ArrayList<FurnitureContainer> furnitureContainers = new ArrayList<>();

    public void add(Fluid fluid) {
        fluids.add(fluid);
    }

    public void remove(Fluid fluid) {
        fluids.remove(fluid);
    }

    public void add(Commodity commodity) {
        dynamics.add(commodity);
        commodities.add(commodity);
    }

    public void remove(Commodity commodity) {
        dynamics.remove(commodity);
        commodities.remove(commodity);
    }

    public void add(Item item) {
        dynamics.add(item);
        items.add(item);
    }

    public void remove(Item item) {
        dynamics.remove(item);
        items.remove(item);
    }

    public void add(ItemContainer itemContainer) {
        dynamics.add(itemContainer);
        itemContainers.add(itemContainer);
        containers.add(itemContainer);
    }

    public void remove(ItemContainer itemContainer) {
        dynamics.remove(itemContainer);
        itemContainers.remove(itemContainer);
        containers.remove(itemContainer);
    }

    public void add(Vehicle vehicle) {
        transports.add(vehicle);
        vehicles.add(vehicle);
    }

    public void remove(Vehicle vehicle) {
        transports.remove(vehicle);
        vehicles.remove(vehicle);
    }

    public void add(Terrain terrain) {
        stables.add(terrain);
        terrains.add(terrain);
    }

    public void remove(Terrain terrain) {
        stables.remove(terrain);
        terrains.remove(terrain);
    }

    public void add(Furniture furniture) {
        stables.add(furniture);
        furnitures.add(furniture);
    }

    public void remove(Furniture furniture) {
        stables.remove(furniture);
        furnitures.remove(furniture);
    }

    public void add(FurnitureContainer furnitureContainer) {
        stables.add(furnitureContainer);
        furnitureContainers.add(furnitureContainer);
        containers.add(furnitureContainer);
    }

    public void remove(FurnitureContainer furnitureContainer) {
        stables.remove(furnitureContainer);
        furnitureContainers.remove(furnitureContainer);
        containers.remove(furnitureContainer);
    }

    public String count() {
        return "Widgets:" + "\n" +
                "Items:" + dynamics.size() + "\n" +
                "Furnitures:" + furnitures.size() + "\n";
    }
}
