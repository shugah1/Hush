package com.hush.game.Screens;

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
import com.hush.game.Entities.Player;
import com.hush.game.Main;
import com.hush.game.UI.HUD;
import com.hush.game.UI.Settings;
import org.ini4j.Wini;

import java.io.File;
import java.io.IOException;

public class LoseScreen extends ScreenAdapter {
    Settings game;
    SpriteBatch batch;
    Texture endText;
    Texture scoreText;
    Texture restartText;
    Texture returnText;
    BitmapFont font;
    Sound sound;
    HUD hud;
    Player player;


    int minutes = hud.worldTimer / 60;
    int seconds = hud.worldTimer % 60;
    String score = minutes + " : " + seconds  + " s";
    int cursorX;
    int cursorY;
    float buttonWidth = Gdx.graphics.getWidth() / 5;
    float buttonHeight = Gdx.graphics.getHeight() / 9;
    float buttonX = Gdx.graphics.getWidth() / 2 - buttonWidth / 2;

    float endX = buttonX;
    float endY = buttonHeight * 7;
    float scoreX = buttonX;
    float scoreY = buttonHeight * 5;
    float restartX = buttonX;
    float restartY = buttonHeight * 3;
    float returnX = buttonX;
    float returnY = buttonHeight;

    public LoseScreen(Settings game) {
        this.game = game;
        batch = new SpriteBatch();
        font = new BitmapFont();
        endText = new Texture("Text/loseText.png");
        scoreText = new Texture(("Text/scoreText.png"));
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
                if (cursorX > restartX && cursorX < restartX + buttonWidth) {
                    if (cursorY > restartY && cursorY < restartY + buttonHeight) {
                        if (Gdx.input.isTouched()) {
                            sound.play(0.25f);
                            game.setScreen(new Main(game));
                        }
                    }
                }
                if (cursorX > returnX && cursorX < returnX + buttonWidth) {
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

        Gdx.gl.glClearColor(0.25f, 0.25f, 0.25f, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        batch.begin();
        batch.draw(endText, endX, endY, buttonWidth, buttonHeight);
        batch.draw(scoreText, scoreX - buttonWidth, scoreY, buttonWidth, buttonHeight);
        batch.draw(restartText, restartX, restartY, buttonWidth, buttonHeight);
        batch.draw(returnText, returnX, returnY, buttonWidth, buttonHeight);

        font.draw(batch, score, buttonX + buttonWidth * 0.75f, scoreY + buttonHeight * 0.8f);
        batch.end();

    }

    @Override
    public void hide() {
        Gdx.input.setInputProcessor(null);
    }
}