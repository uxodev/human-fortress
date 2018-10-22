package io.github.uxodev.libgdx.display.ui.infopanel;

import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.ui.ButtonGroup;
import com.badlogic.gdx.scenes.scene2d.ui.ImageTextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import io.github.uxodev.model.both._data.Game;
import io.github.uxodev.model.both.widget.instance.Widget;
import io.github.uxodev.model.both.widget.instance.dynamic.Dynamic;
import io.github.uxodev.model.both.widget.instance.stable.Stable;
import io.github.uxodev.model.city.map.voxel.Voxel;

import java.util.ArrayList;
import java.util.stream.Collectors;

public class InfoPanelDisplayWidgets extends StandardInfoPanelCenter {
    private Widget checkedWidget;
    private boolean modflagCheckedWidget;

    InfoPanelDisplayWidgets() {
        super();
        titleBotButtons.get(0).setText("Terrains");
        titleBotButtons.get(1).setText("Furniture");
        titleBotButtons.get(2).setText("Items");
        titleBotButtons.get(3).setText("Parts");
        titleBotButtons.get(4).setText("Decorations");
        titleBotButtons.get(5).setText("Use Tokens");
        titleBotButtons.get(6).setText("Contained");
    }

    @Override
    protected void update() {
        if (InfoPanel.modflagInfoPanel) fillWidgets();
        if (modflagCheckedWidget) fillCheckedWidget();
    }

    private void fillWidgets() {
        InfoPanel.modflagInfoPanel = false;
        ArrayList<Stable> terrains = new ArrayList<>();
        ArrayList<Stable> stables = new ArrayList<>();
        ArrayList<Dynamic> dynamics = new ArrayList<>();
        for (Voxel voxel : Game.currentMap.voxelSelect.volumeSelected) {
            if (voxel.supportedStables.getTerrainWall() != null)
                terrains.add(voxel.supportedStables.getTerrainWall());
            else if (voxel.supportedStables.getFurniture() != null)
                stables.add(voxel.supportedStables.getFurniture());
            else if (voxel.supportedStables.getFurnitureContainer() != null)
                stables.add(voxel.supportedStables.getFurnitureContainer());

            if (voxel.supportedStables.getTerrainFloor() != null)
                terrains.add(voxel.supportedStables.getTerrainFloor());

            dynamics.addAll(voxel.supportedDynamics.getDynamics());
        }
        ButtonGroup<ImageTextButton> buttonGroup = new ButtonGroup<>();
        populateColumn(2, dynamics, buttonGroup);
        populateColumn(1, stables, buttonGroup);
        populateColumn(0, terrains, buttonGroup);
        // first checked widget is top of list of dynamics -> stables -> terrains
        if (buttonGroup.getButtons().size > 0)
            buttonGroup.getButtons().get(0).getClickListener().clicked(null, 0, 0);
        else setCheckedWidget(null);
    }

    private void fillCheckedWidget() {
        modflagCheckedWidget = false;
        titleTopButtons.get(2).setText(checkedWidget == null ? "" : checkedWidget.name);
        titleTopButtons.get(3).setText("Loc: " + (checkedWidget == null ? "" :
                checkedWidget.loc == null ? "" : checkedWidget.loc.toString()));
        titleTopButtons.get(4).setText("Value: " + (checkedWidget == null ? "" : String.valueOf(checkedWidget.getValue())));
        titleTopButtons.get(5).setText("Contain: " + (checkedWidget == null ? "" : checkedWidget.getContainToken().name));
        titleTopButtons.get(6).setText("Quantity: " + (checkedWidget == null ? "" : String.valueOf(checkedWidget.getQuantity())));

        if (checkedWidget == null) {
            clearColumn(3);
            clearColumn(4);
            clearColumn(5);
            clearColumn(6);
        } else {
            populateColumn(3,
                    checkedWidget.getParts().stream().map(part -> part.name)
                            .collect(Collectors.toCollection(ArrayList::new)));
            populateColumn(4,
                    checkedWidget.getDecorations().stream().map(decoration -> decoration.name)
                            .collect(Collectors.toCollection(ArrayList::new)));
            populateColumn(5,
                    checkedWidget.getUseTokens().stream().map(useToken -> useToken.name)
                            .collect(Collectors.toCollection(ArrayList::new)));
            populateColumn(6,
                    checkedWidget.getContained().stream().map((iContainable -> ((Widget) iContainable).name))
                            .collect(Collectors.toCollection(ArrayList::new)));
        }
    }

    private void populateColumn(int column, ArrayList<? extends Widget> widgets,
                                ButtonGroup<ImageTextButton> buttonGroup) {
        ArrayList<String> names = new ArrayList<>();
        ArrayList<ChangeListener> changeListeners = new ArrayList<>();
        for (Widget widget : widgets) {
            names.add(widget.name);
            changeListeners.add(new ChangeListener() {
                @Override
                public void changed(ChangeEvent event, Actor actor) {
                    setCheckedWidget(widget);
                }
            });
        }
        populateColumn(column, names, changeListeners, buttonGroup);
    }

    private void setCheckedWidget(Widget widget) {
        modflagCheckedWidget = true;
        checkedWidget = widget;
    }
}
