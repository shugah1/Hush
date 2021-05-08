package com.hush.game.Objects;

import com.badlogic.gdx.physics.box2d.*;
import com.hush.game.UI.Settings;
import com.hush.game.Screens.Main;

public class MovingWall {

    //declaring and initializing variables
    public World world;
    public int x;
    public int y;
    public float w;
    public float h;
    public Fixture fix;
    public Body b2body;


    public MovingWall(int x, int y, float w, float h, Main screen) {
        this.world = Main.world;

        this.x = x;
        this.y = y;
        this.w = w;
        this.h = h;

        BodyDef bdef = new BodyDef();
        bdef.position.set(this.x / Settings.PPM, this.y / Settings.PPM);
        bdef.type = BodyDef.BodyType.DynamicBody;
        b2body = world.createBody(bdef);

        FixtureDef fdef = new FixtureDef();
        PolygonShape shape = new PolygonShape();
        shape.setAsBox(this.w / Settings.PPM,this.h / Settings.PPM);
        fdef.friction = 0;
        fdef.shape = shape;
        fix = b2body.createFixture(fdef);
        fix.setUserData(this);
    }

    public void contact() {
        //
    }
}
