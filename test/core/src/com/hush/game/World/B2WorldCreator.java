package com.hush.game.World;

import com.badlogic.gdx.maps.MapObject;
import com.badlogic.gdx.maps.objects.RectangleMapObject;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.math.Rectangle;
import com.hush.game.Entities.*;
import com.hush.game.Main;
import com.hush.game.Objects.*;
import com.hush.game.UI.Settings;

/**
 * Puts everything into the world.
 */
public class B2WorldCreator {

    private Main main;

    /*
     * Pre: Takes in 3 parameters
     * @param world
     * @param map
     * @param game
     * these are used to determine their position on the game map.
     * Post: Puts the objects created in the tiledmap into the world after loading them in below.
     */
    public B2WorldCreator(Main world, TiledMap map, Settings game) {
        this.main = world;

        //creates the player
        for (MapObject object : map.getLayers().get("Player").getObjects().getByType(RectangleMapObject.class)) {
            Rectangle rect = ((RectangleMapObject) object).getRectangle();

            main.player = new Player(main.world, world, rect.getX()/Settings.PPM, rect.getY()/Settings.PPM, game);
        }
        //Creates Wall
        for (MapObject object : map.getLayers().get("Wall").getObjects().getByType(RectangleMapObject.class)) {
            Rectangle rect = ((RectangleMapObject) object).getRectangle();

            new Wall((int) (rect.getX() + rect.getWidth() / 2), (int) (rect.getY() + rect.getHeight() / 2), rect.getWidth() / 2f, rect.getHeight() / 2f, world);
        }
        //creates the rock
        for (MapObject object : map.getLayers().get("Rock").getObjects().getByType(RectangleMapObject.class)) {
            Rectangle rect = ((RectangleMapObject) object).getRectangle();

            new Rock((int) (rect.getX() + rect.getWidth() / 2), (int) (rect.getY() + rect.getHeight() / 2), rect.getWidth() / 2f, rect.getHeight() / 2f, world);
        }
        //creates the lock
        for (MapObject object : map.getLayers().get("Lock").getObjects().getByType(RectangleMapObject.class)) {
            Rectangle rect = ((RectangleMapObject) object).getRectangle();

            new KeyHole((int) (rect.getX() + rect.getWidth() / 2), (int) (rect.getY() + rect.getHeight() / 2), rect.getWidth() / 2f, rect.getHeight() / 2f, world);
        }
        //creates the snow
        for (MapObject object : map.getLayers().get("Snow").getObjects().getByType(RectangleMapObject.class)) {
            Rectangle rect = ((RectangleMapObject) object).getRectangle();

            new SnowRock((int) (rect.getX() + rect.getWidth() / 2), (int) (rect.getY() + rect.getHeight() / 2), rect.getWidth() / 2f, rect.getHeight() / 2f, world);
        }
        //creates the death wall object
        for (MapObject object : map.getLayers().get("DWall").getObjects().getByType(RectangleMapObject.class)) {
            Rectangle rect = ((RectangleMapObject) object).getRectangle();

            new DamageWall((int) (rect.getX() + rect.getWidth() / 2), (int) (rect.getY() + rect.getHeight() / 2), rect.getWidth() / 2f, rect.getHeight() / 2f, world);
        }
        //creates the idle enemy
        for (MapObject object : map.getLayers().get("SEnemy").getObjects().getByType(RectangleMapObject.class)) {
            Rectangle rect = ((RectangleMapObject) object).getRectangle();

            new StaticEnemy(main.world, world, rect.getX()/Settings.PPM, rect.getY()/Settings.PPM);
        }
        //creates the dynamic enemy
        for (MapObject object : map.getLayers().get("DEnemy").getObjects().getByType(RectangleMapObject.class)) {
            Rectangle rect = ((RectangleMapObject) object).getRectangle();

            new DynamicEnemy(main.world, world, rect.getX()/Settings.PPM, rect.getY()/Settings.PPM);
        }
        //creates the goal
        for (MapObject object : map.getLayers().get("Goal").getObjects().getByType(RectangleMapObject.class)) {
            Rectangle rect = ((RectangleMapObject) object).getRectangle();

            new Goal((int) (rect.getX() + rect.getWidth() / 2), (int) (rect.getY() + rect.getHeight() / 2), rect.getWidth() / 2f, rect.getHeight() / 2f, world);
        }
        // creates the horizontally moving enemy
        for (MapObject object : map.getLayers().get("HEnemy").getObjects().getByType(RectangleMapObject.class)) {
            Rectangle rect = ((RectangleMapObject) object).getRectangle();

            new HorizontalEnemy(main.world, world, rect.getX()/Settings.PPM, rect.getY()/Settings.PPM);
        }
        //creates the vertical moving enemy
        for (MapObject object : map.getLayers().get("VEnemy").getObjects().getByType(RectangleMapObject.class)) {
            Rectangle rect = ((RectangleMapObject) object).getRectangle();

            new VerticalEnemy(main.world, world, rect.getX()/Settings.PPM, rect.getY()/Settings.PPM);
        }
        //creates the key
        for (MapObject object : map.getLayers().get("Key").getObjects().getByType(RectangleMapObject.class)) {
            Rectangle rect = ((RectangleMapObject) object).getRectangle();

            new Key((int) (rect.getX() + rect.getWidth() / 2), (int) (rect.getY() + rect.getHeight() / 2), rect.getWidth() / 2f, rect.getHeight() / 2f, world);
        }

    }
}
