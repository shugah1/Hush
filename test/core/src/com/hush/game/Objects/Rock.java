package com.hush.game.Objects;

import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.physics.box2d.*;
import com.hush.game.Entities.GameObject;
import com.hush.game.Entities.Player;
import com.hush.game.UI.Settings;
import com.hush.game.Main;
import com.hush.game.World.Tags;

/**
 * Moveable wall.
 */
public class Rock extends GameObject {

    //declaring and initializing variables
    public World world;
    public int x;
    public int y;
    public float w;
    public float h;
    private TextureAtlas ta;
    private Animation<TextureRegion> key;
    private TextureRegion sprite;

    /*
     * Pre: constructor for the Rock
     * @param x
     * @param y
     * @param w
     * @param h
     * @param screen
     * Post: determines the sprite, position, and creates the b2body.
     */
    public Rock(int x, int y, float w, float h, Main screen) {
        this.world = Main.world;
        this.x = x;
        this.y = y;
        this.w = w;
        this.h = h;

        //textures
        ta = new TextureAtlas("Sprites/Objects.atlas");
        key = new Animation<TextureRegion>(1/5f, ta.findRegions("Rock"), Animation.PlayMode.LOOP);
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
        //collision
        fdef.filter.categoryBits = Tags.WALL_BIT;
        fdef.filter.maskBits = Tags.DEFAULT_BIT | Tags.PLAYER_BIT | Tags.ENEMY_BIT | Tags.PROJECTILE_BIT | Tags.DAMAGE_BIT | Tags.WALL_BIT | Tags.SWALL_BIT;

        fdef.shape = shape;
        b2body.createFixture(fdef).setUserData(this);


    }
    /*
     * Pre: When the player comes into contact with the rock
     * @param player
     * Post: Stops the rock once the player stops touching it
     */
    public void contact(Player player){
        b2body.setLinearVelocity(0f,0f);
    }

    /*Pre:
     * @param deltaTime
     * Post: Every frame the rocks region is being updated, and is getting a new position
     */
    public void update(float deltaTime){
        setRegion(sprite);
        setBounds(b2body.getPosition().x - getRegionWidth() / Settings.PPM / 2f, b2body.getPosition().y - getRegionHeight() / Settings.PPM / 2f, getRegionWidth() / Settings.PPM, getRegionHeight() / Settings.PPM);}

}
