package io.github.uxodev.model.both.unit.instance._objcomp.decisionTree._common._1role;

import io.github.uxodev.model.both.unit.instance.Unit;
import io.github.uxodev.model.both.unit.instance._objcomp.decisionTree._common._2task.Task;
import io.github.uxodev.model.both.unit.instance._objcomp.decisionTree.priorityset.Priority;

public abstract class Role implements Priority {
    protected final Unit parent;

    protected Task currentTask;
    private boolean isNoTasksAvailable = false;
    private boolean isComplete = true;

    private int priority = 0;

    public Role(Unit parent) {
        this.parent = parent;
    }

    public int restart() {
        clearComplete();
        System.out.println("restarted role " + this);
        return 0;
    }

    public abstract void complete();

    public abstract void interrupt();

    public int step() {
        int step = currentTask.step();
        if (step < 0) {
            System.out.println(parent + " " + currentTask + " isFailure");
            currentTask.cleanup();
            markComplete();
            return 1;
        } else if (step > 0) {
            System.out.println(parent + " " + currentTask + " isSuccess");
            currentTask.cleanup();
            markComplete();
            return 1;
        }
        return 0;
    }

    @Override
    public int getPriority() {
        return priority;
    }

    public void setPriority(int priority) {
        this.priority = priority;
    }

    public abstract void setDefaultPriority();

    public boolean isComplete() {
        return isComplete;
    }

    protected boolean markComplete() {
        isComplete = true;
        return true;
    }

    protected void clearComplete() {
        isComplete = false;
    }

    public boolean isNoTasksAvailable() {
        return isNoTasksAvailable;
    }

    public void setNoTasksAvailable(boolean noTasksAvailable) {
        isNoTasksAvailable = true;
    }

    @Override
    public String toString() {
        return this.getClass().getSimpleName();
    }
}
