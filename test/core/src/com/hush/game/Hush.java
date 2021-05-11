package com.hush.game;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class Hush extends Game {
    SpriteBatch batch;
    ShapeRenderer shapeRenderer;
    BitmapFont font;

    @Override
    public void create () {
        Gdx.graphics.setWindowedMode(1920, 1080);
        batch = new SpriteBatch();
        shapeRenderer = new ShapeRenderer();
        font = new BitmapFont();
        setScreen(new SplashScreen(this));
    }

    @Override
    public void dispose () {
        batch.dispose();
        shapeRenderer.dispose();
        font.dispose();
    }
}

    /*
    ShapeRenderer shapeRenderer;
    SpriteBatch batch;
    Texture titleText;
    Texture startText;
    Texture settingsText;
    Texture quitText;
    BitmapFont font;
    Sound sound;
    Animation<TextureRegion> animation;

    float elapsed;
    int cursorX;
    int cursorY;
    int titleX = 710;
    int titleY = 750;
    int startX = 810;
    int startY = 550;
    int settingsX = 810;
    int settingsY = 350;
    int quitX = 810;
    int quitY = 150;

    @Override
    public void create () {
        Gdx.graphics.setWindowedMode(1920, 1080);
        shapeRenderer = new ShapeRenderer();
        batch = new SpriteBatch();
        font = new BitmapFont();
        setScreen(new TitleScreen(this));

        titleText = new Texture("titleText.png");
        startText = new Texture("startText.png");
        settingsText = new Texture("settingsText.png");
        quitText = new Texture("quitText.png");
        sound = Gdx.audio.newSound(Gdx.files.internal("Menu1.wav"));
        animation = GifDecoder.loadGIFAnimation(Animation.PlayMode.LOOP, Gdx.files.internal("polish cow.gif").read());
    }

    @Override
    public void render () {

        cursorX = Gdx.input.getX();
        cursorY = Gdx.graphics.getHeight() - Gdx.input.getY();

        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);


        elapsed += Gdx.graphics.getDeltaTime();
        batch.begin();
        batch.draw(animation.getKeyFrame(elapsed), cursorX, cursorY);
        batch.end();

        if (cursorX > startX && cursorX < startX + 300) {
            if (cursorY > startY && cursorY < startY + 100) {
                if (Gdx.input.isTouched()) {
                    sound.play(0.25f);
                }
            }
        }
        if (cursorX > settingsX && cursorX < settingsX + 300) {
            if (cursorY > settingsY && cursorY < settingsY + 100) {
                if (Gdx.input.isTouched()) {
                    sound.play(0.25f);
                }
            }
        }
        if (cursorX > quitX && cursorX < quitX + 300) {
            if (cursorY > quitY && cursorY < quitY + 100) {
                if (Gdx.input.isTouched()) {
                    sound.play(0.25f);
                    System.exit(0);
                }
            }
        }


        shapeRenderer.begin(ShapeRenderer.ShapeType.Filled);
        shapeRenderer.setColor(0.25f, 0.25f, 0.25f, 1);
        shapeRenderer.rect(titleX, titleY, 500, 200);
        shapeRenderer.setColor(0.25f, 0.25f, 0.25f, 1);
        shapeRenderer.rect(startX, startY, 300, 100);
        shapeRenderer.setColor(0.25f, 0.25f, 0.25f, 1);
        shapeRenderer.rect(settingsX, settingsY, 300, 100);
        shapeRenderer.setColor(0.25f, 0.25f, 0.25f, 1);
        shapeRenderer.rect(quitX, quitY, 300, 100);
        shapeRenderer.end();


        batch.begin();
        batch.draw(titleText, titleX, titleY, 500, 200);
        batch.draw(startText, startX, startY, 300, 100);
        batch.draw(settingsText, settingsX, settingsY, 300, 100);
        batch.draw(quitText, quitX, quitY, 300, 100);
        batch.end();

        shapeRenderer.begin(ShapeRenderer.ShapeType.Filled);
        shapeRenderer.setColor(0, 104, 255, 1);
        shapeRenderer.circle(cursorX, cursorY, 10);
        shapeRenderer.end();


    }

    @Override
    public void dispose () {
        shapeRenderer.dispose();
        batch.dispose();
        font.dispose();
        sound.dispose();
    }
}

     */
