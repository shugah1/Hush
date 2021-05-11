package com.hush.game.Entities;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.*;
import com.badlogic.gdx.utils.Array;
import com.hush.game.UI.Settings;
import com.hush.game.Main;
import com.hush.game.World.Tags;

public class Player extends GameObject {
    public enum State {IDLE, MOVING_ACROSS, MOVING_UP, MOVING_DOWN, ATTACKING}
    public State currentState;
    public State previousState;
    private float SPEED;
    public World world;
    public static Body b2body;
    private Vector2 moveVector = new Vector2();
    private Animation walking;
    private Animation idle;
    private boolean movingRight;
    private float stateTimer;
    public float stamina;
    public float maxStamina;
    public boolean running;
    public boolean recharing;
    public float runSpeed = 2f;
    public float walkSpeed = 1f;
    float x;
    float y;
    Texture image = new Texture("KnightItem.png");
    Texture newImage = new Texture("Item.png");
    Sound sound = Gdx.audio.newSound(Gdx.files.internal("PowerUp1.wav"));

    public Player(World world, Main screen, float x, float y) {
        super();
        this.x = x;
        this.y = y;
        setPosition(x,y);
        this.world = world;

        currentState = State.IDLE;
        previousState = State.IDLE;
        stateTimer = 0;
        movingRight =true;
        maxStamina = 10;
        stamina = maxStamina;
        recharing = false;

        setRegion(image);
        definePlayer();
    }

    public State getState() {
        if (b2body.getLinearVelocity().y > 0)
            return State.MOVING_UP;
        else if (b2body.getLinearVelocity().y < 0)
            return State.MOVING_DOWN;
        else if (b2body.getLinearVelocity().x != 0)
            return State.MOVING_ACROSS;
        else
            return State.IDLE;
    }

    public void definePlayer(){
        BodyDef bdef = new BodyDef();
        bdef.position.set(x,y);
        bdef.type = BodyDef.BodyType.DynamicBody;
        b2body = world.createBody(bdef);

        FixtureDef fdef = new FixtureDef();
        CircleShape shape = new CircleShape();
        shape.setRadius(getRegionWidth() / Settings.PPM / 2);

        fdef.filter.categoryBits = Tags.PLAYER_BIT;
        fdef.filter.maskBits = Tags.DEFAULT_BIT | Tags.DAMAGE_BIT | Tags.ENEMY_BIT | Tags.PROJECTILE_BIT | Tags.WALL_BIT;
        fdef.shape = shape;
        b2body.createFixture(fdef).setUserData(this);
    }

    public void handleInput(float dt){
        //control our player using immediate impulses
        moveVector.set(0,0);
        if (Gdx.input.isKeyPressed(Input.Keys.W)) {
            moveVector.add(new Vector2(0,1));
        }
        if (Gdx.input.isKeyPressed(Input.Keys.S)) {
            moveVector.add(new Vector2(0,-1));
        }
        if (Gdx.input.isKeyPressed(Input.Keys.A)) {
            moveVector.add(new Vector2(-1,0));
        }
        if (Gdx.input.isKeyPressed(Input.Keys.D)) {
            moveVector.add(new Vector2(1,0));
        }
        if (!recharing) {
            running = Gdx.input.isKeyPressed(Input.Keys.SHIFT_LEFT);
        }
    }

    public void update(float deltaTime){
        handleInput(deltaTime);
        if (running) {
            stamina = Math.max( stamina - (deltaTime * 10), 0);
            if (stamina <= 0){
                running = false;
                recharing = true;
            }
            SPEED = runSpeed;
        } else {
            stamina = Math.min(stamina + (deltaTime * 3), maxStamina);
            SPEED = walkSpeed;
            recharing = !(stamina == maxStamina);
        }
        b2body.setLinearVelocity(moveVector.scl(SPEED));
        setRegion(image);
        setBounds(b2body.getPosition().x - getRegionWidth() / Settings.PPM / 2f, b2body.getPosition().y - getRegionHeight() / Settings.PPM / 2f, getRegionWidth() / Settings.PPM, getRegionHeight() / Settings.PPM);
    }
}
