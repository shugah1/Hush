package com.hush.game;

import com.badlogic.gdx.*;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.hush.game.UI.Settings;

public class LevelSelect extends ScreenAdapter {
    Settings game;
    ShapeRenderer shapeRenderer;
    SpriteBatch batch;
    Texture selectText;
    Texture tutorialText;
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
    float tutorialY = buttonHeight * 5;
    float quitX = buttonX;
    float quitY = buttonHeight;

    public LevelSelect(Settings game) {
        this.game = game;
        shapeRenderer = new ShapeRenderer();
        batch = new SpriteBatch();
        selectText = new Texture("Text/selectText.png");
        tutorialText = new Texture("Text/tutorialText.png");
        quitText = new Texture("Text/quitText.png");
        sound = Gdx.audio.newSound(Gdx.files.internal("Menu1.wav"));
    }

    @Override
    public void show(){
        Gdx.input.setInputProcessor(new InputAdapter() {
            @Override
            public boolean touchDown(int screenX, int screenY, int pointer, int button) {
                cursorX = Gdx.input.getX();
                cursorY = Gdx.graphics.getHeight() - Gdx.input.getY();
                if (cursorX >= tutorialX && cursorX <= tutorialX + buttonWidth) {
                    if (cursorY >= tutorialY && cursorY <= tutorialY + buttonHeight) {
                        if (Gdx.input.isTouched()) {
                            sound.play(0.25f);
                            game.setScreen(new Main(game));
                        }
                    }
                }
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
        batch.draw(tutorialText, tutorialX, tutorialY, buttonWidth, buttonHeight);
        batch.draw(quitText, quitX, quitY, buttonWidth, buttonHeight);
        batch.end();

    }

    @Override
    public void hide(){
        Gdx.input.setInputProcessor(null);
    }
}

