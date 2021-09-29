package ru.gb.pool;

import ru.gb.base.SpritePool;
import ru.gb.math.Rect;
import ru.gb.sprite.EnemyShip;

public class EnemyPool extends SpritePool<EnemyShip> {

    private final BulletPool bulletPool;
    private final Rect worldBounds;
    private final ExplosionPool explosionPool;
    private final MedKitPool medKitPool;

    public EnemyPool(BulletPool bulletPool, Rect worldBounds, ExplosionPool explosionPool, MedKitPool medKitPool) {
        this.bulletPool = bulletPool;
        this.worldBounds = worldBounds;
        this.explosionPool = explosionPool;
        this.medKitPool = medKitPool;
    }

    @Override
    protected EnemyShip newObject() {
        return new EnemyShip(bulletPool, explosionPool, worldBounds, medKitPool);
    }
}
