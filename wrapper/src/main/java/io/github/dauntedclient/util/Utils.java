/*
 * Daunted Client - the client for Daunt
 * Copyright (C) 2023  fwanchan and drifter16
 */

package io.github.dauntedclient.util;

import java.io.*;
import java.net.*;
import java.nio.file.*;

import org.apache.commons.io.*;

import lombok.experimental.UtilityClass;

/**
 * General utils.
 */
@UtilityClass
public class Utils {

	public URLConnection getConnection(String agent, URL url) throws IOException {
		URLConnection connection = url.openConnection();
		connection.addRequestProperty("User-Agent", agent); // Force consistent behaviour
		return connection;
	}

	public String urlToString(URL url) throws IOException {
		return urlToString(GlobalConstants.USER_AGENT, url);
	}

	public String urlToString(String agent, URL url) throws IOException {
		try (InputStream in = getConnection(agent, url).getInputStream()) {
			return IOUtils.toString(in);
		}
	}

	public void urlToFile(URL url, Path file) throws IOException {
		urlToFile(GlobalConstants.USER_AGENT, url, file);
	}

	public void urlToFile(String agent, URL url, Path file) throws IOException {
		try (InputStream in = getConnection(agent, url).getInputStream()) {
			FileUtils.copyInputStreamToFile(in, file.toFile());
		}
	}

	public URL sneakyParse(String url) {
		try {
			return new URL(url);
		} catch (MalformedURLException error) {
			throw new AssertionError(error);
		}
	}

	public void ensureDirectory(Path path) throws IOException {
		if (Files.isDirectory(path))
			return;

		// if the file is just an empty one created by mistake - or a broken link -
		// delete it
		if (Files.isRegularFile(path) && Files.size(path) == 0)
			Files.delete(path);

		if (!Files.isDirectory(path.getParent()))
			ensureDirectory(path.getParent());

		Files.createDirectory(path);
	}

}
