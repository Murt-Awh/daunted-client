/*
 * Daunted Client - the client for Daunt
 * Copyright (C) 2023  fwanchan and drifter16
 */

package io.github.dauntedclient.client.network;

import java.io.*;
import java.net.URL;
import java.time.*;
import java.util.*;
import java.util.function.Predicate;
import java.util.stream.*;

import org.apache.logging.log4j.*;

import com.google.common.base.Objects;
import com.google.gson.*;

import io.github.dauntedclient.client.Client;
import io.github.dauntedclient.client.event.EventHandler;
import io.github.dauntedclient.client.event.impl.*;
import io.github.dauntedclient.client.mod.impl.DauntedClientConfig;
import io.github.dauntedclient.client.util.MinecraftUtils;
import io.github.dauntedclient.util.*;

public final class OnlineApi {

	private ZonedDateTime lastUpdate;

	private Map<UUID, Boolean> cache;
	private final Logger LOGGER = LogManager.getLogger();

	{
		clearCache();
	}

	/**
	 * Fetch and cache a stream of uuids.
	 *
	 * @param uuids the uuids.
	 */
	public void fetch(Stream<UUID> uuids) {
		List<UUID> filtered;
		synchronized (cache) {
			filtered = uuids.filter(((Predicate<UUID>) cache::containsKey).negate()).collect(Collectors.toList());
			if (filtered.isEmpty())
				return;

			for (UUID uuid : filtered)
				cache.put(uuid, null);
		}

		MinecraftUtils.USER_DATA.submit(() -> {
			try {
				String param = join(filtered);
				try (Reader reader = new InputStreamReader(Utils.getConnection(GlobalConstants.USER_AGENT,
						new URL(GlobalConstants.API + "/online/get/" + param)).getInputStream())) {
					int index = 0;
					for (JsonElement element : JsonParser.parseReader(reader).getAsJsonArray()) {
						if (index >= filtered.size())
							break;

						synchronized (cache) {
							cache.put(filtered.get(index),
									element.isJsonPrimitive() && Duration
											.between(ZonedDateTime.parse(element.getAsString()), ZonedDateTime.now())
											.toMinutes() < 30);
						}
						index++;
					}
				}
			} catch (IOException error) {
				LOGGER.error("Failed to get player data", error);
			}
		});
	}

	/**
	 * Recall a cached uuid.
	 *
	 * @param uuid the uuid.
	 * @return whether the player is online.
	 */
	public boolean recall(UUID uuid) {
		return Objects.firstNonNull(cache.get(uuid), false);
	}

	/**
	 * Recall a cached uuid, otherwise fetch it.
	 *
	 * @param uuid the uuid.
	 * @return whether the player is online.
	 */
	public boolean recallOrFetch(UUID uuid) {
		if (uuid.version() != 4)
			return false;

		fetch(Stream.of(uuid));
		return recall(uuid);
	}

	private String join(List<UUID> players) {
		StringBuilder result = new StringBuilder();
		for (int index = 0; index < players.size(); index++) {
			if (index != 0)
				result.append(',');
			result.append(players.get(index));
		}
		return result.toString();
	}

	public void logIn() throws IOException {
		lastUpdate = ZonedDateTime.now();
		new URL(GlobalConstants.API + "/online/log_in/" + MinecraftUtils.getPlayerUuid().toString()).openConnection()
				.getInputStream();
	}

	public void logOut() throws IOException {
		lastUpdate = null;
		new URL(GlobalConstants.API + "/online/log_out/" + MinecraftUtils.getPlayerUuid().toString()).openConnection()
				.getInputStream();
	}

	public void clearCache() {
		cache = new HashMap<>();
		cache.put(MinecraftUtils.getPlayerUuid(), true);
	}

	@EventHandler
	public void onWorldLoad(WorldLoadEvent event) {
		clearCache();
	}

	@EventHandler
	public void onTick(PreTickEvent event) throws IOException {
		if (lastUpdate == null)
			return;

		if (Duration.between(lastUpdate, ZonedDateTime.now()).toMinutes() >= 29)
			logIn();
	}

	@EventHandler
	public void onPostStart(PostGameStartEvent event) throws IOException {
		try {
			if (DauntedClientConfig.instance.broadcastOnline)
				logIn();
		} catch (IOException error) {
			Client.INSTANCE.getEvents().unregister(this);
			throw error;
		}
	}

	@EventHandler
	public void onQuit(GameQuitEvent event) throws IOException {
		if (lastUpdate != null)
			logOut();
	}

}
