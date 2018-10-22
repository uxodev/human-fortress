package io.github.uxodev.model.both.widget.instance._objcomp.composite;

import io.github.uxodev.model.both.widget.instance.part.Part;

import java.util.ArrayList;

public interface IComposite {
    ArrayList<Part> getParts();
    int getVolume();
    int getWeight();
    int getValue();
}
