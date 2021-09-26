package ru.gb.screen;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Align;

import java.util.List;

import ru.gb.base.BaseScreen;
import ru.gb.base.Font;
import ru.gb.math.Rect;
import ru.gb.pool.BulletPool;
import ru.gb.pool.EnemyPool;
import ru.gb.pool.ExplosionPool;
import ru.gb.pool.MedKitPool;
import ru.gb.sprite.Background;
import ru.gb.sprite.Bullet;
import ru.gb.sprite.EnemyShip;
import ru.gb.sprite.Explosion;
import ru.gb.sprite.GameOver;
import ru.gb.sprite.MainShip;
import ru.gb.sprite.MedKit;
import ru.gb.sprite.NewGameButton;
import ru.gb.sprite.Star;
import ru.gb.utils.EnemyEmitter;

public class GameScreen extends BaseScreen {

    private static final int STAR_COUNT = 64;
    private static final String FRAGS = "Frags: ";
    private static final float MARGIN = 0.01f;
    private static final String HP = "HP: ";
    private static final String LEVEL = "Level: ";

    private Texture bg;
    private TextureAtlas atlas;

    private Background background;
    private Star[] stars;
    private MainShip mainShip;

    private BulletPool bulletPool;
    private ExplosionPool explosionPool;
    private EnemyPool enemyPool;
    private MedKitPool medKitPool;

    private EnemyEmitter enemyEmitter;

    private GameOver gameOver;
    private NewGameButton newGameButton;

    private Music music;
    private Sound pewMain;
    private Sound pewSmallShip;
    private Sound explosionSound;
    private Sound pewMediumShip;
    private Sound pewBigShip;
    private Sound mainShipExplSound;

    private Font font;
    private StringBuilder sbFrags;
    private StringBuilder sbHP;
    private StringBuilder sbLevel;

    private int frags;

    private Explosion mainShipBoom;


    @Override
    public void show() {
        super.show();
        bg = new Texture("textures/bg.png");
        atlas = new TextureAtlas("textures/mainAtlas.atlas");
        background = new Background(bg);
        gameOver = new GameOver(atlas);
        newGameButton = new NewGameButton(atlas, this);
        font = new Font("font/font.fnt", "font/font.png");
        font.setSize(0.02f);
        sbFrags = new StringBuilder();
        sbHP = new StringBuilder();
        sbLevel = new StringBuilder();
        stars = new Star[STAR_COUNT];
        for (int i = 0; i < stars.length; i++) {
            stars[i] = new Star(atlas);
        }
        bulletPool = new BulletPool();
        medKitPool = new MedKitPool(atlas);
        explosionSound = Gdx.audio.newSound(Gdx.files.internal("sounds/explosion.wav"));
        mainShipExplSound = Gdx.audio.newSound(Gdx.files.internal("sounds/mainShipExplode.wav"));
        explosionPool = new ExplosionPool(atlas, explosionSound);
        mainShipBoom = new Explosion(atlas, mainShipExplSound);
        enemyPool = new EnemyPool(bulletPool, worldBounds, explosionPool, medKitPool);
        pewSmallShip = Gdx.audio.newSound(Gdx.files.internal("sounds/pewSmall.wav"));
        pewMediumShip = Gdx.audio.newSound(Gdx.files.internal("sounds/pewMedium.wav"));
        pewBigShip = Gdx.audio.newSound(Gdx.files.internal("sounds/pewBig.wav"));
        enemyEmitter = new EnemyEmitter(atlas, enemyPool, worldBounds, pewSmallShip, pewMediumShip, pewBigShip);
        pewMain = Gdx.audio.newSound(Gdx.files.internal("sounds/pewMain.wav"));
        mainShip = new MainShip(atlas, bulletPool, mainShipBoom, pewMain);
        music = Gdx.audio.newMusic(Gdx.files.internal("sounds/imperial.mp3"));
        music.setLooping(true);
        music.setVolume(0.1f);
        music.play();

    }

    public void startNewGame() {
        mainShip.startNewGame();
        medKitPool.dispose();
        bulletPool.dispose();
        enemyPool.dispose();
        explosionPool.dispose();
        frags = 0;
    }

    @Override
    public void render(float delta) {
        super.render(delta);
        update(delta);
        freeAllDestroyed();
        checkCollisions();
        draw();
    }

    public void update(float delta) {
        for (Star star : stars) {
            star.update(delta);
        }
        explosionPool.updateActiveSprites(delta);
        mainShipBoom.updateMSE(delta);
        if (!mainShip.isDestroyed()) {
            mainShip.update(delta);
            bulletPool.updateActiveSprites(delta);
            medKitPool.updateActiveSprites(delta);
            enemyPool.updateActiveSprites(delta);
            enemyEmitter.generate(delta, frags);
        }
    }

    public void draw() {
        batch.begin();
        background.draw(batch);
        for (Star star : stars) {
            star.draw(batch);
        }
        if (!mainShip.isDestroyed()) {
            mainShip.draw(batch);
            bulletPool.drawActiveSprites(batch);
            medKitPool.drawActiveSprites(batch);
            enemyPool.drawActiveSprites(batch);
        } else {
            gameOver.draw(batch);
            newGameButton.draw(batch);
        }
        explosionPool.drawActiveSprites(batch);
        mainShipBoom.drawMSE(batch);
        printInfo();
        batch.end();
    }

    @Override
    public void resize(Rect worldBounds) {
        super.resize(worldBounds);
        background.resize(worldBounds);
        for (Star star : stars) {
            star.resize(worldBounds);
        }
        mainShip.resize(worldBounds);
        gameOver.resize(worldBounds);
        newGameButton.resize(worldBounds);
    }

    @Override
    public void dispose() {
        super.dispose();
        bg.dispose();
        atlas.dispose();
        bulletPool.dispose();
        music.dispose();
        pewMain.dispose();
        enemyPool.dispose();
        pewSmallShip.dispose();
        pewMediumShip.dispose();
        pewBigShip.dispose();
        explosionPool.dispose();
        explosionSound.dispose();
        mainShipExplSound.dispose();
        medKitPool.dispose();
    }

    @Override
    public boolean touchDown(Vector2 touch, int pointer, int button) {
        if (!mainShip.isDestroyed()) {
            mainShip.touchDown(touch, pointer, button);
        } else {
            newGameButton.touchDown(touch, pointer, button);
        }
        return false;
    }

    @Override
    public boolean touchUp(Vector2 touch, int pointer, int button) {
        if (!mainShip.isDestroyed()) {
            mainShip.touchUp(touch, pointer, button);
        } else {
            newGameButton.touchUp(touch, pointer, button);
        }
        return false;
    }

    private void checkCollisions() {
        if (mainShip.isDestroyed()) {
            return;
        }
        List<EnemyShip> enemyShipList = enemyPool.getActiveObjects();
        for (EnemyShip enemyShip : enemyShipList) {
            float minDst = enemyShip.getHalfWidth() + mainShip.getHalfWidth();
            if (mainShip.pos.dst(enemyShip.pos) < minDst) {
                enemyShip.destroy();
                mainShipBoom.flushDestroy();
                mainShip.destroy();
            }
        }
        List<Bullet> bulletList = bulletPool.getActiveObjects();
        for (Bullet bullet : bulletList) {
            if (bullet.getOwner() != mainShip) {
                if (mainShip.inCollision(bullet)) {
                    mainShip.damage(bullet.getDamage());
                    bullet.destroy();
                    if (mainShip.getHp() <= 0) {
                        mainShipBoom.flushDestroy();
                    }
                }
            } else {
                for (EnemyShip enemyShip : enemyShipList) {
                    if (enemyShip.inCollision(bullet)) {
                        enemyShip.damage(bullet.getDamage());
                        bullet.destroy();
                        if (enemyShip.isDestroyed()) {
                            frags++;
                        }
                    }
                }
            }
        }
        List<MedKit> medKitList = medKitPool.getActiveObjects();
        for (MedKit medKit : medKitList) {
            if (mainShip.inCollision(medKit)) {
                mainShip.damage(medKit.getHealing());
                medKit.destroy();
            }
        }
    }

    @Override
    public boolean keyDown(int keycode) {
        if (!mainShip.isDestroyed()) {
            mainShip.keyDown(keycode);
        }
        return false;
    }

    @Override
    public boolean keyUp(int keycode) {
        if (!mainShip.isDestroyed()) {
            mainShip.keyUp(keycode);
        }
        return false;
    }

    private void freeAllDestroyed() {
        bulletPool.freeAllDestroyedActiveSprites();
        enemyPool.freeAllDestroyedActiveSprites();
        explosionPool.freeAllDestroyedActiveSprites();
        medKitPool.freeAllDestroyedActiveSprites();
    }

    private void printInfo() {
        sbFrags.setLength(0);
        font.draw(batch, sbFrags.append(FRAGS).append(frags), worldBounds.getLeft() + MARGIN, worldBounds.getTop() - MARGIN);
        sbHP.setLength(0);
        font.draw(batch, sbHP.append(HP).append(mainShip.getHp()), worldBounds.pos.x, worldBounds.getTop() - MARGIN, Align.center);
        sbLevel.setLength(0);
        font.draw(batch, sbLevel.append(LEVEL).append(enemyEmitter.getLevel()), worldBounds.getRight() - MARGIN, worldBounds.getTop() - MARGIN, Align.right);

    }
}
