package com.hush.game.Entities;

import com.badlogic.gdx.ai.steer.proximities.FieldOfViewProximity;
import com.badlogic.gdx.graphics.g2d.*;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.*;
import com.hush.game.Main;
import com.hush.game.UI.Settings;
import com.hush.game.World.Tags;
import javax.swing.text.html.HTML;

/**
 * parent class of all enemies.
 */
public abstract class Enemy extends GameObject {
    //Initializing and defining Variables
    protected World world;
    Player player;
    protected Main screen;
    private Vector2 fromPoint;
    private boolean hit = true;
    private Vector2 toPoint;
    float x;
    float y;
    public boolean toReset = false;
    protected TextureAtlas ta;
    protected Animation<TextureRegion> ring;
    protected float elapsedTime = 0f;
    public float detecRadius;

    /*
     * constructor for the Enemy parent class
     * @param world
     * @param screen
     * @param x
     * @param y
     * determines the sprite, position, sensor and creates the b2body of enemy.
     */
    public Enemy(World world, Main screen, float x, float y){
        //Defining some Variables
        this.world = world;
        this.screen = screen;
        this.player = screen.player;
        this.x = x;
        this.y = y;
        ta = Settings.manager.get("sprites/enemies.atlas");
        ring = new Animation<TextureRegion>(1/5f, ta.findRegions("detec_circle"), Animation.PlayMode.LOOP);
        detecRadius = 40 + player.sound;

        setPosition(x,y);
        defineEnemy();



    }
    /*Pre: N/A
      Post:
     * Creates and defines the enemy b2body
     * Shape, density, which bits interact of b2body
     */
    public void defineEnemy(){
        //Enemy body
        BodyDef bdef = new BodyDef();
        bdef.position.set(x, y);
        bdef.type = BodyDef.BodyType.DynamicBody;
        b2body = world.createBody(bdef);

        FixtureDef fdef = new FixtureDef();
        CircleShape shape = new CircleShape();
        shape.setRadius(10 / Settings.PPM );
        // collision masks.
        fdef.density = 10000f;
        fdef.filter.categoryBits = Tags.ENEMY_BIT;
        fdef.filter.maskBits = Tags.DEFAULT_BIT | Tags.DAMAGE_BIT | Tags.ENEMY_BIT | Tags.PROJECTILE_BIT | Tags.WALL_BIT | Tags.PLAYER_BIT;
        fdef.shape = shape;
        b2body.createFixture(fdef).setUserData(this);

        //Enemy Sensor creation
        CircleShape sensor = new CircleShape();
        sensor.setRadius(detecRadius /Settings.PPM);
        //Enemy sensor collisoin creation
        fdef.filter.categoryBits = Tags.SENSOR_BIT;
        fdef.filter.maskBits = Tags.PLAYER_BIT | Tags.DEFAULT_BIT;
        fdef.shape = sensor;
        fdef.isSensor = true;
        b2body.createFixture(fdef).setUserData(this);

    }
    /*
    Pre: N/A
    Post: recreates the enemies body every frame.
     */
    public void resetFixture() {
        //gets every enemy in the game, and destroys their body.
        for (Fixture fixture : b2body.getFixtureList()) {
            b2body.destroyFixture(fixture);
        }
        //body is then recreated the same as the constructor.
        FixtureDef fdef = new FixtureDef();
        CircleShape shape = new CircleShape();
        shape.setRadius(10 / Settings.PPM );

        fdef.density = 10000f;
        fdef.filter.categoryBits = Tags.ENEMY_BIT;
        fdef.filter.maskBits = Tags.DEFAULT_BIT | Tags.DAMAGE_BIT | Tags.ENEMY_BIT | Tags.PROJECTILE_BIT | Tags.WALL_BIT | Tags.PLAYER_BIT | Tags.SWALL_BIT;
        fdef.shape = shape;
        b2body.createFixture(fdef).setUserData(this);

        //Enemy Sensor
        CircleShape sensor = new CircleShape();
        sensor.setRadius(detecRadius /Settings.PPM);
        fdef.filter.categoryBits = Tags.SENSOR_BIT;
        fdef.filter.maskBits = Tags.PLAYER_BIT | Tags.DEFAULT_BIT;
        fdef.shape = sensor;
        fdef.isSensor = true;
        b2body.createFixture(fdef).setUserData(this);

    }

    /*
    Pre: Player
    Post: Returns true if the player is hit, if not returns false.
     */
    public boolean calculateCollisionPoint(Player player){
        fromPoint = b2body.getPosition();
        toPoint = player.b2body.getPosition().cpy();
        RayCastCallback callback = new RayCastCallback() {
            @Override
            /*
            Pre: Fixture, point, normal, fraction.
            Post: Draws the raycast, between the fixture, initial and final point.
             */
            public float reportRayFixture(Fixture fixture, Vector2 point, Vector2 normal, float fraction) {
                //checks what the enemy hits. If not the player returns false
                if (fixture.getFilterData().categoryBits == Tags.DEFAULT_BIT || fixture.getFilterData().categoryBits == Tags.WALL_BIT
                        || fixture.getFilterData().categoryBits == Tags.SWALL_BIT || fixture.getFilterData().categoryBits == Tags.DAMAGE_BIT
                        || fixture.getFilterData().categoryBits == Tags.ENEMY_BIT ) {
                    hit = false;
                    return fraction;
                }else{
                    //else returns that it hit the player.
                    hit = true;
                }
                return fraction;
            }
        };
        world.rayCast(callback, fromPoint, toPoint);
        return hit;
    }
    /*
    Pre: N/A
    Post: when called, it resets the enemy's body.
     */
    public void resetCol() {
        resetFixture();

    }
    /*
    Pre: Deltatime
    Post: returns elapsed time to be used in child classes, and reset collision for all enemies.
     */
    public void update(float dt) {
        elapsedTime += dt;
        resetCol();

    }
    /*
    Pre: SpriteBatch
    Post: uses the pre-existing spritebatch to draw the enemies sonar radius.
     */
    @Override
    public void draw(Batch batch) {
        super.draw(batch);
    }

    public void drawRing(Batch batch) {
        TextureRegion image = ring.getKeyFrame(elapsedTime);
        batch.draw(image, b2body.getPosition().x - image.getRegionWidth() * (detecRadius * 2 / 28) / Settings.PPM / 2f, b2body.getPosition().y - image.getRegionHeight() * (detecRadius * 2 / 28) / Settings.PPM / 2f, image.getRegionWidth() * (detecRadius * 2 / 28) / Settings.PPM, image.getRegionHeight() * (detecRadius * 2 / 28) / Settings.PPM);

    }
}