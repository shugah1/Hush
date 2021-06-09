package com.hush.game.Screens;

// Imports libraries
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputAdapter;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.hush.game.Main;
import com.hush.game.UI.Settings;

// Help Screen Class
public class HelpScreen extends ScreenAdapter {
    // Initializes Variables
    Settings game;
    SpriteBatch batch;
    Texture testBackground;
    Texture titleText;
    Texture movementHelp;
    Texture enemyHelp;
    Texture keyHelp;
    Texture abilityHelp;
    Texture goalHelp;
    Texture settingsText;
    Texture helpText;
    Texture nextText;
    Texture quitText;

    Texture wasdImage;
    Texture enemyImage;
    Texture goalImage;
    Texture keyImage;
    Texture lockImage;
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

    public HelpScreen(Settings game) {
        // Assigns variables
        this.game = game;
        batch = new SpriteBatch();
        shapeRenderer = new ShapeRenderer();
        sound = Gdx.audio.newSound(Gdx.files.internal("test/core/assets/SoundEffects/Menu1.wav"));

        // Text Variables
        testBackground = new Texture(("test/core/assets/bg.png"));
        titleText = new Texture("Text/titleText.png");
        movementHelp = new Texture("Text/movementHelp.png");
        enemyHelp = new Texture("Text/enemyHelp.png");
        keyHelp = new Texture("Text/keyHelp.png");
        abilityHelp = new Texture("Text/abilityHelp.png");
        goalHelp = new Texture("Text/goalHelp.png");
        settingsText = new Texture("Text/settingsText.png");
        helpText = new Texture("Text/helpText.png");
        nextText = new Texture("Text/nextText.png");
        quitText = new Texture("Text/quitText.png");

        // Image Variables
        wasdImage = new Texture("wasdImage.png");
        enemyImage = new Texture("enemyImage");
        goalImage = new Texture("goalImage.png");
        keyImage = new Texture("keyImage.png");
        lockImage = new Texture("lockImage.png");
    }

    @Override
    public void show(){
        // Help Screen 1 Input Check
        Gdx.input.setInputProcessor(new InputAdapter() {
            @Override
            public boolean touchDown(int screenX, int screenY, int pointer, int button) {
                cursorX = Gdx.input.getX();
                cursorY = Gdx.graphics.getHeight() - Gdx.input.getY();

                // Next Button
                if (cursorX > column2 * 1.4f && cursorX < column2 * 1.4f + buttonWidth) {
                    if (cursorY > buttonHeight && cursorY < buttonHeight + buttonHeight) {
                        if (Gdx.input.isTouched()) {
                            sound.play(0.25f);
                            game.setScreen(new HelpScreen2(game));
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
        // Renders Help Screen 1
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
        batch.draw(movementHelp, column1, buttonHeight * 2 + helpHeight * 3, buttonWidth * 2f, helpHeight);
        batch.draw(enemyHelp, column2, buttonHeight * 2 + helpHeight * 2, buttonWidth * 2f, helpHeight);
        batch.draw(keyHelp, column1, buttonHeight * 2 + helpHeight, buttonWidth * 2f, helpHeight);
        batch.draw(goalHelp, column2, buttonHeight * 2, buttonWidth * 2f, helpHeight);

        batch.draw(wasdImage, column2 * 1.25f, buttonHeight * 2 + helpHeight * 3, buttonWidth * 0.75f, helpHeight);
        batch.draw(enemyImage, column1 * 2.2f, buttonHeight * 2 + helpHeight * 2, buttonWidth * 0.75f, helpHeight);
        batch.draw(keyImage, column2 * 1.1f, buttonHeight * 2 + helpHeight, buttonWidth, helpHeight);
        batch.draw(lockImage, column2 * 1.5f, buttonHeight * 2 + helpHeight, buttonWidth * 0.5f, helpHeight);
        batch.draw(goalImage, column1 * 2.2f, buttonHeight * 2, buttonWidth * 0.75f, helpHeight);

        batch.draw(nextText, column2 * 1.4f, buttonHeight, buttonWidth, buttonHeight);
        batch.end();
    }

    @Override
    public void hide(){
        Gdx.input.setInputProcessor(null);
    }
}