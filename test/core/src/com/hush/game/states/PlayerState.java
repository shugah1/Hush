package com.hush.game.states;

import com.badlogic.gdx.ai.fsm.State;
import com.badlogic.gdx.ai.msg.Telegram;
import com.badlogic.gdx.math.Vector2;
import com.hush.game.Entities.Player;

/**
 * enumerator for the player state.
 */
public enum PlayerState implements State<Player> {
    /*
    Pre: player stands still
    Post: When the player is standing still it plays the idle state.
     */
    IDLE() {
        public void enter(Player player) {
            player.elapsedTime = 0;
        }
        /*
        Pre: player
        Post: updates the players state depending on if their walking, running, or standing still.
         */
        public void update(Player player) {
            player.elapsedTime += player.deltaTime;
            if (!player.moveVector.equals(new Vector2(0, 0))) {
                if (!player.running) {
                    player.state.changeState(WALK);
                } else {
                    player.state.changeState(RUN);
                }
            }

            player.idle();
        }

        public void exit(Player player) {}
    },
    /*
    Pre: player walk
    Post: if the player is walking determines the speed, and tells the player class to play the animation.
     */
    WALK() {
        public void enter(Player player) {

        }
        /*
        Pre: player
        Post: updates states and speed.
         */
        public void update(Player player) {
            player.elapsedTime += player.deltaTime;
            player.SPEED = player.walkSpeed;

            player.b2body.setLinearVelocity(player.moveVector.scl(player.SPEED));

            if (player.running) {
                player.state.changeState(RUN);
            }

            if (player.moveVector.equals(new Vector2(0, 0))) {
                player.state.changeState(IDLE);
            }

            if (!player.moveVector.equals(new Vector2(0, 0))) {
                player.walk();
            }
        }

        public void exit(Player player) {}
    },
    /*
    Pre: running = true
    post: sets the player speed when running.
     */
    RUN() {
        public void enter(Player player) {

        }
        /*
        Pre: player
        Post: Updates the players speed, and stamina.
         */
        public void update(Player player) {
            player.elapsedTime += player.deltaTime * 2;
            player.SPEED = player.runSpeed;

            player.b2body.setLinearVelocity(player.moveVector.scl(player.SPEED));

            player.stamina = Math.max( player.stamina - (player.deltaTime * 10), 0);
            if (player.stamina <= 0){
                player.state.changeState(WALK);
                player.recharing = true;
            }

            if (player.moveVector.equals(new Vector2(0, 0))) {
                player.state.changeState(IDLE);
            } else if (!player.running) {
                player.state.changeState(WALK);
            }

            if (!player.moveVector.equals(new Vector2(0, 0))) {
                player.walk();
            }
        }

        public void exit(Player player) {
            player.running = false;
        }
    },
    /**
     * Not used :)
     */
    ITEM() {
        public void enter(Player player) {
            player.elapsedTime = 0;
        }

        public void update(Player player) {}

        public void exit(Player player) {}
    },

    HIT() {
        public void enter(Player player) {
            player.elapsedTime = 0;
        }

        public void update(Player player) {}

        public void exit(Player player) {}
    },

    DEAD() {
        public void enter(Player player) {
            player.elapsedTime = 0;
        }

        public void update(Player player) {
            player.deadAction();
        }

        public void exit(Player player) {}
    };

    @Override
    public void enter(Player entity) {

    }

    @Override
    public void update(Player entity) {

    }

    @Override
    public void exit(Player entity) {

    }

    @Override
    public boolean onMessage(Player entity, Telegram telegram) {
        return false;
    }
}
