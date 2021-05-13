package com.hush.game.Entities;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.physics.box2d.World;
import com.hush.game.Main;
import com.hush.game.UI.Settings;

public class StaticEnemy extends Enemy{

    Texture image = new Texture("test/core/assets/badlogic.jpg");

    public StaticEnemy(World world, Main screen, float x, float y) {
        super(world, screen, x, y);
        setRegion(image);
    }

    @Override
    public void update(float dt) {
        //setBounds(b2body.getPosition().x - getWidth() / Settings.PPM / 2f, b2body.getPosition().y - getHeight() / Settings.PPM / 2f, getWidth() / Settings.PPM, getHeight() / Settings.PPM);
    }
}
