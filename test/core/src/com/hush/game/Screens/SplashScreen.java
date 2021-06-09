package com.hush.game.Screens;

// Imports libraries
import ca.error404.bytefyte.constants.Globals;
import com.badlogic.gdx.*;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.hush.game.UI.Settings;
import org.ini4j.Wini;

import java.io.File;
import java.io.IOException;

// Splash Screen Class
public class SplashScreen extends ScreenAdapter {
    // Initializes and assigns variables
    Settings game;
    ShapeRenderer shapeRenderer;
    SpriteBatch batch;
    Texture testBackground;
    Texture titleText;
    Texture splashText;
    Texture namesText;
    Sound sound;

    int cursorX;
    int cursorY;

    // Sets button variables to scale with screen
    float buttonWidth = Gdx.graphics.getWidth() / 5f;
    float buttonHeight = Gdx.graphics.getHeight() / 9f;
    float buttonX = Gdx.graphics.getWidth() / 2f - buttonWidth / 2;
    float splashWidth = (Gdx.graphics.getWidth() / 5f) * 2;
    float splashX = Gdx.graphics.getWidth() / 2f - splashWidth / 2;
    float titleX = buttonX;
    float titleY = buttonHeight * 7;
    float splashY = buttonHeight;
    float namesWidth = Gdx.graphics.getWidth() / 6f;
    float namesHeight = Gdx.graphics.getHeight() / 4f;
    float namesX = Gdx.graphics.getWidth() - namesWidth;
    float namesY = Gdx.graphics.getHeight() - namesHeight;

    public SplashScreen(Settings game) {
        // Assigns variables
        this.game = game;
        testBackground = new Texture(("test/core/assets/TitleTheme.png"));
        titleText = new Texture("Text/titleText.png");
        splashText = new Texture("Text/splashText.png");
        namesText = new Texture("Text/namesText.png");
        shapeRenderer = new ShapeRenderer();
        batch = new SpriteBatch();

        // Assigns song and volume
        sound = Gdx.audio.newSound(Gdx.files.internal("test/core/assets/SoundEffects/Menu1.wav"));
        Settings.music = game.newSong("TitleTheme");
        Settings.music.setVolume(Settings.musicVolume / 10f);
        Settings.music.play();
    }

    @Override
    public void show(){
        // SplashScreen Input Check, assigns cursor position
        Gdx.input.setInputProcessor(new InputAdapter() {
            @Override
            public boolean touchDown(int screenX, int screenY, int pointer, int button) {
                cursorX = Gdx.input.getX();
                cursorY = Gdx.graphics.getHeight() - Gdx.input.getY();

                // Splash Screen Button, sends to Main Menu
                if (Gdx.input.isTouched()) {
                    sound.play(0.25f);
                    game.setScreen(new MainMenu(game));
                }
                return true;
            }
        });
    }

    @Override
    public void render(float delta) {
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

        // Renders Splash Screen
        Gdx.gl.glClearColor(0.25f, 0.25f, 0.25f, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        batch.begin();
        batch.draw(testBackground, 0, 0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        batch.draw(titleText, titleX, titleY, buttonWidth, buttonHeight);
        batch.draw(splashText, splashX, splashY, splashWidth, buttonHeight);
        batch.draw(namesText, namesX, namesY, namesWidth, namesHeight);
        batch.end();

    }

    @Override
    public void hide(){
        Gdx.input.setInputProcessor(null);
    }
}
