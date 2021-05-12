package com.hush.game.Entities;

import com.badlogic.gdx.ai.steer.proximities.FieldOfViewProximity;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.*;
import com.hush.game.Main;
import com.hush.game.UI.Settings;
import com.hush.game.World.Tags;

import javax.swing.text.html.HTML;

public abstract class Enemy extends GameObject {
    Player player;
    protected World world;
    protected Main screen;
    float x;
    float y;


    public Enemy(World world, Main screen, float x, float y){
        this.world = world;
        this.screen = screen;
        this.x = x;
        this.y = y;
        setPosition(x,y);
        defineEnemy();


    }

    public void defineEnemy(){
        //Enemy body
        BodyDef bdef = new BodyDef();
        bdef.position.set(x,y);
        bdef.type = BodyDef.BodyType.KinematicBody;
        b2body = world.createBody(bdef);

        FixtureDef fdef = new FixtureDef();
        CircleShape shape = new CircleShape();
        shape.setRadius(20 / Settings.PPM );

        fdef.filter.categoryBits = Tags.ENEMY_BIT;
        fdef.filter.maskBits = Tags.DEFAULT_BIT | Tags.DAMAGE_BIT | Tags.ENEMY_BIT | Tags.PROJECTILE_BIT | Tags.WALL_BIT | Tags.PLAYER_BIT;
        fdef.shape = shape;
        b2body.createFixture(fdef).setUserData(this);

        //Enemy Sensor
        CircleShape sensor = new CircleShape();
        sensor.setRadius(100 / Settings.PPM);
        fdef.filter.categoryBits = Tags.SENSOR_BIT;
        fdef.filter.maskBits = Tags.PLAYER_BIT;
        fdef.shape = sensor;
        fdef.isSensor = true;
        b2body.createFixture(fdef).setUserData(this);



    }

    public abstract void update(float dt);

}
