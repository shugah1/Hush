package Entities;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.*;
import com.hush.game.MainMenu;
import com.hush.game.Settings;

public class Player extends Sprite {

    private static final int SPEEDX = 80;
    private static final int SPEEDY = 80;
    public World world;
    public static Body b2body;



    public Player(World world) {
        this.world = world;
        definePlayer();
        update(1);
    }

    public void definePlayer(){
        BodyDef bdef = new BodyDef();
        bdef.position.set(5/ Settings.PPM,15/ Settings.PPM);
        bdef.type = BodyDef.BodyType.DynamicBody;
        b2body = world.createBody(bdef);

        FixtureDef fdef = new FixtureDef();
        CircleShape shape = new CircleShape();
        shape.setRadius(5 / Settings.PPM);


        fdef.shape = shape;
        b2body.createFixture(fdef);
    }

    public static void handleInput(float dt){
        //control our player using immediate impulses
        if(Gdx.input.isKeyPressed(Input.Keys.W) && b2body.getLinearVelocity().y <= 2)
            b2body.applyLinearImpulse(new Vector2(0,0.1f), b2body.getWorldCenter(), true);
        if(Gdx.input.isKeyPressed(Input.Keys.D) && b2body.getLinearVelocity().x <= 2)
            b2body.applyLinearImpulse(new Vector2(0.1f,0), b2body.getWorldCenter(), true);
        if(Gdx.input.isKeyPressed(Input.Keys.A)&& b2body.getLinearVelocity().x >= -2)
            b2body.applyLinearImpulse(new Vector2(-0.1f,0), b2body.getWorldCenter(), true);
        if(Gdx.input.isKeyPressed(Input.Keys.S) && b2body.getLinearVelocity().y >= -2)
            b2body.applyLinearImpulse(new Vector2(0,-0.1f), b2body.getWorldCenter(), true);

    }


    //@Override
    public void update(float deltaTime){



    }



   //@Override
   //public void render(SpriteBatch batch) {
   //    batch.draw(image, pos.x, pos.y, getWidth(), getHeight());
   //}
}
