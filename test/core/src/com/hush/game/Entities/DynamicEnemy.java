package com.hush.game.Entities;

import com.badlogic.gdx.ai.utils.RaycastCollisionDetector;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.*;
import com.hush.game.Main;
import com.hush.game.UI.Settings;
import com.hush.game.World.Tags;

import java.util.Random;

public class DynamicEnemy extends Enemy{
    private boolean closeEnough = true;
    private float countMax = 5;
    private float count = countMax;
    final private float range = 10;
    final private Random r;
    final private Animation<TextureRegion> EnemyUp;
    final private Animation<TextureRegion> EnemyDown;
    final private Animation<TextureRegion> EnemyLeft;
    final private Animation<TextureRegion> EnemyRight;
    public Vector2 pos;
    public Vector2 goToPos;
    public float speed = 0.01f;
    private boolean canMove = true;
    TextureRegion Enemysprite;

    public DynamicEnemy(World world, Main screen, float x, float y) {
        super(world, screen, x, y);
        r = new Random();
        ta = new TextureAtlas("Sprites/enemies.atlas");
        pos = new Vector2(x, y);
        goToPos = pos;

        EnemyDown = new Animation<TextureRegion>(1f/5f, ta.findRegions("enemy_down"), Animation.PlayMode.LOOP);
        EnemyUp = new Animation<TextureRegion>(1f/5f, ta.findRegions("enemy_up"), Animation.PlayMode.LOOP);
        EnemyLeft = new Animation<TextureRegion>(1f/5f, ta.findRegions("enemy_left"), Animation.PlayMode.LOOP);
        EnemyRight = new Animation<TextureRegion>(1f/5f, ta.findRegions("enemy_right"), Animation.PlayMode.LOOP);

        Enemysprite = EnemyDown.getKeyFrame(0, true);
        setRegion(Enemysprite);


    }

    @Override
    public void update(float dt) {
        super.update(dt);
        closeEnough = pos.dst(goToPos) <= (speed * dt);
        walk();
        if(count == 0 && closeEnough){
            count = countMax;
            float randX = r.nextFloat() * range;
            float randY = r.nextFloat() * range;
            Vector2 temp = new Vector2(randX, randY);
            if(findPos(temp)){
                goToPos = temp;
            } else {
                goToPos = pos;
                count = 0;
            }
        } else {
            count = Math.max(0, count-dt);
        }

        pos.lerp(goToPos, speed);
        b2body.setTransform(pos, 0);

        setRegion(Enemysprite);
        setBounds(b2body.getPosition().x - getRegionWidth() / Settings.PPM / 2f, b2body.getPosition().y - getRegionHeight() / Settings.PPM / 2f, getRegionWidth() / Settings.PPM, getRegionHeight() / Settings.PPM);
    }
    public boolean findPos(Vector2 toPoint){

        RayCastCallback callback = new RayCastCallback() {
            @Override
            public float reportRayFixture(Fixture fixture, Vector2 point, Vector2 normal, float fraction) {
                if(fixture.getFilterData().categoryBits == Tags.DEFAULT_BIT || fixture.getFilterData().categoryBits == Tags.DAMAGE_BIT){
                    canMove = false;
                    return 0;
                } else {
                    canMove = true;
                    return -1;
                }

            }
        };
        world.rayCast(callback, pos, toPoint);
        System.out.println(canMove);
        return canMove;
    }
    public void walk() {
        if (goToPos.y > pos.y) {
            Enemysprite = EnemyUp.getKeyFrame(elapsedTime, true);
        } else if (goToPos.y < pos.y) {
            Enemysprite = EnemyDown.getKeyFrame(elapsedTime, true);
        }

        if (goToPos.x < pos.x && (goToPos.x - pos.x) > (goToPos.y - pos.y)) {
            Enemysprite = EnemyLeft.getKeyFrame(elapsedTime, true);
        } else if (goToPos.x > pos.x) {
            Enemysprite = EnemyRight.getKeyFrame(elapsedTime, true);
        }
    }
}