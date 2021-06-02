package com.hush.game.Entities;

import ca.error404.bytefyte.constants.Globals;
import ca.error404.bytefyte.constants.ScreenSizes;
import com.badlogic.gdx.Files;
import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.ai.fsm.DefaultStateMachine;
import com.badlogic.gdx.ai.fsm.StateMachine;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
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
import org.ini4j.Wini;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import static com.hush.game.UI.HUD.invisInv;
import static com.hush.game.UI.HUD.stun;

public class Player extends GameObject {
    Enemy enemy;
    public float SPEED;
    public float deltaTime;
    public World world;
    public Body b2body;
    public Vector2 moveVector = new Vector2();
    public boolean invis = false;
    private float invisDuration = 3;
    private float invisTimer = invisDuration;


    private Animation<TextureRegion> walkUp;
    private Animation<TextureRegion> walkDown;
    private Animation<TextureRegion> walkLeft;
    private Animation<TextureRegion> walkRight;
    private Animation<TextureRegion> item;
    private Animation<TextureRegion> dead;

    public StateMachine state;
    public float elapsedTime = 0;
    public Vector2 facing = new Vector2(0,-1);
    public static boolean pDead ;
    public boolean win;
    public boolean deadState;


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
    Settings game;
    public boolean ByteFyte;
    //Sound sound = Gdx.audio.newSound(Gdx.files.internal("PowerUp1.wav"));

    public Player(World world, Main screen, float x, float y, Settings game) {
        super();
        this.game = game;
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
        maxSound = 75;
        sound = 0;
        recharing = false;
        walkSound = false;
        runSound = false;
        pDead = false;
        win = false;
        deadState = false;


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
        if (Gdx.input.isKeyJustPressed(Input.Keys.E)) {
            HUD.stunCounter();

        }
        if (Gdx.input.isKeyJustPressed(Input.Keys.Q)) {
            if(!invis && invisInv != 0){
                HUD.invisCounter();
                invis = true;
            }

        }

        if (Gdx.input.isKeyJustPressed(Input.Keys.F5)) {
            launchByteFyte();
        }

        if (!recharing && !moveVector.isZero()) {
            running = Gdx.input.isKeyPressed(Input.Keys.SHIFT_LEFT);
        }
    }

    public void launchByteFyte() {
        try {
            // Window settings
            File settings = new File(Globals.workingDirectory + "settings.ini");

            // Checks for active save data
            if (!settings.exists()) {
                // Creates save file and writes default save data
                File file = new File(Globals.workingDirectory);
                file.mkdirs();

                settings.createNewFile();

                Wini ini = new Wini(settings);
                ini.add("Settings", "screen size", ScreenSizes.screenSize);
                ini.add("Settings", "music volume", ca.error404.bytefyte.Main.musicVolume);
                ini.add("Settings", "sfx volume", ca.error404.bytefyte.Main.sfxVolume);
                ini.add("Settings", "cutscene volume", ca.error404.bytefyte.Main.cutsceneVolume);
                ini.add("Settings", "fullscreen", ScreenSizes.fullScreen);
                ini.add("Settings", "debug", ca.error404.bytefyte.Main.debug);
                ini.add("Menu", "bill", ca.error404.bytefyte.Main.bill);
                ini.add("Menu", "stamina", ca.error404.bytefyte.Main.stamina);
                ini.store();
            } else {
                Wini ini = new Wini(settings);
                // loads save data and assigns variables
                try {
                    ScreenSizes.screenSize = Integer.parseInt(ini.get("Settings", "screen size"));
                    ca.error404.bytefyte.Main.musicVolume = Integer.parseInt(ini.get("Settings", "music volume"));
                    ca.error404.bytefyte.Main.cutsceneVolume = Integer.parseInt(ini.get("Settings", "cutscene volume"));
                    ca.error404.bytefyte.Main.sfxVolume = Integer.parseInt(ini.get("Settings", "sfx volume"));
                    ScreenSizes.fullScreen = Boolean.parseBoolean(ini.get("Settings", "fullscreen"));
                    ca.error404.bytefyte.Main.debug = Boolean.parseBoolean(ini.get("Settings", "debug"));
                    ca.error404.bytefyte.Main.bill = Boolean.parseBoolean(ini.get("Menu", "bill"));
                    ca.error404.bytefyte.Main.stamina = Boolean.parseBoolean(ini.get("Menu", "stamina"));
                } catch (Exception ignored) {

                }
            }

            // Sets screen size
            if (ScreenSizes.fullScreen) {
                Gdx.graphics.setFullscreenMode(Gdx.graphics.getDisplayMode());
            } else {
                Gdx.graphics.setWindowedMode(ScreenSizes.screenSizes.get(ScreenSizes.screenSize).get(0), ScreenSizes.screenSizes.get(ScreenSizes.screenSize).get(1));
            }

            // window title and window icon
            Gdx.graphics.setTitle("Byte Fyte");
            Gdx.graphics.setResizable(false);

            // Starts app
            new ca.error404.bytefyte.Main(game);
            Settings.music.stop();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void update(float deltaTime){
        this.deltaTime = deltaTime;
        handleInput(deltaTime);
        state.update();
        //System.out.println(state.getCurrentState());
        if (deadState && b2body != null) {
            remove = true;
        }
        if(invis && invisTimer > 0){
            invisTimer = Math.max(0, invisTimer - deltaTime);

        } else {
            invis = false;
            invisTimer = invisDuration;
        }

        if (state.getCurrentState() != PlayerState.RUN) {
            stamina = Math.min(stamina + (deltaTime * 3), maxStamina);
            SPEED = walkSpeed;
            recharing = !(stamina == maxStamina);
        }
        if(walkSound){
            sound = Math.min(sound + 0.5f, maxSound);
        }else if(runSound){
            sound = Math.min(sound + 5f, maxSound);
        }else{
            sound = Math.max(sound - 0.5f, 0);
        }

        if(b2body!= null){
            x = b2body.getPosition().x;
            y = b2body.getPosition().y;
        }
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