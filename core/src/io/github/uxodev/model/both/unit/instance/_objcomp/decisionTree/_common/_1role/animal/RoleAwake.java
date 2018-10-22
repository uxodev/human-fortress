package io.github.uxodev.model.both.unit.instance._objcomp.decisionTree._common._1role.animal;

import io.github.uxodev.model.both.unit.instance.Unit;
import io.github.uxodev.model.both.unit.instance._objcomp.decisionTree._common._1role.Role;
import io.github.uxodev.model.both.unit.instance._objcomp.decisionTree._common._2task.task.TaskPile;

public class RoleAwake extends Role {
    private final TaskPile taskPile;

    public RoleAwake(Unit parent) {
        super(parent);
        taskPile = new TaskPile(parent);
        currentTask = taskPile;
    }

    @Override
    public int restart() {
        super.restart();
        currentTask = taskPile;
        int restart = taskPile.restart();
        if (restart != 0) {
            markComplete();
            setPriority(0);
        }
        return restart;
    }

    @Override
    public void complete() {

    }

    @Override
    public void interrupt() {

    }

    @Override
    public void setDefaultPriority() {
        setPriority(70);
    }
}
