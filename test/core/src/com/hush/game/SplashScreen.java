package com.hush.game;

import com.badlogic.gdx.*;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.hush.game.UI.Settings;

public class SplashScreen extends ScreenAdapter {
    Settings game;
    ShapeRenderer shapeRenderer;
    SpriteBatch batch;
    Texture titleText;
    Texture splashText;
    Texture namesText;
    Sound sound;

    int cursorX;
    int cursorY;
    int titleX = 710;
    int titleY = 750;
    int splashX = 460;
    int splashY = 210;
    int namesX = 1570;
    int namesY = 700;

    public SplashScreen(Settings game) {
        this.game = game;
        titleText = new Texture("Text/titleText.png");
        splashText = new Texture("Text/splashText.png");
        namesText = new Texture("Text/namesText.png");
        shapeRenderer = new ShapeRenderer();
        batch = new SpriteBatch();
        sound = Gdx.audio.newSound(Gdx.files.internal("Menu1.wav"));
    }

    @Override
    public void show(){
        Gdx.input.setInputProcessor(new InputAdapter() {
            @Override
            public boolean touchDown(int screenX, int screenY, int pointer, int button) {
                cursorX = Gdx.input.getX();
                cursorY = Gdx.graphics.getHeight() - Gdx.input.getY();
                if (Gdx.input.isTouched()) {
                    sound.play(0.25f);
                    game.setScreen(new MainMenu(game));
                }
                return true;
            }
        });
    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(0.25f, 0.25f, 0.25f, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        batch.begin();
        batch.draw(titleText, titleX, titleY, 500, 200);
        batch.draw(splashText, splashX, splashY, 1000, 150);
        batch.draw(namesText, namesX, namesY, 350, 350);
        batch.end();

    }

    @Override
    public void hide(){
        Gdx.input.setInputProcessor(null);
    }
}
