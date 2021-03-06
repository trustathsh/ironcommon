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
 * This file is part of ironcommon, version 0.1.3, implemented by the Trust@HsH
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
import java.io.FileWriter;
import java.io.IOException;

import org.yaml.snakeyaml.DumperOptions;
import org.yaml.snakeyaml.Yaml;

import de.hshannover.f4.trust.ironcommon.util.ObjectChecks;

/**
 * Class that encapsulates writing of YAML-files.
 *
 * @author Marcel Reichenbach
 */
public final class YamlWriter {

	private static DumperOptions mOptions;

	static {
		mOptions = buildDumperOptions();
	}

	/**
	 * Only static calls are allowed.
	 */
	private YamlWriter() {
	}

	/**
	 * Save a given {@link Object} to the yml-file.
	 *
	 * @param fileName
	 *            The file name of the yml-file.
	 * @param object
	 *            The {@link Object} to be stored.
	 * @throws IOException
	 *             If the file could not be opened, created (when it doesn't
	 *             exist) or the given filename is a directory.
	 */
	public static synchronized void persist(String fileName, Object object)
			throws IOException {
		ObjectChecks.checkForNullReference(fileName, "fileName is null");
		ObjectChecks.checkForNullReference(object, "object is null");

		FileWriter fileWriter = null;
		File f = null;

		try {
			fileWriter = new FileWriter(fileName);
		} catch (IOException e) {
			f = new File(fileName);
			if (f.isDirectory()) {
				throw new IOException(fileName + " is a directory");
			} else {
				throw new IOException("Could not create " + fileName + ": "
						+ e.getMessage());
			}
		}

		Yaml yaml = new Yaml(mOptions);
		yaml.dump(object, fileWriter);
		fileWriter.close();
	}

	private static DumperOptions buildDumperOptions() {
		DumperOptions options = new DumperOptions();
		options.setDefaultFlowStyle(DumperOptions.FlowStyle.BLOCK);
		return options;
	}
}
