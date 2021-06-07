package com.hush.game.World;

import com.badlogic.gdx.physics.box2d.*;
import com.hush.game.Entities.*;
import com.hush.game.Objects.*;
import com.hush.game.UI.Settings;

public class WorldContactListener implements ContactListener {


    /**
     * called when 2 things start touching
     * @param contact
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

        switch (cDef){
            case Tags.PLAYER_BIT | Tags.DAMAGE_BIT:
                if(fixA.getFilterData().categoryBits == Tags.PLAYER_BIT ){
                    damageWall = ((DamageWall) fixB.getUserData());
                    player = ((Player) fixA.getUserData());
                }else{
                    damageWall = ((DamageWall) fixA.getUserData());
                    player = ((Player) fixB.getUserData());
                }
                if(!player.armored){
                    damageWall.contact(player);
                }
                break;
            case Tags.PLAYER_BIT | Tags.SENSOR_BIT:
                if(fixA.getFilterData().categoryBits == Tags.PLAYER_BIT ){
                    enemy = ((Enemy) fixB.getUserData());
                    player = ((Player) fixA.getUserData());
                }else{
                    enemy = ((Enemy) fixA.getUserData());
                    player = ((Player) fixB.getUserData());
                }

                enemy.toReset = true;
                if(!player.invis) {
                    if (enemy.calculateCollisionPoint(player)) {
                        player.pDead = true;
                        player.die();
                        //System.out.println("dead");
                    }
                }
                break;
            case Tags.PLAYER_BIT | Tags.KEY_BIT:
                if(fixA.getFilterData().categoryBits == Tags.PLAYER_BIT ){
                    key = ((Key) fixB.getUserData());
                    player = ((Player) fixA.getUserData());
                }else{
                    key = ((Key) fixA.getUserData());
                    player = ((Player) fixB.getUserData());
                }
                key.contact(player);
                break;
            case Tags.PLAYER_BIT | Tags.GOAL_BIT:
                if(fixA.getFilterData().categoryBits == Tags.PLAYER_BIT ){
                    goal = ((Goal) fixB.getUserData());
                    player = ((Player) fixA.getUserData());
                }else{
                    goal = ((Goal) fixA.getUserData());
                    player = ((Player) fixB.getUserData());
                }
                if(player.hasKey) {
                    goal.contact(player);
                }
                break;
        }
    }

    /**
     * when the two things stop touching
     * @param contact
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
            case Tags.PLAYER_BIT | Tags.WALL_BIT:
                if (fixA.getFilterData().categoryBits == Tags.PLAYER_BIT) {
                    rock= ((Rock) fixB.getUserData());
                    player = ((Player) fixA.getUserData());
                } else {
                    rock = ((Rock) fixA.getUserData());
                    player = ((Player) fixB.getUserData());
                }
                rock.contact(player);
                break;
            case Tags.PLAYER_BIT | Tags.SWALL_BIT:
                if (fixA.getFilterData().categoryBits == Tags.PLAYER_BIT) {
                    snowRock= ((SnowRock) fixB.getUserData());
                    player = ((Player) fixA.getUserData());
                } else {
                    snowRock = ((SnowRock) fixA.getUserData());
                    player = ((Player) fixB.getUserData());
                }
                snowRock.contact(player);
                break;
        }
    }

    /**
     * when something has collided you can change characteristics
     * @param contact
     * @param oldManifold
     */
    @Override
    public void preSolve(Contact contact, Manifold oldManifold) {
        //
    }

    /**
     * the result.
     * @param contact
     * @param impulse
     */
    @Override
    public void postSolve(Contact contact, ContactImpulse impulse) {
        //
    }
}
