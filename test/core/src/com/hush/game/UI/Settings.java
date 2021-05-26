package com.hush.game.UI;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.hush.game.Main;
import com.hush.game.SplashScreen;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;

public class Settings extends Game {
	public SpriteBatch batch;
	public static final int V_WIDTH = 480;
	public static final int V_HEIGHT = 270;
	public static final float PPM = 100;

	public static boolean dead = false;
	public static boolean win = false;

	public static String songName;
	public static String internalSongName;
	public static double songLoopStart;
	public static double songLoopEnd;
	public static Music music;

	public static int musicVolume = 5;

	public static AssetManager audioManager = new AssetManager();
	public static AssetManager manager = new AssetManager();




	@Override
	public void create () {
		batch = new SpriteBatch();
		System.out.println(musicVolume);
		setScreen(new SplashScreen(this));
	}

	@Override
	public void render () { super.render(); }


	
	@Override
	public void dispose () {
		//
	}

	/**
	 * Pre: Song Title
	 * Post: Loads the song to be played
	 */
	public Music newSong(String song) {
		// Locate file
		String fileName = "songdata.tsv";

		ClassLoader classLoader = Main.class.getClassLoader();
		InputStream inputStream = classLoader.getResourceAsStream(fileName);
		InputStreamReader streamReader = new InputStreamReader(inputStream, StandardCharsets.UTF_8);

		BufferedReader br = new BufferedReader(streamReader);

		String oneData = "";
		int i = 0;
		boolean keepLooping = true;

		// Loops through CSV and changes song info if criteria met
		try {
			while ((oneData = br.readLine()) != null && keepLooping) {
				String[] data = oneData.split("	");

				if (i > 0 && data[0].equalsIgnoreCase(song)) {
					internalSongName = data[0];
					songLoopStart = Double.parseDouble(data[1]);
					songLoopEnd = Double.parseDouble(data[2]);
					keepLooping = false;
				}

				i++;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

		if (songLoopEnd == -1) {
			songLoopEnd = Double.POSITIVE_INFINITY;
		}

		audioManager.load("audio/music/" + internalSongName + ".wav", Music.class);
		audioManager.finishLoading();

		Music music = audioManager.get("audio/music/" + internalSongName + ".wav", Music.class);
		music.setLooping(true);
		return music;
	}

}
