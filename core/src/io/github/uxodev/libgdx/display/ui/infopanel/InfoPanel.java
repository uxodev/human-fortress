package io.github.uxodev.libgdx.display.ui.infopanel;

import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.utils.Align;
import io.github.uxodev.libgdx.display.ui.Ui;

public class InfoPanel extends Table {
    static final int STANDARD_CELL_WIDTH = 170;
    static final int STANDARD_CELL_HEIGHT = 20;
    private static final int LARGE_CELL_HEIGHT = 30;

    public static boolean modflagInfoPanel = false;
    private Container<StandardInfoPanelCenter> centerContainer = new Container<>();

    public InfoPanel() {
        centerContainer.setBackground(Ui.background);

        ImageTextButton widgetsButton = new ImageTextButton("-Widgets-", Ui.toggleButtonStyle);
        ImageTextButton unitsButton = new ImageTextButton("-Units-", Ui.toggleButtonStyle);
        ImageTextButton zonesButton = new ImageTextButton("-Zones-", Ui.toggleButtonStyle);

        Table titleTable = new Table();
        titleTable.setBackground(Ui.background);
        titleTable.padBottom(1);
        titleTable.add(
                Ui.createDisabledButton(""), Ui.createDisabledButton(""),
                Ui.createDisabledButton(""), Ui.createDisabledButton(""),
                widgetsButton, unitsButton, zonesButton);
        for (Cell cell : titleTable.getCells())
            cell.size(STANDARD_CELL_WIDTH, LARGE_CELL_HEIGHT);

        InfoPanelDisplayWidgets infoPanelDisplayWidgets = new InfoPanelDisplayWidgets();
        InfoPanelDisplayUnits infoPanelDisplayUnits = new InfoPanelDisplayUnits();
        InfoPanelDisplayZones infoPanelDisplayZones = new InfoPanelDisplayZones();

        widgetsButton.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                centerContainer.setActor(infoPanelDisplayWidgets);
                modflagInfoPanel = true;
            }
        });
        unitsButton.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                centerContainer.setActor(infoPanelDisplayUnits);
                modflagInfoPanel = true;
            }
        });
        zonesButton.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                centerContainer.setActor(infoPanelDisplayZones);
                modflagInfoPanel = true;
            }
        });

        ButtonGroup<ImageTextButton> topButtonGroup = new ButtonGroup<>();
        topButtonGroup.add(widgetsButton, unitsButton, zonesButton);
        topButtonGroup.getButtons().get(0).toggle();

        this.add(titleTable).align(Align.topLeft).row();
        this.add(centerContainer).align(Align.topLeft);
    }

    public void update() {
        centerContainer.getActor().update();
    }
}
