package com.hush.game.Entities;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.physics.box2d.World;
import com.hush.game.Main;
import com.hush.game.UI.Settings;

public class StaticEnemy extends Enemy{
    //Initializing and defining Variables
    private Animation<TextureRegion> idle;
    private float elapsedTime = 0f;

    public StaticEnemy(World world, Main screen, float x, float y) {
        //Defining some Variables
        super(world, screen, x, y);
        TextureAtlas ta = new TextureAtlas("Sprites/enemies.atlas");

        //Defines different sprites according to direction
        idle = new Animation<TextureRegion>(1/5f, ta.findRegions("kevin_idle"), Animation.PlayMode.LOOP);
        setRegion(idle.getKeyFrame(elapsedTime,true));
    }

    @Override
    public void update(float dt) {
        super.update(dt);
        detecRadius = 20 + player.sound;
        setRegion(idle.getKeyFrame(elapsedTime,true));
        setBounds(b2body.getPosition().x - getRegionWidth() / Settings.PPM / 2f, b2body.getPosition().y - getRegionHeight() / Settings.PPM / 2f, getRegionWidth() / Settings.PPM, getRegionHeight() / Settings.PPM);
    }
}
