package io.github.uxodev.model.both.widget.instance.dynamic.transport;

import io.github.uxodev.model.both.widget.instance.part.Part;
import io.github.uxodev.model.both.widget.token.material.MaterialToken;
import io.github.uxodev.model.both.widget.token.shape.dynamic.transport.VehicleShapeToken;
import io.github.uxodev.model.both.widget.token.source.Source;
import io.github.uxodev.model.city.map.voxel.Voxel;

import java.util.ArrayList;

public class Vehicle extends Transport {
    private VehicleShapeToken vehicleShapeToken;

    public Vehicle(Source source, MaterialToken materialToken, VehicleShapeToken vehicleShapeToken,
                   ArrayList<Part> parts) {
        super(source, materialToken, vehicleShapeToken, parts);
        this.vehicleShapeToken = vehicleShapeToken;
    }

    @Override
    public void start(Voxel dest) {
        loc.map.enclosedWidgets.remove(this);
        dest.map.enclosedWidgets.add(this);
        move(dest);
    }

    @Override
    public void move(Voxel dest) {
        loc.supportedDynamics.remove(this);
        dest.supportedDynamics.add(this);
        loc = dest;
    }

    @Override
    public VehicleShapeToken getShapeToken() {
        return vehicleShapeToken;
    }
}
