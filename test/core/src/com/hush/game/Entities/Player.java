package com.hush.game.Entities;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.*;
import com.hush.game.Settings;

public class Player extends Sprite {

    private static final float  SPEEDX = 6.9f;
    private static final float SPEEDY = 80;
    public World world;
    public static Body b2body;
    private Vector2 moveVector = new Vector2();
    Texture image = new Texture("badlogic.jpg");



    public Player(World world) {
        this.world = world;
        definePlayer();
        setRegion(image);
    }

    public void definePlayer(){
        BodyDef bdef = new BodyDef();
        bdef.position.set(5/ Settings.PPM,15/ Settings.PPM);
        bdef.type = BodyDef.BodyType.DynamicBody;
        b2body = world.createBody(bdef);

        FixtureDef fdef = new FixtureDef();
        CircleShape shape = new CircleShape();
        shape.setRadius(100 / Settings.PPM);


        fdef.shape = shape;
        b2body.createFixture(fdef);

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


    }


    //@Override
    public void update(float deltaTime){
        handleInput(deltaTime);
        b2body.setLinearVelocity(moveVector.scl(SPEEDX));
        setBounds(b2body.getPosition().x - getWidth()/2, b2body.getPosition().y - getHeight()/2, 1,2);



    }



   //@Override
   //public void render(SpriteBatch batch) {
   //    batch.draw(image, pos.x, pos.y, getWidth(), getHeight());
   //}
}
