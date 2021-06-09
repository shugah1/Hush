package com.hush.game.UI;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.hush.game.Entities.Player;
import com.hush.game.Main;

/**
 * shows information on the screen. i.e time, powerups.
 */
public class HUD  {
    //Initializing and defining Variables
    public Stage stage;
    private Viewport viewport;
    public static Integer worldTimer;
    private float timeCount;
    public static Integer armourInv;
    public static Integer invisInv;
    private Player player;
    Texture armourImage = new Texture("test/core/assets/HUD/Shield.png");
    Texture invisImage = new Texture("test/core/assets/HUD/invisibility-hush.png");
    Texture Stamina = new Texture("test/core/assets/HUD/LifeBarMiniUnder.png");
    Texture StaminaRed = new Texture("test/core/assets/HUD/LifeBarMiniProgress.png");
    Texture Sound = new Texture("test/core/assets/HUD/SoundBarMiniProgressCut.png");
    Texture Key = new Texture("test/core/assets/HUD/Key.png");

    SpriteBatch batch = new SpriteBatch();

    Label countdownLabel;
    Label timeLabel;
    Label keyLabel;
    Label hushLabel;
    private static Label armourLabel;
    private static Label invisLabel;
    /*
    Pre: Main, to be put on the world
    Post: adds everything onto the screen.
     */
    public HUD (Main game){
        //Defining some Variables
        worldTimer = 0;
        timeCount = 0;
        armourInv = 1;
        invisInv = 1;
        player = game.player;
        viewport = new FitViewport(Settings.V_WIDTH, Settings.V_HEIGHT, new OrthographicCamera());
        stage = new Stage(viewport);

        //Creates and sets parameters of table
        Table table = new Table();
        table.top();
        table.setFillParent(true);

        //Creates labels
        countdownLabel = new Label(String.format("%03d", worldTimer), new Label.LabelStyle(new BitmapFont(), Color.WHITE));
        timeLabel = new Label("TIME", new Label.LabelStyle(new BitmapFont(), Color.WHITE));
        keyLabel = new Label(" ", new Label.LabelStyle(new BitmapFont(), Color.WHITE));
        hushLabel = new Label("HUSH", new Label.LabelStyle(new BitmapFont(), Color.WHITE));
        armourLabel = new Label(String.format("%01d", armourInv), new Label.LabelStyle(new BitmapFont(), Color.WHITE));
        invisLabel = new Label(String.format("%01d", invisInv), new Label.LabelStyle(new BitmapFont(), Color.WHITE));

        //Adds labels to table
        table.add(hushLabel).expandX().padTop(10);
        table.add(timeLabel).expandX().padTop(10);
        table.add(invisLabel).expandX().padTop(10);
        table.row();
        table.add(keyLabel).expandX();
        table.add(countdownLabel).expandX();
        table.add(armourLabel).expandX();
        stage.addActor(table);
    }
    /*
    Pre: dt
    Post: updates the counter every frame.
     */
    public void update(float dt){
        //Counts the time and updates the time count label
        timeCount += dt;

        if (timeCount > 1){
            worldTimer ++;
            countdownLabel.setText(String.format("%03d", worldTimer));
            timeCount = 0;
        }
    }
    /*
    Pre: stun called.
    Post: Decreases the counter on the stun.
     */
    public static void stunCounter() {
        //Reduces the armour counter by 1
        if (armourInv > 0){
            armourInv -= 1;
            armourLabel.setText(String.format("%01d", armourInv));
        }
    }
    /*
    Pre: invis called.
    Post: decreases the invis counter
     */
    public static void invisCounter() {
        //Reduces the invisibility counter by 1
        if (invisInv > 0){
            invisInv -= 1;
            invisLabel.setText(String.format("%01d", invisInv));
        }

    }
    /*
    Pre: N/A
    Post: renders all the images necessary for the HUD.
     */
    public void render(){
        //Draws the stamina and sound bar
        batch.begin();
        TextureRegion StaminaRedCrop = new TextureRegion(StaminaRed, 0, 0, (int)((18 * (player.stamina / player.maxStamina))), 4);
        batch.draw(Stamina, 10, 20, Stamina.getWidth() * 10, Stamina.getHeight() * 10);
        batch.draw(StaminaRedCrop, 10, 20, StaminaRedCrop.getRegionWidth() * 10, StaminaRedCrop.getRegionHeight() * 10);
        TextureRegion SoundCrop = new TextureRegion(Sound, 0, 0, (int)((18 * (player.sound / player.maxSound))), 4);
        batch.draw(Stamina, 10, 70, Stamina.getWidth() * 10, Stamina.getHeight() * 10);
        batch.draw(SoundCrop, 10, 70, SoundCrop.getRegionWidth() * 10, SoundCrop.getRegionHeight() * 10);

        //Draws the icons for the armour and invisibility
        batch.draw(armourImage, armourLabel.getX() * 2.5f, armourLabel.getY() * 2.66f, 40, 40);
        batch.draw(invisImage, invisLabel.getX() * 2.5f, invisLabel.getY() * 2.66f, 40, 40);
        if(player.hasKey){
            batch.draw(Key, armourLabel.getX() * 0.53f, armourLabel.getY() * 2.66f, 40, 40);
        }
        batch.end();
    }
}