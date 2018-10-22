package io.github.uxodev.model.both.widget.instance._objcomp.composite;

import io.github.uxodev.model.both.widget.instance.part.Part;

import java.util.ArrayList;

public class Composite implements IComposite {
    private final ArrayList<Part> parts;
    private final int volume;
    private final int weight;
    private final int value;

    // create mirror part if given zero parts
    public Composite(ArrayList<Part> parts) {
        parts = (parts == null || parts.isEmpty())
                ? new ArrayList<>()
                : parts;
        this.parts = parts;
        int v = 0;
        int w = 0;
        int a = 0;
        for (Part part : parts) {
            v += part.getVolume();
            w += part.getWeight();
            a += part.getValue();
        }
        volume = v;
        weight = w;
        value = a;
    }

    @Override
    public ArrayList<Part> getParts() {
        return parts;
    }

    @Override
    public int getVolume() {
        return volume;
    }

    @Override
    public int getWeight() {
        return weight;
    }

    @Override
    public int getValue() {
        return value;
    }
}
