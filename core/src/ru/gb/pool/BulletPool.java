package ru.gb.pool;

import ru.gb.base.SpritePool;
import ru.gb.sprite.Bullet;

public class BulletPool extends SpritePool<Bullet> {

    @Override
    protected Bullet newObject() {
        return new Bullet();
    }


}
