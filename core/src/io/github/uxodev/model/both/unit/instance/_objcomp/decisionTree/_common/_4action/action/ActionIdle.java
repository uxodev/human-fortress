package io.github.uxodev.model.both.unit.instance._objcomp.decisionTree._common._4action.action;

import io.github.uxodev.model.both.unit.instance.Unit;
import io.github.uxodev.model.both.unit.instance._objcomp.decisionTree._common._4action.Action;

public class ActionIdle extends Action {

    public ActionIdle(Unit parent) {
        super(parent);
    }

    public void restart() {
        super.restart();
    }

    @Override
    protected void setBucket() {
        wait = 0;
        bucket = 30;
    }

    @Override
    public boolean isActionValid() {
        return true;
    }

    @Override
    protected boolean isNextActionValid() {
        return true;
    }

    @Override
    protected void doAction() {

    }

    @Override
    public void cleanup() {

    }
}
