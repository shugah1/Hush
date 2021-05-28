package com.hush.game.Entities;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.physics.box2d.Body;
import com.hush.game.Main;

public abstract class GameObject extends Sprite {

    public Body b2body;
    public boolean remove;

    public GameObject(){
        Main.gameObjectAdd.add(this);
    }

    public abstract void update(float dt);

    public void destroy(){
        remove = true;
    }

}
