package ca.error404.bytefyte.scene.menu;


import ca.error404.bytefyte.Main;
import ca.error404.bytefyte.constants.Globals;
import ca.error404.bytefyte.constants.ScreenSizes;
import ca.error404.bytefyte.ui.Button;
import ca.error404.bytefyte.ui.MenuCursor;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.controllers.Controller;
import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.hush.game.UI.Settings;
import org.ini4j.Wini;

import java.io.File;
import java.io.IOException;
import java.util.Set;

/*
 * Pre: game instance
 * Post: creates the menuscene screen
 * */
public class MenuScene implements Screen {

    //delairing variables
    protected ShapeRenderer shapeRenderer;
    protected Texture background;
    protected Vector2 bgPos = new Vector2(0, 0);
    protected int xSpeed = -100;
    protected int ySpeed = -100;
    protected final Main game;

    protected Viewport viewport;
    protected Camera cam;

    /*
    * Constructor
    * Pre: Game instance
    * Post: New MenuScene
    * */
    public MenuScene(Main game) {
        this.game = game;
    }


    /*
     * Pre: None
     * Post: Initializes variables
     * */
    @Override
    public void show() {
        Main.cursors.clear();
        Main.buttons.clear();

        // sets up variables
        shapeRenderer = new ShapeRenderer();
        cam = new OrthographicCamera(1920, 1080);
        cam.position.set(960, 540, cam.position.z);
        cam.update();
        viewport = new FitViewport(1920, 1080, cam);
    }

    @Override
    /*
     * Pre: game instance
     * Post: handles the rendering for the screen
     * */
    public void render(float delta) {
        //drawing things
        update(delta);
        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        game.batch.setProjectionMatrix(cam.combined);
        shapeRenderer.setProjectionMatrix(cam.combined);
        game.batch.begin();

        drawBackground();

        for (Button button : Main.buttons) { button.draw(game.batch); }

        for (MenuCursor cursor : Main.cursors) { cursor.draw(game.batch); }

        if (Main.debug) {
            shapeRenderer.begin(ShapeRenderer.ShapeType.Line);

            for (MenuCursor cursor : Main.cursors) {
                shapeRenderer.rect(cursor.rect.getX(), cursor.rect.getY(), cursor.rect.getWidth(), cursor.rect.getHeight());
            }

            for (Button button : game.buttons) {
                shapeRenderer.rect(button.buttonRect.getX(), button.buttonRect.getY(), button.buttonRect.getWidth(), button.buttonRect.getHeight());
            }

            shapeRenderer.end();
        }

        game.batch.end();

        for (int i=0; i < Main.transitions.size(); i++) Main.transitions.get(i).draw();
    }

    /*
     * Pre: game instance
     * Post: creates the background
     * */
    public void drawBackground() {
        float w = background.getWidth();
        float h = background.getHeight();

        if (bgPos.x <= -(w + (1920 / 2f))) {
            bgPos.x += w;
        } else if (bgPos.x >= (w + (1920 / 2f))) {
            bgPos.x -= w;
        }

        if (bgPos.y <= -(1080 - (-1080 / 2f))) {
            bgPos.y += h;
        } else if (bgPos.y >= (1080 - (-1080 / 2f))) {
            bgPos.y -= h;
        }

        float x = bgPos.x;

        while (x > -(cam.viewportWidth)) {
            x -= w;
        }

        //draws the background
        while (x < cam.viewportWidth) {
            game.batch.draw(background, x, bgPos.y);
            game.batch.draw(background, x, bgPos.y + h);
            game.batch.draw(background, x, bgPos.y - h);
            game.batch.draw(background, x, bgPos.y - h * 2);
            game.batch.draw(background, x, bgPos.y + h * 2);

            x += w;
        }
    }

    /*
     * Pre: game instance
     * Post: handles the update function
     * */
    public void update(float deltaTime) {
        if (Gdx.input.isKeyJustPressed(Input.Keys.F5)) {
            launchHush();
        }

        bgPos.x += xSpeed * deltaTime;
        bgPos.y += ySpeed * deltaTime;


        // changes the music
        if (game.music != null) {
            if (game.music.getPosition() >= game.songLoopEnd) {
                game.music.setPosition((float) (game.music.getPosition() - (game.songLoopEnd - game.songLoopStart)));
            }
        }

        for (MenuCursor cursor : Main.cursors) { cursor.update(deltaTime); }
        for (int i=0; i < game.buttons.size(); i++) { game.buttons.get(i).update(); }

        // clear all controller inputs
        Set<Controller> keys = Main.recentButtons.keySet();
        for (Controller key : keys) {
            Main.recentButtons.get(key).clear();
        }

        for (int i=0; i < Main.transitions.size(); i++) Main.transitions.get(i).update(deltaTime);
    }

    public void launchHush() {
        try {
            // Window settings
            File settings = new File(Globals.workingDirectory + "settings.ini");

            // Checks for active save data
            if (!settings.exists()) {
                // Creates save file and writes default save data
                File file = new File(Globals.workingDirectory);
                file.mkdirs();

                settings.createNewFile();

                Wini ini = new Wini(settings);
                ini.add("Settings", "screen size", ScreenSizes.screenSize);
                ini.add("Settings", "music volume", ca.error404.bytefyte.Main.musicVolume);
                ini.add("Settings", "sfx volume", ca.error404.bytefyte.Main.sfxVolume);
                ini.add("Settings", "cutscene volume", ca.error404.bytefyte.Main.cutsceneVolume);
                ini.add("Settings", "fullscreen", ScreenSizes.fullScreen);
                ini.add("Settings", "debug", ca.error404.bytefyte.Main.debug);
                ini.add("Menu", "bill", ca.error404.bytefyte.Main.bill);
                ini.add("Menu", "stamina", ca.error404.bytefyte.Main.stamina);
                ini.store();
            } else {
                Wini ini = new Wini(settings);
                // loads save data and assigns variables
                try {
                    ScreenSizes.screenSize = Integer.parseInt(ini.get("Settings", "screen size"));
                    ca.error404.bytefyte.Main.musicVolume = Integer.parseInt(ini.get("Settings", "music volume"));
                    ca.error404.bytefyte.Main.cutsceneVolume = Integer.parseInt(ini.get("Settings", "cutscene volume"));
                    ca.error404.bytefyte.Main.sfxVolume = Integer.parseInt(ini.get("Settings", "sfx volume"));
                    ScreenSizes.fullScreen = Boolean.parseBoolean(ini.get("Settings", "fullscreen"));
                    ca.error404.bytefyte.Main.debug = Boolean.parseBoolean(ini.get("Settings", "debug"));
                    ca.error404.bytefyte.Main.bill = Boolean.parseBoolean(ini.get("Menu", "bill"));
                    ca.error404.bytefyte.Main.stamina = Boolean.parseBoolean(ini.get("Menu", "stamina"));
                } catch (Exception ignored) {

                }
            }

            // Sets screen size
            Gdx.graphics.setWindowedMode(1280, 720);

            // window title and window icon
            Gdx.graphics.setTitle("Hush");
            Gdx.graphics.setResizable(false);

            // Starts app
            Main.game.create();
            try {
                game.music.stop();
            } catch (Exception ignored) {}
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void resize(int width, int height) {

    }

    @Override
    public void pause() {

    }

    @Override
    public void resume() {

    }

    @Override
    public void hide() {

    }

    /*
    * Pre: None
    * Post: Disposes of all applicable assets
    * */
    @Override
    public void dispose() {
        game.batch.dispose();
        shapeRenderer.dispose();
    }
}
