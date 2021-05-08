package com.hush.game.Screens;

import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.utils.viewport.StretchViewport;
import com.hush.game.Entities.Player;
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


public class Main implements Screen {

    public static World world;
    private OrthographicCamera cam;
    SpriteBatch batch;
    private Player player;
    private Viewport gamePort;
    private Settings game;
    private Box2DDebugRenderer b2dr;
    private TextureAtlas atlas;

    TiledGameMap gameMap;

    public Main(Settings game){
        atlas = new TextureAtlas("test/core/assets/Sprites/Ninja.atlas");
        this.game = game;
        //Gdx.graphics.setWindowedMode(1920, 1080);
        batch = new SpriteBatch();
        cam = new OrthographicCamera();
        world = new World(new Vector2(0, 0/ Settings.PPM), true);
        gameMap = new TiledGameMap("C:\\Users\\stupp\\Desktop\\untitled.tmx", this);
        gamePort = new StretchViewport(Settings.V_WIDTH /Settings.PPM,Settings.V_HEIGHT /Settings.PPM,cam);
        cam.position.set(gamePort.getWorldWidth() /2, gamePort.getWorldHeight() / 2, 0);
        //cam.setToOrtho(false, Gdx.graphics.getWidth()/ Settings.PPM, Gdx.graphics.getHeight()/ Settings.PPM);
        cam.update();

        b2dr = new Box2DDebugRenderer();

        player = new Player(world, this);

        world.setContactListener(new WorldContactListener());

    }

    public TextureAtlas getAtlas(){
        return atlas;
    }

    @Override
    public void show() {

    }

    public void update(float dt){
        player.update(dt);
        cam.update();
        world.step(1/60f,6,2);


    }

    @Override
    public void render(float delta) {
        update(delta);
        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        game.batch.setProjectionMatrix(cam.combined);

        batch.begin();
        player.draw(batch);
        cam.position.x = player.b2body.getPosition().x;
        cam.position.y = player.b2body.getPosition().y;
        cam.update();
        gameMap.update(Gdx.graphics.getDeltaTime());
        gameMap.render(cam, batch);
        b2dr.render(world, cam.combined);



        batch.end();



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
        batch.dispose();
        gameMap.dispose();
        world.dispose();
        b2dr.dispose();

    }
}
