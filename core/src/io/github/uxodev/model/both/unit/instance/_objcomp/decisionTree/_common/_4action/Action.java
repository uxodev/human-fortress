package io.github.uxodev.model.both.unit.instance._objcomp.decisionTree._common._4action;

import io.github.uxodev.model.both.unit.instance.Unit;

public abstract class Action {
    protected final Unit parent;
    protected int wait = 0;
    protected int bucket = 0;

    protected Action(Unit parent) {
        this.parent = parent;
    }

    // override to get args
    protected void restart() {

    }

    public int tryStart() {
        if (isNextActionValid()) { // check before wasting time waiting
            setBucket();
            return 0;
        } else {
            System.out.println(this + " failed tryStart normal");
            return -1;
        }
    }

    public int step() {
        wait++;
        if (wait > bucket)
            return tryAction();
        return 0;
    }

    protected int tryAction() {
        if (isNextActionValid()) { // check right before doing it
            doAction();
            return 1;
        } else {
            System.out.println(this + " failed tryAction normal");
            return -1; // action became ineligible while waiting
        }
    }

    protected abstract void setBucket();

    public abstract boolean isActionValid();

    protected abstract boolean isNextActionValid();

    protected abstract void doAction();

    public abstract void cleanup();

    @Override
    public String toString() {
        return this.getClass().getSimpleName();
    }
}
