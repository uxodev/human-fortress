package io.github.uxodev.model.both.unit.instance._objcomp.decisionTree._common._3plan.plan;

import io.github.uxodev.model.both.unit.instance.Unit;
import io.github.uxodev.model.both.unit.instance._objcomp.decisionTree._common._3plan.Plan;
import io.github.uxodev.model.both.unit.instance._objcomp.decisionTree._common._4action.action.ActionIdle;

public class PlanIdle extends Plan {
    private final ActionIdle idle;

    public PlanIdle(Unit parent) {
        super(parent);
        idle = new ActionIdle(parent);
        actions.add(idle);
    }

    public void restart(boolean b) {
        idle.restart();
        super.restart();
    }

    @Override
    public void cleanup() {

    }
}
