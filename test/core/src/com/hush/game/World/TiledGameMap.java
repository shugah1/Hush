package com.hush.game.World;

import ca.error404.bytefyte.shaders.GrayscaleShader;
import com.hush.game.UI.Settings;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.maps.tiled.*;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.physics.box2d.*;
import com.hush.game.Main;

/**
 * Pre: Map Level
 * Post: Loads the map into the world.
 */
public class TiledGameMap {

    // defines variables
    TiledMap tiledMap;
    public static OrthogonalTiledMapRenderer tiledMapRenderer;
    Main main;
    public static B2WorldCreator creator;

    //public static World world;
    public static Box2DDebugRenderer b2dr;
    /*
    Pre: Needs the map, the main, and the game as a parameter.
    Post: Constructor that when called loads the map into the world.
     */
    public TiledGameMap(String map, Main main, Settings game) {
        this.main = main;
        //loads the map
        tiledMap = new TmxMapLoader().load(map);
        //renders the map and scales it.
        tiledMapRenderer = new OrthogonalTiledMapRenderer(tiledMap,1/ Settings.PPM);

        b2dr = new Box2DDebugRenderer();
        creator = new B2WorldCreator(main, tiledMap, game);
    }
    /*
    Pre: Camera being used and variable used for grayscale.
    Post: grayscales the world if player is inaudible
    Gets called instead of normal render when player in invisible, causes the world to become gray.
     */
    public void render(OrthographicCamera camera, boolean grayscale) {
        if (grayscale) { tiledMapRenderer.getBatch().setShader(GrayscaleShader.grayscaleShader); }
        tiledMapRenderer.setView(camera);
        tiledMapRenderer.render();
        tiledMapRenderer.getBatch().setShader(null);
    }

    /*
    Pre: Camera being used in the map
    Post: renders the map into the world
     */
    public void render(OrthographicCamera camera) {
        render(camera, false);
    }

    // does nothing :)
    public static void update(float delta) {
        //
    }

    /*
    Pre: N/A
    Post: Disposes/ gets rid of all elements of the tiledmap after done using it.
     */
    public void dispose() {
        tiledMap.dispose();
        tiledMapRenderer.dispose();
        b2dr.dispose();
    }
}
