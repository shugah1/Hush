package com.hush.game.Screens;

import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.viewport.StretchViewport;
import com.hush.game.Entities.GameObject;
import com.hush.game.Entities.Player;
import com.hush.game.Objects.MovingWall;
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

import java.util.ArrayList;

import static com.hush.game.World.TiledGameMap.creator;


public class Main implements Screen {

    public static World world;
    private OrthographicCamera cam;
    //SpriteBatch batch;
    private Player player;
    private Viewport gamePort;
    private Settings game;
    private Box2DDebugRenderer b2dr;
    private TextureAtlas atlas;
    public MovingWall movingWall;
    public static ArrayList<GameObject> gameObject = new ArrayList<>();
    public static ArrayList<GameObject> gameObjectAdd = new ArrayList<>();
    public static ArrayList<GameObject> gameObjectBye = new ArrayList<>();
    public TiledGameMap gameMap;

    public Main(Settings game){
        atlas = new TextureAtlas("test/core/assets/Sprites/Ninja.atlas");
        this.game = game;
        //Gdx.graphics.setWindowedMode(1920, 1080);
        //batch = new SpriteBatch();
        cam = new OrthographicCamera();
        world = new World(new Vector2(0, 0/ Settings.PPM), true);
        gameMap = new TiledGameMap("test/core/assets/TiledMaps/untitled.tmx", this);
        gamePort = new StretchViewport(Settings.V_WIDTH /Settings.PPM,Settings.V_HEIGHT /Settings.PPM,cam);
        cam.position.set(gamePort.getWorldWidth() /2, gamePort.getWorldHeight() / 2, 0);
        cam.update();

        b2dr = new Box2DDebugRenderer();
        world.setContactListener(new WorldContactListener());
    }

    public TextureAtlas getAtlas(){
        return atlas;
    }

    @Override
    public void show() {
        //
    }

    public void update(float dt){

        //player.update(dt);
        //movingWall.update(dt);
        cam.position.x = player.b2body.getPosition().x;
        cam.position.y = player.b2body.getPosition().y;

        world.step(1/60f,6,2);
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
        cam.update();
        gameObject.addAll(gameObjectAdd);
        gameObject.removeAll(gameObjectBye);
        gameObjectAdd.clear();
        gameObjectBye.clear();
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
        game.batch.setProjectionMatrix(cam.combined);
        b2dr.render(world, cam.combined);
        game.batch.end();
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