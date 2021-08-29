package ru.gb;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.ScreenUtils;

public class Stargame extends ApplicationAdapter {
	SpriteBatch batch;
	Texture img;
	
	@Override
	public void create () {
		batch = new SpriteBatch();
		img = new Texture("ava.jpg");
	}

	@Override
	public void render () {
		ScreenUtils.clear(1, 0.5f, 0, 1);
		batch.begin();
		batch.setColor(1, 1, 1,0.8f);
		batch.draw(img, 210, 150, 200, 200);
		batch.end();
	}
	
	@Override
	public void dispose () {
		batch.dispose();
		img.dispose();
	}
}
