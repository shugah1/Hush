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
import com.hush.game.Screens.Main;
import com.hush.game.World.Tags;

public class Player extends GameObject {
    public enum State {IDLE, MOVING_ACROSS, MOVING_UP, MOVING_DOWN, ATTACKING}
    public State currentState;
    public State previousState;
    private static final float  SPEEDX = 3;
    private static final float SPEEDY = 80;
    public World world;
    public static Body b2body;
    private Vector2 moveVector = new Vector2();
    private Animation walking;
    private Animation idle;
    private boolean movingRight;
    private float stateTimer;
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

        //Array<TextureRegion> frames = new Array<TextureRegion>();
        //for(int i = 1; i < 4; i++)
        //    frames.add(new TextureRegion(getTexture(), i * 16, 0, 16,16));
        //walking = new Animation(0.1f, frames);
        //frames.clear();
//
        //for(int i = 4; i<6; i++)
        //    frames.add(new TextureRegion(getTexture(), i * 16, 0, 16,16));
        //frames.clear();
        definePlayer();
        setRegion(image);
    }

    //public TextureRegion getFrame(float dt){
    //    currentState = getState();
//
    //    TextureRegion region;
    //    switch (currentState){
    //        case IDLE:
    //            region walking.getKeyFrame(stateTimer);
    //            break;
//
    //    }
    //}

    public State getState(){
        if(b2body.getLinearVelocity().y > 0)
            return State.MOVING_UP;
        else if(b2body.getLinearVelocity().y < 0)
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
        shape.setRadius(10 / Settings.PPM);

        fdef.filter.categoryBits = Tags.PLAYER_BIT;
        fdef.filter.maskBits = Tags.DEFAULT_BIT | Tags.DAMAGE_BIT | Tags.ENEMY_BIT | Tags.PROJECTILE_BIT | Tags.WALL_BIT;



        fdef.shape = shape;
        b2body.createFixture(fdef).setUserData(this);

    }

    public void handleInput(float dt){
        //control our player using immediate impulses
        moveVector.set(0,0);
        if(Gdx.input.isKeyPressed(Input.Keys.W)){
            moveVector.add(new Vector2(0,1));
        }
        if(Gdx.input.isKeyPressed(Input.Keys.S)){
            moveVector.add(new Vector2(0,-1));
        }
        if(Gdx.input.isKeyPressed(Input.Keys.A)){
            moveVector.add(new Vector2(-1,0));
        }
        if(Gdx.input.isKeyPressed(Input.Keys.D)){
            moveVector.add(new Vector2(1,0));
        }
        if(Gdx.input.isKeyPressed(Input.Keys.Q)){
            long id = sound.play(0.25f);
            setRegion(newImage);
        }
        if(Gdx.input.isKeyPressed(Input.Keys.E)){
            long id = sound.play(0.25f);
            setRegion(image);
        }

    }


    //@Override
    public void update(float deltaTime){
        handleInput(deltaTime);
        b2body.setLinearVelocity(moveVector.scl(SPEEDX));
        setBounds(b2body.getPosition().x - getWidth()/2, b2body.getPosition().y - getHeight()/2, 0.2f,0.2f);


    }



   //@Override
   //public void render(SpriteBatch batch) {
   //    batch.draw(image, pos.x, pos.y, getWidth(), getHeight());
   //}
}
