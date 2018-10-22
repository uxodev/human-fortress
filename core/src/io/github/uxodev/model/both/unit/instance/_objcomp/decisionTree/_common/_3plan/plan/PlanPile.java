package io.github.uxodev.model.both.unit.instance._objcomp.decisionTree._common._3plan.plan;

import io.github.uxodev.model.both._objcomp.lock.Lockable;
import io.github.uxodev.model.both.unit.search.Find;
import io.github.uxodev.model.both.unit.instance.Unit;
import io.github.uxodev.model.both.unit.instance._objcomp.decisionTree._common._3plan.Plan;
import io.github.uxodev.model.both.unit.instance._objcomp.decisionTree._common._4action.action.ActionPath;
import io.github.uxodev.model.both.unit.instance._objcomp.decisionTree._common._4action.action.ActionPickUp;
import io.github.uxodev.model.both.unit.instance._objcomp.decisionTree._common._4action.action.ActionPutDown;
import io.github.uxodev.model.both.widget.instance.dynamic.Dynamic;
import io.github.uxodev.model.city.map.voxel.Voxel;

import java.util.Stack;

public class PlanPile extends Plan {
    private final ActionPath moveToItemLoc;
    private final ActionPickUp actionPickUpItem;
    private final ActionPath moveToPileLoc;
    private final ActionPutDown actionPutDownItem;

    public PlanPile(Unit parent) {
        super(parent);
        moveToItemLoc = new ActionPath(parent);
        actionPickUpItem = new ActionPickUp(parent);
        moveToPileLoc = new ActionPath(parent);
        actionPutDownItem = new ActionPutDown(parent);
        actions.add(moveToItemLoc);
        actions.add(actionPickUpItem);
        actions.add(moveToPileLoc);
        actions.add(actionPutDownItem);
    }

    public void restart(Dynamic dynamic, Voxel expectedLocItem, Voxel dest) {
        Stack<Voxel> toExpectedLocItem = Find.pathToLocHorizPlusC(parent.loc.map,
                parent.loc, expectedLocItem);
        Stack<Voxel> toPile = Find.pathToLocHorizPlusC(parent.loc.map,
                !toExpectedLocItem.isEmpty() ? toExpectedLocItem.firstElement() : parent.loc, dest);
        moveToItemLoc.restart(toExpectedLocItem);
        actionPickUpItem.restart(dynamic, expectedLocItem);
        moveToPileLoc.restart(toPile);
        actionPutDownItem.restart(dynamic, dest);
        dynamic.addLock(Lockable.LockReason.RESERVED);
        dest.addLock(Lockable.LockReason.RESERVED);
        super.restart();
    }

    @Override
    public void cleanup() {
        actionPickUpItem.cleanup();
        actionPutDownItem.cleanup();
    }
}
