package com.hush.game.World;

import com.hush.game.Tools.B2WorldCreator;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.maps.tiled.*;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.physics.box2d.*;
import com.hush.game.Tutorial;

public class TiledGameMap {

    TiledMap tiledMap;
    public static OrthogonalTiledMapRenderer tiledMapRenderer;
    Tutorial tut;


    //public static World world;
    public static Box2DDebugRenderer b2dr;

    public TiledGameMap(String map, Tutorial tut) {
        this.tut = tut;
        tiledMap = new TmxMapLoader().load(map);
        tiledMapRenderer = new OrthogonalTiledMapRenderer(tiledMap,1/16f);

        b2dr = new Box2DDebugRenderer();
        new B2WorldCreator(tut, tiledMap);

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
        b2dr.dispose();


    }



}
