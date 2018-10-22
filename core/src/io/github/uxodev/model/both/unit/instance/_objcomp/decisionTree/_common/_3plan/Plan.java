package io.github.uxodev.model.both.unit.instance._objcomp.decisionTree._common._3plan;

import io.github.uxodev.model.both.unit.instance.Unit;
import io.github.uxodev.model.both.unit.instance._objcomp.decisionTree._common._4action.Action;

import java.util.ArrayList;

public abstract class Plan {
    protected final Unit parent;
    protected ArrayList<Action> actions = new ArrayList<>();
    private int actionIndex = -1;
    private Action chosenAction;

    protected Plan(Unit parent) {
        this.parent = parent;
    }

    protected int restart() {
        actionIndex = -1;
        return transition();
    }

    public int step() {
        int step = chosenAction.step();
        if (step > 0) {
            chosenAction.cleanup();
            System.out.println(parent + " " + chosenAction + " isSuccess");
            return transition();
        } else if (step < 0) {
            chosenAction.cleanup();
            System.out.println(parent + " " + chosenAction + " isFailure");
            return -1;
        }
        return 0;
    }

    private int transition() {
        actionIndex++;
        if (actionIndex < actions.size()) {
            chosenAction = actions.get(actionIndex);
            chosenAction.tryStart();
            if (!chosenAction.isActionValid()) {
                System.out.println("current action was invalid");
                return -1;
            }
            if (!isRemainingActionsValid()) {
                System.out.println("a future action was invalid");
                return -1;
            }
            return 0;
        } else {
            return 1; // no more actions remaining
        }
    }

    // Checks all immediately, otherwise if the current is not allowed then it uses a step,
    // but if a future is not allowed then it fails immediately.
    // A task with a lot of options doesn't waste steps.
    private boolean isRemainingActionsValid() {
        for (int i = actionIndex; i < actions.size(); i++)
            if (!actions.get(i).isActionValid())
                return false;
        return true;
    }

    public abstract void cleanup();

    @Override
    public String toString() {
        return this.getClass().getSimpleName();
    }
}
