package com.hush.game.desktop;

import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import com.hush.game.UI.Settings;
import com.hush.game.constants.Globals;
import org.ini4j.Wini;

import java.io.File;
import java.io.IOException;
import java.util.Set;

public class DesktopLauncher {
	public static void main (String[] arg) throws IOException {
		new Globals();
		Settings stage = new Settings();
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

			ini.add("Completion", "Tutorial", 0);
			ini.add("Completion", "Level 1", 0);
			ini.add("Completion", "Level 2", 0);
			ini.add("Completion", "Level 3", 0);
			ini.add("Completion", "Level 4", 0);
			ini.add("Completion", "Level 5", 0);

			ini.add("High Score", "Tutorial", 999);
			ini.add("High Score", "Level 1", 999);
			ini.add("High Score", "Level 2", 999);
			ini.add("High Score", "Level 3", 999);
			ini.add("High Score", "Level 4", 999);
			ini.add("High Score", "Level 5", 999);

			ini.store();
		} else {
			Wini ini = new Wini(settings);
			// loads save data and assigns variables
			try {
				Settings.musicVolume = Integer.parseInt(ini.get("Settings", "music volume"));

				Settings.highScore.put("Tutorial", Integer.parseInt(ini.get("High Score", "Tutorial")));
				Settings.highScore.put("Level 1", Integer.parseInt(ini.get("High Score", "Level 1")));
				Settings.highScore.put("Level 2", Integer.parseInt(ini.get("High Score", "Level 2")));
				Settings.highScore.put("Level 3", Integer.parseInt(ini.get("High Score", "Level 3")));
				Settings.highScore.put("Level 4", Integer.parseInt(ini.get("High Score", "Level 4")));
				Settings.highScore.put("Level 5", Integer.parseInt(ini.get("High Score", "Level 5")));

				Settings.completion.put("Tutorial", Integer.parseInt(ini.get("Completion", "Tutorial")));
				Settings.completion.put("Level 1", Integer.parseInt(ini.get("Completion", "Level 1")));
				Settings.completion.put("Level 2", Integer.parseInt(ini.get("Completion", "Level 2")));
				Settings.completion.put("Level 3", Integer.parseInt(ini.get("Completion", "Level 3")));
				Settings.completion.put("Level 4", Integer.parseInt(ini.get("Completion", "Level 4")));
				Settings.completion.put("Level 5", Integer.parseInt(ini.get("Completion", "Level 5")));

			} catch (Exception ignored) {

			}
		}

		config.title = "Hush";
		config.resizable = false;
		config.width = 1280;
		config.height = 720;
		new LwjglApplication(stage, config);

	}
}