package com.hush.game.Entities;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.World;
import com.hush.game.Screens.Main;

public abstract class Enemy extends GameObject {
    protected World world;
    protected Main screen;
    public Body b2body;

    public Enemy(World world, Main screen, float x, float y){
        this.world = world;
        this.screen = screen;
        setPosition(x,y);
    }

    protected abstract void defineEnemy();
    public abstract void update();
    public abstract void update(float dt);
}
