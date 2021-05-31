package com.hush.game.Entities;

import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.World;
import com.hush.game.Main;
import com.hush.game.UI.Settings;

public class HorizontalEnemy extends Enemy{

    private float speed = 1f;
    private float moveInterval = 2;
    private float moveTimer = moveInterval;
    public Vector2 HorizontalVector = new Vector2();

    final private Animation<TextureRegion> SpriteLeft;
    final private Animation<TextureRegion> SpriteRight;
    TextureRegion Horizontalsprite;

    public HorizontalEnemy(World world, Main screen, float x, float y) {
        super(world, screen, x, y);


        SpriteLeft = new Animation<TextureRegion>(1f/5f, ta.findRegions("enemy_left"), Animation.PlayMode.LOOP);
        SpriteRight = new Animation<TextureRegion>(1f/5f, ta.findRegions("enemy_right"), Animation.PlayMode.LOOP);

        Horizontalsprite = SpriteRight.getKeyFrame(0, true);
        setRegion(Horizontalsprite);



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
        HorizontalVector.set(speed, 0);
        b2body.setLinearVelocity(HorizontalVector);


        setRegion(Horizontalsprite);
        setBounds(b2body.getPosition().x - getRegionWidth() / Settings.PPM / 2f, b2body.getPosition().y - getRegionHeight() / Settings.PPM / 2f, getRegionWidth() / Settings.PPM, getRegionHeight() / Settings.PPM);
    }

    public void changeDir(){
        speed *= -1;


    }
    public void walk() {
        if (HorizontalVector.x < 0) {
            Horizontalsprite = SpriteLeft.getKeyFrame(elapsedTime, true);
        } else if (HorizontalVector.x > 0) {
            Horizontalsprite = SpriteRight.getKeyFrame(elapsedTime, true);
        }
    }
}
