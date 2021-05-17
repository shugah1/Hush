package com.hush.game.UI;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.utils.Disposable;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.hush.game.Entities.GameObject;
import com.hush.game.Entities.Player;
import com.hush.game.Main;


public class HUD  {
    public Stage stage;
    private Viewport viewport;
    private Integer worldTimer;
    private float timeCount;
    private Integer score;
    public static Integer stun;
    public static Integer invis;
    private Player player;
    Texture stunImage = new Texture("ScrollThunder.png");
    Texture invisImage = new Texture("invisibility-hush.png");
    Texture Stamina = new Texture("LifeBarMiniUnder.png");
    Texture StaminaRed = new Texture("LifeBarMiniProgress.png");

    SpriteBatch batch = new SpriteBatch();

    Label countdownLabel;
    Label scoreLabel;
    Label timeLabel;
    Label levelLabel;
    Label worldLabel;
    Label hushLabel;
    private static Label stunLabel;
    private static Label invisLabel;

    public HUD (Main game){
        worldTimer = 0;
        timeCount = 0;
        score= 0;
        stun = 3;
        invis = 3;
        player = game.player;
        viewport = new FitViewport(Settings.V_WIDTH, Settings.V_HEIGHT, new OrthographicCamera());
        stage = new Stage(viewport);

        Table table = new Table();
        table.top();
        table.setFillParent(true);

        countdownLabel = new Label(String.format("%03d", worldTimer), new Label.LabelStyle(new BitmapFont(), Color.WHITE));
        //scoreLabel = new Label(String.format("%06d", score), new Label.LabelStyle(new BitmapFont(), Color.WHITE));
        timeLabel = new Label("TIME", new Label.LabelStyle(new BitmapFont(), Color.WHITE));
        levelLabel = new Label("1-1", new Label.LabelStyle(new BitmapFont(), Color.WHITE));
        worldLabel = new Label("WORLD", new Label.LabelStyle(new BitmapFont(), Color.WHITE));
        stunLabel = new Label(String.format("%01d", stun), new Label.LabelStyle(new BitmapFont(), Color.WHITE));
        invisLabel = new Label(String.format("%01d", invis), new Label.LabelStyle(new BitmapFont(), Color.WHITE));

        //hushLabel = new Label("HUSH", new Label.LabelStyle(new BitmapFont(), Color.WHITE));

        //table.add(hushLabel).expandX().padTop(10);
        table.add(worldLabel).expandX().padTop(10);
        table.add(timeLabel).expandX().padTop(10);
        table.add(invisLabel).expandX().padTop(10);
        table.row();
        //table.add(scoreLabel).expandX();
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
        }
    }
    public static void invisCounter() {
        if (invis > 0){
            invis -= 1;
            invisLabel.setText(String.format("%01d", invis));
        }
    }
    public void render(){
        batch.begin();
        System.out.println(18 * (player.stamina / player.maxStamina));
        TextureRegion StaminaRedCrop = new TextureRegion(StaminaRed, 0, 0, (int)((18 * (player.stamina / player.maxStamina))), 4);
        batch.draw(Stamina, 10, 20, Stamina.getWidth() * 10, Stamina.getHeight() * 10);
        batch.draw(StaminaRed, 10, 20, StaminaRedCrop.getRegionWidth() * 10, StaminaRedCrop.getRegionHeight() * 10);
        batch.draw(stunImage, 1575, 888, 40, 40);
        batch.draw(invisImage, 1575, 964, 40, 40);
        batch.end();

    }
}
