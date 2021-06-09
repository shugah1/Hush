package com.hush.game.World;

import com.badlogic.gdx.physics.box2d.*;
import com.hush.game.Entities.*;
import com.hush.game.Objects.*;
import com.hush.game.UI.Settings;

/**
 * Handles all the collision that occurs between objects, and determines what happens when they collide.
 */
public class WorldContactListener implements ContactListener {


    /*
     * called when 2 things start touching
     * @param contact
     * Determines what happes when things start touching
     */
    @Override
    public void beginContact(Contact contact) {
        Fixture fixA = contact.getFixtureA();
        Fixture fixB = contact.getFixtureB();

        int cDef = fixA.getFilterData().categoryBits | fixB.getFilterData().categoryBits;
        DamageWall damageWall;
        Rock rock;
        Goal goal;
        Enemy enemy;
        Player player;
        Settings settings;
        Key key;
        //Switch that determines what happens when either the player touches and object or vice versa.
        switch (cDef){
            //if the player touches the damage wall.
            case Tags.PLAYER_BIT | Tags.DAMAGE_BIT:
                if(fixA.getFilterData().categoryBits == Tags.PLAYER_BIT ){
                    damageWall = ((DamageWall) fixB.getUserData());
                    player = ((Player) fixA.getUserData());
                }else{
                    damageWall = ((DamageWall) fixA.getUserData());
                    player = ((Player) fixB.getUserData());
                }
                //if the player is armoued they wont die. if they arent they will
                if(!player.armored){
                    damageWall.contact(player);
                }
                break;
                //determines what happens when the sensor bit touches the player.
            case Tags.PLAYER_BIT | Tags.SENSOR_BIT:
                if(fixA.getFilterData().categoryBits == Tags.PLAYER_BIT ){
                    enemy = ((Enemy) fixB.getUserData());
                    player = ((Player) fixA.getUserData());
                }else{
                    enemy = ((Enemy) fixA.getUserData());
                    player = ((Player) fixB.getUserData());
                }
                //resets the enemy when in contact.
                enemy.toReset = true;
                // if the enemy is not inaudible, then the enemy will calculate where the player is. If it is in its line
                //then the player will die
                if(!player.invis) {
                    if (enemy.calculateCollisionPoint(player)) {
                        player.pDead = true;
                        player.die();
                    }
                }
                break;
                //when the player collects the key.
            case Tags.PLAYER_BIT | Tags.KEY_BIT:
                if(fixA.getFilterData().categoryBits == Tags.PLAYER_BIT ){
                    key = ((Key) fixB.getUserData());
                    player = ((Player) fixA.getUserData());
                }else{
                    key = ((Key) fixA.getUserData());
                    player = ((Player) fixB.getUserData());
                }
                //if the player touches the key, its function will be called and the player collects it.
                key.contact(player);
                break;
                //when the player reaches the goal.
            case Tags.PLAYER_BIT | Tags.GOAL_BIT:
                if(fixA.getFilterData().categoryBits == Tags.PLAYER_BIT ){
                    goal = ((Goal) fixB.getUserData());
                    player = ((Player) fixA.getUserData());
                }else{
                    goal = ((Goal) fixA.getUserData());
                    player = ((Player) fixB.getUserData());
                }
                // if the player has a key, then they are able to reach the goal.
                if(player.hasKey) {
                    goal.contact(player);
                }
                break;
        }
    }

    /*
     * when the two things stop touching
     * @param contact
     * Determines what happens when two objects stop touching each other.
     */
    @Override
    public void endContact(Contact contact) {
        Fixture fixA = contact.getFixtureA();
        Fixture fixB = contact.getFixtureB();

        int cDef = fixA.getFilterData().categoryBits | fixB.getFilterData().categoryBits;
        Rock rock;
        SnowRock snowRock;
        Player player;

        switch (cDef) {
            //what happens when the player stops touches a rock
            case Tags.PLAYER_BIT | Tags.WALL_BIT:
                if (fixA.getFilterData().categoryBits == Tags.PLAYER_BIT) {
                    rock= ((Rock) fixB.getUserData());
                    player = ((Player) fixA.getUserData());
                } else {
                    rock = ((Rock) fixA.getUserData());
                    player = ((Player) fixB.getUserData());
                }
                // when the player stops touching the rock, it will stop moving
                rock.contact(player);
                break;
            //what happens when the player stops touches a snowy rock
            case Tags.PLAYER_BIT | Tags.SWALL_BIT:
                if (fixA.getFilterData().categoryBits == Tags.PLAYER_BIT) {
                    snowRock= ((SnowRock) fixB.getUserData());
                    player = ((Player) fixA.getUserData());
                } else {
                    snowRock = ((SnowRock) fixA.getUserData());
                    player = ((Player) fixB.getUserData());
                }
                // when the player stops touching the snowy rock, it will stop moving
                snowRock.contact(player);
                break;
        }
    }

    /*
     * when something has collided you can change characteristics
     * @param contact
     * @param oldManifold
     */
    @Override
    public void preSolve(Contact contact, Manifold oldManifold) {
        //useless
    }

    /*
     * the result.
     * @param contact
     * @param impulse
     */
    @Override
    public void postSolve(Contact contact, ContactImpulse impulse) {
        //useless
    }
}
