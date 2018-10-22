package io.github.uxodev.model.both.unit.instance._objcomp.decisionTree._common._4action.action;

import io.github.uxodev.model.both.Transfer;
import io.github.uxodev.model.both.Verify;
import io.github.uxodev.model.both.unit.search.Find;
import io.github.uxodev.model.both.unit.instance.Unit;
import io.github.uxodev.model.both.unit.instance._objcomp.decisionTree._common._4action.ActionCycle;
import io.github.uxodev.model.city.map.voxel.Voxel;
import io.github.uxodev.model.city.pathfinder.Pathfinder;

import java.util.Stack;

public class ActionPath extends ActionCycle {
    private Stack<Voxel> path;
    private Voxel next;

    public ActionPath(Unit parent) {
        super(parent);
    }

    public void restart(Stack<Voxel> path) {
        System.out.println("restarted with path " + path);
        this.path = path;
        super.restart();
    }

    @Override
    protected void setBucket() {
        wait = 0;
        bucket = Find.findMoveBucketLowest(next, Pathfinder.Method.WALK);
    }

    @Override
    public boolean isActionValid() {
        return Verify.verifyCanPathTo(path, Pathfinder.Method.WALK);
    }

    @Override
    protected boolean isNextActionValid() {
        return Verify.verifyCanPathToNext(parent.loc, next, Pathfinder.Method.WALK);
    }

    @Override
    protected void doAction() {
        Transfer.move(parent, next);
    }

    @Override
    protected boolean isCycleContinues() {
        return !path.isEmpty();
    }

    @Override
    protected void doCycle() {
        next = path.pop();
    }

    @Override
    public void cleanup() {

    }
}
