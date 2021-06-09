package com.hush.game.World;

/**
 * Essential class in collision
 * Pre: N/A
 * Post: can assign each variable into a collision mask. Determines what will collide with what, and what happens.
 */
public class Tags {

    //Defines which object is which using bits
    public static final int DEFAULT_BIT = 1;
    public static final int PLAYER_BIT = 2;
    public static final int DAMAGE_BIT = 4;
    public static final int ATTACK_BIT = 6;
    public static final int ENEMY_BIT = 8;
    public static final int PROJECTILE_BIT = 16;
    public static final int POWERUP_BIT = 32;
    public static final int WALL_BIT = 64;
    public static final int SENSOR_BIT = 128;
    public static final int GOAL_BIT = 256;
    public static final int KEY_BIT = 512;
    public static final int SWALL_BIT = 1024;

}
