package com.hush.game.Objects;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.physics.box2d.*;
import com.hush.game.Entities.GameObject;
import com.hush.game.Entities.Player;
import com.hush.game.UI.Settings;
import com.hush.game.Main;
import com.hush.game.World.Tags;

public class Key extends GameObject {

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
     * constructor for the Rock
     * @param x - gets x
     * @param y - gets y
     * @param w - gets width
     * @param h - gets height
     * @param screen - gets the game location
     * determines the sprite, position, and creates the b2body.
     */
    public Key(int x, int y, float w, float h, Main screen) {
        super();
        this.world = Main.world;
        this.x = x;
        this.y = y;
        this.w = w;
        this.h = h;

        //textures
        ta = new TextureAtlas("Sprites/Objects.atlas");
        key = new Animation<TextureRegion>(1/5f, ta.findRegions("GoldKey"), Animation.PlayMode.LOOP);
        sprite = key.getKeyFrame(0, true);
        setRegion(sprite);

        //creates b2body and collision masks
        BodyDef bdef = new BodyDef();
        bdef.position.set(this.x / Settings.PPM, this.y / Settings.PPM);
        bdef.type = BodyDef.BodyType.StaticBody;
        b2body = world.createBody(bdef);

        FixtureDef fdef = new FixtureDef();
        PolygonShape shape = new PolygonShape();
        shape.setAsBox(getRegionWidth() / Settings.PPM,getRegionHeight() / Settings.PPM);
        fdef.friction = 0;
        fdef.filter.categoryBits = Tags.KEY_BIT;
        fdef.filter.maskBits = Tags.PLAYER_BIT | Tags.ENEMY_BIT | Tags.PROJECTILE_BIT | Tags.DAMAGE_BIT | Tags.WALL_BIT | Tags.SWALL_BIT;
        fdef.shape = shape;
        b2body.createFixture(fdef).setUserData(this);

    }

    /**
     * When the player comes into contact with the key
     * @param player
     * Player collects the key on contact. Allows them to finish the level
     */
    public void contact(Player player){
        player.hasKey = true;
        remove = true;
    }

    /**
     * Every frame the rocks region is being updated, and is getting a new position
     * @param deltaTime
     */
    public void update(float deltaTime){
        setRegion(sprite);
        //setBounds(x - getWidth()/2f, b2body.getPosition().y - getHeight()/2f, getRegionWidth()/Settings.PPM,getRegionHeight()/Settings.PPM);
        setBounds(b2body.getPosition().x - getRegionWidth() / Settings.PPM / 2f, b2body.getPosition().y - getRegionHeight() / Settings.PPM / 2f, getRegionWidth() / Settings.PPM *1.25f, getRegionHeight() / Settings.PPM*1.25f);

    }

}
