package ru.gb.base;

import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;

import ru.gb.math.Rect;
import ru.gb.pool.BulletPool;
import ru.gb.sprite.Bullet;

public class Ship extends Sprite {

    private static final float DAMAGE_ANIMATE_INTERVAL = 0.1f;

    protected final Vector2 v0 = new Vector2();
    protected final Vector2 v = new Vector2();

    protected  BulletPool bulletPool;
    protected  TextureRegion bulletRegion;
    protected  Vector2 bulletV;
    protected  Vector2 bulletPos;
    protected  float bulletHeight;
    protected  int bulletDamage;
    protected  Sound bulletSound;

    protected float reloadTimer;
    protected float reloadInterval;

    protected Rect worldBounds;

    protected int hp;

    private float damageAnimateTimer = DAMAGE_ANIMATE_INTERVAL;

    public Ship() {

    }

    public Ship(TextureRegion region, int rows, int cols, int frames) {
        super(region, rows, cols, frames);
    }

    @Override
    public void update(float delta) {
        pos.mulAdd(v, delta);
        reloadTimer += delta;
        if (reloadTimer >= reloadInterval) {
            reloadTimer = 0f;
            shoot();
        }

        damageAnimateTimer += delta;
        if (damageAnimateTimer >= DAMAGE_ANIMATE_INTERVAL) {
            frame = 0;
        }
    }

    private void shoot() {
        Bullet bullet = bulletPool.obtain();
        bullet.set(this, bulletRegion, bulletPos, bulletV, bulletHeight, worldBounds, bulletDamage);
        bulletSound.play();
    }
}
