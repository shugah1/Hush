package com.hush.game.Objects;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.*;
import com.hush.game.Entities.GameObject;
import com.hush.game.Entities.Player;
import com.hush.game.Main;
import com.hush.game.UI.Settings;
import com.hush.game.World.Tags;

/**
 * literally only difference between this and the rock class is the sprite.
 */
public class SnowRock extends GameObject {

    //declaring and initializing variables
    public World world;
    public int x;
    public int y;
    public float w;
    public float h;
    private TextureAtlas ta;
    private Animation<TextureRegion> key;
    private TextureRegion sprite;

    /**
     * constructor for the SnowRock
     * @param x
     * @param y
     * @param w
     * @param h
     * @param screen
     * determines the sprite, position, and creates the b2body.
     */
    public SnowRock(int x, int y, float w, float h, Main screen) {
        super();
        this.world = Main.world;
        this.x = x;
        this.y = y;
        this.w = w;
        this.h = h;

        //textures
        ta = new TextureAtlas("Sprites/Objects.atlas");
        key = new Animation<TextureRegion>(1/5f, ta.findRegions("SnowRock"), Animation.PlayMode.LOOP);
        sprite = key.getKeyFrame(0, true);
        setRegion(sprite);

        //creates b2body and collision masks
        BodyDef bdef = new BodyDef();
        bdef.position.set(this.x / Settings.PPM, this.y / Settings.PPM);
        bdef.type = BodyDef.BodyType.DynamicBody;
        b2body = world.createBody(bdef);

        FixtureDef fdef = new FixtureDef();
        PolygonShape shape = new PolygonShape();
        shape.setAsBox(getRegionWidth() / Settings.PPM /2.15f,getRegionHeight()  / Settings.PPM /2f);
        fdef.density = 300f;
        fdef.friction = 0f;
        b2body.setFixedRotation(true);
        fdef.filter.categoryBits = Tags.SWALL_BIT;
        fdef.filter.maskBits = Tags.DEFAULT_BIT | Tags.PLAYER_BIT | Tags.ENEMY_BIT | Tags.PROJECTILE_BIT | Tags.DAMAGE_BIT | Tags.WALL_BIT;

        fdef.shape = shape;
        b2body.createFixture(fdef).setUserData(this);


    }

    /**
     * When the player comes into contact with the rock
     * @param player
     * Stops the rock once the player stops touching it
     */
    public void contact(Player player){
        b2body.setLinearVelocity(0f,0f);
    }

    /**
     * Every frame the rocks region is being updated, and is getting a new position
     * @param deltaTime
     */
    public void update(float deltaTime){
        setRegion(sprite);
        //setBounds(b2body.getPosition().x - getWidth()/2, b2body.getPosition().y - getHeight()/2, w/Settings.PPM*2,h/Settings.PPM*2);
        setBounds(b2body.getPosition().x - getRegionWidth() / Settings.PPM / 2f, b2body.getPosition().y - getRegionHeight() / Settings.PPM / 2f, getRegionWidth() / Settings.PPM, getRegionHeight() / Settings.PPM);}
}

