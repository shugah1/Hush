package com.hush.game.Objects;

import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.World;
import com.hush.game.Entities.GameObject;
import com.hush.game.Entities.Player;
import com.hush.game.Main;
import com.hush.game.UI.Settings;
import com.hush.game.World.Tags;

public class KeyHole extends GameObject {

    //declaring and initializing variables
    public World world;
    public int x;
    public int y;
    public float w;
    public float h;
    private TextureAtlas ta;
    private Animation<TextureRegion> key;
    private TextureRegion sprite;
    private Player player;

    /**
     * constructor for the KeyHole
     * @param x
     * @param y
     * @param w
     * @param h
     * @param screen
     * determines the sprite, position, and creates the b2body.
     */
    public KeyHole(int x, int y, float w, float h, Main screen){
        this.world = Main.world;
        this.x = x;
        this.y = y;
        this.w = w;
        this.h = h;
        player = screen.player;

        //textures
        ta = new TextureAtlas("Sprites/Objects.atlas");
        key = new Animation<TextureRegion>(1/5f, ta.findRegions("Lock"), Animation.PlayMode.LOOP);
        sprite = key.getKeyFrame(0, true);
        setRegion(sprite);

        //creates b2body and collision masks
        BodyDef bdef = new BodyDef();
        bdef.position.set(this.x / Settings.PPM, this.y / Settings.PPM);
        bdef.type = BodyDef.BodyType.StaticBody;
        b2body = world.createBody(bdef);

        FixtureDef fdef = new FixtureDef();
        PolygonShape shape = new PolygonShape();
        shape.setAsBox(getRegionWidth() / Settings.PPM /2.15f,getRegionHeight()  / Settings.PPM /2f);
        fdef.density = 300f;
        fdef.friction = 0f;
        b2body.setFixedRotation(true);
        fdef.filter.categoryBits = Tags.DEFAULT_BIT;
        fdef.filter.maskBits = Tags.DEFAULT_BIT | Tags.PLAYER_BIT | Tags.ENEMY_BIT | Tags.PROJECTILE_BIT | Tags.DAMAGE_BIT | Tags.WALL_BIT | Tags.SWALL_BIT;

        fdef.shape = shape;
        b2body.createFixture(fdef).setUserData(this);

    }

    @Override
    public void update(float dt) {
        if(player.hasKey){
            remove = true;
        }
        setRegion(sprite);
        //setBounds(b2body.getPosition().x - getWidth()/2, b2body.getPosition().y - getHeight()/2, w/Settings.PPM*2,h/Settings.PPM*2);
        setBounds(b2body.getPosition().x - getRegionWidth() / Settings.PPM / 2f, b2body.getPosition().y - getRegionHeight() / Settings.PPM / 2f, getRegionWidth() / Settings.PPM, getRegionHeight() / Settings.PPM);
    }

}

