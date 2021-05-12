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
    protected World world;
    protected Main screen;
    private Vector2 fromPoint;
    private Vector2 point;
    private boolean hit = true;
    private Vector2 toPoint;
    private Vector2 normal = new Vector2();
    private Vector2 collisionPoint = new Vector2();
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
        fdef.filter.maskBits = Tags.PLAYER_BIT | Tags.DEFAULT_BIT;
        fdef.shape = sensor;
        fdef.isSensor = true;
        b2body.createFixture(fdef).setUserData(this);

    }

    public boolean calculateCollisionPoint(Player player){
        fromPoint = b2body.getPosition();
        toPoint = player.b2body.getPosition().cpy();
        collisionPoint = new Vector2();
        RayCastCallback callback = new RayCastCallback() {
            @Override
            public float reportRayFixture(Fixture fixture, Vector2 point, Vector2 normal, float fraction) {
                //System.out.println("To point" + toPoint);
                System.out.println(point);
                if (fixture.getFilterData().categoryBits == Tags.DEFAULT_BIT || fixture.getFilterData().categoryBits == Tags.WALL_BIT ) {
                    hit = false;
                    return fraction;
                }else{
                    hit = true;
                }

                return fraction;

            }
        };
        //System.out.println(fromPoint + " " + toPoint);
        world.rayCast(callback, fromPoint, toPoint);
        System.out.println(hit);
        return hit;

    }

    public abstract void update(float dt);

}
