package io.github.uxodev.model.both.unit.instance._objcomp.decisionTree._common._4action.action;

import io.github.uxodev.model.both.Transfer;
import io.github.uxodev.model.both.Verify;
import io.github.uxodev.model.both._objcomp.lock.Lockable;
import io.github.uxodev.model.both.unit.instance.Unit;
import io.github.uxodev.model.both.unit.instance._objcomp.decisionTree._common._4action.Action;
import io.github.uxodev.model.both.widget.instance.dynamic.Dynamic;
import io.github.uxodev.model.city.map.voxel.Voxel;

public class ActionPickUp extends Action {
    private Dynamic dynamic;
    private Voxel expectedLoc;

    public ActionPickUp(Unit parent) {
        super(parent);
    }

    public void restart(Dynamic dynamic, Voxel expectedLoc) {
        this.dynamic = dynamic;
        this.expectedLoc = expectedLoc;
        super.restart();
    }

    @Override
    protected void setBucket() {
        wait = 0;
        bucket = 12;
    }

    @Override
    public boolean isActionValid() {
        return Verify.verifyItemInExpectedLoc(dynamic, expectedLoc);
    }

    @Override
    protected boolean isNextActionValid() {
        return Verify.verifyStandingOnOrNextToHoriz(parent.loc, dynamic.loc)
                && Verify.verifyCanCarry(parent, dynamic);
    }

    @Override
    protected void doAction() {
        Transfer.pickUp(parent, dynamic);
    }

    @Override
    public void cleanup() {
        dynamic.removeLock(Lockable.LockReason.RESERVED);
    }
}
