package com.hush.game.World;

import com.hush.game.UI.Settings;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.maps.tiled.*;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.physics.box2d.*;
import com.hush.game.Main;

public class TiledGameMap {

    TiledMap tiledMap;
    public static OrthogonalTiledMapRenderer tiledMapRenderer;
    Main main;
    public static B2WorldCreator creator;

    //public static World world;
    public static Box2DDebugRenderer b2dr;

    public TiledGameMap(String map, Main main, Settings game) {
        this.main = main;
        tiledMap = new TmxMapLoader().load(map);
        tiledMapRenderer = new OrthogonalTiledMapRenderer(tiledMap,1/ Settings.PPM);

        b2dr = new Box2DDebugRenderer();
        creator = new B2WorldCreator(main, tiledMap, game);
    }

    public void render(OrthographicCamera camera) {
        tiledMapRenderer.setView(camera);
        tiledMapRenderer.render();
    }


    public static void update(float delta) {
        //
    }


    public void dispose() {
        tiledMap.dispose();
        tiledMapRenderer.dispose();
        b2dr.dispose();
    }
}
