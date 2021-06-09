package com.hush.game.Screens;

// Imports libraries
import com.badlogic.gdx.Gdx;
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
import com.hush.game.constants.Globals;
import org.ini4j.Wini;

import java.io.File;
import java.io.IOException;

// Win Screen Class
public class WinScreen extends ScreenAdapter {
    // Initializes variables
    Settings game;
    SpriteBatch batch;
    Texture testBackground;
    Texture endText;
    Texture scoreText;
    Texture newHighScoreText;
    Texture oldHighScoreText;
    Texture nextLevelText;
    Texture restartText;
    Texture returnText;
    BitmapFont font;
    Sound sound;

    // Formats timestamp scores
    String minutes = String.format("%02d : ", HUD.worldTimer / 60);
    String seconds = String.format("%02d s", HUD.worldTimer % 60);
    String hsMinutes = String.format("%02d : ", Settings.highScore.get(LevelSelect.mapSelect) / 60);
    String hsSeconds = String.format("%02d s", Settings.highScore.get(LevelSelect.mapSelect) % 60);

    int cursorX;
    int cursorY;
    float buttonWidth = Gdx.graphics.getWidth() / 5f;
    float buttonHeight = Gdx.graphics.getHeight() / 9f;
    float buttonX = Gdx.graphics.getWidth() / 2f - buttonWidth / 2f;

    boolean newHighScore = false;
    float winY = buttonHeight * 7;
    float scoreY = buttonHeight * 5;
    float nextY = buttonHeight * 2.5f;
    float restartY = buttonHeight * 1.25f;
    float returnY = 0;

    public WinScreen(Settings game) {
        // Assign Variables
        this.game = game;
        batch = new SpriteBatch();
        testBackground = new Texture(("TestBackground"));
        sound = Gdx.audio.newSound(Gdx.files.internal("test/core/assets/SoundEffects/Menu1.wav"));

        // Text Variables
        endText = new Texture("Text/winText.png");
        scoreText = new Texture(("Text/scoreText.png"));
        newHighScoreText = new Texture("Text/newHighScoreText.png");
        oldHighScoreText = new Texture("Text/oldHighScoreText.png");
        nextLevelText = new Texture("Text/nextLevelText.png");
        restartText = new Texture("Text/restartText.png");
        returnText = new Texture("Text/returnText.png");

        // Initializes Free Typer and assigns parameters
        FreeTypeFontGenerator generator = new FreeTypeFontGenerator(Gdx.files.internal("Cyberverse Condensed Bold Italic.ttf"));
        FreeTypeFontGenerator.FreeTypeFontParameter parameter = new FreeTypeFontGenerator.FreeTypeFontParameter();
        parameter.size = (int) buttonHeight;
        font = generator.generateFont(parameter);
        font.setColor(0f, 104f, 255f, 1f);

        // Checks and assigns New High Score
        System.out.println(HUD.worldTimer);
        if (HUD.worldTimer < Settings.highScore.get(LevelSelect.mapSelect)) {
            Settings.highScore.put(LevelSelect.mapSelect, HUD.worldTimer);
            newHighScore = true;

            // Writes High Score to the settings file
            File settings = new File(Globals.workingDirectory + "settings.ini");
            try {
                Wini ini = new Wini(settings);
                ini.add("High Score", LevelSelect.mapSelect, Settings.highScore.get(LevelSelect.mapSelect));
                ini.store();
                System.out.println("ooga booga");
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        // Writes Completion to the settings file
        Settings.completion.put(LevelSelect.mapSelect, 1);
        File settings = new File(Globals.workingDirectory + "settings.ini");
        try {
            Wini ini = new Wini(settings);
            ini.add("Completion", LevelSelect.mapSelect, 1);
            ini.store();
            System.out.println("ooga booga");
        } catch (IOException e) {
                e.printStackTrace();
        }

    }

    @Override
    public void show() {
        // Win Screen Input Check
        Gdx.input.setInputProcessor(new InputAdapter() {
            @Override
            public boolean touchDown(int screenX, int screenY, int pointer, int button) {
                cursorX = Gdx.input.getX();
                cursorY = Gdx.graphics.getHeight() - Gdx.input.getY();
                // Next Level Button
                if (!LevelSelect.mapSelect.equals("Level 5")) {
                    if (cursorX > buttonX && cursorX < buttonX + buttonWidth) {
                        if (cursorY > nextY && cursorY < nextY + buttonHeight) {
                            if (Gdx.input.isTouched()) {
                                switch (LevelSelect.mapSelect) {
                                    case "Tutorial":
                                        LevelSelect.mapSelect = "Level 1";
                                        break;
                                    case "Level 1":
                                        LevelSelect.mapSelect = "Level 2";
                                        break;
                                    case "Level 2":
                                        LevelSelect.mapSelect = "Level 3";
                                        break;
                                    case "Level 3":
                                        LevelSelect.mapSelect = "Level 4";
                                        break;
                                    case "Level 4":
                                        LevelSelect.mapSelect = "Level 5";
                                        break;
                                }
                                sound.play(0.25f);
                                game.setScreen(new Main(game));
                            }
                        }
                    }
                }

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
        // Renders Win Screen
        Gdx.gl.glClearColor(0.25f, 0.25f, 0.25f, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        batch.begin();
        batch.draw(testBackground, 0, 0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        batch.draw(endText, buttonX, winY, buttonWidth, buttonHeight);
        batch.draw(scoreText, buttonX - buttonWidth, scoreY, buttonWidth, buttonHeight);
        batch.draw(restartText, buttonX, restartY, buttonWidth, buttonHeight);
        batch.draw(returnText, buttonX, returnY, buttonWidth, buttonHeight);
        font.draw(batch, minutes + seconds, buttonX + buttonWidth * 0.75f, scoreY + buttonHeight * 0.8f);
        if (!LevelSelect.mapSelect.equals("Level 5")) {
            batch.draw(nextLevelText, buttonX, nextY, buttonWidth, buttonHeight);
        }

        // Checks and prints New High Score notification
        if (newHighScore) {
            batch.draw(newHighScoreText, buttonX, buttonHeight * 3.5f, buttonWidth, buttonHeight);
        }
        else {
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