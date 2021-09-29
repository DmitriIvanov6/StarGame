package ru.gb.sprite;

import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;

import ru.gb.base.Ship;
import ru.gb.math.Rect;
import ru.gb.pool.BulletPool;
import ru.gb.pool.ExplosionPool;
import ru.gb.pool.MedKitPool;

public class EnemyShip extends Ship {

    private static final Vector2 startV = new Vector2(0, -0.3f);

    public EnemyShip(BulletPool bulletPool, ExplosionPool explosionPool, Rect worldBounds, MedKitPool medKitPool) {
        this.bulletPool = bulletPool;
        this.explosionPool = explosionPool;
        this.worldBounds = worldBounds;
        this.bulletV = new Vector2();
        this.bulletPos = new Vector2();
        this.medKitPool = medKitPool;
    }

    @Override
    public void update(float delta) {
        super.update(delta);
        if (getTop() < worldBounds.getTop()) {
            v.set(v0);
        } else {
            reloadTimer = 0.8f * reloadInterval;
        }
        this.bulletPos.set(pos.x, pos.y - getHalfHeight());
        if (getTop() < worldBounds.getBottom()) {
            flyBy();
        }
    }

    public void set(
            TextureRegion[] regions,
            Vector2 v0,
            TextureRegion bulletRegion,
            float bulletHeight,
            Vector2 bulletV,
            int bulletDamage,
            float reloadInterval,
            Sound bulletSound,
            float height,
            int hp
    ) {
        this.regions = regions;
        this.v0.set(v0);
        this.bulletRegion = bulletRegion;
        this.bulletHeight = bulletHeight;
        this.bulletV.set(bulletV);
        this.bulletDamage = bulletDamage;
        this.reloadInterval = reloadInterval;
        this.bulletSound = bulletSound;
        setHeightProportion(height);
        this.hp = hp;
        v.set(startV);
    }

    @Override
    public void destroy() {
        super.destroy();
        reloadTimer = 0f;
        boom();
        medKitDrop();
    }

    public boolean inCollision(Rect rect) {
        return !(
                rect.getRight() < getLeft()
                        || rect.getLeft() > getRight()
                        || rect.getBottom() > getTop()
                        || rect.getTop() < pos.y
        );
    }

    private void boom() {
        Explosion explosion = explosionPool.obtain();
        explosion.set(pos, getHeight());

    }

    private void flyBy() {
        super.destroy();
    }

    private void medKitDrop() {
        double chance = Math.random();
        if (chance >= 0.5) {
            MedKit medKit = medKitPool.obtain();
            medKit.set(bulletPos, worldBounds);
        }
    }
}
