package com.hush.game.Objects;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.*;
import com.hush.game.Entities.Player;
import com.hush.game.UI.Settings;
import com.hush.game.Screens.Main;
import com.hush.game.World.Tags;

public class MovingWall {

    //declaring and initializing variables
    public World world;
    public int x;
    public int y;
    public float w;
    public float h;
    public Fixture fix;
    public static Body b2body;
    private Vector2 moveVector = new Vector2();
    private static final float  SPEEDX = 6.9f;


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
        fdef.density = 300f;
        fdef.friction = 0f;

        fdef.filter.categoryBits = Tags.WALL_BIT;
        fdef.filter.maskBits = Tags.DEFAULT_BIT | Tags.PLAYER_BIT | Tags.ENEMY_BIT | Tags.PROJECTILE_BIT | Tags.DAMAGE_BIT;

        fdef.shape = shape;
        b2body.createFixture(fdef).setUserData(this);
        fix = b2body.createFixture(fdef);
    }

    public static void contact(Player player){
        b2body.setLinearVelocity(0f,0f);
        b2body.setFixedRotation(true);
    }
}
