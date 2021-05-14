package com.hush.game;

import com.badlogic.gdx.*;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.hush.game.UI.Settings;

public class SettingsScreen extends ScreenAdapter {
    Settings game;
    SpriteBatch batch;
    Texture settingsText;
    Texture audioText;
    Texture videoText;
    Texture slider;
    Texture quitText;
    Sound sound;

    int cursorX;
    int cursorY;
    int settingsX = 810;
    int settingsY = 750;
    int audioX = 490;
    int audioY = 550;
    int videoX = 490;
    int videoY = 350;
    int sliderX = 960;
    int sliderY = 550;
    int quitX = 810;
    int quitY = 150;

    public SettingsScreen(Settings game) {
        this.game = game;
        batch = new SpriteBatch();
        settingsText = new Texture("settingsText.png");
        audioText = new Texture("audioText.png");
        videoText = new Texture("videoText.png");
        slider = new Texture("slider.png");
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
        batch.draw(audioText, audioX, audioY, 300, 100);
        batch.draw(videoText, videoX, videoY, 300, 100);
        batch.draw(slider, sliderX, sliderY, 450, 100);
        batch.draw(slider, sliderX, sliderY - 200, 450, 100);
        batch.draw(quitText, quitX, quitY, 300, 100);
        batch.end();
    }

    @Override
    public void hide(){
        Gdx.input.setInputProcessor(null);
    }
}