package io.github.uxodev.libgdx.display.ui.censuspanel;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.scenes.scene2d.ui.Container;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import io.github.uxodev.libgdx.display.ui.Ui;
import io.github.uxodev.model.both._data.Game;

public class ConsolePanel extends Table {
    private StringBuilder censusString;
    private Label censusLabel;
    private StringBuilder consoleString;
    private Label consoleLabel;

    public ConsolePanel() {
        censusString = new StringBuilder();
        censusLabel = new Label("", new Label.LabelStyle(Ui.font, Color.WHITE));
        Container<Label> censusContainer = new Container<>(censusLabel);

        consoleString = new StringBuilder();
        consoleLabel = new Label("", new Label.LabelStyle(Ui.font, Color.WHITE));
        Container<Label> consoleContainer = new Container<>(consoleLabel);

        this.add(censusContainer).top().left().row();
        this.add(consoleContainer).bottom().left();
    }

    public void update() {
        censusString.setLength(0);
        censusString.append(Game.currentMap.count());
        censusLabel.setText(censusString);

        consoleString.setLength(0);
        consoleString.append("----");
        consoleString.append("\nHover=").append(Game.currentMap.voxelSelect.hovering.toString());
        consoleString.append("\nSelected=").append(Game.currentMap.voxelSelect.volumeSelected.size());
        consoleString.append("\nFuture=").append(Game.currentMap.voxelSelect.volumeFuture.size());
        consoleString.append("\n");
        consoleString.append("\n").append(Game.time.toString());
        consoleString.append("\n");
        consoleString.append("\n").append("FPS=").append(Gdx.graphics.getFramesPerSecond());
        consoleLabel.setText(consoleString);
    }
}
