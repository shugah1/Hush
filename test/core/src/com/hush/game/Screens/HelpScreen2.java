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
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.hush.game.UI.Settings;

public class HelpScreen2 extends ScreenAdapter {
    Settings game;
    SpriteBatch batch;
    Texture testBackground;
    Texture titleText;
    Texture invisHelp;
    Texture armourHelp;
    Texture soundHelp;
    Texture timeHelp;
    Texture goalHelp;
    Texture settingsText;
    Texture helpText;
    Texture backText;
    Texture quitText;

    Texture invisImage;
    Texture armourImage;
    Texture soundImage;
    Texture soundUnderImage;
    Texture timeImage;
    Sound sound;
    ShapeRenderer shapeRenderer;

    int cursorX;
    int cursorY;
    float buttonWidth = Gdx.graphics.getWidth() / 5f;
    float buttonHeight = Gdx.graphics.getHeight() / 9f;
    float buttonX = Gdx.graphics.getWidth() / 2f - buttonWidth / 2;
    float column1 = buttonWidth * 0.5f;
    float column2 = buttonWidth * 2.5f;
    float helpHeight = buttonHeight * 1.25f;
    float helpY = buttonHeight * 7;
    float quitY = buttonHeight;

    public HelpScreen2(Settings game) {
        this.game = game;
        batch = new SpriteBatch();
        testBackground = new Texture(("TestBackground"));
        titleText = new Texture("Text/titleText.png");
        invisHelp = new Texture("Text/invisHelp.png");
        armourHelp = new Texture("Text/armourHelp.png");
        soundHelp = new Texture("Text/soundHelp.png");
        timeHelp = new Texture("Text/timeHelp.png");
        goalHelp = new Texture("Text/goalHelp.png");
        settingsText = new Texture("Text/settingsText.png");
        helpText = new Texture("Text/helpText.png");
        backText = new Texture("Text/backText.png");
        quitText = new Texture("Text/quitText.png");

        invisImage = new Texture("HUD/invisibility-hush.png");
        armourImage = new Texture("armourImage.png");
        soundImage = new Texture("HUD/SoundBarMiniProgressCut.png");
        soundUnderImage = new Texture("HUD/LifeBarMiniUnder.png");
        timeImage = new Texture("timeImage.png");
        sound = Gdx.audio.newSound(Gdx.files.internal("test/core/assets/SoundEffects/Menu1.wav"));
        shapeRenderer = new ShapeRenderer();
    }

    @Override
    public void show(){
        Gdx.input.setInputProcessor(new InputAdapter() {
            @Override
            public boolean touchDown(int screenX, int screenY, int pointer, int button) {
                cursorX = Gdx.input.getX();
                cursorY = Gdx.graphics.getHeight() - Gdx.input.getY();
                if (cursorX > column1 && cursorX < column1 + buttonWidth) {
                    if (cursorY > buttonHeight && cursorY < buttonHeight + buttonHeight) {
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

        batch.end();

        shapeRenderer.begin(ShapeRenderer.ShapeType.Filled);
        shapeRenderer.setColor(0.25f, 0.25f, 0.25f, 1);
        shapeRenderer.rect(column1, buttonHeight, buttonWidth * 4f, helpHeight * 6);
        shapeRenderer.end();

        batch.begin();
        batch.draw(helpText, buttonX, helpY, buttonWidth, buttonHeight);
        batch.draw(quitText, buttonX, quitY, buttonWidth, buttonHeight);
        batch.draw(invisHelp, column1, buttonHeight * 2 + helpHeight * 3, buttonWidth * 2f, helpHeight);
        batch.draw(armourHelp, column2, buttonHeight * 2 + helpHeight * 2, buttonWidth * 2f, helpHeight);
        batch.draw(soundHelp, column1, buttonHeight * 2 + helpHeight, buttonWidth * 2f, helpHeight);
        batch.draw(timeHelp, column2, buttonHeight * 2, buttonWidth * 2f, helpHeight);

        batch.draw(invisImage, column2 * 1.25f, buttonHeight * 2 + helpHeight * 3, buttonWidth * 0.75f, helpHeight);
        batch.draw(armourImage, column1 * 2.2f, buttonHeight * 2 + helpHeight * 2, buttonWidth * 0.75f, helpHeight);

        batch.draw(soundUnderImage, column2 * 1.1f, buttonHeight * 2 + helpHeight, buttonWidth * 1.5f, helpHeight);
        batch.draw(soundImage, column2 * 1.1f, buttonHeight * 2 + helpHeight, buttonWidth * 1.5f, helpHeight);
        batch.draw(timeImage, column1 * 2.2f, buttonHeight * 2, buttonWidth * 0.9f, helpHeight);

        batch.draw(backText, column1, buttonHeight, buttonWidth, buttonHeight);
        batch.end();
    }

    @Override
    public void hide(){
        Gdx.input.setInputProcessor(null);
    }
}