package com.hush.game.Screens;

import com.badlogic.gdx.*;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.hush.game.Main;
import com.hush.game.UI.Settings;

public class LevelSelect extends ScreenAdapter {
    Settings game;
    ShapeRenderer shapeRenderer;
    SpriteBatch batch;
    Texture selectText;
    Texture tutorialText;
    Texture level1Text;
    Texture level2Text;
    Texture level3Text;
    Texture level4Text;
    Texture level5Text;
    Texture quitText;
    Sound sound;

    int cursorX;
    int cursorY;
    float buttonWidth = Gdx.graphics.getWidth() / 5;
    float buttonHeight = Gdx.graphics.getHeight() / 9;
    float buttonX = Gdx.graphics.getWidth() / 2 - buttonWidth / 2;

    float selectX = buttonX;
    float selectY = buttonHeight * 7;
    float tutorialX = buttonX;
    float row1Y = buttonHeight * 5;
    float row2Y = buttonHeight * 3;
    float quitX = buttonX;
    float quitY = buttonHeight;

    public static String mapSelect;

    public LevelSelect(Settings game) {
        this.game = game;
        shapeRenderer = new ShapeRenderer();
        batch = new SpriteBatch();
        selectText = new Texture("Text/selectText.png");
        tutorialText = new Texture("Text/tutorialText.png");

        level1Text = new Texture("Text/level1Text.png");
        level2Text = new Texture("Text/level2Text.png");
        level3Text = new Texture("Text/level3Text.png");
        level4Text = new Texture("Text/level4Text.png");
        level5Text = new Texture("Text/level5Text.png");

        quitText = new Texture("Text/quitText.png");
        sound = Gdx.audio.newSound(Gdx.files.internal("test/core/assets/SoundEffects/Menu1.wav"));
    }

    @Override
    public void show(){
        Gdx.input.setInputProcessor(new InputAdapter() {
            @Override
            public boolean touchDown(int screenX, int screenY, int pointer, int button) {
                cursorX = Gdx.input.getX();
                cursorY = Gdx.graphics.getHeight() - Gdx.input.getY();
                // Tutorial Button
                if (cursorX >=  tutorialX - buttonWidth * 1.5f && cursorX <=  tutorialX - buttonWidth * 1.5f + buttonWidth) {
                    if (cursorY >= row1Y && cursorY <= row1Y + buttonHeight) {
                        if (Gdx.input.isTouched()) {
                            sound.play(0.25f);
                            mapSelect = "Tutorial(MidPoint)";
                            game.setScreen(new Main(game));
                        }
                    }
                }
                // Level 1 Button
                if (cursorX >=  tutorialX && cursorX <= tutorialX + buttonWidth) {
                    if (cursorY >= row1Y && cursorY <= row1Y + buttonHeight) {
                        if (Gdx.input.isTouched()) {
                            sound.play(0.25f);
                            mapSelect = "";
                            game.setScreen(new Main(game));
                        }
                    }
                }
                // Level 2 Button
                if (cursorX >=  tutorialX + buttonWidth * 1.5f && cursorX <=  tutorialX + buttonWidth * 1.5f + buttonWidth) {
                    if (cursorY >= row1Y && cursorY <= row1Y + buttonHeight) {
                        if (Gdx.input.isTouched()) {
                            sound.play(0.25f);
                            mapSelect = "";
                            game.setScreen(new Main(game));
                        }
                    }
                }
                // Level 3 Button
                if (cursorX >=  tutorialX - buttonWidth * 1.5f && cursorX <=  tutorialX - buttonWidth * 1.5f + buttonWidth) {
                    if (cursorY >= row2Y && cursorY <= row2Y + buttonHeight) {
                        if (Gdx.input.isTouched()) {
                            sound.play(0.25f);
                            mapSelect = "";
                            game.setScreen(new Main(game));
                        }
                    }
                }
                // Level 4 Button
                if (cursorX >=  tutorialX && cursorX <= tutorialX + buttonWidth) {
                    if (cursorY >= row2Y && cursorY <= row2Y + buttonHeight) {
                        if (Gdx.input.isTouched()) {
                            sound.play(0.25f);
                            mapSelect = "";
                            game.setScreen(new Main(game));
                        }
                    }
                }
                // Level 5 Button
                if (cursorX >=  tutorialX + buttonWidth * 1.5f && cursorX <=  tutorialX + buttonWidth * 1.5f + buttonWidth) {
                    if (cursorY >= row1Y && cursorY <= row1Y + buttonHeight) {
                        if (Gdx.input.isTouched()) {
                            sound.play(0.25f);
                            mapSelect = "";
                            Settings.music.stop();
                            game.setScreen(new Main(game));
                        }
                    }
                }
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
        batch.draw(selectText, selectX, selectY, buttonWidth, buttonHeight);

        batch.draw(tutorialText, tutorialX - buttonWidth * 1.5f, row1Y, buttonWidth, buttonHeight);
        batch.draw(level1Text, tutorialX, row1Y, buttonWidth, buttonHeight);
        batch.draw(level2Text, tutorialX + buttonWidth * 1.5f, row1Y, buttonWidth, buttonHeight);

        batch.draw(level3Text, tutorialX - buttonWidth * 1.5f, row2Y, buttonWidth, buttonHeight);
        batch.draw(level4Text, tutorialX, row2Y, buttonWidth, buttonHeight);
        batch.draw(level5Text, tutorialX + buttonWidth * 1.5f, row2Y, buttonWidth, buttonHeight);

        batch.draw(quitText, quitX, quitY, buttonWidth, buttonHeight);
        batch.end();

    }

    @Override
    public void hide(){
        Gdx.input.setInputProcessor(null);
    }
}

