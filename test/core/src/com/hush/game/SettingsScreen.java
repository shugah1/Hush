package com.hush.game;

import com.badlogic.gdx.*;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.hush.game.UI.Settings;

public class SettingsScreen extends ScreenAdapter {
    Settings game;
    SpriteBatch batch;
    ShapeRenderer shapeRenderer;
    Texture settingsText;
    Texture audioText;
    Texture videoText;
    Texture slider;
    Texture quitText;
    Sound sound;

    int cursorX;
    int cursorY;
    float buttonWidth = Gdx.graphics.getWidth() / 5;
    float buttonHeight = Gdx.graphics.getHeight() / 9;
    float buttonX = Gdx.graphics.getWidth() / 2 - buttonWidth / 2;

    float settingsX = buttonX;
    float settingsY = buttonHeight * 7;
    float audioX = buttonX;
    float audioY = buttonHeight * 5;
    float videoX = buttonX;
    float videoY = buttonHeight * 3;
    float sliderX = buttonX;
    float sliderY = buttonHeight * 5;
    float quitX = buttonX;
    float quitY = buttonHeight;

    public SettingsScreen(Settings game) {
        this.game = game;
        batch = new SpriteBatch();
        shapeRenderer = new ShapeRenderer();
        settingsText = new Texture("Text/settingsText.png");
        audioText = new Texture("Text/audioText.png");
        videoText = new Texture("Text/videoText.png");
        slider = new Texture("Text/slider.png");
        quitText = new Texture("Text/quitText.png");
        sound = Gdx.audio.newSound(Gdx.files.internal("Menu1.wav"));
    }

    @Override
    public void show(){
        Gdx.input.setInputProcessor(new InputAdapter() {
            // Settings Input
            @Override
            public boolean touchDown(int screenX, int screenY, int pointer, int button) {
                cursorX = Gdx.input.getX();
                cursorY = Gdx.graphics.getHeight() - Gdx.input.getY();
                // Quit Button
                if (cursorX > quitX && cursorX < quitX + buttonWidth) {
                    if (cursorY > quitY && cursorY < quitY + buttonHeight) {
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
        batch.draw(settingsText, settingsX, settingsY, buttonWidth, buttonHeight);
        batch.draw(audioText, audioX - buttonWidth, audioY, buttonWidth, buttonHeight);
        batch.draw(videoText, videoX - buttonWidth, videoY, buttonWidth, buttonHeight);

        batch.draw(slider, sliderX + buttonWidth * 0.75f, sliderY, buttonWidth * 1.25f, buttonHeight);
        batch.draw(slider, sliderX + buttonWidth * 0.75f, sliderY - buttonHeight * 2, buttonWidth * 1.25f, buttonHeight);
        batch.draw(quitText, quitX, quitY, buttonWidth, buttonHeight);
        batch.end();
    }

    @Override
    public void hide(){
        Gdx.input.setInputProcessor(null);
    }
}