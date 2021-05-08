package com.hush.game.World;

import com.badlogic.gdx.physics.box2d.*;
import com.hush.game.Entities.Player;
import com.hush.game.Objects.DamageWall;

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
        Player player;

        switch (cDef){
            case Tags.PLAYER_BIT | Tags.DAMAGE_BIT:
                if(fixA.getFilterData().categoryBits == Tags.PLAYER_BIT ){
                    damageWall = ((DamageWall) fixB.getUserData());
                    player = ((Player) fixA.getUserData());
                }else{
                    damageWall = ((DamageWall) fixA.getUserData());
                    player = ((Player) fixB.getUserData());
                }
                damageWall.contact(player);
                break;
        }

    }

    /**
     * when the two things stop touching
     * @param contact
     */
    @Override
    public void endContact(Contact contact) {

    }

    /**
     * when something has collided you can change characteristics
     * @param contact
     * @param oldManifold
     */
    @Override
    public void preSolve(Contact contact, Manifold oldManifold) {

    }

    /**
     * the result.
     * @param contact
     * @param impulse
     */
    @Override
    public void postSolve(Contact contact, ContactImpulse impulse) {

    }
}
