package Tools;

import com.badlogic.gdx.maps.MapObject;
import com.badlogic.gdx.maps.objects.RectangleMapObject;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.physics.box2d.*;
import com.hush.game.MainMenu;
import com.hush.game.Settings;

import java.util.Set;

public class B2WorldCreator {

    public B2WorldCreator(World world, TiledMap map){

        BodyDef bdef = new BodyDef();
        PolygonShape shape = new PolygonShape();
        FixtureDef fdef = new FixtureDef();
        Body body;

        //Creates Walls
        for(MapObject object : map.getLayers().get(1).getObjects().getByType(RectangleMapObject.class)){
            Rectangle rect = ((RectangleMapObject) object).getRectangle();

            bdef.type = BodyDef.BodyType.StaticBody;
            bdef.position.set((rect.getX() + rect.getWidth() /2)/ Settings.PPM, (rect.getY() + rect.getHeight() /2)/ Settings.PPM);

            body = world.createBody(bdef);

            shape.setAsBox(rect.getWidth() /2, rect.getHeight() /2);
            fdef.shape = shape;
            body.createFixture(fdef);
        }
    }
}
