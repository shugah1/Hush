package com.hush.game;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;


public class main extends ApplicationAdapter {
	ShapeRenderer shapeRenderer;
	//score board
	SpriteBatch batch;
	BitmapFont font;
	//Items text
	SpriteBatch batch1;
	BitmapFont font1;
	SpriteBatch batch2;
	BitmapFont font2;
	//invisibility image
	Texture titleText;
	SpriteBatch invisibility;
	//stun gun image
	SpriteBatch stunGun;
	Texture stunImage;


//circle starting point
	float circleX = 200;
	float circleY = 100;


//score board
	int item1counter = 0;
	int item2counter = 0;
	int score = 0;

	@Override
	public void create() {
		shapeRenderer = new ShapeRenderer();
		batch = new SpriteBatch();
		font = new BitmapFont();
		batch1 = new SpriteBatch();
		font1 = new BitmapFont();
		batch2 = new SpriteBatch();
		font2 = new BitmapFont();
		//invisibility image
		titleText = new Texture("invisibility-hush.png");
		invisibility = new SpriteBatch();
		//Stun gun image
		stunImage = new Texture("ScrollThunder.png");
		stunGun = new SpriteBatch();
	}

	@Override
	public void render() {

//score board location
		float scoreX = Gdx.graphics.getWidth() - 80;
		float scoreY = Gdx.graphics.getHeight() - 18;


//power up 1 dimensions
		float invisibilityX = Gdx.graphics.getWidth() - 80;
		float invisibilityY = Gdx.graphics.getHeight() - 390;
		float invisibilitytextY = Gdx.graphics.getHeight() - 370;
		float invisibilitytextX = Gdx.graphics.getWidth() - 40;

//power up 2 dimensions
		float item2X = Gdx.graphics.getWidth() - 80;
		float item2Y = Gdx.graphics.getHeight() - 460;
		float item2textX = Gdx.graphics.getWidth() - 40;
		float item2textY = Gdx.graphics.getHeight() - 440;


		if (Gdx.input.isTouched()) {
			circleX = Gdx.input.getX();
			circleY = Gdx.graphics.getHeight() - Gdx.input.getY();
		}

		if(Gdx.input.isKeyPressed(Input.Keys.W)){
			circleY = circleY + 5;
		}
		else if(Gdx.input.isKeyPressed(Input.Keys.S)){
			circleY = circleY - 5;
		}

		if(Gdx.input.isKeyPressed(Input.Keys.A)){
			circleX = circleX - 5;
		}
		else if(Gdx.input.isKeyPressed(Input.Keys.D)){
			circleX = circleX + 5;
		}
		else if(Gdx.input.isKeyJustPressed(Input.Keys.U)){
			score = score + 1;
		}

		Gdx.gl.glClearColor(.25f, .25f, .25f, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

		shapeRenderer.begin(ShapeRenderer.ShapeType.Filled);
		shapeRenderer.setColor(0, 1, 0, 1);
		shapeRenderer.circle(circleX, circleY, 75);
		shapeRenderer.end();

	//Score board
		batch.begin();
		font.draw(batch, "Score: " + score, scoreX, scoreY);
		batch.end();

//Inventory
	//Item 1 Image
		invisibility.begin();
		invisibility.draw(titleText, invisibilityX, invisibilityY, 40, 40);
		invisibility.end();

	//Item 1 Text
		batch1.begin();
		font1.draw(batch1, " " + item1counter, invisibilitytextX, invisibilitytextY);
		batch1.end();

	//Item 2 Image
		stunGun.begin();
		stunGun.draw(stunImage, item2X, item2Y, 40, 40);
		stunGun.end();

	//Item 2 Text
		batch2.begin();
		font2.draw(batch2, " " + item2counter, item2textX, item2textY);
		batch2.end();
	}

	@Override
	public void dispose() {
		shapeRenderer.dispose();
		batch.dispose();
		font.dispose();
		batch1.dispose();
		font1.dispose();
		batch2.dispose();
		font2.dispose();
		invisibility.dispose();
		stunGun.dispose();
	}
}

