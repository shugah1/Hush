package com.hush.game;

import com.badlogic.gdx.*;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class SettingsScreen extends ScreenAdapter {
    Hush game;
    SpriteBatch batch;
    Texture settingsText;
    Texture quitText;
    Sound sound;

    int cursorX;
    int cursorY;
    int settingsX = 810;
    int settingsY = 350;
    int quitX = 810;
    int quitY = 150;

    public SettingsScreen(Hush game) {
        this.game = game;
        batch = new SpriteBatch();
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
                if (cursorX > quitX && cursorX < quitX + 300) {
                    if (cursorY > quitY && cursorY < quitY + 100) {
                        if (Gdx.input.isTouched()) {
                            sound.play(0.25f);
                            game.setScreen(new MainMenu(game));
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
        batch.draw(settingsText, settingsX, settingsY, 300, 100);
        batch.draw(quitText, quitX, quitY, 300, 100);
        batch.end();
    }

    @Override
    public void hide(){
        Gdx.input.setInputProcessor(null);
    }
}