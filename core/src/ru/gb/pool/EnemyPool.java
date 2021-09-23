package ru.gb.pool;

import ru.gb.base.SpritePool;
import ru.gb.math.Rect;
import ru.gb.sprite.EnemyShip;

public class EnemyPool extends SpritePool<EnemyShip> {

    private final BulletPool bulletPool;
    private final Rect worldBounds;
    private final ExplosionPool explosionPool;

    public EnemyPool(BulletPool bulletPool, Rect worldBounds, ExplosionPool explosionPool) {
        this.bulletPool = bulletPool;
        this.worldBounds = worldBounds;
        this.explosionPool = explosionPool;
    }

    @Override
    protected EnemyShip newObject() {
        return new EnemyShip(bulletPool, explosionPool, worldBounds);
    }
}
