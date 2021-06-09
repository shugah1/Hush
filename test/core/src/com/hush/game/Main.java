package com.hush.game;

// Imports libraries
import com.badlogic.gdx.*;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.utils.viewport.StretchViewport;
import com.hush.game.Entities.Enemy;
import com.hush.game.Entities.GameObject;
import com.hush.game.Entities.Player;
import com.hush.game.Objects.Rock;
import com.hush.game.Screens.LevelSelect;
import com.hush.game.Screens.LoseScreen;
import com.hush.game.Screens.*;
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
import java.util.ArrayList;

/**
 * main class that runs the game.
 */
// Main Screen Class
public class Main implements Screen {
    // Initializes variables
    public static World world;
    private OrthographicCamera cam;
    public Player player;
    private Viewport gamePort;
    private Settings game;
    private Box2DDebugRenderer b2dr;
    private HUD hud;
    public Rock rock;
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
    Texture helpText;
    Texture returnText;
    Texture notificationText1;
    Texture notificationText2;
    Texture startText;
    Texture finalImage;
    Sound sound;

    public static boolean paused = false;
    boolean notification = true;

    // Sets button variables to scale with screen
    float buttonWidth = Gdx.graphics.getWidth() / 5f;
    float buttonHeight = Gdx.graphics.getHeight() / 9f;
    float buttonX = Gdx.graphics.getWidth() / 2f - buttonWidth / 2;
    float column1 = buttonWidth * 0.5f;
    float helpHeight = buttonHeight * 1.25f;
    float pauseY = buttonHeight * 7;
    float resumeY = buttonHeight * 4f;
    float restartY = buttonHeight * 2.5f;
    float returnY = buttonHeight;

    /*
    Pre: settings to get setup
    Post: loads in everything.
     */
    public Main(Settings game){
        //loads atlas
        Settings.manager.load("sprites/player.atlas", TextureAtlas.class);
        Settings.manager.finishLoading();
        this.game = game;
        //creates camera and world
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
        //creates the gameport and cam position.
        gamePort = new StretchViewport(Settings.V_WIDTH / Settings.PPM,Settings.V_HEIGHT / Settings.PPM,cam);
        cam.position.set(gamePort.getWorldWidth() /2, gamePort.getWorldHeight() / 2, 0);
        cam.update();

        b2dr = new Box2DDebugRenderer();
        //calls the collision.
        world.setContactListener(new WorldContactListener());
        //gets song name in regards to each level.
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
        helpText = new Texture("Text/helpText.png");
        returnText = new Texture("Text/returnText.png");
        sound = Gdx.audio.newSound(Gdx.files.internal("test/core/assets/SoundEffects/Menu1.wav"));

        notificationText1 = new Texture("Text/notificationText1.png");
        notificationText2 = new Texture("Text/notificationText2.png");
        startText = new Texture("Text/startText.png");
        finalImage = new Texture("finalImage.png");
    }

    @Override
    public void show() {
        //
    }
    /*
    Pre: dt
    Post: Updates everthing in the game.
     */
    public void update(float dt) {
        //checks if player wins
        if (player.win) {
            if (Settings.completion.get("Level 5") == 0 && LevelSelect.mapSelect.equals("Level 5")) {
                game.setScreen(new CreditScreen(game));
            }
            else {
                game.setScreen(new WinScreen(game));
                Settings.music.stop();
                player.win = false;
                gameObject.clear();
            }
        }
        //checks if player loses.
        if (player.pDead) {
            game.setScreen(new LoseScreen(game));
            player.pDead = false;
            gameObject.clear();
        }

        //Loops song
        if (game.music.getPosition() >= game.songLoopEnd) {
            game.music.setPosition((float) (game.music.getPosition() - (game.songLoopEnd - game.songLoopStart)));
        }
        //loops through all the game objects and destroys, adds or updates them.
        for( int i = 0; i < gameObject.size(); i++){
            if (gameObject.get(i).remove){
                try{
                    world.destroyBody(gameObject.get(i).b2body);
                }catch (Exception e){
                }
                gameObjectBye.add(gameObject.get(i));
            }else{
                gameObject.get(i).update(dt);
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
        }

        world.step(1/60f,6,2);
        //tells the camera to follow the player.
        try {
            cam.position.x = player.b2body.getTransform().getPosition().x;
            cam.position.y = player.b2body.getTransform().getPosition().y;
        } catch (Exception e) {
            cam.position.x = player.x;
            cam.position.y = player.y;
        }

        cam.update();
        //updates the gameobjects list.
        gameObject.addAll(gameObjectAdd);
        gameObject.removeAll(gameObjectBye);
        gameObjectAdd.clear();
        gameObjectBye.clear();

        hud.update(dt);
    }
    /*
    Pre:delta time
    Post: renders everything in the game.
     */
    @Override
    public void render(float delta) {
        // Pause Menu Loop
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
                                paused = false;
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
                                paused = false;
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

        // Notification
        else if (Settings.completion.get("RealTutorial") == 0 && notification){
            Gdx.input.setInputProcessor(new InputAdapter() {
                // Notification Menu Input
                @Override
                public boolean touchDown(int screenX, int screenY, int pointer, int button) {
                    if (Gdx.input.isTouched()) {
                        sound.play(0.25f);
                        notification = false;
                    }
                    return true;
                }
            });

            // Renders Notification
            shapeRenderer.begin(ShapeRenderer.ShapeType.Filled);
            shapeRenderer.setColor(0.25f, 0.25f, 0.25f, 1);
            shapeRenderer.rect(column1, buttonHeight, buttonWidth * 4f, helpHeight * 6);
            shapeRenderer.end();

            batch.begin();
            batch.draw(notificationText1, buttonX * 0.75f, buttonHeight * 6.5f, buttonWidth * 2f, buttonHeight * 2);
            batch.draw(finalImage, buttonX, buttonHeight * 4, buttonWidth, buttonHeight * 2.5f);
            batch.draw(notificationText2, buttonX * 0.75f, buttonHeight * 2, buttonWidth * 2f, buttonHeight * 2);
            batch.draw(startText, buttonX, buttonHeight, buttonWidth, buttonHeight);
            batch.end();
        }

        // Normal Update Loop
        else {
            update(delta);
            Gdx.gl.glClearColor(0, 0, 0, 1);
            Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

            gameMap.render(cam, player.invis);

            game.batch.begin();
            for (GameObject gO : gameObject) {
                gO.draw(game.batch);
            }
            for (GameObject go : gameObject) {
                try {
                    Enemy enem = (Enemy) go;
                    enem.drawRing(game.batch);
                } catch (Exception ignored) {}
            }
            game.batch.setProjectionMatrix(cam.combined);
            b2dr.render(world, cam.combined);
            hud.stage.draw();
            game.batch.end();
            hud.render();
        }
    }
    /*
    Pre: width, height.
    Post: updates the games width and height.
     */
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
    /*
    Pre: N/A
    Post: disposes everything.
     */
    @Override
    public void dispose() {
        game.batch.dispose();
        gameMap.dispose();
        world.dispose();
        b2dr.dispose();
    }
}