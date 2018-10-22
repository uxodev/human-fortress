package io.github.uxodev.model.both.unit.instance._objcomp.decisionTree;

import io.github.uxodev.model.both._data.Game;
import io.github.uxodev.model.both._data.Time;
import io.github.uxodev.model.both.unit.instance.Unit;
import io.github.uxodev.model.both.unit.instance._objcomp.decisionTree._common._1role.Role;
import io.github.uxodev.model.both.unit.instance._objcomp.decisionTree._common._1role.animal.RoleAwake;
import io.github.uxodev.model.both.unit.instance._objcomp.decisionTree._common._1role.animal.RoleIdle;
import io.github.uxodev.model.both.unit.instance._objcomp.decisionTree.priorityset.PrioritySet;

import java.util.EnumMap;

public class Schedule {
    private final Unit parent;

    public EnumMap<Time.SentientPhase, PrioritySet<Role>> phaseSchedules = new EnumMap<>(Time.SentientPhase.class);
    private PrioritySet<Role> currentSchedule;

    private Role currentRole;

    public Schedule(Unit parent) {
        this.parent = parent;
        for (Time.SentientPhase sentientPhase : Time.SentientPhase.values())
            phaseSchedules.put(sentientPhase, new PrioritySet<>());

        initSchedules();

        updateSchedule();
        currentRole = currentSchedule.peek();
    }

    private void initSchedules() { // initialize with idle role
        for (Time.SentientPhase sentientPhase : Time.SentientPhase.values()) {
            addScheduleRole(sentientPhase, new RoleIdle(parent));
            addScheduleRole(sentientPhase, new RoleAwake(parent));
        }
    }

    public void updateSchedule() {
        currentSchedule = phaseSchedules.get(Game.time.getSentientPhase());
        currentSchedule.getValues().forEach(Role::setDefaultPriority);
        System.out.println("updated schedule to " + currentSchedule.peek());
    }

    public void addScheduleRole(Time.SentientPhase sentientPhase, Role role) {
        phaseSchedules.get(sentientPhase).add(role);
    }

    public void removeScheduleRole(Time.SentientPhase sentientPhase, Role role) {
        phaseSchedules.get(sentientPhase).remove(role);
    }

    public void step() {
        if (currentRole.isComplete()) {
            currentRole.complete();
            currentRole = currentSchedule.peek();
            System.out.println(parent + " chose role " + currentRole);
            int restart = currentRole.restart();
            if (restart == 0)
                currentRole.step();
        } else {
            currentRole.step();
        }
    }
}
