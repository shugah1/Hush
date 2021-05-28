package com.hush.game.desktop;

import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import com.hush.game.UI.Settings;
import com.hush.game.constants.Globals;
import org.ini4j.Wini;

import java.io.File;
import java.io.IOException;

public class DesktopLauncher {
	public static void main (String[] arg) throws IOException {


		new Globals();
		LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();

		File settings = new File(Globals.workingDirectory + "settings.ini");

		// Checks for active save data
		if (!settings.exists()) {
			// Creates save file and writes default save data
			File file = new File(Globals.workingDirectory);
			file.mkdirs();

			settings.createNewFile();

			Wini ini = new Wini(settings);
			ini.add("Settings", "music volume", Settings.musicVolume);
			ini.store();
		} else {
			Wini ini = new Wini(settings);
			// loads save data and assigns variables
			try {
				Settings.musicVolume = Integer.parseInt(ini.get("Settings", "music volume"));
			} catch (Exception ignored) {

			}
		}

		config.title = "Hush";
		config.resizable = true;
		config.width = 1280;
		config.height = 720;
		new LwjglApplication(new Settings(), config);

	}
}