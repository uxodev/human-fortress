package io.github.uxodev.model.both.unit.instance._objcomp.decisionTree._common._2task.task;

import io.github.uxodev.model.both.unit.search.AreaAndDynamic;
import io.github.uxodev.model.both.unit.search.Find;
import io.github.uxodev.model.both.unit.instance.Unit;
import io.github.uxodev.model.both.unit.instance._objcomp.decisionTree._common._2task.Task;
import io.github.uxodev.model.both.unit.instance._objcomp.decisionTree._common._3plan.plan.PlanPile;
import io.github.uxodev.model.both.widget.instance.dynamic.Dynamic;
import io.github.uxodev.model.city.map.voxel.Voxel;

public class TaskPile extends Task {
    private final PlanPile planPile;

    public TaskPile(Unit parent) {
        super(parent);
        planPile = new PlanPile(parent);
        currentPlan = planPile;
    }

    @Override
    public int restart() {
        AreaAndDynamic areaAndDynamic = Find.findAreaWithSpaceAndMatchingDynamic(parent.loc);
        if (areaAndDynamic.stockArea.isPresent() && areaAndDynamic.dynamic.isPresent()) {
            Voxel dest = areaAndDynamic.stockArea.get().pile.getSpace();
            Dynamic dynamic = areaAndDynamic.dynamic.get();
            Voxel expectedLocItem = dynamic.loc;
            planPile.restart(dynamic, expectedLocItem, dest);
            currentPlan = planPile;
            return 0;
        }
        return -1;
    }

    @Override
    public void cleanup() {
        currentPlan.cleanup();
    }
}
