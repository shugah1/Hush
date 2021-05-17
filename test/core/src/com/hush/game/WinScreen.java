package com.hush.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputAdapter;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.hush.game.UI.Settings;

public class WinScreen extends ScreenAdapter {
    Settings game;
    SpriteBatch batch;
    Texture endText;
    Texture restartText;
    Texture returnText;
    Sound sound;

    int cursorX;
    int cursorY;
    int winX = 660;
    int winY = 750;
    int restartX = 810;
    int restartY = 550;
    int returnX = 810;
    int returnY = 350;

    public WinScreen(Settings game) {
        this.game = game;
        batch = new SpriteBatch();
        endText = new Texture("Text/winText.png");
        restartText = new Texture("Text/restartText.png");
        returnText = new Texture("Text/returnText.png");
        sound = Gdx.audio.newSound(Gdx.files.internal("Menu1.wav"));
    }

    @Override
    public void show() {
        Main.gameObject.clear();
        Gdx.input.setInputProcessor(new InputAdapter() {
            @Override
            public boolean touchDown(int screenX, int screenY, int pointer, int button) {
                cursorX = Gdx.input.getX();
                cursorY = Gdx.graphics.getHeight() - Gdx.input.getY();
                if (cursorX > restartX && cursorX < restartX + 300) {
                    if (cursorY > restartY && cursorY < restartY + 100) {
                        if (Gdx.input.isTouched()) {
                            sound.play(0.25f);
                            game.setScreen(new Main(game));
                        }
                    }
                }
                if (cursorX > returnX && cursorX < returnX + 300) {
                    if (cursorY > returnY && cursorY < returnY + 100) {
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
        batch.draw(endText, winX, winY, 600, 200);
        batch.draw(restartText, restartX, restartY, 300, 100);
        batch.draw(returnText, returnX, returnY, 300, 100);
        batch.end();

    }

    @Override
    public void hide() {
        Gdx.input.setInputProcessor(null);
    }
}