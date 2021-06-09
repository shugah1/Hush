package com.hush.game.Screens;

// Imports libraries
import ca.error404.bytefyte.constants.Globals;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.InputAdapter;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.hush.game.Main;
import com.hush.game.UI.Settings;
import org.ini4j.Wini;

import java.io.File;
import java.io.IOException;

// Level Select Screen Class
public class LevelSelect extends ScreenAdapter {
    // Initializes variables
    Settings game;
    ShapeRenderer shapeRenderer;
    SpriteBatch batch;
    Texture testBackground;
    Texture selectText;
    Texture tutorialText;
    Texture level1Text;
    Texture level2Text;
    Texture level3Text;
    Texture level4Text;
    Texture level5Text;
    Texture quitText;
    Sound sound;
    BitmapFont font;

    int cursorX;
    int cursorY;
    float buttonWidth = Gdx.graphics.getWidth() / 5f;
    float buttonHeight = Gdx.graphics.getHeight() / 9f;
    float buttonX = Gdx.graphics.getWidth() / 2f - buttonWidth / 2;
    float selectX = buttonX;
    float selectY = buttonHeight * 7;
    float row1Y = buttonHeight * 5;
    float row2Y = buttonHeight * 3;
    float quitX = buttonX;
    float quitY = buttonHeight;

    public static String mapSelect;

    public LevelSelect(Settings game) {
        // Assigns variables
        this.game = game;
        shapeRenderer = new ShapeRenderer();
        batch = new SpriteBatch();
        sound = Gdx.audio.newSound(Gdx.files.internal("test/core/assets/SoundEffects/Menu1.wav"));

        // Text Variables
        testBackground = new Texture(("test/core/assets/bg.png"));
        selectText = new Texture("Text/selectText.png");
        tutorialText = new Texture("Text/tutorialText.png");
        level1Text = new Texture("Text/level1Text.png");
        level2Text = new Texture("Text/level2Text.png");
        level3Text = new Texture("Text/level3Text.png");
        level4Text = new Texture("Text/level4Text.png");
        level5Text = new Texture("Text/level5Text.png");
        quitText = new Texture("Text/quitText.png");

        // Loads Save Data and Assigns Completion Variables
        File settings = new File(Globals.workingDirectory + "settings.ini");
        try {
            Wini ini = new Wini(settings);
            Settings.completion.put("RealTutorial", Integer.parseInt(ini.get("Completion", "RealTutorial")));
            Settings.completion.put("Level 1", Integer.parseInt(ini.get("Completion", "Level 1")));
            Settings.completion.put("Level 2", Integer.parseInt(ini.get("Completion", "Level 2")));
            Settings.completion.put("Level 3", Integer.parseInt(ini.get("Completion", "Level 3")));
            Settings.completion.put("Level 4", Integer.parseInt(ini.get("Completion", "Level 4")));
            Settings.completion.put("Level 5", Integer.parseInt(ini.get("Completion", "Level 5")));

            Settings.highScore.put("RealTutorial", Integer.parseInt(ini.get("High Score", "RealTutorial")));
            Settings.highScore.put("Level 1", Integer.parseInt(ini.get("High Score", "Level 1")));
            Settings.highScore.put("Level 2", Integer.parseInt(ini.get("High Score", "Level 2")));
            Settings.highScore.put("Level 3", Integer.parseInt(ini.get("High Score", "Level 3")));
            Settings.highScore.put("Level 4", Integer.parseInt(ini.get("High Score", "Level 4")));
            Settings.highScore.put("Level 5", Integer.parseInt(ini.get("High Score", "Level 5")));
        } catch (Exception ignored) {

        }

        // Initializes Font Typer and assigns variables
        FreeTypeFontGenerator generator = new FreeTypeFontGenerator(Gdx.files.internal("Cyberverse Condensed Bold Italic.ttf"));
        FreeTypeFontGenerator.FreeTypeFontParameter parameter = new FreeTypeFontGenerator.FreeTypeFontParameter();
        parameter.size = (int) buttonHeight;
        font = generator.generateFont(parameter);
        font.setColor(0f, 104f, 255f, 1f);
    }

    @Override
    public void show(){
        // LevelSelect Input Check
        Gdx.input.setInputProcessor(new InputAdapter() {
            @Override
            public boolean touchDown(int screenX, int screenY, int pointer, int button) {
                cursorX = Gdx.input.getX();
                cursorY = Gdx.graphics.getHeight() - Gdx.input.getY();

                // Tutorial Button
                if (cursorX >=  buttonX - buttonWidth * 1.5f && cursorX <=  buttonX - buttonWidth * 1.5f + buttonWidth) {
                    if (cursorY >= row1Y && cursorY <= row1Y + buttonHeight) {
                        if (Gdx.input.isTouched()) {
                            sound.play(0.25f);
                            mapSelect = "RealTutorial";
                            Settings.music.stop();
                            game.setScreen(new Main(game));
                        }
                    }
                }

                // Level 1 Button and Tutorial Completion Check
                if (Settings.completion.get("RealTutorial") == 1) {
                    if (cursorX >= buttonX && cursorX <= buttonX + buttonWidth) {
                        if (cursorY >= row1Y && cursorY <= row1Y + buttonHeight) {
                            if (Gdx.input.isTouched()) {
                                sound.play(0.25f);
                                mapSelect = "Level 1";
                                Settings.music.stop();
                                game.setScreen(new Main(game));
                            }
                        }
                    }
                }

                // Level 2 Button and Level 1 Completion Check
                if (Settings.completion.get("Level 1") == 1) {
                    if (cursorX >= buttonX + buttonWidth * 1.5f && cursorX <= buttonX + buttonWidth * 1.5f + buttonWidth) {
                        if (cursorY >= row1Y && cursorY <= row1Y + buttonHeight) {
                            if (Gdx.input.isTouched()) {
                                sound.play(0.25f);
                                mapSelect = "Level 2";
                                Settings.music.stop();
                                game.setScreen(new Main(game));
                            }
                        }
                    }
                }

                // Level 3 Button and Level 2 Completion Check
                if (Settings.completion.get("Level 2") == 1) {
                    if (cursorX >= buttonX - buttonWidth * 1.5f && cursorX <= buttonX - buttonWidth * 1.5f + buttonWidth) {
                        if (cursorY >= row2Y && cursorY <= row2Y + buttonHeight) {
                            if (Gdx.input.isTouched()) {
                                sound.play(0.25f);
                                mapSelect = "Level 3";
                                Settings.music.stop();
                                game.setScreen(new Main(game));
                            }
                        }
                    }
                }

                // Level 4 Button and Level 3 Completion Check
                if (Settings.completion.get("Level 3") == 1) {
                    if (cursorX >= buttonX && cursorX <= buttonX + buttonWidth) {
                        if (cursorY >= row2Y && cursorY <= row2Y + buttonHeight) {
                            if (Gdx.input.isTouched()) {
                                sound.play(0.25f);
                                mapSelect = "Level 4";
                                Settings.music.stop();
                                game.setScreen(new Main(game));
                            }
                        }
                    }
                }

                // Level 5 Button and Level 4 Completion Check
                if (Settings.completion.get("Level 4") == 1) {
                    if (cursorX >= buttonX + buttonWidth * 1.5f && cursorX <= buttonX + buttonWidth * 1.5f + buttonWidth) {
                        if (cursorY >= row2Y && cursorY <= row2Y + buttonHeight) {
                            if (Gdx.input.isTouched()) {
                                sound.play(0.25f);
                                mapSelect = "Level 5";
                                Settings.music.stop();
                                game.setScreen(new Main(game));
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

        // Renders Level Select Menu
        Gdx.gl.glClearColor(0.25f, 0.25f, 0.25f, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        batch.begin();
        batch.draw(testBackground, 0, 0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        batch.draw(selectText, selectX, selectY, buttonWidth, buttonHeight);
        batch.draw(tutorialText, buttonX - buttonWidth * 1.5f, row1Y, buttonWidth, buttonHeight);

        if (Settings.completion.get("RealTutorial") == 1) {
            batch.draw(level1Text, buttonX, row1Y, buttonWidth, buttonHeight);
            String hsMinutes = String.format("%02d : ", Settings.highScore.get("RealTutorial") / 60);
            String hsSeconds = String.format("%02d", Settings.highScore.get("RealTutorial") % 60);
            font.draw(batch, hsMinutes + hsSeconds, buttonX * 0.25f, row1Y);
        }
        if (Settings.completion.get("Level 1") == 1) {
            batch.draw(level2Text, buttonX + buttonWidth * 1.5f, row1Y, buttonWidth, buttonHeight);
            String hsMinutes = String.format("%02d : ", Settings.highScore.get("Level 1") / 60);
            String hsSeconds = String.format("%02d", Settings.highScore.get("Level 1") % 60);
            font.draw(batch, hsMinutes + hsSeconds, buttonX, row1Y);
        }
        if (Settings.completion.get("Level 2") == 1) {
            batch.draw(level3Text, buttonX - buttonWidth * 1.5f, row2Y, buttonWidth, buttonHeight);
            String hsMinutes = String.format("%02d : ", Settings.highScore.get("Level 2") / 60);
            String hsSeconds = String.format("%02d", Settings.highScore.get("Level 2") % 60);
            font.draw(batch, hsMinutes + hsSeconds, buttonX * 1.75f, row1Y);
        }
        if (Settings.completion.get("Level 3") == 1) {
            batch.draw(level4Text, buttonX, row2Y, buttonWidth, buttonHeight);
            String hsMinutes = String.format("%02d : ", Settings.highScore.get("Level 3") / 60);
            String hsSeconds = String.format("%02d", Settings.highScore.get("Level 3") % 60);
            font.draw(batch, hsMinutes + hsSeconds, buttonX * 0.25f, row2Y);
        }
        if (Settings.completion.get("Level 4") == 1) {
            batch.draw(level5Text, buttonX + buttonWidth * 1.5f, row2Y, buttonWidth, buttonHeight);
            String hsMinutes = String.format("%02d : ", Settings.highScore.get("Level 4") / 60);
            String hsSeconds = String.format("%02d", Settings.highScore.get("Level 4") % 60);
            font.draw(batch, hsMinutes + hsSeconds, buttonX, row2Y);
        }
        if (Settings.completion.get("Level 5") == 1) {
            batch.draw(level5Text, buttonX + buttonWidth * 1.5f, row2Y, buttonWidth, buttonHeight);
            String hsMinutes = String.format("%02d : ", Settings.highScore.get("Level 5") / 60);
            String hsSeconds = String.format("%02d", Settings.highScore.get("Level 5") % 60);
            font.draw(batch, hsMinutes + hsSeconds, buttonX * 1.75f, row2Y);
        }

        batch.draw(quitText, quitX, quitY, buttonWidth, buttonHeight);
        batch.end();
    }

    @Override
    public void hide(){
        Gdx.input.setInputProcessor(null);
    }
}

