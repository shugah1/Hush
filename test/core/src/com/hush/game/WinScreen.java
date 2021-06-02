package com.hush.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputAdapter;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.hush.game.UI.HUD;
import com.hush.game.UI.Settings;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;

public class WinScreen extends ScreenAdapter {
    Settings game;
    SpriteBatch batch;
    Texture endText;
    Texture scoreText;
    Texture newHighScoreText;
    Texture restartText;
    Texture returnText;
    BitmapFont font;
    Sound sound;

    int minutes = HUD.worldTimer / 60;
    int seconds = HUD.worldTimer % 60;
    String score = minutes + " : " + seconds + " s";
    int highScore;
    int cursorX;
    int cursorY;
    float buttonWidth = Gdx.graphics.getWidth() / 5;
    float buttonHeight = Gdx.graphics.getHeight() / 9;
    float buttonX = Gdx.graphics.getWidth() / 2 - buttonWidth / 2;

    boolean newHighScore = false;
    float winY = buttonHeight * 7;
    float scoreY = buttonHeight * 5;
    float restartY = buttonHeight * 3;
    float returnY = buttonHeight;

    public WinScreen(Settings game) {
        this.game = game;
        batch = new SpriteBatch();
        endText = new Texture("Text/winText.png");
        scoreText = new Texture(("Text/scoreText.png"));
        newHighScoreText = new Texture(("Text/newHighScoreText.png"));
        restartText = new Texture("Text/restartText.png");
        returnText = new Texture("Text/returnText.png");
        sound = Gdx.audio.newSound(Gdx.files.internal("Menu1.wav"));

        FreeTypeFontGenerator generator = new FreeTypeFontGenerator(Gdx.files.internal("Cyberverse Condensed Bold Italic.ttf"));
        FreeTypeFontGenerator.FreeTypeFontParameter parameter = new FreeTypeFontGenerator.FreeTypeFontParameter();
        parameter.size = (int) buttonHeight;
        font = generator.generateFont(parameter);
        font.setColor(0f, 104f, 255f, 1f);

        try {
            File myObj = new File("C:\\Hush-main\\Hush-main\\Hush\\test\\core\\assets\\highScore");
            Scanner myReader = new Scanner(myObj);
            while (myReader.hasNextLine()) {
                String data = myReader.nextLine();
                highScore = Integer.parseInt(data);
                System.out.println(data);
            }
            myReader.close();
        } catch (FileNotFoundException e) {
            System.out.println("An error occurred.");
            e.printStackTrace();
        }

        if (highScore > HUD.worldTimer) {
            try {
                FileWriter myWriter = new FileWriter("C:\\Hush-main\\Hush-main\\Hush\\test\\core\\assets\\highScore");
                myWriter.write(HUD.worldTimer.toString());
                myWriter.close();
                System.out.println("New High Score");
                newHighScore = true;
            } catch (IOException e) {
                System.out.println("An error occurred.");
                e.printStackTrace();
            }
        }
    }

    @Override
    public void show() {
        Main.gameObject.clear();
        Gdx.input.setInputProcessor(new InputAdapter() {
            // WinScreen Input
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
        Gdx.gl.glClearColor(0.25f, 0.25f, 0.25f, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        batch.begin();
        batch.draw(endText, buttonX, winY, buttonWidth, buttonHeight);
        batch.draw(scoreText, buttonX - buttonWidth, scoreY, buttonWidth, buttonHeight);
        batch.draw(restartText, buttonX, restartY, buttonWidth, buttonHeight);
        batch.draw(returnText, buttonX, returnY, buttonWidth, buttonHeight);

        // Checks and prints New High Score notification
        if (newHighScore) {
            batch.draw(newHighScoreText, buttonX, scoreY - buttonHeight * 3/4, buttonWidth, buttonHeight * 3/4);
        }

        font.draw(batch, score, buttonX + buttonWidth * 0.75f, scoreY + buttonHeight * 0.8f);
        batch.end();

    }

    @Override
    public void hide() {
        Gdx.input.setInputProcessor(null);
    }
}