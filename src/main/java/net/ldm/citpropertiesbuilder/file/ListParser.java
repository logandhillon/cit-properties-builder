package net.ldm.citpropertiesbuilder.file;

import java.io.*;
import java.nio.file.Files;
import java.util.*;

import net.ldm.citpropertiesbuilder.Main;
import org.apache.commons.io.FilenameUtils;

public class ListParser {
	public static final List<String> IDS;
	public static final List<String> TEXTURES;

	public static List<String> fileToArray(String fileName) {
		List<String> items = new ArrayList<>();
		try (InputStream inputStream = Main.class.getResourceAsStream("/lists/"+fileName)) {
			assert inputStream != null;
			try (BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream))) {
				String line;
				while ((line = reader.readLine()) != null) items.add(line);
			}
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
		return items;
	}

	static {
		IDS = ListParser.fileToArray("ids.txt").stream().filter(s -> !s.startsWith("#")).toList();
		TEXTURES = new ArrayList<>();
		if (Files.exists(FileManager.getItemTexturesPath()))
			TEXTURES.addAll(Arrays.stream(Objects.requireNonNull(FileManager.getItemTexturesPath().toFile().listFiles()))
					.map(File::getName).filter(name -> name.toLowerCase().endsWith(".png"))
					.map(FilenameUtils::getBaseName).toList());
		TEXTURES.addAll(IDS);
	}
}

// update item list at https://mcreator.net/wiki/minecraft-block-and-item-list-registry-and-code-names