package com.hush.game;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;


public class main extends ApplicationAdapter {
	ShapeRenderer shapeRenderer;

	float circleX = 200;
	float circleY = 100;

	@Override
	public void create() {
		shapeRenderer = new ShapeRenderer();
	}

	@Override
	public void render() {

		if(Gdx.input.isKeyPressed(Input.Keys.W)){
			circleY += 10;
		}
		else if(Gdx.input.isKeyPressed(Input.Keys.S)){
			circleY -= 10;
		}

		if(Gdx.input.isKeyPressed(Input.Keys.A)){
			circleX -= 10;
		}
		else if(Gdx.input.isKeyPressed(Input.Keys.D)){
			circleX += 10;
		}

		if(circleX < 0){
			circleX = 0;
		}

		if(circleX > Gdx.graphics.getWidth()){
			circleX = Gdx.graphics.getWidth();
		}

		if(circleY < 0){
			circleY = 0;
		}

		if(circleY > Gdx.graphics.getHeight()){
			circleY = Gdx.graphics.getHeight();
		}

		Gdx.gl.glClearColor(.25f, .25f, .25f, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

		shapeRenderer.begin(ShapeRenderer.ShapeType.Filled);
		shapeRenderer.setColor(1, 1, 1, 0);
		shapeRenderer.circle(circleX, circleY, 75);
		shapeRenderer.end();
	}

	@Override
	public void dispose() {
		shapeRenderer.dispose();
	}
}