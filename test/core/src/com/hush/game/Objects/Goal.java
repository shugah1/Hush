package com.hush.game.Objects;

import com.badlogic.gdx.physics.box2d.*;
import com.hush.game.Entities.Player;
import com.hush.game.UI.Settings;
import com.hush.game.Main;
import com.hush.game.World.Tags;

/**
 * Goal to reach to beat the level.
 */
public class Goal {
    //declaring and initializing variables
    public World world;
    public int x;
    public int y;
    public float w;
    public float h;
    public Fixture fix;
    public Body b2body;
    Settings settings;

    public Goal(int x, int y, float w, float h, Main screen) {
        this.world = Main.world;
        this.x = x;
        this.y = y;
        this.w = w;
        this.h = h;

        //creates b2body and collision masks
        BodyDef bdef = new BodyDef();
        bdef.position.set(this.x / Settings.PPM, this.y / Settings.PPM);
        bdef.type = BodyDef.BodyType.StaticBody;
        b2body = world.createBody(bdef);

        FixtureDef fdef = new FixtureDef();
        PolygonShape shape = new PolygonShape();
        shape.setAsBox(this.w / Settings.PPM,this.h / Settings.PPM);
        fdef.friction = 0;

        fdef.filter.categoryBits = Tags.GOAL_BIT;
        fdef.filter.maskBits = Tags.DEFAULT_BIT | Tags.PLAYER_BIT | Tags.ENEMY_BIT | Tags.PROJECTILE_BIT | Tags.WALL_BIT | Tags.SWALL_BIT;

        fdef.shape = shape;
        b2body.createFixture(fdef).setUserData(this);
    }
    /*
     * Pre: When the player comes into contact with the goal
     * @param player
     * Post: Player collects the goal on contact. Allows them to finish the level
     */

    public void contact(Player player) {
        player.win = true;

    }
}
