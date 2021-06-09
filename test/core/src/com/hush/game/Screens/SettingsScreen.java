package com.hush.game.Screens;

// Imports libraries
import ca.error404.bytefyte.constants.Globals;
import com.badlogic.gdx.*;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;

import com.hush.game.UI.Settings;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import org.ini4j.Wini;

import java.io.File;
import java.io.IOException;

// Settings Screen Class
public class SettingsScreen extends ScreenAdapter {
    // Initializes variables
    Settings game;
    SpriteBatch batch;
    ShapeRenderer shapeRenderer;
    Texture testBackground;
    Texture settingsText;
    Texture audioText;
    Texture videoText;
    Texture settingsButtons;
    Texture quitText;
    Sound sound;
    BitmapFont font;

    int cursorX;
    int cursorY;
    float buttonWidth = Gdx.graphics.getWidth() / 5f;
    float buttonHeight = Gdx.graphics.getHeight() / 9f;
    float setWidth = buttonWidth * 1.25f;
    float buttonX = Gdx.graphics.getWidth() / 2f - buttonWidth / 2f;
    int displayVolume = Settings.musicVolume;

    float settingsX = buttonX;
    float settingsY = buttonHeight * 7;
    float setX = buttonX + buttonWidth * 0.75f;
    float audioY = buttonHeight * 5;
    float sliderY = buttonHeight * 5;
    float quitX = buttonX;
    float quitY = buttonHeight;

    public SettingsScreen(Settings game) {
        // Assigns variables
        this.game = game;
        batch = new SpriteBatch();
        shapeRenderer = new ShapeRenderer();
        sound = Gdx.audio.newSound(Gdx.files.internal("test/core/assets/SoundEffects/Menu1.wav"));

        // Text Variables
        testBackground = new Texture(("test/core/assets/main.png"));
        settingsText = new Texture("Text/settingsText.png");
        audioText = new Texture("Text/audioText.png");
        videoText = new Texture("Text/videoText.png");
        settingsButtons = new Texture("Text/settingsButtons.png");
        quitText = new Texture("Text/quitText.png");

        FreeTypeFontGenerator generator = new FreeTypeFontGenerator(Gdx.files.internal("Cyberverse Condensed Bold Italic.ttf"));
        FreeTypeFontGenerator.FreeTypeFontParameter parameter = new FreeTypeFontGenerator.FreeTypeFontParameter();
        parameter.size = (int) buttonHeight;
        font = generator.generateFont(parameter);
        font.setColor(0f, 104f, 255f, 1f);
    }

    @Override
    public void show(){
        // Settings Screen Input Check
        Gdx.input.setInputProcessor(new InputAdapter() {
            @Override
            public boolean touchDown(int screenX, int screenY, int pointer, int button) {
                cursorX = Gdx.input.getX();
                cursorY = Gdx.graphics.getHeight() - Gdx.input.getY();

                // Audio Set Down Button
                if (cursorX > setX && cursorX < setX + setWidth / 2) {
                    if (cursorY > audioY && cursorY < audioY + buttonHeight) {
                        if (Gdx.input.isTouched()) {
                            if (displayVolume != 0) {
                                displayVolume--;
                                sound.play(0.25f);

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
                        }
                    }
                }

                // Audio Set Up Button
                if (cursorX > setX + setWidth / 2 && cursorX < setX + setWidth) {
                    if (cursorY > audioY && cursorY < audioY + buttonHeight) {
                        if (Gdx.input.isTouched()) {
                            if (displayVolume != 10) {
                                displayVolume++;
                                sound.play(0.25f);

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
                            }
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

        // Renders Settings Screen
        Gdx.gl.glClearColor(0.25f, 0.25f, 0.25f, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        batch.begin();
        batch.draw(testBackground, 0, 0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        batch.draw(settingsText, settingsX, settingsY, buttonWidth, buttonHeight);
        batch.draw(audioText, buttonX - buttonWidth, audioY, buttonWidth, buttonHeight);
        batch.draw(settingsButtons, setX, sliderY, buttonWidth * 1.25f, buttonHeight);
        batch.draw(quitText, quitX, quitY, buttonWidth, buttonHeight);

        font.draw(batch, Integer.toString(displayVolume), buttonX + buttonWidth * 1.3f, audioY + buttonHeight * 0.8f);
        batch.end();
    }

    @Override
    public void hide(){
        Gdx.input.setInputProcessor(null);
    }
}