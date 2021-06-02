package com.hush.game.UI;

import com.badlogic.gdx.Gdx;
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


public class HUD  {
    public Stage stage;
    private Viewport viewport;
    public static Integer worldTimer;
    private float timeCount;
    public static Integer stun;
    public static Integer invisInv;
    private Player player;
    Texture stunImage = new Texture("test/core/assets/HUD/ScrollThunder.png");
    Texture invisImage = new Texture("test/core/assets/HUD/invisibility-hush.png");
    Texture Stamina = new Texture("test/core/assets/HUD/LifeBarMiniUnder.png");
    Texture StaminaRed = new Texture("test/core/assets/HUD/LifeBarMiniProgress.png");
    Texture Sound = new Texture("test/core/assets/HUD/SoundBarMiniProgressCut.png");

    SpriteBatch batch = new SpriteBatch();

    Label countdownLabel;
    Label timeLabel;
    Label levelLabel;
    Label worldLabel;
    private static Label stunLabel;
    private static Label invisLabel;

    public HUD (Main game){
        worldTimer = 0;
        timeCount = 0;
        stun = 3;
        invisInv = 3;
        player = game.player;
        viewport = new FitViewport(Settings.V_WIDTH, Settings.V_HEIGHT, new OrthographicCamera());
        stage = new Stage(viewport);

        Table table = new Table();
        table.top();
        table.setFillParent(true);

        countdownLabel = new Label(String.format("%03d", worldTimer), new Label.LabelStyle(new BitmapFont(), Color.WHITE));
        timeLabel = new Label("TIME", new Label.LabelStyle(new BitmapFont(), Color.WHITE));
        levelLabel = new Label("1-1", new Label.LabelStyle(new BitmapFont(), Color.WHITE));
        worldLabel = new Label("WORLD", new Label.LabelStyle(new BitmapFont(), Color.WHITE));
        stunLabel = new Label(String.format("%01d", stun), new Label.LabelStyle(new BitmapFont(), Color.WHITE));
        invisLabel = new Label(String.format("%01d", invisInv), new Label.LabelStyle(new BitmapFont(), Color.WHITE));

        table.add(worldLabel).expandX().padTop(10);
        table.add(timeLabel).expandX().padTop(10);
        table.add(invisLabel).expandX().padTop(10);
        table.row();
        table.add(levelLabel).expandX();
        table.add(countdownLabel).expandX();

        table.add(stunLabel).expandX();

        stage.addActor(table);
    }
    public void update(float dt){
        timeCount += dt;
        if (timeCount > 1){
            worldTimer ++;
            countdownLabel.setText(String.format("%03d", worldTimer));
            timeCount = 0;
        }
    }
    public static void stunCounter() {
        if (stun > 0){
            stun -= 1;
            stunLabel.setText(String.format("%01d", stun));
            System.out.println(stunLabel.getX());
        }
    }
    public static void invisCounter() {
        if (invisInv > 0){
            invisInv -= 1;
            invisLabel.setText(String.format("%01d", invisInv));
        }
    }
    public void render(){
        batch.begin();
        TextureRegion StaminaRedCrop = new TextureRegion(StaminaRed, 0, 0, (int)((18 * (player.stamina / player.maxStamina))), 4);
        batch.draw(Stamina, 10, 20, Stamina.getWidth() * 10, Stamina.getHeight() * 10);
        batch.draw(StaminaRedCrop, 10, 20, StaminaRedCrop.getRegionWidth() * 10, StaminaRedCrop.getRegionHeight() * 10);

        TextureRegion SoundCrop = new TextureRegion(Sound, 0, 0, (int)((18 * (player.sound / player.maxSound))), 4);
        batch.draw(Stamina, 10, 70, Stamina.getWidth() * 10, Stamina.getHeight() * 10);
        batch.draw(SoundCrop, 10, 70, SoundCrop.getRegionWidth() * 10, SoundCrop.getRegionHeight() * 10);
        batch.draw(stunImage, stunLabel.getX() * 2.5f, stunLabel.getY() * 2.66f, 40, 40);
        batch.draw(invisImage, invisLabel.getX() * 2.5f, invisLabel.getY() * 2.66f, 40, 40);
        batch.end();
    }
}