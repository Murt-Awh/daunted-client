/*
 * Daunted Client - the client for Daunt
 * Copyright (C) 2023  fwanchan and drifter16
 */

package io.github.dauntedclient.client;

import java.io.*;
import java.nio.file.*;
import java.util.*;

import io.github.dauntedclient.client.mod.*;
import io.github.dauntedclient.client.mod.option.*;
import io.github.dauntedclient.client.mod.option.impl.TextFileOption;

public class FilePollingTask implements Runnable, Closeable {

	private Map<String, ModOption<?>> files = new HashMap<>();
	private WatchKey key;

	public FilePollingTask(ModManager mods) throws IOException {
		WatchService service = FileSystems.getDefault().newWatchService();

		key = Client.INSTANCE.getConfigFolder().register(service, StandardWatchEventKinds.ENTRY_MODIFY,
				StandardWatchEventKinds.ENTRY_CREATE);

		for (Mod mod : mods)
			for (ModOption<?> option : mod.getOptions())
				if (option instanceof TextFileOption)
					files.put(((TextFileOption) option).getPath().getFileName().toString(), option);
	}

	@Override
	public void run() {
		for (WatchEvent<?> event : key.pollEvents()) {
			ModOption<?> option = files.get(((Path) event.context()).getFileName().toString());

			if (option != null) {
				try {
					((TextFileOption) option).read();
				} catch (IOException error) {
				}
			}
		}
	}

	@Override
	public void close() {
		key.reset();
	}

}
