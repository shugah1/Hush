package com.hush.game.Entities;

import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.World;
import com.hush.game.Main;
import com.hush.game.UI.Settings;
/**
 * extends the parent enemy class and adds Vertical Enemy characteristics.
 */
public class VerticalEnemy extends Enemy{
    //Initializing and defining Variables
    private float speed = 1f;
    private float moveInterval = 2;
    private float moveTimer = moveInterval;
    public Vector2 VerticalVector = new Vector2();

    final private Animation<TextureRegion> SpriteUp;
    final private Animation<TextureRegion> SpriteDown;
    TextureRegion Verticalsprite;

    /*
     * constructor for the VerticalEnemy class
     * Pre:
     * @param world
     * @param screen
     * @param x
     * @param y
     * Post:
     * determines the sprite, position, sensor and creates the b2body of enemy.
     */
    public VerticalEnemy(World world, Main screen, float x, float y) {
        super(world, screen, x, y);

        //Defines different sprites according to direction
        SpriteUp = new Animation<TextureRegion>(1f/5f, ta.findRegions("VEnemyUp"), Animation.PlayMode.LOOP);
        SpriteDown = new Animation<TextureRegion>(1f/5f, ta.findRegions("VEnemyDown"), Animation.PlayMode.LOOP);
        Verticalsprite = SpriteDown.getKeyFrame(0, true);
        setRegion(Verticalsprite);
    }
    /*
    Pre: Takes in the parameter delta time, which is every frame change
    Post: Updates the enemy position, radius and size every frame.
     */
    @Override
    public void update(float dt) {
        //updates based on the parent
        super.update(dt);
        //updates the radius.
        detecRadius = 20 + player.sound;
        walk();

        //Creates a timer than changes the direction of the enemy
        if(moveTimer == 0){
            moveTimer = moveInterval;
            changeDir();
        } else {
            moveTimer = Math.max(0, moveTimer-dt);
        }
        VerticalVector.set(0, speed);
        b2body.setLinearVelocity(VerticalVector);

        //Sets the sprite and bounds of the enemy
        setRegion(Verticalsprite);
        setBounds(b2body.getPosition().x - getRegionWidth() / Settings.PPM / 2f, b2body.getPosition().y - getRegionHeight() / Settings.PPM / 2f, getRegionWidth() / Settings.PPM, getRegionHeight() / Settings.PPM);

    }
    /*
    Pre: N/A
    Post: Causes the enemy to turn around when called.
     */
    public void changeDir(){
        speed *= -1;
    }
    /*
   Pre:N/A
   Post: Calls on the function and causes the enemy to walk.
    */
    public void walk() {
        //Checks the directional vector of the enemy sets the sprite to the corresponding direction
        if (VerticalVector.y < 0) {
            Verticalsprite = SpriteDown.getKeyFrame(elapsedTime, true);
        } else if (VerticalVector.y > 0) {
            Verticalsprite = SpriteUp.getKeyFrame(elapsedTime, true);
        }
    }
}


