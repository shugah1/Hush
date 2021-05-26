package com.hush.game.Entities;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.CircleShape;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.World;
import com.hush.game.Main;
import com.hush.game.UI.Settings;
import com.hush.game.World.Tags;

public class StaticEnemy extends Enemy{

    private Animation<TextureRegion> idle;


    public StaticEnemy(World world, Main screen, float x, float y) {
        super(world, screen, x, y);
        idle = new Animation<TextureRegion>(1/5f, ta.findRegions("kevin_idle"), Animation.PlayMode.LOOP);
        setRegion(idle.getKeyFrame(elapsedTime,true));



    }


    @Override
    public void update(float dt) {
        super.update(dt);
        setRegion(idle.getKeyFrame(elapsedTime,true));
        setBounds(b2body.getPosition().x - getRegionWidth() / Settings.PPM / 2f, b2body.getPosition().y - getRegionHeight() / Settings.PPM / 2f, getRegionWidth() / Settings.PPM, getRegionHeight() / Settings.PPM);
    }
}
