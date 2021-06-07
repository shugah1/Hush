package com.hush.game.desktop;

import ca.error404.bytefyte.constants.Globals;
import ca.error404.bytefyte.constants.ScreenSizes;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import com.hush.game.Main;
import com.hush.game.UI.Settings;
import org.ini4j.Wini;

import java.io.File;
import java.io.IOException;

public class DesktopLauncher {
	public static void main (String[] arg) throws IOException {
		new Globals();
		LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();

		// Window settings
		File settings = new File(ca.error404.bytefyte.constants.Globals.workingDirectory + "settings.ini");

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
				Settings.musicVolume = Integer.parseInt(ini.get("Settings", "music volume"));
				ca.error404.bytefyte.Main.cutsceneVolume = Integer.parseInt(ini.get("Settings", "cutscene volume"));
				ca.error404.bytefyte.Main.sfxVolume = Integer.parseInt(ini.get("Settings", "sfx volume"));
				ScreenSizes.fullScreen = Boolean.parseBoolean(ini.get("Settings", "fullscreen"));
				ca.error404.bytefyte.Main.debug = Boolean.parseBoolean(ini.get("Settings", "debug"));
				ca.error404.bytefyte.Main.bill = Boolean.parseBoolean(ini.get("Menu", "bill"));
				ca.error404.bytefyte.Main.stamina = Boolean.parseBoolean(ini.get("Menu", "stamina"));
			} catch (Exception ignored) {

			}
		}

		config.title = "Hush";
		config.resizable = false;
		config.width = 1280;
		config.height = 720;
		new LwjglApplication(new Settings(), config);
	}
}