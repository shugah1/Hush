package com.hush.game.Screens;

import com.badlogic.gdx.*;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.hush.game.Screens.MainMenu;
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
    float buttonWidth = Gdx.graphics.getWidth() / 5;
    float buttonHeight = Gdx.graphics.getHeight() / 9;
    float buttonX = Gdx.graphics.getWidth() / 2 - buttonWidth / 2;
    float splashWidth = (Gdx.graphics.getWidth() / 5) * 2;
    float splashX = Gdx.graphics.getWidth() / 2 - splashWidth / 2;

    float titleX = buttonX;
    float titleY = buttonHeight * 7;
    float splashY = buttonHeight;
    float namesWidth = Gdx.graphics.getWidth() / 6;
    float namesHeight = Gdx.graphics.getHeight() / 4;
    float namesX = Gdx.graphics.getWidth() - namesWidth;
    float namesY = Gdx.graphics.getHeight() - namesHeight;

    public SplashScreen(Settings game) {
        this.game = game;
        titleText = new Texture("Text/titleText.png");
        splashText = new Texture("Text/splashText.png");
        namesText = new Texture("Text/namesText.png");
        shapeRenderer = new ShapeRenderer();
        batch = new SpriteBatch();
        sound = Gdx.audio.newSound(Gdx.files.internal("test/core/assets/SoundEffects/Menu1.wav"));
        Settings.music = game.newSong("TitleTheme");
        Settings.music.setVolume(Settings.musicVolume / 10f);
        Settings.music.play();
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
        batch.draw(titleText, titleX, titleY, buttonWidth, buttonHeight);
        batch.draw(splashText, splashX, splashY, splashWidth, buttonHeight);
        batch.draw(namesText, namesX, namesY, namesWidth, namesHeight);
        batch.end();

    }

    @Override
    public void hide(){
        Gdx.input.setInputProcessor(null);
    }
}
