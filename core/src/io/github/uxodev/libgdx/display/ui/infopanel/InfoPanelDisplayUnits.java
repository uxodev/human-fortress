package io.github.uxodev.libgdx.display.ui.infopanel;

import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.ui.ButtonGroup;
import com.badlogic.gdx.scenes.scene2d.ui.ImageTextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import io.github.uxodev.model.both._data.Game;
import io.github.uxodev.model.both.unit.instance.Unit;
import io.github.uxodev.model.city.map.voxel.Voxel;

import java.util.ArrayList;

import static io.github.uxodev.model.both._data.Time.SentientPhase.*;

public class InfoPanelDisplayUnits extends StandardInfoPanelCenter {
    private Unit checkedUnit;
    private boolean modflagCheckedUnit;

    InfoPanelDisplayUnits() {
        super();
        titleBotButtons.get(0).setText("Units");
        titleBotButtons.get(1).setText("Morning");
        titleBotButtons.get(2).setText("Daytime");
        titleBotButtons.get(3).setText("Afternoon");
        titleBotButtons.get(4).setText("Evening");
        titleBotButtons.get(5).setText("Night");
        titleBotButtons.get(6).setText("");
    }

    @Override
    protected void update() {
        if (InfoPanel.modflagInfoPanel) fillUnits();
        if (modflagCheckedUnit) fillCheckedUnit();
    }

    private void fillUnits() {
        InfoPanel.modflagInfoPanel = false;

        ArrayList<Unit> units = new ArrayList<>();
        for (Voxel voxel : Game.currentMap.voxelSelect.volumeSelected)
            units.addAll(voxel.supportedUnits.units);

        ButtonGroup<ImageTextButton> buttonGroup = new ButtonGroup<>();
        populateColumn(0, units, buttonGroup);

        if (buttonGroup.getButtons().size > 0)
            buttonGroup.getButtons().get(0).getClickListener().clicked(null, 0, 0);
        else setCheckedUnit(null);
    }

    private void fillCheckedUnit() {
        modflagCheckedUnit = false;

        titleTopButtons.get(6).setText("Carrying: " +
                (checkedUnit == null ? "" :
                        checkedUnit.held == null ? "" : checkedUnit.held));

        if (checkedUnit == null) {
            clearColumn(1);
            clearColumn(2);
            clearColumn(3);
            clearColumn(4);
            clearColumn(5);
            clearColumn(6);
        } else {
            populateColumn(1,
                    checkedUnit.roleSchedule.phaseSchedules.get(MORNING).getStrings());
            populateColumn(2,
                    checkedUnit.roleSchedule.phaseSchedules.get(DAYTIME).getStrings());
            populateColumn(3,
                    checkedUnit.roleSchedule.phaseSchedules.get(AFTERNOON).getStrings());
            populateColumn(4,
                    checkedUnit.roleSchedule.phaseSchedules.get(EVENING).getStrings());
            populateColumn(5,
                    checkedUnit.roleSchedule.phaseSchedules.get(NIGHT).getStrings());
        }
    }

    private void populateColumn(int column, ArrayList<? extends Unit> units,
                                ButtonGroup<ImageTextButton> buttonGroup) {
        ArrayList<String> names = new ArrayList<>();
        ArrayList<ChangeListener> changeListeners = new ArrayList<>();
        for (Unit unit : units) {
            names.add(unit.name);
            changeListeners.add(new ChangeListener() {
                @Override
                public void changed(ChangeEvent event, Actor actor) {
                    setCheckedUnit(unit);
                }
            });
        }
        populateColumn(column, names, changeListeners, buttonGroup);
    }

    private void setCheckedUnit(Unit unit) {
        checkedUnit = unit;
        modflagCheckedUnit = true;
    }
}
