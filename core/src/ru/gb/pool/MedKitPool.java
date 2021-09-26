package ru.gb.pool;

import com.badlogic.gdx.graphics.g2d.TextureAtlas;

import ru.gb.base.SpritePool;
import ru.gb.sprite.MedKit;

public class MedKitPool extends SpritePool<MedKit> {

    private final TextureAtlas atlas;

    public MedKitPool(TextureAtlas atlas) {
        this.atlas = atlas;
    }

    @Override
    protected MedKit newObject() {
        return new MedKit(atlas);
    }
}
