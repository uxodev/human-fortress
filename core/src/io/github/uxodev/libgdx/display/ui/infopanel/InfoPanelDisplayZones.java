package io.github.uxodev.libgdx.display.ui.infopanel;

import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.ui.ButtonGroup;
import com.badlogic.gdx.scenes.scene2d.ui.ImageTextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import io.github.uxodev.model.both._data.Game;
import io.github.uxodev.model.city.map.voxel.Voxel;
import io.github.uxodev.model.city.zone.Zone;
import io.github.uxodev.model.city.zone.area.Area;

import java.util.ArrayList;
import java.util.HashSet;

public class InfoPanelDisplayZones extends StandardInfoPanelCenter {
    private Zone checkedZone;
    private boolean modflagCheckedZone;
    private Area checkedArea;
    private boolean modflagCheckedArea;

    InfoPanelDisplayZones() {
        super();
        titleBotButtons.get(0).setText("Zones");
        titleBotButtons.get(1).setText("Required");
        titleBotButtons.get(2).setText("Optional");
        titleBotButtons.get(3).setText("Additional");
        titleBotButtons.get(4).setText("Required");
        titleBotButtons.get(5).setText("Matching");
        titleBotButtons.get(6).setText("Extra");
    }

    @Override
    protected void update() {
        if (InfoPanel.modflagInfoPanel) fillZones();
        if (modflagCheckedZone) fillCheckedZone();
        if (modflagCheckedArea) fillCheckedArea();
    }

    private void fillZones() {
        InfoPanel.modflagInfoPanel = false;

        fillAreas();
    }

    private void fillCheckedZone() {
        modflagCheckedZone = false;
    }

    private void fillAreas() {
        InfoPanel.modflagInfoPanel = false;
        HashSet<Area> areas = new HashSet<>();

        for (Voxel voxel : Game.currentMap.voxelSelect.volumeSelected)
            areas.addAll(voxel.supportedZones.areas);

        ButtonGroup<ImageTextButton> buttonGroup = new ButtonGroup<>();
        populateColumn(1, new ArrayList<>(areas), buttonGroup);

        if (buttonGroup.getButtons().size > 0)
            buttonGroup.getButtons().get(0).getClickListener().clicked(null, 0, 0);
        else setCheckedArea(null);
    }

    private void fillCheckedArea() {
        modflagCheckedArea = false;

        titleTopButtons.get(4).setText(checkedArea == null ? "" : checkedArea.areaType.name());
        titleTopButtons.get(5).setText("Active: " + (checkedArea == null ? "" : checkedArea.isActive()));
        titleTopButtons.get(6).setText("Value: " + (checkedArea == null ? "" : String.valueOf(checkedArea.value)));

        if (checkedArea == null) {
            clearColumn(4);
            clearColumn(5);
            clearColumn(6);
        } else {
            populateColumn(4, checkedArea.reqDisplay);
            populateColumn(5, checkedArea.addlDisplay);
            populateColumn(6, checkedArea.extraDisplay);
        }
    }

    private void populateColumn(int column, ArrayList<? extends Area> areas,
                                ButtonGroup<ImageTextButton> buttonGroup) {
        ArrayList<String> names = new ArrayList<>();
        ArrayList<ChangeListener> changeListeners = new ArrayList<>();
        for (Area area : areas) {
            names.add(area.areaType.name());
            changeListeners.add(new ChangeListener() {
                @Override
                public void changed(ChangeEvent event, Actor actor) {
                    setCheckedArea(area);
                }
            });
        }
        populateColumn(column, names, changeListeners, buttonGroup);
    }

    private void setCheckedZone(Zone zone) {
        modflagCheckedZone = true;
        checkedZone = zone;
    }

    private void setCheckedArea(Area area) {
        modflagCheckedArea = true;
        checkedArea = area;
    }
}
