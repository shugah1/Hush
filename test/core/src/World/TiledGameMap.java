package World;

import Tools.B2WorldCreator;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.maps.tiled.*;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.*;
import com.hush.game.MainMenu;
import com.hush.game.Settings;

public class TiledGameMap {

    TiledMap tiledMap;
    public static OrthogonalTiledMapRenderer tiledMapRenderer;


    public static World world;
    public static Box2DDebugRenderer b2dr;

    public TiledGameMap(String map) {
        tiledMap = new TmxMapLoader().load(map);
        tiledMapRenderer = new OrthogonalTiledMapRenderer(tiledMap,1/16f);

        world = new World(new Vector2(0,0/ Settings.PPM), true);
        b2dr = new Box2DDebugRenderer();

        new B2WorldCreator(world, tiledMap);

    }


    public static void render(OrthographicCamera camera, SpriteBatch batch) {
        tiledMapRenderer.setView(camera);
        tiledMapRenderer.render();

        batch.setProjectionMatrix(camera.combined);
        //batch.begin();
        //render(camera, batch);
        //batch.end();

    }


    public static void update(float delta) {



    }


    public void dispose() {
        tiledMap.dispose();
        tiledMapRenderer.dispose();
        world.dispose();
        b2dr.dispose();


    }



}
