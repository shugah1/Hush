package com.hush.game.Tools;

import com.badlogic.gdx.maps.MapObject;
import com.badlogic.gdx.maps.objects.RectangleMapObject;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.math.Rectangle;
import com.hush.game.Tutorial;

public class B2WorldCreator {

    public B2WorldCreator(Tutorial world, TiledMap map) {

        //Creates Walls
        for (MapObject object : map.getLayers().get("Wall").getObjects().getByType(RectangleMapObject.class)) {
            Rectangle rect = ((RectangleMapObject) object).getRectangle();

            new StaticWall((int) (rect.getX() + rect.getWidth() / 2), (int) (rect.getY() + rect.getHeight() / 2), rect.getWidth() / 2f, rect.getHeight() / 2f, world);

        }

        for (MapObject object : map.getLayers().get("MWall").getObjects().getByType(RectangleMapObject.class)) {
            Rectangle rect = ((RectangleMapObject) object).getRectangle();

            new MovingWall((int) (rect.getX() + rect.getWidth() / 2), (int) (rect.getY() + rect.getHeight() / 2), rect.getWidth() / 2f, rect.getHeight() / 2f, world);
        }


    }
}
