package com.hush.game.Screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputAdapter;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.hush.game.Entities.Player;
import com.hush.game.Main;
import com.hush.game.UI.HUD;
import com.hush.game.UI.Settings;

public class LoseScreen extends ScreenAdapter {
    Settings game;
    SpriteBatch batch;
    Texture testBackground;
    Texture endText;
    Texture scoreText;
    Texture oldHighScoreText;
    Texture restartText;
    Texture returnText;
    BitmapFont font;
    Sound sound;

    String minutes = String.format("%02d : ", HUD.worldTimer / 60);
    String seconds = String.format("%02d s", HUD.worldTimer % 60);
    String hsMinutes = String.format("%02d : ", Settings.highScore.get(LevelSelect.mapSelect) / 60);
    String hsSeconds = String.format("%02d s", Settings.highScore.get(LevelSelect.mapSelect) % 60);

    int cursorX;
    int cursorY;
    float buttonWidth = Gdx.graphics.getWidth() / 5f;
    float buttonHeight = Gdx.graphics.getHeight() / 9f;
    float buttonX = Gdx.graphics.getWidth() / 2f - buttonWidth / 2;

    float endY = buttonHeight * 7;
    float scoreY = buttonHeight * 5;
    float restartY = buttonHeight * 2.25f;
    float returnY = buttonHeight;

    public LoseScreen(Settings game) {
        this.game = game;
        batch = new SpriteBatch();
        font = new BitmapFont();
        testBackground = new Texture(("TestBackground"));
        endText = new Texture("Text/loseText.png");
        scoreText = new Texture(("Text/scoreText.png"));
        oldHighScoreText = new Texture("Text/oldHighScoreText.png");
        restartText = new Texture("Text/restartText.png");
        returnText = new Texture("Text/returnText.png");

        sound = Gdx.audio.newSound(Gdx.files.internal("test/core/assets/SoundEffects/Menu1.wav"));

        FreeTypeFontGenerator generator = new FreeTypeFontGenerator(Gdx.files.internal("Cyberverse Condensed Bold Italic.ttf"));
        FreeTypeFontGenerator.FreeTypeFontParameter parameter = new FreeTypeFontGenerator.FreeTypeFontParameter();
        parameter.size = (int)buttonHeight; // font size
        font = generator.generateFont(parameter);
        font.setColor(0f, 104f, 255f, 1f);
        generator.dispose(); // avoid memory leaks, important
    }

    @Override
    public void show() {
        Player.pDead = false;
        Gdx.input.setInputProcessor(new InputAdapter() {
            @Override
            public boolean touchDown(int screenX, int screenY, int pointer, int button) {
                cursorX = Gdx.input.getX();
                cursorY = Gdx.graphics.getHeight() - Gdx.input.getY();
                if (cursorX > buttonX && cursorX < buttonX + buttonWidth) {
                    if (cursorY > restartY && cursorY < restartY + buttonHeight) {
                        if (Gdx.input.isTouched()) {
                            sound.play(0.25f);
                            game.setScreen(new Main(game));
                        }
                    }
                }
                if (cursorX > buttonX && cursorX < buttonX + buttonWidth) {
                    if (cursorY > returnY && cursorY < returnY + buttonHeight) {
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
        batch.draw(testBackground, 0, 0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        batch.draw(endText, buttonX, endY, buttonWidth, buttonHeight);
        batch.draw(scoreText, buttonX - buttonWidth, scoreY, buttonWidth, buttonHeight);
        batch.draw(restartText, buttonX, buttonHeight * 2.25f, buttonWidth, buttonHeight);
        batch.draw(returnText, buttonX, returnY, buttonWidth, buttonHeight);

        font.draw(batch, minutes + seconds, buttonX + buttonWidth * 0.75f, scoreY + buttonHeight * 0.8f);

        batch.draw(oldHighScoreText, buttonX - buttonWidth, buttonHeight * 3.5f, buttonWidth, buttonHeight);
        font.draw(batch, hsMinutes + hsSeconds, buttonX + buttonWidth * 0.75f, buttonHeight * 3.5f + buttonHeight * 0.8f);
        batch.end();

    }

    @Override
    public void hide() {
        Gdx.input.setInputProcessor(null);
    }
}