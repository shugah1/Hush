package com.hush.game.Entities;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.physics.box2d.Body;
import com.hush.game.Main;

/**
 * Gameobject class that holds all the essential (i.e player, objects,etc.) game elements into a list, to be called.
 */
public abstract class GameObject extends Sprite {
    //defines variables
    public Body b2body;
    public boolean remove;
    /*
    Pre: N/A
    Post: Constructor that adds objects to the game list.
     */
    public GameObject(){
        Main.gameObjectAdd.add(this);
    }
    /*
    Pre: deltatime; changes every frame
    Post: Gets passed onto every class that extends it.
     */
    public abstract void update(float dt);

    /*
    Pre: N/A
    Post: When called, removes the extended object from the list.
     */
    public void destroy(){
        remove = true;
    }

}
