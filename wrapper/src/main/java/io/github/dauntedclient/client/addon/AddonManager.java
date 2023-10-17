/*
 * Daunted Client - the client for Daunt
 * Copyright (C) 2023  fwanchan and drifter16
 */

package io.github.dauntedclient.client.addon;

import java.io.*;
import java.lang.reflect.*;
import java.net.MalformedURLException;
import java.nio.charset.StandardCharsets;
import java.nio.file.*;
import java.nio.file.attribute.BasicFileAttributes;
import java.util.*;

import org.apache.commons.lang3.ArrayUtils;
import org.apache.logging.log4j.*;
import org.spongepowered.asm.mixin.Mixins;

import com.google.gson.*;

import io.github.dauntedclient.client.Client;
import io.github.dauntedclient.client.mod.*;
import io.github.dauntedclient.util.Utils;
import io.github.dauntedclient.wrapper.ClassWrapper;
import lombok.*;

@RequiredArgsConstructor
public final class AddonManager {

	private static final Logger LOGGER = LogManager.getLogger();

	@Getter
	private static AddonManager instance;
	private final Path directory;
	private List<AddonInfo> queuedAddons;
	@Getter
	private boolean loaded;

	public static void premain(String[] args) throws IOException {
		if (!Boolean.getBoolean("io.github.dauntedclient.client.addon.disabled")) {
			// we don't have an MC instance, so we must manually parse

			Path folder;
			int gameDirIndex = ArrayUtils.indexOf(args, "--gameDir");
			if (gameDirIndex < args.length - 1 && gameDirIndex > 0)
				folder = Paths.get(args[gameDirIndex + 1]);
			else
				folder = Paths.get(".");

			folder = folder.resolve("addons");
			Utils.ensureDirectory(folder);

			LOGGER.info("Scanning for addons...");

			AddonManager addons = new AddonManager(folder);
			instance = addons;
			addons.discover();

			LOGGER.info("Discovered {} addons", addons.queuedAddons == null ? 0 : addons.queuedAddons.size());
		}
	}

	public void discover() throws IOException {
		Files.walkFileTree(directory, new SimpleFileVisitor<Path>() {

			@Override
			public FileVisitResult visitFile(Path path, BasicFileAttributes attrs) throws IOException {
				if (!(Files.isRegularFile(path) && path.getFileName().toString().endsWith(".jar")))
					return FileVisitResult.CONTINUE;

				try {
					add(AddonInfo.parse(path));
				} catch (Throwable error) {
					LOGGER.error("Could not load addon info from {}", directory.relativize(path), error);
				}
				return FileVisitResult.CONTINUE;
			}

		});

		if (Boolean.getBoolean("io.github.dauntedclient.client.addon.dev")) {
			try {
				add(AddonInfo.parseFromClasspath());
			} catch (InvalidAddonException | IOException error) {
				throw new IllegalStateException("Could not load addon info from classpath", error);
			}
		}
	}

	public void add(AddonInfo addon) throws MalformedURLException {
		if (queuedAddons == null)
			queuedAddons = new LinkedList<>();

		queuedAddons.add(addon);

		if (addon.getPath().isPresent())
			ClassWrapper.getInstance().addURL(addon.getPath().get().toUri().toURL());

		for (String mixin : addon.getMixins())
			Mixins.addConfiguration(mixin);
	}

	/**
	 * Fully loads all of the addons.
	 *
	 * @param mods the mod manager.
	 */
	public void load(ModManager mods) {
		if (queuedAddons == null || queuedAddons.isEmpty())
			return;
		if (loaded)
			throw new UnsupportedOperationException("Already loaded");

		loaded = true;

		LOGGER.info("Registering and loading addons...");

		// arraylist since it apparently has better sorting speeds
		List<Addon> addons = new ArrayList<>();

		for (AddonInfo info : queuedAddons) {
			try {
				ClassWrapper wrapper = ClassWrapper.getInstance();
				Class<?> mainClass = wrapper.loadClass(info.getMain());
				Addon addon = construct(mainClass);
				addon.setInfo(info);
				addons.add(addon);
			} catch (Throwable error) {
				LOGGER.error(
						"Could not load addon {} from {}", info.getId(), info.getPath()
								.map((path) -> directory.relativize(path)).map(Path::toString).orElse("classpath"),
						error);
			}
		}

		addons.sort(Comparator.comparing(Addon::getName));

		for (Addon addon : addons) {
			try {
				Utils.ensureDirectory(addon.getConfigFolder());
			} catch (IOException error) {
				LOGGER.error("Could not create {}", addon.getConfigFolder(), error);
			}

			try {
				Client.INSTANCE.getMods().register(addon, loadConfig(addon));
			} catch (Throwable error) {
				LOGGER.error("Could not register addon {}", addon.getId(), error);
			}
		}

		// allow gc to do a thing
		queuedAddons.clear();
		queuedAddons = null;
	}

	/**
	 * Saves all of the addons' config files.
	 *
	 * @param mods the mod manager.
	 */
	public void save(ModManager mods) {
		for (Mod mod : mods) {
			if (!(mod instanceof Addon))
				continue;

			Addon addon = (Addon) mod;
			JsonObject config = mods.save(addon);
			try (Writer writer = new OutputStreamWriter(Files.newOutputStream(addon.getConfigFile()),
					StandardCharsets.UTF_8)) {
				writer.write(config.toString());
			} catch (Throwable error) {
				LOGGER.error("Could not save config for {}", addon.getId(), error);
			}
		}
	}

	private JsonObject loadConfig(Addon addon) {
		if (!Files.exists(addon.getConfigFile()))
			return new JsonObject();

		try (Reader reader = new InputStreamReader(Files.newInputStream(addon.getConfigFile()),
				StandardCharsets.UTF_8)) {
			return JsonParser.parseReader(reader).getAsJsonObject();
		} catch (IOException error) {
			LOGGER.error("Could not load addon config for {}", addon.getId(), error);
			return new JsonObject();
		}
	}

	private Addon construct(Class<?> clazz) throws NoSuchMethodException, SecurityException, InstantiationException,
			IllegalAccessException, IllegalArgumentException, InvocationTargetException {
		// special method
		try {
			Method instanceMethod = clazz.getMethod("findAddonInstance");
			if (Addon.class.isAssignableFrom(instanceMethod.getReturnType()))
				return (Addon) instanceMethod.invoke(null);
		} catch (NoSuchMethodException ignored) {
		} catch (ReflectiveOperationException error) {
			LOGGER.warn(error);
		}

		Constructor<?> constructor = clazz.getConstructor();
		return (Addon) constructor.newInstance();
	}

	public List<AddonInfo> getQueuedAddons() {
		if (queuedAddons == null)
			return Collections.emptyList();

		return queuedAddons;
	}

}
