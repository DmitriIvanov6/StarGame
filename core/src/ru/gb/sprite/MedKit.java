package ru.gb.sprite;

import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.math.Vector2;

import ru.gb.base.Sprite;
import ru.gb.math.Rect;

public class MedKit extends Sprite {

    public static final int HP_REGEN = -25;
    private static final float MEDKIT_HEIGHT = 0.04f;

    private Rect worldBounds;
    private final Vector2 v = new Vector2();
    private int healing;
    private final Vector2 medKitV = new Vector2(0, -0.1f);

    public MedKit(TextureAtlas atlas) {
        super(atlas.findRegion("medkit"));
        setHeightProportion(MEDKIT_HEIGHT);
        this.v.set(medKitV);
        this.healing = HP_REGEN;
    }

    public void set(
            Vector2 pos0,
            Rect worldBounds
    ) {
        this.pos.set(pos0);
        this.worldBounds = worldBounds;
    }

    @Override
    public void update(float delta) {
        super.update(delta);
        pos.mulAdd(v, delta);
        if (isOutside(worldBounds)) {
            destroy();
        }
    }

    public int getHealing() {
        return healing;
    }
}
