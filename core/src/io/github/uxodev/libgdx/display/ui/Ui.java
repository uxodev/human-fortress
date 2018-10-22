package io.github.uxodev.libgdx.display.ui;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.scenes.scene2d.utils.Drawable;
import com.badlogic.gdx.utils.Align;
import io.github.uxodev.libgdx.display.ui.buttonPanel.ButtonPanel;
import io.github.uxodev.libgdx.display.ui.censuspanel.ConsolePanel;
import io.github.uxodev.libgdx.display.ui.infopanel.InfoPanel;

// static class
public class Ui {
    public static Skin skin = new Skin(Gdx.files.internal("dark.json"));
    public static BitmapFont font = new BitmapFont();
    public static Label.LabelStyle labelStyle = new Label.LabelStyle(font, Color.BLACK);
    public static ImageTextButton.ImageTextButtonStyle toggleButtonStyle = new ImageTextButton.ImageTextButtonStyle(
            skin.getDrawable("buttonUp"),
            skin.getDrawable("buttonDown"),
            skin.getDrawable("buttonDown"),
            font);
    public static ImageTextButton.ImageTextButtonStyle normalButtonStyle = new ImageTextButton.ImageTextButtonStyle(
            skin.getDrawable("buttonUp"),
            skin.getDrawable("buttonDown"),
            skin.getDrawable("buttonUp"),
            font);
    public static ScrollPane.ScrollPaneStyle scrollPaneStyle = new ScrollPane.ScrollPaneStyle(
            skin.get(ScrollPane.ScrollPaneStyle.class));
    public static Drawable background = skin.getDrawable("list");

    public static Stage stage = new Stage();
    private static Stack rootStack = new Stack();
    private static InfoPanel infoPanel = new InfoPanel();
    private static ButtonPanel buttonPanel = new ButtonPanel();
    private static ConsolePanel consolePanel = new ConsolePanel();

    public static void init() {
        rootStack.setFillParent(true);
        stage.addActor(rootStack);

        infoPanel.bottom().left();
        buttonPanel.bottom().right();
        consolePanel.top().left();
        rootStack.add(infoPanel);
        rootStack.add(buttonPanel);
        rootStack.add(consolePanel);
    }

    public static void update() {
        infoPanel.update();
        consolePanel.update();
    }

    public static void openInfoPanel() {
        infoPanel.setVisible(true);
    }

    public static void closeInfoPanel() {
        infoPanel.setVisible(false);
    }

    public static ImageTextButton createToggleButton(String text) {
        ImageTextButton toggleButton = new ImageTextButton(text, toggleButtonStyle);
        toggleButton.align(Align.left);
        return toggleButton;
    }

    public static ImageTextButton createClickButton(String text) {
        ImageTextButton clickButton = new ImageTextButton(text, normalButtonStyle);
        clickButton.align(Align.left);
        return clickButton;
    }

    public static ImageTextButton createDisabledButton(String text) {
        ImageTextButton toggleButton = new ImageTextButton(text, normalButtonStyle);
        toggleButton.align(Align.left);
        toggleButton.setDisabled(true);
        return toggleButton;
    }
}
