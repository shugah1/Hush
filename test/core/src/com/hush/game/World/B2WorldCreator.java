package com.hush.game.World;

import com.badlogic.gdx.maps.MapObject;
import com.badlogic.gdx.maps.objects.RectangleMapObject;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.utils.Array;
import com.hush.game.Entities.Player;
import com.hush.game.Screens.Main;
import com.hush.game.Objects.DamageWall;
import com.hush.game.Objects.MovingWall;
import com.hush.game.Objects.StaticWall;
import com.hush.game.UI.Settings;

public class B2WorldCreator {
    private Array<Player> player;

    private Main main;

    public B2WorldCreator(Main world, TiledMap map) {
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
        player = new Array<Player>();
        for (MapObject object : map.getLayers().get("Player").getObjects().getByType(RectangleMapObject.class)) {
            Rectangle rect = ((RectangleMapObject) object).getRectangle();

            player.add(new Player(main.world, world, rect.getX()/Settings.PPM, rect.getY()/Settings.PPM));
        }


    }

    public Array<Player> getPlayer(){
        return player;
    }
}
