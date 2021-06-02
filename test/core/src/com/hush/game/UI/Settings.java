package com.hush.game.UI;

import ca.error404.bytefyte.constants.ControllerButtons;
import com.badlogic.gdx.Game;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.controllers.Controller;
import com.badlogic.gdx.controllers.ControllerAdapter;
import com.badlogic.gdx.controllers.Controllers;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.Array;
import com.hush.game.Main;
import com.hush.game.Screens.SplashScreen;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.Hashtable;

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

	public static Hashtable<String, Integer> highScore;
	public static Integer completion = 0;

	@Override
	public void create () {
		batch = new SpriteBatch();
		highScore = new Hashtable<>();
		System.out.println(musicVolume);
		reloadControllers();
		setScreen(new SplashScreen(this));
	}

	public static void reloadControllers() {
		// makes sure that there are controllers plugged in
		if (Controllers.getControllers().size > 0) {
			int currentController = 0;

			// loops through all controllers
			for (int i=0; i < Controllers.getControllers().size; i++) {
				Controller cont = Controllers.getControllers().get(i);

				// Checks for Xbox controllers
				if (ControllerButtons.isXboxController(cont)) {
					if (currentController < 4) {
						ca.error404.bytefyte.Main.controllers[currentController] = cont;
						currentController += 1;
						ca.error404.bytefyte.Main.recentButtons.put(cont, new Array<Integer>());

						// Creates controller
						cont.addListener(new ControllerAdapter() {
							public boolean buttonDown(Controller controller, int buttonIndex) {
								ca.error404.bytefyte.Main.recentButtons.get(controller).add(buttonIndex);
								return false;
							}
						});
					}

					// Add controller to controller array
					ca.error404.bytefyte.Main.allControllers.add(cont);
					ca.error404.bytefyte.Main.recentButtons.put(cont, new Array<Integer>());
					cont.addListener(new ControllerAdapter() {
						public boolean buttonDown(Controller controller, int buttonIndex) {
							ca.error404.bytefyte.Main.recentButtons.get(controller).add(buttonIndex);
							return false;
						}
					});
				}
			}
		}
	}

	@Override
	public void render () {
		super.render();
	}



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
		String fileName = "songdata hush.tsv";

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

			if (keepLooping) {
				return null;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

		if (songLoopEnd == -1) {
			songLoopEnd = Double.POSITIVE_INFINITY;
		}

		songName = internalSongName;

		audioManager.load("audio/music/" + internalSongName + ".wav", Music.class);
		audioManager.finishLoading();

		Music music = audioManager.get("audio/music/" + internalSongName + ".wav", Music.class);
		music.setLooping(true);
		return music;
	}

}