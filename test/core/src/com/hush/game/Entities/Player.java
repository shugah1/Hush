package com.hush.game.Entities;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.ai.fsm.DefaultStateMachine;
import com.badlogic.gdx.ai.fsm.StateMachine;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.*;
import com.badlogic.gdx.utils.Array;
import com.hush.game.UI.HUD;
import com.hush.game.UI.Settings;
import com.hush.game.Main;
import com.hush.game.World.Tags;
import com.hush.game.states.PlayerState;

import static com.hush.game.UI.HUD.invis;
import static com.hush.game.UI.HUD.stun;

public class Player extends GameObject {
    public float SPEED;
    public float deltaTime;
    public World world;
    public Body b2body;
    public Vector2 moveVector = new Vector2();

    private Animation<TextureRegion> walkUp;
    private Animation<TextureRegion> walkDown;
    private Animation<TextureRegion> walkLeft;
    private Animation<TextureRegion> walkRight;
    private Animation<TextureRegion> item;
    private Animation<TextureRegion> dead;

    public StateMachine state;
    public float elapsedTime = 0;
    public Vector2 facing = new Vector2(0,-1);

    public boolean deadState = false;
    private float stateTimer;
    public float stamina;
    public float maxStamina;
    public float sound;
    public float maxSound;
    public boolean walkSound;
    public boolean runSound;
    public boolean running;
    public boolean recharing;
    public float runSpeed = 2f;
    public float walkSpeed = 1f;
    public float x;
    public float y;
    TextureRegion sprite;
    Texture image = new Texture("KnightItem.png");
    Texture newImage = new Texture("Item.png");
    //Sound sound = Gdx.audio.newSound(Gdx.files.internal("PowerUp1.wav"));

    public Player(World world, Main screen, float x, float y) {
        super();
        this.x = x;
        this.y = y;
        setPosition(x,y);
        this.world = world;
        state = new DefaultStateMachine<>(this, PlayerState.IDLE);

        TextureAtlas textureAtlas = Settings.manager.get("sprites/player.atlas", TextureAtlas.class);

        walkDown = new Animation<TextureRegion>(1f/5f, textureAtlas.findRegions("walk_down"), Animation.PlayMode.LOOP);
        walkUp = new Animation<TextureRegion>(1f/5f, textureAtlas.findRegions("walk_up"), Animation.PlayMode.LOOP);
        walkLeft = new Animation<TextureRegion>(1f/5f, textureAtlas.findRegions("walk_left"), Animation.PlayMode.LOOP);
        walkRight = new Animation<TextureRegion>(1f/5f, textureAtlas.findRegions("walk_right"), Animation.PlayMode.LOOP);

        dead = new Animation<TextureRegion>(1f/5f, textureAtlas.findRegions("dead"), Animation.PlayMode.LOOP);
        item = new Animation<TextureRegion>(1f/5f, textureAtlas.findRegions("item"), Animation.PlayMode.LOOP);

        sprite = walkDown.getKeyFrame(0, true);
        setRegion(sprite);

        stateTimer = 0;
        maxStamina = 10;
        stamina = maxStamina;
        maxSound = 100;
        sound = 0;
        recharing = false;
        walkSound = false;
        runSound = false;

        definePlayer();
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
        fdef.filter.maskBits = Tags.DEFAULT_BIT | Tags.DAMAGE_BIT | Tags.ENEMY_BIT | Tags.PROJECTILE_BIT | Tags.WALL_BIT | Tags.SENSOR_BIT | Tags.GOAL_BIT;
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
        if (Gdx.input.isKeyJustPressed(Input.Keys.Q)) {
            HUD.stunCounter();
        }
        if (Gdx.input.isKeyJustPressed(Input.Keys.E)) {
            HUD.invisCounter();
        }
        if (!recharing) {
            running = Gdx.input.isKeyPressed(Input.Keys.SHIFT_LEFT);
        }
    }

    public void update(float deltaTime){
        if (deadState && b2body != null) {
            world.destroyBody(b2body);
            b2body = null;
        }
        this.deltaTime = deltaTime;
        handleInput(deltaTime);
        state.update();

        if (state.getCurrentState() != PlayerState.RUN) {
            stamina = Math.min(stamina + (deltaTime * 3), maxStamina);
            SPEED = walkSpeed;
            recharing = !(stamina == maxStamina);
        }

        if (b2body != null) {
            x = b2body.getPosition().x;
            y = b2body.getPosition().y;
        }
        if(walkSound){
            sound = Math.min(sound + 1f, maxSound);
        }else if(runSound){
            sound = Math.min(sound + 5f, maxSound);
        }else{
            sound = Math.max(sound - 0.5f, 0);
        }
        System.out.println(sound);
        setRegion(sprite);
        setBounds(x - getRegionWidth() / Settings.PPM / 2f, y - getRegionHeight() / Settings.PPM / 2f, getRegionWidth() / Settings.PPM, getRegionHeight() / Settings.PPM);
    }

    public void idle() {
        if (facing.x == 0) {
            if (facing.y <= 0) {
                sprite = walkDown.getKeyFrame(elapsedTime, true);
            } else {
                sprite = walkUp.getKeyFrame(elapsedTime, true);
            }
        } else {
            if (facing.x > 0) {
                sprite = walkRight.getKeyFrame(elapsedTime, true);
            } else {
                sprite = walkLeft.getKeyFrame(elapsedTime, true);
            }
        }
    }

    public void walk() {
        if (moveVector.y > 0) {
            sprite = walkUp.getKeyFrame(elapsedTime, true);
        } else {
            sprite = walkDown.getKeyFrame(elapsedTime, true);
        }

        if (moveVector.x < 0) {
            sprite = walkLeft.getKeyFrame(elapsedTime, true);
        } else if (moveVector.x > 0) {
            sprite = walkRight.getKeyFrame(elapsedTime, true);
        }

        facing = moveVector.cpy();
    }

    public void die() {
        deadState = true;
        state.changeState(PlayerState.DEAD);
    }

    public void deadAction() {
        sprite = dead.getKeyFrame(0, false);
    }
}