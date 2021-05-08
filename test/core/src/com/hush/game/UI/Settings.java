package com.hush.game.UI;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.hush.game.Screens.Main;

public class Settings extends Game {
	public SpriteBatch batch;
	public static final int V_WIDTH = 800;
	public static final int V_HEIGHT = 400;
	public static final float PPM = 100;



	@Override
	public void create () {
		batch = new SpriteBatch();
		setScreen(new Main(this));
		Gdx.graphics.setWindowedMode(1920, 1080);




	}

	@Override
	public void render () {
		super.render();




	}


	
	@Override
	public void dispose () {



	}

}
