package net.ldm.citpropertiesbuilder.core;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Locale;

public enum OperatingSystem {
	WINDOWS, UNIX, MACOS, UNKNOWN;

	public static final OperatingSystem CURRENT_OS = parseOsName(System.getProperty("os.name"));

	public static OperatingSystem parseOsName(String name) {
		if (name == null) return UNKNOWN;
		name = name.trim().toLowerCase(Locale.ROOT);
		if (name.contains("win")) return WINDOWS;
		else if (name.contains("mac")) return MACOS;
		else if (name.contains("linux") || name.contains("unix") || name.contains("solaris") || name.contains("sunos"))
			return UNIX;
		else return UNKNOWN;
	}

	public static Path getWorkingDirectory() {
		String home = System.getProperty("user.home", ".");
		switch (OperatingSystem.CURRENT_OS) {
			case WINDOWS -> {
				String appdata = System.getenv("APPDATA");
				return Paths.get(appdata == null ? home : appdata).toAbsolutePath().resolve("cit-properties-builder");
			}
			case MACOS -> {
				return Paths.get(home, "Library", "Application Support").toAbsolutePath().resolve("cit-properties-builder");
			}
			default -> {
				return Paths.get(home).toAbsolutePath().resolve("cit-properties-builder");
			}
		}
	}
}
