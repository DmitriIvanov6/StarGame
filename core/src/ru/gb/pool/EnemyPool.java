package ru.gb.pool;

import ru.gb.base.SpritePool;
import ru.gb.math.Rect;
import ru.gb.sprite.EnemyShip;

public class EnemyPool extends SpritePool<EnemyShip> {

    private final BulletPool bulletPool;
    private final Rect worldBounds;

    public EnemyPool(BulletPool bulletPool, Rect worldBounds) {
        this.bulletPool = bulletPool;
        this.worldBounds = worldBounds;
    }

    @Override
    protected EnemyShip newObject() {
        return new EnemyShip(bulletPool, worldBounds);
    }
}
