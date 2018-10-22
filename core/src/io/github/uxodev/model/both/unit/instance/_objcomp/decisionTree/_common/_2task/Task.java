package io.github.uxodev.model.both.unit.instance._objcomp.decisionTree._common._2task;

import io.github.uxodev.model.both.unit.instance.Unit;
import io.github.uxodev.model.both.unit.instance._objcomp.decisionTree._common._3plan.Plan;

public abstract class Task {
    protected final Unit parent;
    protected Plan currentPlan;

    protected Task(Unit parent) {
        this.parent = parent;
    }

    protected int restart() {
        System.out.println("restarted task " + this);
        return 0;
    }

    public int step() {
        int step = currentPlan.step();
        if (step > 0) {
            currentPlan.cleanup();
            System.out.println(parent + " " + currentPlan + " isSuccess");
            return 1;
        } else if (step < 0) {
            currentPlan.cleanup();
            System.out.println(parent + " " + currentPlan + " isFailure");
            return -1;
        }
        return 0;
    }

    public abstract void cleanup();

    @Override
    public String toString() {
        return this.getClass().getSimpleName();
    }
}
