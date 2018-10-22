package io.github.uxodev.model.both.unit.instance.sentient;

import io.github.uxodev.model.both._data.Game;
import io.github.uxodev.model.both.unit.instance.Unit;
import io.github.uxodev.model.city.map.voxel.Voxel;

public class Sentient extends Unit {

    public Sentient() {

    }

    @Override
    public void start(Voxel dest) {
        loc.map.enclosedUnits.remove(this);
        dest.map.enclosedUnits.add(this);
        move(dest);
    }

    @Override
    public void move(Voxel dest) {
        loc.supportedUnits.remove(this);
        dest.supportedUnits.add(this);
        loc = dest;
    }

    @Override
    public void step() {
        if (Game.time.getMinute() == 0) roleSchedule.updateSchedule();
        roleSchedule.step();
        super.step();
    }
}
