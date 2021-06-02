package com.hush.game;

import com.badlogic.gdx.*;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.utils.viewport.StretchViewport;
import com.hush.game.Entities.GameObject;
import com.hush.game.Entities.Player;
import com.hush.game.Objects.MovingWall;
import com.hush.game.Screens.LevelSelect;
import com.hush.game.Screens.LoseScreen;
import com.hush.game.Screens.MainMenu;
import com.hush.game.Screens.WinScreen;
import com.hush.game.UI.HUD;
import com.hush.game.UI.Settings;
import com.hush.game.World.WorldContactListener;
import com.hush.game.World.TiledGameMap;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Box2DDebugRenderer;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.utils.viewport.Viewport;
import ca.error404.bytefyte.constants.Globals;
import org.ini4j.Wini;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;

public class Main implements Screen {

    public static World world;
    private OrthographicCamera cam;
    public Player player;
    private Viewport gamePort;
    private Settings game;
    private Box2DDebugRenderer b2dr;
    private TextureAtlas atlas;
    private HUD hud;
    public MovingWall movingWall;
    public static ArrayList<GameObject> gameObject = new ArrayList<>();
    public static ArrayList<GameObject> gameObjectAdd = new ArrayList<>();
    public static ArrayList<GameObject> gameObjectBye = new ArrayList<>();
    public TiledGameMap gameMap;

    // Pause Menu Variables
    ShapeRenderer shapeRenderer;
    SpriteBatch batch;
    Texture pauseText;
    Texture resumeText;
    Texture restartText;
    Texture returnText;
    Sound sound;
    boolean paused = false;
    float buttonWidth = Gdx.graphics.getWidth() / 5;
    float buttonHeight = Gdx.graphics.getHeight() / 9;
    float buttonX = Gdx.graphics.getWidth() / 2 - buttonWidth / 2;
    float pauseY = buttonHeight * 7;
    float resumeY = buttonHeight * 5;
    float restartY = buttonHeight * 3;
    float returnY = buttonHeight;

    public Main(Settings game){
        Settings.manager.load("sprites/player.atlas", TextureAtlas.class);
        Settings.manager.finishLoading();
        this.game = game;
        cam = new OrthographicCamera();
        world = new World(new Vector2(0, 0/ Settings.PPM), true);

        gameMap = new TiledGameMap("test/core/assets/TiledMaps/" + LevelSelect.mapSelect + ".tmx", this, game);
        // loads save data and assigns variables
        File settings = new File(Globals.workingDirectory + "settings.ini");
        try {
            Wini ini = new Wini(settings);
            Settings.highScore.put(LevelSelect.mapSelect, Integer.parseInt(ini.get("High Score", LevelSelect.mapSelect)));

        } catch (Exception ignored) {
            Settings.highScore.put(LevelSelect.mapSelect, Integer.MAX_VALUE);
        }

        gamePort = new StretchViewport(Settings.V_WIDTH / Settings.PPM,Settings.V_HEIGHT / Settings.PPM,cam);
        cam.position.set(gamePort.getWorldWidth() /2, gamePort.getWorldHeight() / 2, 0);
        cam.update();

        b2dr = new Box2DDebugRenderer();
        world.setContactListener(new WorldContactListener());

        if (!Settings.songName.equalsIgnoreCase(LevelSelect.mapSelect) ) {
            Settings.music = game.newSong(LevelSelect.mapSelect);

          if (Settings.music == null) { Settings.music = game.newSong("menu weird"); }
        }
        Settings.music.play();
        game.music.setVolume(Settings.musicVolume / 10f);
        hud = new HUD(this);

        // Initializes Pause Menu Variables
        shapeRenderer = new ShapeRenderer();
        batch = new SpriteBatch();
        pauseText = new Texture("Text/pauseText.png");
        resumeText = new Texture("Text/resumeText.png");
        restartText = new Texture("Text/restartText.png");
        returnText = new Texture("Text/returnText.png");
        sound = Gdx.audio.newSound(Gdx.files.internal("test/core/assets/SoundEffects/Menu1.wav"));
    }

    @Override
    public void show() {
        //
    }

    public void update(float dt) {
        if (player.win) {
            game.setScreen(new WinScreen(game));
            player.win = false;
            gameObject.clear();
        }
        if (player.pDead) {
            game.setScreen(new LoseScreen(game));
            player.pDead = false;
            gameObject.clear();
        }
        //Loops song
        if (game.music.getPosition() >= game.songLoopEnd) {
            game.music.setPosition((float) (game.music.getPosition() - (game.songLoopEnd - game.songLoopStart)));
        }

        for(GameObject gO : gameObject ){
            if (gO.remove){
                try{
                    world.destroyBody(gO.b2body);
                }catch (Exception e){
                }
            }else{
                gO.update(dt);
            }
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

        // Set Pause Menu
        if (!paused && Gdx.input.isKeyJustPressed(Input.Keys.ESCAPE)) {
            paused = true;
            System.out.println("Paused");
        }

        world.step(1/60f,6,2);

        try {
            cam.position.x = player.b2body.getTransform().getPosition().x;
            cam.position.y = player.b2body.getTransform().getPosition().y;
        } catch (Exception e) {
            cam.position.x = player.x;
            cam.position.y = player.y;
        }

        cam.update();

        gameObject.addAll(gameObjectAdd);
        gameObject.removeAll(gameObjectBye);
        gameObjectAdd.clear();
        gameObjectBye.clear();

        hud.update(dt);
    }

    @Override
    public void render(float delta) {
        if (paused) {
            Gdx.input.setInputProcessor(new InputAdapter() {
                // Pause Menu Input
                @Override
                public boolean touchDown(int screenX, int screenY, int pointer, int button) {
                    int cursorX = Gdx.input.getX();
                    int cursorY = Gdx.graphics.getHeight() - Gdx.input.getY();
                    // Resume Button Check
                    if (cursorX > buttonX && cursorX < buttonX + buttonWidth) {
                        if (cursorY > resumeY && cursorY < resumeY + buttonHeight) {
                            if (Gdx.input.isTouched()) {
                                sound.play(0.25f);
                                paused = false;
                            }
                        }
                    }
                    // Restart Button Check
                    if (cursorX > buttonX && cursorX < buttonX + buttonWidth) {
                        if (cursorY > restartY && cursorY < restartY + buttonHeight) {
                            if (Gdx.input.isTouched()) {
                                sound.play(0.25f);
                                Settings.music.stop();
                                gameObject.clear();
                                game.setScreen(new Main(game));
                            }
                        }
                    }
                    // Return Button Check
                    if (cursorX > buttonX && cursorX < buttonX + buttonWidth) {
                        if (cursorY > returnY && cursorY < returnY + buttonHeight) {
                            if (Gdx.input.isTouched()) {
                                sound.play(0.25f);
                                Settings.music.stop();
                                gameObject.clear();
                                game.setScreen(new MainMenu(game));
                            }
                        }
                    }
                    return true;
                }
            });

            // Renders Pause Menu
            shapeRenderer.begin(ShapeRenderer.ShapeType.Filled);
            shapeRenderer.setColor(0.25f, 0.25f, 0.25f, 1);
            shapeRenderer.rect(buttonX - buttonWidth / 2, buttonHeight, buttonWidth * 2, buttonHeight * 7);
            shapeRenderer.end();

            batch.begin();
            batch.draw(pauseText, buttonX, pauseY, buttonWidth, buttonHeight);
            batch.draw(resumeText, buttonX, resumeY, buttonWidth, buttonHeight);
            batch.draw(restartText, buttonX, restartY, buttonWidth, buttonHeight);
            batch.draw(returnText, buttonX, returnY, buttonWidth, buttonHeight);
            batch.end();
        }
        else {
            update(delta);
            Gdx.gl.glClearColor(0, 0, 0, 1);
            Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

            gameMap.render(cam);

            game.batch.begin();
            for (GameObject gO : gameObject) {
                gO.draw(game.batch);
            }
            game.batch.setProjectionMatrix(cam.combined);
            b2dr.render(world, cam.combined);
            hud.stage.draw();
            game.batch.end();
            hud.render();
        }
    }

    @Override
    public void resize(int width, int height) {
        gamePort.update(width,height);
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

    @Override
    public void dispose() {
        game.batch.dispose();
        gameMap.dispose();
        world.dispose();
        b2dr.dispose();
    }
}