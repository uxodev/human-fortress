package io.github.uxodev.model.both.unit.instance._objcomp.decisionTree._common._4action;

import io.github.uxodev.model.both.unit.instance.Unit;

public abstract class ActionCycle extends Action {

    protected ActionCycle(Unit parent) {
        super(parent);
    }

    @Override
    public int tryStart() {
        if (isCycleContinues()) {
            doCycle();
            return super.tryStart();
        } else {
            return 1; // no more cycles left
        }
    }

    @Override
    protected int tryAction() {
        if (isNextActionValid()) {
            doAction();
            return tryStart(); // try to cycle instead of finishing
        } else {
            System.out.println(this + " failed tryAction cycle");
            return -1;
        }
    }

    // whether to do another cycle or consider action a success
    protected abstract boolean isCycleContinues();

    // shift vars to next cycle
    protected abstract void doCycle();
}
