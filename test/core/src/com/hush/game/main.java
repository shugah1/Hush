package com.hush.game;

import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputMultiplexer;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.viewport.StretchViewport;
import com.hush.game.Entities.Enemy;
import com.hush.game.Entities.GameObject;
import com.hush.game.Entities.Player;
import com.hush.game.Objects.MovingWall;
import com.hush.game.UI.HUD;
import com.hush.game.UI.Settings;
import com.hush.game.World.WorldContactListener;
import com.hush.game.World.TiledGameMap;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Box2DDebugRenderer;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.hush.game.constants.Globals;
import org.ini4j.Wini;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Random;

import static com.hush.game.World.TiledGameMap.creator;


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
    Texture stunImage = new Texture("ScrollThunder.png");


    public Main(Settings game){
        Settings.manager.load("sprites/player.atlas", TextureAtlas.class);
        Settings.manager.finishLoading();
        this.game = game;
        cam = new OrthographicCamera();
        world = new World(new Vector2(0, 0/ Settings.PPM), true);
        gameMap = new TiledGameMap("test/core/assets/TiledMaps/Tutorial(MidPoint).tmx", this);
        gamePort = new StretchViewport(Settings.V_WIDTH /Settings.PPM,Settings.V_HEIGHT /Settings.PPM,cam);
        cam.position.set(gamePort.getWorldWidth() /2, gamePort.getWorldHeight() / 2, 0);
        cam.update();

        b2dr = new Box2DDebugRenderer();
        world.setContactListener(new WorldContactListener());
        Settings.music = game.newSong("hub");
        Settings.music.play();
        game.music.setVolume(Settings.musicVolume / 10f);
        hud = new HUD(this);
    }

    public TextureAtlas getAtlas(){
        return atlas;
    }

    @Override
    public void show() {
        //
    }

    public void update(float dt) {
        //        Loops song
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

        world.step(1/60f,6,2);

        cam.position.x = player.b2body.getPosition().x;
        cam.position.y = player.b2body.getPosition().y;
        cam.update();

        gameObject.addAll(gameObjectAdd);
        gameObject.removeAll(gameObjectBye);
        gameObjectAdd.clear();
        gameObjectBye.clear();

        hud.update(dt);
    }

    @Override
    public void render(float delta) {
        update(delta);
        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        gameMap.render(cam);


        game.batch.begin();
        for(GameObject gO : gameObject ){
            gO.draw(game.batch);
        }
        hud.stage.draw();
        game.batch.setProjectionMatrix(cam.combined);
        b2dr.render(world, cam.combined);
        game.batch.end();
        hud.render();
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