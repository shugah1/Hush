package com.hush.game.Entities;

import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.World;
import com.hush.game.Main;
import com.hush.game.UI.Settings;

public class VerticalEnemy extends Enemy{

    private float speed = 1f;
    private float moveInterval = 2;
    private float moveTimer = moveInterval;
    public Vector2 VerticalVector = new Vector2();

    final private Animation<TextureRegion> SpriteUp;
    final private Animation<TextureRegion> SpriteDown;
    TextureRegion Verticalsprite;

    public VerticalEnemy(World world, Main screen, float x, float y) {

        super(world, screen, x, y);
        SpriteUp = new Animation<TextureRegion>(1f/5f, ta.findRegions("enemy_up"), Animation.PlayMode.LOOP);
        SpriteDown = new Animation<TextureRegion>(1f/5f, ta.findRegions("enemy_down"), Animation.PlayMode.LOOP);

        Verticalsprite = SpriteDown.getKeyFrame(0, true);
        setRegion(Verticalsprite);
    }
    @Override
    public void update(float dt) {

        super.update(dt);
        walk();

        if(moveTimer == 0){
            moveTimer = moveInterval;
            changeDir();
        } else {
            moveTimer = Math.max(0, moveTimer-dt);
        }
        VerticalVector.set(0, speed);
        b2body.setLinearVelocity(VerticalVector);


        setRegion(Verticalsprite);
        setBounds(b2body.getPosition().x - getRegionWidth() / Settings.PPM / 2f, b2body.getPosition().y - getRegionHeight() / Settings.PPM / 2f, getRegionWidth() / Settings.PPM, getRegionHeight() / Settings.PPM);

    }

    public void changeDir(){
        speed *= -1;


    }
    public void walk() {
        if (VerticalVector.y < 0) {
            Verticalsprite = SpriteDown.getKeyFrame(elapsedTime, true);
        } else if (VerticalVector.y > 0) {
            Verticalsprite = SpriteUp.getKeyFrame(elapsedTime, true);
        }
    }
}


