package io.github.uxodev.libgdx.display.ui.infopanel;

import com.badlogic.gdx.scenes.scene2d.ui.ButtonGroup;
import com.badlogic.gdx.scenes.scene2d.ui.ImageTextButton;
import com.badlogic.gdx.scenes.scene2d.ui.ScrollPane;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.utils.Align;
import io.github.uxodev.libgdx.display.ui.Ui;

import java.util.ArrayList;

import static io.github.uxodev.libgdx.display.ui.infopanel.InfoPanel.STANDARD_CELL_HEIGHT;
import static io.github.uxodev.libgdx.display.ui.infopanel.InfoPanel.STANDARD_CELL_WIDTH;

public abstract class StandardInfoPanelCenter extends Table {
    private static final int numEntriesAcross = 7;
    private static final int numEntriesDown = 6;

    protected Table titleTopTable = new Table();
    protected Table titleBotTable = new Table();
    protected Table contentTable = new Table();

    protected ArrayList<ImageTextButton> titleTopButtons = new ArrayList<>();
    protected ArrayList<ImageTextButton> titleBotButtons = new ArrayList<>();
    protected ArrayList<Table> columns = new ArrayList<>();

    protected StandardInfoPanelCenter() {
        super(Ui.skin);

        titleTopTable.align(Align.topLeft);
        for (int i = 0; i < numEntriesAcross; i++) titleTopButtons.add(createTitleButton(titleTopTable));

        titleBotTable.align(Align.topLeft);
        titleBotTable.padBottom(2);
        for (int i = 0; i < numEntriesAcross; i++) titleBotButtons.add(createTitleButton(titleBotTable));
        titleBotButtons.forEach(titleBotButton -> titleBotButton.align(Align.center));

        contentTable.align(Align.topLeft);
        for (int i = 0; i < numEntriesAcross; i++)
            columns.add(createScrollPane(contentTable));

        this.add(titleTopTable).row();
        this.add(titleBotTable).row();
        this.add(contentTable);
    }

    protected abstract void update();

    protected static ImageTextButton createTitleButton(Table table) {
        ImageTextButton infoPanelButton = Ui.createClickButton("");
        infoPanelButton.setDisabled(true);
        table.add(infoPanelButton).size(STANDARD_CELL_WIDTH, STANDARD_CELL_HEIGHT);
        return infoPanelButton;
    }

    protected static ImageTextButton createColumnButton(Table table,
                                                        String text, ChangeListener changeListener,
                                                        boolean toggle) {
        ImageTextButton infoPanelButton = toggle ? Ui.createToggleButton(text) : Ui.createClickButton(text);
        infoPanelButton.addListener(changeListener);
        table.add(infoPanelButton).size(STANDARD_CELL_WIDTH, STANDARD_CELL_HEIGHT);
        table.row();
        return infoPanelButton;
    }

    protected static ImageTextButton createColumnButton(Table table,
                                                        String text) {
        ImageTextButton infoPanelButton = Ui.createClickButton(text);
        table.add(infoPanelButton).size(STANDARD_CELL_WIDTH, STANDARD_CELL_HEIGHT);
        table.row();
        return infoPanelButton;
    }

    protected static Table createScrollPane(Table table) {
        Table internalTable = new Table();
        internalTable.align(Align.topLeft);
        ScrollPane scrollPane = new ScrollPane(internalTable, Ui.scrollPaneStyle);
        scrollPane.setScrollingDisabled(true, false);
        scrollPane.setFadeScrollBars(false);
        scrollPane.setScrollbarsOnTop(true);
        table.add(scrollPane).size(STANDARD_CELL_WIDTH, STANDARD_CELL_HEIGHT * numEntriesDown);
        return internalTable;
    }

    // one button is checked at a time in the column
    protected void populateColumn(int column, ArrayList<String> texts, ArrayList<ChangeListener> changeListeners,
                                  ButtonGroup<ImageTextButton> buttonGroup) {
        columns.get(column).clear();
        for (int i = 0; i < texts.size(); i++) {
            ImageTextButton infoPanelButton =
                    createColumnButton(columns.get(column), texts.get(i), changeListeners.get(i), true);
            buttonGroup.add(infoPanelButton);
        }
        if (texts.size() > 0) buttonGroup.getButtons().get(0).getClickListener().clicked(null, 0, 0);
    }

    // buttons don't stay checked in the column
    protected void populateColumn(int column, ArrayList<String> texts, ArrayList<ChangeListener> changeListeners) {
        columns.get(column).clear();
        for (int i = 0; i < texts.size(); i++) {
            createColumnButton(columns.get(column), texts.get(i),
                    changeListeners.get(i), false);
        }
    }

    protected void populateColumn(int column, ArrayList<String> texts) {
        columns.get(column).clear();
        for (int i = 0; i < texts.size(); i++) {
            createColumnButton(columns.get(column), texts.get(i));
        }
    }

    protected void clearColumn(int column) {
        columns.get(column).clear();
    }
}
