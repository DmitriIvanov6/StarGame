package ru.gb.sprite;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;

import ru.gb.base.Sprite;
import ru.gb.math.Rect;

public class Logo extends Sprite {
    private static final float HEIGHT = 0.2f;
    private static final float V_LEN = 0.01f;

    private final Vector2 velocity;
    private final Vector2 touch;
    private final Vector2 tmp;


    public Logo(Texture texture) {
        super(new TextureRegion(texture));
        velocity = new Vector2();
        touch = new Vector2();
        tmp = new Vector2();
    }


    @Override
    public void resize(Rect worldBounds) {
        setHeightProportion(HEIGHT);
    }

    @Override
    public void update(float delta) {
        tmp.set(touch);
        if (tmp.sub(pos).len() > V_LEN) {
            pos.add(velocity);
        } else {
            pos.set(touch);
        }
    }

    @Override
    public boolean touchDown(Vector2 touch, int pointer, int button) {
        this.touch.set(touch);
        velocity.set(touch.cpy().sub(pos)).setLength(V_LEN);
        return false;
    }


}
