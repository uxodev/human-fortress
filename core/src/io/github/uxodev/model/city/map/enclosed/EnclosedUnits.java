package io.github.uxodev.model.city.map.enclosed;

import io.github.uxodev.model.both.unit.instance.Unit;
import io.github.uxodev.model.both.unit.instance.animal.Animal;
import io.github.uxodev.model.both.unit.instance.sentient.Sentient;

import java.util.ArrayList;
import java.util.List;

public class EnclosedUnits {
    public final List<Unit> units = new ArrayList<>();
    public final List<Animal> animals = new ArrayList<>();
    public final List<Sentient> sentients = new ArrayList<>();

    public void step() {
        units.forEach(Unit::step);
    }

    public void add(Animal animal) {
        units.add(animal);
        animals.add(animal);
    }

    public void remove(Animal animal) {
        units.remove(animal);
        animals.remove(animal);
    }

    public void add(Sentient sentient) {
        units.add(sentient);
        sentients.add(sentient);
    }

    public void remove(Sentient sentient) {
        units.remove(sentient);
        sentients.remove(sentient);
    }

    public String count() {
        return "Units:" + "\n" +
                "Units:" + units.size() + "\n" +
                "Animals:" + animals.size() + "\n" +
                "Sentients:" + sentients.size() + "\n";
    }
}
