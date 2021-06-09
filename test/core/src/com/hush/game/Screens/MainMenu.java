package com.hush.game.Screens;

// Imports libraries
import ca.error404.bytefyte.constants.Globals;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputAdapter;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.hush.game.UI.Settings;
import org.ini4j.Wini;

import java.io.File;
import java.io.IOException;

// Main Menu Screen Class
public class MainMenu extends ScreenAdapter {
    // Initializes variables
    Settings game;
    SpriteBatch batch;
    Texture testBackground;
    Texture titleText;
    Texture startText;
    Texture settingsText;
    Texture helpText;
    Texture quitText;
    Sound sound;

    int cursorX;
    int cursorY;
    float buttonWidth = Gdx.graphics.getWidth() / 5f;
    float buttonHeight = Gdx.graphics.getHeight() / 9f;
    float buttonX = Gdx.graphics.getWidth() / 2f - buttonWidth / 2;
    float titleY = buttonHeight * 7;
    float startY = buttonHeight * 5;
    float settingsY = buttonHeight * 3.5f;
    float helpY = buttonHeight * 2.25f;
    float quitY = buttonHeight;

    public MainMenu(Settings game) {
        // Initializes variables
        this.game = game;
        batch = new SpriteBatch();
        sound = Gdx.audio.newSound(Gdx.files.internal("test/core/assets/SoundEffects/Menu1.wav"));

        // Text Variables
        testBackground = new Texture(("test/core/assets/bg.png"));
        titleText = new Texture("Text/titleText.png");
        startText = new Texture("Text/startText.png");
        settingsText = new Texture("Text/settingsText.png");
        helpText = new Texture("Text/helpText.png");
        quitText = new Texture("Text/quitText.png");


    }

    @Override
    public void show(){
        // MainMenu Input Check
        if (!Settings.songName.equalsIgnoreCase("TitleTheme")) {
            Settings.music.stop();
            Settings.music = game.newSong("TitleTheme");
            Settings.music.setVolume(Settings.musicVolume / 10f);
            Settings.music.play();
        }

        Gdx.input.setInputProcessor(new InputAdapter() {
            @Override
            public boolean touchDown(int screenX, int screenY, int pointer, int button) {
                cursorX = Gdx.input.getX();
                cursorY = Gdx.graphics.getHeight() - Gdx.input.getY();

                // Start Button
                if (cursorX > buttonX && cursorX < buttonX + buttonWidth) {
                    if (cursorY > startY && cursorY < startY + buttonHeight) {
                        if (Gdx.input.isTouched()) {
                            sound.play(0.25f);
                            game.setScreen(new LevelSelect(game));
                        }
                    }
                }

                // Settings Button
                if (cursorX > buttonX && cursorX < buttonX + buttonWidth) {
                    if (cursorY > settingsY && cursorY < settingsY + buttonHeight) {
                        if (Gdx.input.isTouched()) {
                            sound.play(0.25f);
                            game.setScreen(new SettingsScreen(game));
                        }
                    }
                }

                // Help Button
                if (cursorX > buttonX && cursorX < buttonX + buttonWidth) {
                    if (cursorY > helpY && cursorY < helpY + buttonHeight) {
                        if (Gdx.input.isTouched()) {
                            sound.play(0.25f);
                            game.setScreen(new HelpScreen(game));
                        }
                    }
                }

                // Quit Button
                if (cursorX > buttonX && cursorX < buttonX + buttonWidth) {
                    if (cursorY > quitY && cursorY < quitY + buttonHeight) {
                        if (Gdx.input.isTouched()) {
                            sound.play(0.25f);
                            System.exit(0);
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

        // Renders Main Menu Screen
        Gdx.gl.glClearColor(0.25f, 0.25f, 0.25f, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        batch.begin();
        batch.draw(testBackground, 0, 0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        batch.draw(titleText, buttonX, titleY, buttonWidth, buttonHeight);
        batch.draw(startText, buttonX, startY, buttonWidth, buttonHeight);
        batch.draw(settingsText, buttonX, settingsY, buttonWidth, buttonHeight);
        batch.draw(helpText, buttonX, helpY, buttonWidth, buttonHeight);
        batch.draw(quitText, buttonX, quitY, buttonWidth, buttonHeight);
        batch.end();
    }

    @Override
    public void hide(){
        Gdx.input.setInputProcessor(null);
    }
}