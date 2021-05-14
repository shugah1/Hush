package com.hush.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputAdapter;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.hush.game.UI.Settings;

public class MainMenu extends ScreenAdapter {
    Settings game;
    SpriteBatch batch;
    Texture titleText;
    Texture startText;
    Texture settingsText;
    Texture quitText;
    Sound sound;

    int cursorX;
    int cursorY;
    int titleX = 710;
    int titleY = 750;
    int startX = 810;
    int startY = 550;
    int settingsX = 810;
    int settingsY = 350;
    int quitX = 810;
    int quitY = 150;

    public MainMenu(Settings game) {
        this.game = game;
        batch = new SpriteBatch();
        titleText = new Texture("titleText.png");
        startText = new Texture("startText.png");
        settingsText = new Texture("settingsText.png");
        quitText = new Texture("quitText.png");
        sound = Gdx.audio.newSound(Gdx.files.internal("Menu1.wav"));
    }

    @Override
    public void show(){
        Gdx.input.setInputProcessor(new InputAdapter() {
            @Override
            public boolean touchDown(int screenX, int screenY, int pointer, int button) {
                cursorX = Gdx.input.getX();
                cursorY = Gdx.graphics.getHeight() - Gdx.input.getY();
                if (cursorX > startX && cursorX < startX + 300) {
                    if (cursorY > startY && cursorY < startY + 100) {
                        if (Gdx.input.isTouched()) {
                            sound.play(0.25f);
                            game.setScreen(new LevelSelect(game));
                        }
                    }
                }
                if (cursorX > settingsX && cursorX < settingsX + 300) {
                    if (cursorY > settingsY && cursorY < settingsY + 100) {
                        if (Gdx.input.isTouched()) {
                            sound.play(0.25f);
                            game.setScreen(new SettingsScreen(game));
                        }
                    }
                }
                if (cursorX > quitX && cursorX < quitX + 300) {
                    if (cursorY > quitY && cursorY < quitY + 100) {
                        if (Gdx.input.isTouched()) {
                            sound.play(0.25f);
                            System.exit(0);
                        }
                    }
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
        batch.draw(startText, startX, startY, 300, 100);
        batch.draw(settingsText, settingsX, settingsY, 300, 100);
        batch.draw(quitText, quitX, quitY, 300, 100);
        batch.end();
    }

    @Override
    public void hide(){
        Gdx.input.setInputProcessor(null);
    }
}

