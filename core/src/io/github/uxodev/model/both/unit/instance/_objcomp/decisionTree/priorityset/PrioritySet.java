package io.github.uxodev.model.both.unit.instance._objcomp.decisionTree.priorityset;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashSet;
import java.util.stream.Collectors;

public class PrioritySet<T extends Priority> {
    private HashSet<T> values = new HashSet<>();
    private static final Comparator<Priority> comparator = Comparator.comparingInt(Priority::getPriority);

    public void add(T t) {
        values.add(t);
    }

    public void remove(T t) {
        values.remove(t);
    }

    public T peek() {
        return Collections.max(values, comparator);
    }

    public HashSet<T> getValues() {
        return values;
    }

    public ArrayList<String> getStrings() {
        return values.stream().map(t -> t.toString()).collect(Collectors.toCollection(ArrayList::new));
    }
}
