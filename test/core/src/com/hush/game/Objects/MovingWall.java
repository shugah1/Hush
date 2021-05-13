package com.hush.game.Objects;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.*;
import com.hush.game.Entities.GameObject;
import com.hush.game.Entities.Player;
import com.hush.game.UI.Settings;
import com.hush.game.Main;
import com.hush.game.World.Tags;

public class MovingWall extends GameObject {

    //declaring and initializing variables
    public World world;
    public int x;
    public int y;
    public float w;
    public float h;
    public Fixture fix;
    private Vector2 moveVector = new Vector2();
    private static final float  SPEEDX = 6.9f;
    Texture image = new Texture("Rock.png");

    public MovingWall(int x, int y, float w, float h, Main screen) {
        super();
        this.world = Main.world;
        this.x = x;
        this.y = y;
        this.w = w;
        this.h = h;
        setRegion(image);

        BodyDef bdef = new BodyDef();
        bdef.position.set(this.x / Settings.PPM, this.y / Settings.PPM);
        bdef.type = BodyDef.BodyType.DynamicBody;
        b2body = world.createBody(bdef);

        FixtureDef fdef = new FixtureDef();
        PolygonShape shape = new PolygonShape();
        shape.setAsBox(getRegionWidth() / 20 / Settings.PPM,getRegionHeight() / 20 / Settings.PPM);
        fdef.density = 300f;
        fdef.friction = 0f;
        b2body.setFixedRotation(true);
        fdef.filter.categoryBits = Tags.WALL_BIT;
        fdef.filter.maskBits = Tags.DEFAULT_BIT | Tags.PLAYER_BIT | Tags.ENEMY_BIT | Tags.PROJECTILE_BIT | Tags.DAMAGE_BIT | Tags.WALL_BIT;

        fdef.shape = shape;
        b2body.createFixture(fdef).setUserData(this);


    }

    public void contact(Player player){
        b2body.setLinearVelocity(0f,0f);
    }

    public void update(float deltaTime){
        setRegion(image);
        //setBounds(b2body.getPosition().x - getWidth()/2, b2body.getPosition().y - getHeight()/2, w/Settings.PPM*2,h/Settings.PPM*2);
        setBounds(b2body.getPosition().x - getRegionWidth() / Settings.PPM / 2f, b2body.getPosition().y - getRegionHeight() / Settings.PPM / 2f, getRegionWidth() / Settings.PPM, getRegionHeight() / Settings.PPM);
    }

}
