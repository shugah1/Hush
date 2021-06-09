package com.hush.game.Objects;

import com.badlogic.gdx.physics.box2d.*;
import com.hush.game.Entities.Player;
import com.hush.game.UI.Settings;
import com.hush.game.Main;
import com.hush.game.World.Tags;

/**
 * similar to Wall class, but on contact kills the player.
 */
public class DamageWall {

    //declaring and initializing variables
    public World world;
    public int x;
    public int y;
    public float w;
    public float h;
    public Fixture fix;
    public Body b2body;

    /*
     * constructor for the DamageWall
     * Pre:
     * @param x
     * @param y
     * @param w
     * @param h
     * @param screen
     * Post: determines the sprite, position, and creates the b2body.
     */
    public DamageWall(int x, int y, float w, float h, Main screen) {
        this.world = Main.world;
        this.x = x;
        this.y = y;
        this.w = w;
        this.h = h;

        //creates the b2body and collision masks
        BodyDef bdef = new BodyDef();
        bdef.position.set(this.x / Settings.PPM, this.y / Settings.PPM);
        bdef.type = BodyDef.BodyType.StaticBody;
        b2body = world.createBody(bdef);

        FixtureDef fdef = new FixtureDef();
        PolygonShape shape = new PolygonShape();
        shape.setAsBox(this.w / Settings.PPM,this.h / Settings.PPM);
        fdef.friction = 0;
        //collision.
        fdef.filter.categoryBits = Tags.DAMAGE_BIT;
        fdef.filter.maskBits = Tags.DEFAULT_BIT | Tags.PLAYER_BIT | Tags.ENEMY_BIT | Tags.PROJECTILE_BIT | Tags.WALL_BIT | Tags.SWALL_BIT;

        fdef.shape = shape;
        b2body.createFixture(fdef).setUserData(this);
    }

    /*
     * On contact with the player, the player will die.
     * @param player
     */
    public void contact(Player player) {
        player.pDead = true;
        player.die();
    }
}