/*
 * #%L
 * =====================================================
 *   _____                _     ____  _   _       _   _
 *  |_   _|_ __ _   _ ___| |_  / __ \| | | | ___ | | | |
 *    | | | '__| | | / __| __|/ / _` | |_| |/ __|| |_| |
 *    | | | |  | |_| \__ \ |_| | (_| |  _  |\__ \|  _  |
 *    |_| |_|   \__,_|___/\__|\ \__,_|_| |_||___/|_| |_|
 *                             \____/
 *
 * =====================================================
 *
 * Hochschule Hannover
 * (University of Applied Sciences and Arts, Hannover)
 * Faculty IV, Dept. of Computer Science
 * Ricklinger Stadtweg 118, 30459 Hannover, Germany
 *
 * Email: trust@f4-i.fh-hannover.de
 * Website: http://trust.f4.hs-hannover.de/
 *
 * This file is part of ironcommon, version 0.1.0, implemented by the Trust@HsH
 * research group at the Hochschule Hannover.
 * %%
 * Copyright (C) 2015 Trust@HsH
 * %%
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 * #L%
 */
package de.hshannover.f4.trust.ironcommon.yaml;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import org.apache.log4j.Logger;
import org.yaml.snakeyaml.Yaml;

import de.hshannover.f4.trust.ironcommon.util.ObjectChecks;

/**
 * Class that encapsulates reading of YAML-files.
 *
 * @author Marcel Reichenbach
 */
public final class YamlReader {

	private static Logger mLogger = Logger.getLogger(YamlReader.class);

	/**
	 * Only static calls are allowed.
	 */
	private YamlReader() {
	}

	/**
	 * Tries to load the content of a yml-file as instances of a given
	 * {@link Class}. If the file exists but is empty, the file is newly created
	 * and null is returned.
	 *
	 * @param fileName
	 *            The file name of the yml-file.
	 * @param clazz
	 *            The data-type that the content of the yml-file shall be cast
	 *            into.
	 * @return The content of the yml-file as instances of Class<T>.
	 * @throws IOException
	 *             If the file could not be opened, created (when it doesn't
	 *             exist) or the given filename is a directory.
	 */
	@SuppressWarnings("unchecked")
	public static synchronized <T> T loadAs(String fileName, Class<T> clazz)
			throws IOException {
		ObjectChecks.checkForNullReference(fileName, "fileName is null");
		ObjectChecks.checkForNullReference(clazz, "clazz is null");

		FileReader fileReader = null;
		File f = null;

		try {
			fileReader = new FileReader(fileName);
		} catch (FileNotFoundException e) {
			f = new File(fileName);
			if (f.isDirectory()) {
				throw new IOException(fileName + " is a directory");
			} else if (f.isFile()) {
				throw new IOException("Could not open " + fileName + ": "
						+ e.getMessage());
			} else {
				mLogger.debug("File: "
						+ fileName
						+ " doesn't exist and it's not a directory, try to create it.");
				// If it doens't exist and it's not a directory, try to create
				// it.
				try {
					new FileWriter(fileName).close();
				} catch (IOException ee) {
					throw new IOException("Could not create " + fileName + ": "
							+ ee.getMessage());
				}
				try {
					fileReader = new FileReader(fileName);
				} catch (IOException ee) {
					throw new IOException("Could not open " + fileName + ": "
							+ ee.getMessage());
				}
			}
		}

		Yaml yaml = new Yaml();
		Object data = yaml.loadAs(fileReader, clazz);

		fileReader.close();
		return (T) data;
	}

	/**
	 * Load a yml-File as Map<String, Object>.
	 *
	 * @param fileName
	 *            The file name of the yml-file.
	 * @return a Map<String, Object> with the content of the yml-file. If the
	 *         File exists but is empty, a empty Map<String, Object> is
	 *         returned.
	 * @throws IOException
	 *             If the file could not be opened, created (when it doesn't
	 *             exist) or the given filename is a directory.
	 */
	@SuppressWarnings("unchecked")
	public static Map<String, Object> loadMap(String fileName)
			throws IOException {
		Map<String, Object> ymlMap = loadAs(fileName, HashMap.class);
		if (ymlMap == null) {
			return new HashMap<String, Object>();
		}
		return ymlMap;
	}

}
