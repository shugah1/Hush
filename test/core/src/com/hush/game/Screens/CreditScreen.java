package com.hush.game.Screens;

// Imports libraries
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputAdapter;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.hush.game.Main;
import com.hush.game.UI.Settings;

// Credit Screen Class
public class CreditScreen extends ScreenAdapter {
    // Initializes Variables
    Settings game;
    SpriteBatch batch;
    Texture testBackground;
    Texture congratulationsText;
    Texture finalImage;
    Texture thanksText;
    Texture continueText;
    Sound sound;
    int cursorX;
    int cursorY;

    // Sets button variables to scale with screen
    float buttonWidth = Gdx.graphics.getWidth() / 5f;
    float buttonHeight = Gdx.graphics.getHeight() / 9f;
    float buttonX = Gdx.graphics.getWidth() / 2f - buttonWidth / 2f;
    float continueY = buttonHeight;

    public CreditScreen(Settings game) {
        // Assigns Variables
        this.game = game;
        batch = new SpriteBatch();
        testBackground = new Texture("testBackground");

        // Assigns song and volume
        sound = Gdx.audio.newSound(Gdx.files.internal("test/core/assets/SoundEffects/Menu1.wav"));

        // Text Variables
        congratulationsText = new Texture("Text/congratulationsText.png");
        finalImage = new Texture("finalImage.png");
        thanksText = new Texture("Text/thanksText.png");
        continueText = new Texture("Text/continueText.png");
    }

    @Override
    public void show() {
        // Credit Screen Input Check
        Gdx.input.setInputProcessor(new InputAdapter() {
            @Override
            public boolean touchDown(int screenX, int screenY, int pointer, int button) {
                cursorX = Gdx.input.getX();
                cursorY = Gdx.graphics.getHeight() - Gdx.input.getY();
                // Continue Button
                if (cursorX > buttonX && cursorX < buttonX + buttonWidth) {
                    if (cursorY > continueY && cursorY < continueY + buttonHeight) {
                        if (Gdx.input.isTouched()) {
                            sound.play(0.25f);
                            game.setScreen(new WinScreen(game));
                        }
                    }
                }
                return true;
            }
        });
    }

    @Override
    public void render(float delta) {
        // Renders Credits Screen
        Gdx.gl.glClearColor(0.25f, 0.25f, 0.25f, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        batch.begin();
        batch.draw(testBackground, 0, 0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        batch.draw(congratulationsText, buttonX * 0.9f, buttonHeight * 6.5f, buttonWidth * 1.5f, buttonHeight * 2);
        batch.draw(finalImage, buttonX, buttonHeight * 4, buttonWidth, buttonHeight * 2f);
        batch.draw(thanksText, buttonX * 0.9f, buttonHeight * 2, buttonWidth * 1.5f, buttonHeight * 1.5f);
        batch.draw(continueText, buttonX, continueY, buttonWidth, buttonHeight);
        batch.end();
    }

    @Override
    public void hide() {
        Gdx.input.setInputProcessor(null);
    }
}