package com.hush.game.Entities;

import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.*;
import com.hush.game.Main;
import com.hush.game.UI.Settings;
import com.hush.game.World.Tags;

import java.util.Random;
/**
 * extends the parent enemy class and adds Dynamic Enemy characteristics.
 */
public class DynamicEnemy extends Enemy{
    //Initializing and defining Variables
    private boolean closeEnough = true;
    private float countMax = 4;
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

    /*
     * constructor for the DynamicEnemy class
     * @param world
     * @param screen
     * @param x
     * @param y
     * determines the sprite, position, sensor and creates the b2body of enemy.
     */
    public DynamicEnemy(World world, Main screen, float x, float y) {
        //Defining some Variables
        super(world, screen, x, y);
        r = new Random();
        pos = new Vector2(x, y);
        goToPos = pos;

        //Defines different sprites according to direction
        EnemyDown = new Animation<TextureRegion>(1f/5f, ta.findRegions("enemy_down"), Animation.PlayMode.LOOP);
        EnemyUp = new Animation<TextureRegion>(1f/5f, ta.findRegions("enemy_up"), Animation.PlayMode.LOOP);
        EnemyLeft = new Animation<TextureRegion>(1f/5f, ta.findRegions("enemy_left"), Animation.PlayMode.LOOP);
        EnemyRight = new Animation<TextureRegion>(1f/5f, ta.findRegions("enemy_right"), Animation.PlayMode.LOOP);
        Enemysprite = EnemyDown.getKeyFrame(0, true);
        setRegion(Enemysprite);




    }
    /*
     * Pre:
     * @param dt
     * updates what happens to it every frame.
     * Post: Every frame the enemy region is being updated, and is getting a new position
     * Increases sensor range and finds a new position
     */
    @Override
    public void update(float dt) {
        //updates from the super.
        super.update(dt);
        //updates the radius
        detecRadius = 20 + player.sound;
        // if the enemy is close enough it will go to a position
        closeEnough = pos.dst(goToPos) <= (speed * dt);
        walk();
        //if close enough, then it grabs a random number from a range, checks if it is possible, and then moves to it.
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
        // after all the checks, it goes to the position.
        pos.lerp(goToPos, speed);
        b2body.setTransform(pos, 0);
        //updates the sprite and set the bounds.
        setRegion(Enemysprite);
        setBounds(b2body.getPosition().x - getRegionWidth() / Settings.PPM / 2f, b2body.getPosition().y - getRegionHeight() / Settings.PPM / 2f, getRegionWidth() / Settings.PPM, getRegionHeight() / Settings.PPM);
    }
    /*
     * Pre:
     * @param toPoint, checks the point that the ray hits.
     * Post: Checks if area is blocked or not by a wall
     * returns true or false if wall is blocking or not
     */
    public boolean findPos(Vector2 toPoint){
        RayCastCallback callback = new RayCastCallback() {
            @Override
            /*
            Pre: Fixture, point, normal, fraction.
            Post: Draws the raycast, between the fixture, initial and final point.
             */
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
        //calls the raycast.
        world.rayCast(callback, pos, toPoint);
        return canMove;
    }
    /*Pre: N/A
    Post: Updates the enemy sprite based on its movement
     */
    public void walk() {

        //Checks the enemy's current and next position to find the direction it is going, then sets the sprite to the corresponding direction
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