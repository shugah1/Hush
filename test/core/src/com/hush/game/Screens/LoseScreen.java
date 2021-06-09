package com.hush.game.Screens;

// Imports Variables
import ca.error404.bytefyte.constants.Globals;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputAdapter;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.hush.game.Main;
import com.hush.game.UI.HUD;
import com.hush.game.UI.Settings;
import org.ini4j.Wini;

import java.io.File;
import java.io.IOException;

// Lose Screen Class
public class LoseScreen extends ScreenAdapter {
    // Initializes variables
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

    // Formats score time stamps
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
        // Assigns variables
        this.game = game;
        batch = new SpriteBatch();
        font = new BitmapFont();
        sound = Gdx.audio.newSound(Gdx.files.internal("SoundEffects/Menu1.wav"));

        // Text Variables
        testBackground = new Texture(("another.png"));
        endText = new Texture("Text/loseText.png");
        scoreText = new Texture(("Text/scoreText.png"));
        oldHighScoreText = new Texture("Text/oldHighScoreText.png");
        restartText = new Texture("Text/restartText.png");
        returnText = new Texture("Text/returnText.png");

        // Initializes Free Typer and assigns parameters
        FreeTypeFontGenerator generator = new FreeTypeFontGenerator(Gdx.files.internal("Cyberverse Condensed Bold Italic.ttf"));
        FreeTypeFontGenerator.FreeTypeFontParameter parameter = new FreeTypeFontGenerator.FreeTypeFontParameter();
        parameter.size = (int)buttonHeight; // font size
        font = generator.generateFont(parameter);
        font.setColor(0f, 104f, 255f, 1f);
        generator.dispose(); // avoid memory leaks, important
    }

    @Override
    public void show() {
        // Lose Screen Input Check
        Gdx.input.setInputProcessor(new InputAdapter() {
            @Override
            public boolean touchDown(int screenX, int screenY, int pointer, int button) {
                cursorX = Gdx.input.getX();
                cursorY = Gdx.graphics.getHeight() - Gdx.input.getY();

                // Restart Button
                if (cursorX > buttonX && cursorX < buttonX + buttonWidth) {
                    if (cursorY > restartY && cursorY < restartY + buttonHeight) {
                        if (Gdx.input.isTouched()) {
                            sound.play(0.25f);
                            game.setScreen(new Main(game));
                        }
                    }
                }

                // Return Button
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
        //Loops song
        if (game.music.getPosition() >= game.songLoopEnd) {
            game.music.setPosition((float) (game.music.getPosition() - (game.songLoopEnd - game.songLoopStart)));
        }

        // Set game.music volume
        if (Gdx.input.isKeyJustPressed(Input.Keys.UP)) {
            Settings.musicVolume = Settings.musicVolume < 10 ? Settings.musicVolume + 1 : 10;

            // Writes data to the settings file
            File settings = new File(Globals.workingDirectory + "settings.ini");
            game.music.setVolume(Settings.musicVolume / 10f);

            try {
                Wini ini = new Wini(settings);
                ini.add("Settings", "music volume", Settings.musicVolume);
                ini.store();
            } catch (IOException e) {
                e.printStackTrace();
            }
        } else if (Gdx.input.isKeyJustPressed(Input.Keys.DOWN)) {
            Settings.musicVolume = Settings.musicVolume > 0 ? Settings.musicVolume - 1 : 0;
            game.music.setVolume(Settings.musicVolume / 10f);

            // Writes data to the settings file
            File settings = new File(Globals.workingDirectory + "settings.ini");

            try {
                Wini ini = new Wini(settings);
                ini.add("Settings", "music volume", Settings.musicVolume);
                ini.store();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        // Renders Lose Screen
        Gdx.gl.glClearColor(0.25f, 0.25f, 0.25f, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        batch.begin();
        batch.draw(testBackground, 0, 0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        batch.draw(endText, buttonX, endY, buttonWidth, buttonHeight);
        batch.draw(restartText, buttonX, buttonHeight * 2.25f, buttonWidth, buttonHeight);
        batch.draw(returnText, buttonX, returnY, buttonWidth, buttonHeight);
        batch.draw(scoreText, buttonX - buttonWidth, scoreY, buttonWidth, buttonHeight);
        font.draw(batch, minutes + seconds, buttonX + buttonWidth * 0.75f, scoreY + buttonHeight * 0.8f);
        if (Settings.highScore.get(LevelSelect.mapSelect) != Integer.MAX_VALUE) {
            batch.draw(oldHighScoreText, buttonX - buttonWidth, buttonHeight * 3.5f, buttonWidth, buttonHeight);
            font.draw(batch, hsMinutes + hsSeconds, buttonX + buttonWidth * 0.75f, buttonHeight * 3.5f + buttonHeight * 0.8f);
        }
        batch.end();
    }

    @Override
    public void hide() {
        Gdx.input.setInputProcessor(null);
    }
}