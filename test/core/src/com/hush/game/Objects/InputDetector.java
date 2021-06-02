package com.hush.game.Objects;

import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputListener;

public class InputDetector extends Actor {

    public InputDetector(InputListener listener) {
        addListener(listener);
        System.out.println(listener);
    }
}
