package net.ldm.citpropertiesbuilder.file;

import net.ldm.citpropertiesbuilder.core.OperatingSystem;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Scanner;

public class UserSettings {
	private static Path texturePackDirectory;

	public static void createAllConfigFiles() {
		try {
			OperatingSystem.getWorkingDirectory().toFile().mkdirs();
			File activeDir = OperatingSystem.getWorkingDirectory().resolve("activetexturepack.citpropertiesbuilderconfig").toFile();
			if (activeDir.createNewFile() || !new Scanner(activeDir).hasNextLine()) {
				try (FileWriter writer = new FileWriter(OperatingSystem.getWorkingDirectory()
						.resolve("activetexturepack.citpropertiesbuilderconfig").toFile())) {
					writer.write(System.getProperty("user.dir"));
				}
			}

		} catch (IOException e) {
			throw new RuntimeException(e);
		}
	}

	static {
		createAllConfigFiles();
		try (Scanner reader = new Scanner(OperatingSystem.getWorkingDirectory().resolve("activetexturepack.citpropertiesbuilderconfig"))) {
			while (reader.hasNextLine()) {
				String line = reader.nextLine();
				Path path = Paths.get(line);
				if (path.isAbsolute()) texturePackDirectory = path;
			}
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
	}

	public static Path getTexturePackDirectory() {
		return texturePackDirectory;
	}

	public static void setTexturePackDirectory(Path texturePackDirectory) {
		try (FileWriter writer = new FileWriter(OperatingSystem.getWorkingDirectory()
				.resolve("activetexturepack.citpropertiesbuilderconfig").toFile())) {
			writer.write(texturePackDirectory.toString());
			UserSettings.texturePackDirectory = texturePackDirectory;
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
	}
}
