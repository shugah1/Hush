package com.hush.game.World;

import com.badlogic.gdx.maps.MapObject;
import com.badlogic.gdx.maps.objects.RectangleMapObject;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.utils.Array;
import com.hush.game.Entities.*;
import com.hush.game.Main;
import com.hush.game.Objects.DamageWall;
import com.hush.game.Objects.Goal;
import com.hush.game.Objects.MovingWall;
import com.hush.game.Objects.StaticWall;
import com.hush.game.UI.Settings;

public class B2WorldCreator {

    private Main main;

    public B2WorldCreator(Main world, TiledMap map, Settings game) {
        this.main = world;

        //Creates Walls
        for (MapObject object : map.getLayers().get("Wall").getObjects().getByType(RectangleMapObject.class)) {
            Rectangle rect = ((RectangleMapObject) object).getRectangle();

            new StaticWall((int) (rect.getX() + rect.getWidth() / 2), (int) (rect.getY() + rect.getHeight() / 2), rect.getWidth() / 2f, rect.getHeight() / 2f, world);
        }

        for (MapObject object : map.getLayers().get("MWall").getObjects().getByType(RectangleMapObject.class)) {
            Rectangle rect = ((RectangleMapObject) object).getRectangle();

            new MovingWall((int) (rect.getX() + rect.getWidth() / 2), (int) (rect.getY() + rect.getHeight() / 2), rect.getWidth() / 2f, rect.getHeight() / 2f, world);
        }

        for (MapObject object : map.getLayers().get("DWall").getObjects().getByType(RectangleMapObject.class)) {
            Rectangle rect = ((RectangleMapObject) object).getRectangle();

            new DamageWall((int) (rect.getX() + rect.getWidth() / 2), (int) (rect.getY() + rect.getHeight() / 2), rect.getWidth() / 2f, rect.getHeight() / 2f, world);
        }

        for (MapObject object : map.getLayers().get("Player").getObjects().getByType(RectangleMapObject.class)) {
            Rectangle rect = ((RectangleMapObject) object).getRectangle();

            main.player = new Player(main.world, world, rect.getX()/Settings.PPM, rect.getY()/Settings.PPM, game);
        }

        for (MapObject object : map.getLayers().get("SEnemy").getObjects().getByType(RectangleMapObject.class)) {
            Rectangle rect = ((RectangleMapObject) object).getRectangle();

            new StaticEnemy(main.world, world, rect.getX()/Settings.PPM, rect.getY()/Settings.PPM);
        }
        for (MapObject object : map.getLayers().get("DEnemy").getObjects().getByType(RectangleMapObject.class)) {
            Rectangle rect = ((RectangleMapObject) object).getRectangle();

            new DynamicEnemy(main.world, world, rect.getX()/Settings.PPM, rect.getY()/Settings.PPM);
        }
        for (MapObject object : map.getLayers().get("Goal").getObjects().getByType(RectangleMapObject.class)) {
            Rectangle rect = ((RectangleMapObject) object).getRectangle();

            new Goal((int) (rect.getX() + rect.getWidth() / 2), (int) (rect.getY() + rect.getHeight() / 2), rect.getWidth() / 2f, rect.getHeight() / 2f, world);
        }
        for (MapObject object : map.getLayers().get("HEnemy").getObjects().getByType(RectangleMapObject.class)) {
            Rectangle rect = ((RectangleMapObject) object).getRectangle();

            new HorizontalEnemy(main.world, world, rect.getX()/Settings.PPM, rect.getY()/Settings.PPM);
        }
        for (MapObject object : map.getLayers().get("VEnemy").getObjects().getByType(RectangleMapObject.class)) {
            Rectangle rect = ((RectangleMapObject) object).getRectangle();

            new VerticalEnemy(main.world, world, rect.getX()/Settings.PPM, rect.getY()/Settings.PPM);
        }
    }
}
