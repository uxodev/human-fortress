package io.github.uxodev.model.both.unit.instance._objcomp.decisionTree._common._2task.task;

import io.github.uxodev.model.both.unit.instance.Unit;
import io.github.uxodev.model.both.unit.instance._objcomp.decisionTree._common._2task.Task;
import io.github.uxodev.model.both.unit.instance._objcomp.decisionTree._common._3plan.plan.PlanIdle;

public class TaskIdle extends Task {
    private final PlanIdle planIdle;

    public TaskIdle(Unit parent) {
        super(parent);
        planIdle = new PlanIdle(parent);
        currentPlan = planIdle;
    }

    @Override
    public int restart() {
        planIdle.restart(true);
        currentPlan = planIdle;
        return 0;
    }

    @Override
    public void cleanup() {
        currentPlan.cleanup();
    }
}
