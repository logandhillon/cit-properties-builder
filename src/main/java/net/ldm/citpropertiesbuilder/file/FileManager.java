package net.ldm.citpropertiesbuilder.file;

import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Path;

public class FileManager {
	public static void saveProperties(String type, String item, String texture, String displayName, boolean wildcards) {
		StringBuilder builder = new StringBuilder();
		builder.append("type=").append(type.toLowerCase()).append('\n').append("items=").append(item).append('\n')
				.append("texture=textures/item/").append(texture).append(".png").append('\n')
				.append("nbt.display.Name=ipattern:");
		if (wildcards) builder.append('*');
		builder.append(displayName);
		if (wildcards) builder.append('*');
		try (FileWriter writer = new FileWriter(getPropertiesPath().resolve(toSnakeCase(displayName)+".properties").toFile())) {
			writer.write(builder.toString());
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
	}

	public static String toSnakeCase(String input) {
		String[] words = input.split(" ");
		StringBuilder result = new StringBuilder();
		for (int i = 0; i < words.length; i++) {
			String word = words[i].toLowerCase();
			if (i > 0) {
				result.append("_");
			}
			result.append(word);
		}
		return result.toString();
	}

	public static Path getPropertiesPath() {
		return UserSettings.getTexturePackDirectory().resolve("assets").resolve("minecraft")
				.resolve("optifine").resolve("cit");
	}

	public static Path getItemTexturesPath() {
		return UserSettings.getTexturePackDirectory().resolve("assets").resolve("minecraft")
				.resolve("textures").resolve("item");
	}

	// TODO: 2023-05-05 Add model support
	/*public static Path getItemModelsPath() {
		return UserSettings.getTexturePackDirectory().resolve("assets").resolve("minecraft")
				.resolve("models").resolve("item");
	}*/
}
