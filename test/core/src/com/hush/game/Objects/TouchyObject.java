package com.hush.game.Objects;

import com.badlogic.gdx.maps.MapObject;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TiledMapTile;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.physics.box2d.*;
import com.hush.game.UI.Settings;
import com.hush.game.Screens.Main;

import java.awt.*;

/**
 * Parent class for interactable object
 */
public abstract class TouchyObject {
    protected World world;
    protected TiledMap map;
    protected TiledMapTile tile;
    protected Rectangle bounds;
    protected Body body;
    protected Main tut;
    protected MapObject object;
    protected Fixture fixture;

    private TouchyObject(World world, TiledMap map, Rectangle bounds){
        this.world = world;
        this.map = map;
        this.bounds = bounds;

        BodyDef bdef = new BodyDef();
        FixtureDef fdef = new FixtureDef();
        PolygonShape shape = new PolygonShape();

        bdef.type = BodyDef.BodyType.StaticBody;
        bdef.position.set((float) (bounds.getX() + bounds.getWidth() / 2) / Settings.PPM, (float) (bounds.getY() + bounds.getHeight() / 2) / Settings.PPM);

        body = world.createBody(bdef);

        shape.setAsBox((float) bounds.getWidth() / 2 / Settings.PPM, (float)bounds.getHeight() / 2 / Settings.PPM);
        fdef.shape = shape;
        fixture = body.createFixture(fdef);
    }

    public abstract void onCollect();
    public void setCategoryFilter(int fB){
        Filter filter = new Filter();
        filter.categoryBits = (short) fB;
        fixture.setFilterData(filter);
    }

    public TiledMapTileLayer.Cell getCell(String name){
        TiledMapTileLayer layer = (TiledMapTileLayer) map.getLayers().get(name);
        return layer.getCell((int) (body.getPosition().x * Settings.PPM/16), (int) (body.getPosition().y * Settings.PPM/16));
    }
}
