package ru.gb.sprite;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;

import ru.gb.base.BaseButton;
import ru.gb.math.Rect;
import ru.gb.screen.GameScreen;

public class PlayButton extends BaseButton {

    private final Game game;

    private static final float HEIGHT = 0.2f;
    private static final float PADDING = 0.02f;

    public PlayButton(TextureAtlas atlas, Game game) {
        super(atlas.findRegion("btPlay"));
        this.game = game;
    }

    @Override
    public void resize(Rect worldBounds) {
        setHeightProportion(HEIGHT + 0.06f);
        setLeft(worldBounds.getLeft() + PADDING);
        setBottom(worldBounds.getBottom() + PADDING);
    }

    @Override
    public void action() {
        game.setScreen(new GameScreen());
    }
}
