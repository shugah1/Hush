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
    int selectX = 710;
    int selectY = 750;
    int tutorialX = 810;
    int tutorialY = 490;
    int quitX = 810;
    int quitY = 150;

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
                if (cursorX >= tutorialX && cursorX <= tutorialX + 300) {
                    if (cursorY >= tutorialY && cursorY <= tutorialY + 100) {
                        if (Gdx.input.isTouched()) {
                            sound.play(0.25f);
                            game.setScreen(new Main(game));
                        }
                    }
                }
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
        batch.draw(selectText, selectX, selectY, 500, 200);
        batch.draw(tutorialText, tutorialX, tutorialY, 300, 100);
        batch.draw(quitText, quitX, quitY, 300, 100);
        batch.end();

    }

    @Override
    public void hide(){
        Gdx.input.setInputProcessor(null);
    }
}

