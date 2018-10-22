package io.github.uxodev.model.both.unit.instance._objcomp.decisionTree._common._4action.action;

import io.github.uxodev.model.both.Transfer;
import io.github.uxodev.model.both.Verify;
import io.github.uxodev.model.both._objcomp.lock.Lockable;
import io.github.uxodev.model.both.unit.instance.Unit;
import io.github.uxodev.model.both.unit.instance._objcomp.decisionTree._common._4action.Action;
import io.github.uxodev.model.both.widget.instance.dynamic.Dynamic;
import io.github.uxodev.model.city.map.voxel.Voxel;

public class ActionPutDown extends Action {
    private Dynamic dynamic;
    private Voxel dest;

    public ActionPutDown(Unit parent) {
        super(parent);
    }

    public void restart(Dynamic dynamic, Voxel dest) {
        this.dynamic = dynamic;
        this.dest = dest;
        super.restart();
    }

    @Override
    protected void setBucket() {
        wait = 0;
        bucket = 12;
    }

    @Override
    public boolean isActionValid() {
        return true;
    }

    @Override
    protected boolean isNextActionValid() {
        return Verify.verifyStandingOnOrNextToHoriz(parent.loc, dest)
                && Verify.verifyStillCarrying(parent, dynamic);
    }

    @Override
    protected void doAction() {
        Transfer.putDown(parent, dynamic, dest);
    }

    @Override
    public void cleanup() {
        Transfer.drop(parent, dynamic);
        dynamic.removeLock(Lockable.LockReason.RESERVED);
        dest.removeLock(Lockable.LockReason.RESERVED);
    }
}
