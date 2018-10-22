package io.github.uxodev.libgdx.display.ui.buttonPanel;

import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.ui.ImageTextButton;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.utils.Align;
import io.github.uxodev.libgdx.display.ui.Ui;
import io.github.uxodev.libgdx.input.game.GameInput;
import io.github.uxodev.libgdx.input.map.MapInput;

import java.util.ArrayList;

public class ButtonPanel extends Table {
    private static final int BUTTON_WIDTH = 80;
    private static final int BUTTON_HEIGHT = 60;
    private static final int NUM_BUTTON_X = 3;
    private static final int NUM_BUTTON_Y = 14;

    private final ArrayList<ArrayList<ImageTextButton>> buttons = new ArrayList<>();
    private boolean clearingSelection = true;

    public ButtonPanel() {
        for (int y = 0; y < NUM_BUTTON_Y; y++) {
            for (int x = 0; x < NUM_BUTTON_X; x++) {
                buttons.add(new ArrayList<>());
                ImageTextButton button = createButton();
                add(button).size(BUTTON_WIDTH, BUTTON_HEIGHT);
                buttons.get(x).add(button);
            }
            row();
        }

        setOptionButton(0, 0, "pause", new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                GameInput.switchPaused();
            }
        });
        setOptionButton(1, 0, "one step", new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                GameInput.oneStep();
            }
        });
        setOptionButton(2, 0, "clearing\nselection", new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                clearingSelection = !clearingSelection;
                buttons.get(2).get(0).setText(clearingSelection ? "clearing\nselection" : "keeping\nselection");
            }
        });

        // ramp
        setButton(0, 1, "dig", new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                ButtonPanelEvents.dig();
            }
        });
        setButton(1, 1, "add ramp", new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                ButtonPanelEvents.addRamp();
            }
        });
        setButton(2, 1, "add stair", new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                ButtonPanelEvents.addStair();
            }
        });

        // wall
        setButton(0, 2, "remove\nwall", new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                ButtonPanelEvents.removeWall();
            }
        });
        setButton(1, 2, "add wall 1", new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                ButtonPanelEvents.addWall(1);
            }
        });
        setButton(2, 2, "add wall 2", new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                ButtonPanelEvents.addWall(2);
            }
        });
        setButton(0, 3, "add wall 3", new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                ButtonPanelEvents.addWall(3);
            }
        });
        setButton(1, 3, "add wall 4", new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                ButtonPanelEvents.addWall(4);
            }
        });
        setButton(2, 3, "add wall 5", new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                ButtonPanelEvents.addWall(5);
            }
        });

        // floor
        setButton(0, 4, "remove\nfloor", new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                ButtonPanelEvents.removeFloor();
            }
        });
        setButton(1, 4, "add floor 1", new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                ButtonPanelEvents.addFloor(1);
            }
        });
        setButton(2, 4, "add floor 2", new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                ButtonPanelEvents.addFloor(2);
            }
        });
        setButton(0, 5, "add floor 3", new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                ButtonPanelEvents.addFloor(3);
            }
        });
        setButton(1, 5, "add floor 4", new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                ButtonPanelEvents.addFloor(4);
            }
        });
        setButton(2, 5, "add floor 5", new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                ButtonPanelEvents.addFloor(5);
            }
        });

        // tree
        setButton(0, 6, "", new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                ButtonPanelEvents.createTree();
            }
        });
        setButton(1, 6, "", new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                ButtonPanelEvents.addTrunk();
            }
        });
        setButton(2, 6, "", new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                ButtonPanelEvents.addBranch();
            }
        });

        // furniture
        setButton(0, 7, "", new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                ButtonPanelEvents.removeFurniture();
            }
        });
        setButton(1, 7, "add\ntable", new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                ButtonPanelEvents.addFurniture(1);
            }
        });
        setButton(2, 7, "add\nbed", new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                ButtonPanelEvents.addFurniture(2);
            }
        });

        // item
        setButton(0, 8, "", new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                ButtonPanelEvents.removeItem();
            }
        });
        setButton(1, 8, "add item\nleather figurine", new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                ButtonPanelEvents.addItem(1);
            }
        });
        setButton(2, 8, "add item\njade figurine", new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                ButtonPanelEvents.addItem(2);
            }
        });
        setButton(0, 9, "add item\ncopper spear", new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                ButtonPanelEvents.addItem(3);
            }
        });
        setButton(1, 9, "add item\nsilver flute", new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                ButtonPanelEvents.addItem(4);
            }
        });
        setButton(2, 9, "add item\ngold figurine", new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                ButtonPanelEvents.addItem(5);
            }
        });

        // area
        setButton(0, 10, "", new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                ButtonPanelEvents.removeArea();
            }
        });
        setButton(1, 10, "add area\nleather", new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                ButtonPanelEvents.addArea(1);
            }
        });
        setButton(2, 10, "add area\njade", new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                ButtonPanelEvents.addArea(2);
            }
        });
        setButton(0, 11, "add area\ncopper", new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                ButtonPanelEvents.addArea(3);
            }
        });
        setButton(1, 11, "add area\nfigurine", new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                ButtonPanelEvents.addArea(4);
            }
        });
        setButton(2, 11, "add area\nmetal", new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                ButtonPanelEvents.addArea(5);
            }
        });

        // unit
        setButton(0, 12, "", new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                ButtonPanelEvents.removeUnit();
            }
        });
        setButton(1, 12, "add unit 1", new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                ButtonPanelEvents.addUnit(1);
            }
        });
        setButton(2, 12, "", new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                ButtonPanelEvents.addUnit(2);
            }
        });
    }

    private ImageTextButton createButton() {
        ImageTextButton clickButton = Ui.createClickButton("");
        clickButton.align(Align.center);
        return clickButton;
    }

    private void setOptionButton(int x, int y, String text, ChangeListener changeListener) {
        ImageTextButton imageTextButton = buttons.get(x).get(y);
        imageTextButton.setText(text);
        imageTextButton.addListener(changeListener);
    }

    private void setButton(int x, int y, String text, ChangeListener changeListener) {
        ImageTextButton imageTextButton = buttons.get(x).get(y);
        imageTextButton.setText(text);
        imageTextButton.addListener(changeListener);
        imageTextButton.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                if (clearingSelection) MapInput.clear();
            }
        });
    }
}
