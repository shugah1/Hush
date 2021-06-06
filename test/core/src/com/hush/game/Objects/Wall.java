package com.hush.game.Objects;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.*;
import com.hush.game.UI.Settings;
import com.hush.game.Main;
import com.hush.game.World.Tags;

public class Wall {

    //declaring and initializing variables
    public World world;
    public int x;
    public int y;
    public float w;
    public float h;
    public Fixture fix;
    public Body b2body;
    private Vector2 moveVector = new Vector2();

    /**
     * constructor for the Rock
     * @param x
     * @param y
     * @param w
     * @param h
     * @param screen
     * determines the sprite, position, and creates the b2body.
     */
    public Wall(int x, int y, float w, float h, Main screen) {
        this.world = Main.world;
        this.x = x;
        this.y = y;
        this.w = w;
        this.h = h;

        //creates b2body
        BodyDef bdef = new BodyDef();
        bdef.position.set(this.x / Settings.PPM, this.y / Settings.PPM);
        bdef.type = BodyDef.BodyType.StaticBody;
        b2body = world.createBody(bdef);

        //fixture and collision masks
        FixtureDef fdef = new FixtureDef();
        PolygonShape shape = new PolygonShape();
        shape.setAsBox(this.w / Settings.PPM,this.h / Settings.PPM);
        fdef.friction = 0;
        fdef.filter.categoryBits = Tags.DEFAULT_BIT;
        fdef.filter.maskBits = Tags.PLAYER_BIT | Tags.ENEMY_BIT | Tags.PROJECTILE_BIT | Tags.DAMAGE_BIT | Tags.WALL_BIT;
        fdef.shape = shape;
        b2body.createFixture(fdef).setUserData(this);
    }

}