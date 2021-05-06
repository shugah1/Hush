package com.hush.game.Tools;

import com.badlogic.gdx.physics.box2d.*;
import com.hush.game.Settings;
import com.hush.game.Tutorial;
import com.hush.game.World.TiledGameMap;

public class Wall {

    //declaring and initializing variables
    public World world;
    public int x;
    public int y;
    public float w;
    public float h;
    public Fixture fix;
    public Body b2body;


    public Wall(int x, int y, float w, float h, Tutorial screen) {
        this.world = Tutorial.world;

        this.x = x;
        this.y = y;
        this.w = w;
        this.h = h;

        BodyDef bdef = new BodyDef();
        bdef.position.set(this.x / Settings.PPM, this.y / Settings.PPM);
        bdef.type = BodyDef.BodyType.StaticBody;
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