package ru.gb.pool;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;

import ru.gb.base.SpritePool;
import ru.gb.sprite.Explosion;

public class ExplosionPool extends SpritePool<Explosion> {

    private final TextureAtlas atlas;
    private final Sound explosionSound;

    public ExplosionPool(TextureAtlas atlas, Sound explosionSound){
        this.atlas = atlas;
        this.explosionSound = explosionSound;
    }

    @Override
    protected Explosion newObject() {
        return new Explosion(atlas, explosionSound);
    }
}
