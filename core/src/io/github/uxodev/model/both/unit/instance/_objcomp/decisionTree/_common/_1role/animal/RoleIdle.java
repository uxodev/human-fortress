package io.github.uxodev.model.both.unit.instance._objcomp.decisionTree._common._1role.animal;

import io.github.uxodev.model.both.unit.instance.Unit;
import io.github.uxodev.model.both.unit.instance._objcomp.decisionTree._common._1role.Role;
import io.github.uxodev.model.both.unit.instance._objcomp.decisionTree._common._2task.task.TaskIdle;

public class RoleIdle extends Role {
    private final TaskIdle taskIdle;

    public RoleIdle(Unit parent) {
        super(parent);
        setPriority(1);
        taskIdle = new TaskIdle(parent);
    }

    @Override
    public int restart() {
        super.restart();
        currentTask = taskIdle;
        return taskIdle.restart();
    }

    @Override
    public void complete() {

    }

    @Override
    public void interrupt() {

    }

    @Override
    public void setDefaultPriority() {
        setPriority(1);
    }
}
