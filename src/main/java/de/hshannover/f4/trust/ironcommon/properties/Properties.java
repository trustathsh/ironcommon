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
package de.hshannover.f4.trust.ironcommon.properties;

import java.io.IOException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.log4j.Logger;

import de.hshannover.f4.trust.ironcommon.util.ObjectChecks;

/**
 * Class that loads, handles and stores application properties in
 * configuration-files.
 *
 * @author Marcel Reichenbach
 */
public class Properties {

	private PropertiesWriter mWriter;

	private PropertiesReader mReader;

	private String mFileName;

	private String mPropertyPathPrefix = "";

	private Logger mLogger = Logger.getLogger(Properties.class);

	private Properties(Properties propertyOriginal, String propertyKey) {
		mWriter = propertyOriginal.mWriter;
		mReader = propertyOriginal.mReader;
		mFileName = propertyOriginal.mFileName;
		mPropertyPathPrefix = addPath(propertyOriginal.mPropertyPathPrefix,
				propertyKey);
	}

	/**
	 * Create a {@link Properties} instance for a given filename.
	 *
	 * @param fileName
	 *            the file name of the property-file.
	 */
	public Properties(String fileName) {
		ObjectChecks.checkForNullReference(fileName, "fileName is null");
		mFileName = fileName;
		mWriter = new PropertiesWriter(mFileName);
		mReader = new PropertiesReader(mFileName);
	}

	/**
	 * Load the properties as a {@link Map}.
	 *
	 * @throws PropertyException
	 *             if the file could not be opened, created or is directed to a
	 *             directory
	 * @return a {@link Map}: keys can either point to a "deeper nested"
	 *         {@link Map} or a value.
	 */
	public Map<String, Object> load() throws PropertyException {
		try {
			return mReader.load();
		} catch (IOException e) {
			throw new PropertyException(e.getMessage());
		}
	}

	/**
	 * Save all properties in a file.
	 *
	 * @param properties
	 *            the {@link Map} with all stored deeper nested {@link Map}s
	 *            and/or values.
	 * @throws PropertyException
	 *             if the file could not be opened, created or is directed to a
	 *             directory
	 */
	public void save(Map<String, Object> properties) throws PropertyException {
		try {
			mWriter.save(properties);
		} catch (IOException e) {
			throw new PropertyException(e.getMessage());
		}
	}

	/**
	 * Get a new Properties instance for the given property key. Only property
	 * keys accepted but not a path (Example: key is allowed, not foo.bar.key).
	 *
	 * @param key
	 *            the property key, for which a new instance of Properties shall
	 *            be instantiated
	 * @return a new {@link Properties} instance, containing all subpaths of the
	 *         given key.
	 * @throws PropertyException
	 *             if the given key is not a property key or is not part of a
	 *             property path.
	 */
	public Properties get(String key) throws PropertyException {
		ObjectChecks.checkForNullReference(key, "key is null");
		Properties propertyCopy = new Properties(this, key);
		return propertyCopy;
	}

	/**
	 * Get the value from the property path (Example: foo.bar.key). If the
	 * property path is a empty String, the full root map is returned.
	 *
	 * @param propertyPath
	 *            Example: foo.bar.key
	 * @return the value from the propertyPath.
	 * @throws PropertyException
	 *             if the propertyKey-Path is not a property key or is a not
	 *             part of a property path.
	 */
	@SuppressWarnings("unchecked")
	public Object getValue(String propertyPath) throws PropertyException {
		// add the PreFixPropertyPath if this Property is a copy deeper level
		String fullPropertyPath = addPath(mPropertyPathPrefix, propertyPath);

		// check propertyPath
		ObjectChecks.checkForNullReference(fullPropertyPath,
				"propertyPath is null");

		// split propertyPath
		String[] propertyKeyArray = fullPropertyPath.split("\\.");

		// load the application properties
		Map<String, Object> applicationConfigs = load();

		// return the full root map
		if (fullPropertyPath.equals("")) {
			return applicationConfigs;
		}

		// iterate the root Map for every token
		for (int i = 0; i < propertyKeyArray.length; i++) {
			Object tmp = applicationConfigs.get(propertyKeyArray[i]);
			if (tmp == null) {
				if (propertyKeyArray.length > 1) {
					throw new PropertyException("property path["
							+ fullPropertyPath + "] have not a property key["
							+ propertyKeyArray[i] + "] !");
				} else {
					throw new PropertyException(propertyKeyArray[i]
							+ "] is not a property key!");
				}
			}
			if (tmp instanceof Map) {
				applicationConfigs = (Map<String, Object>) tmp;
			} else {
				return tmp;
			}
		}
		return applicationConfigs;
	}

	/**
	 * Get the value from the property path. If the property path does not
	 * exist, the default value is returned.
	 *
	 * @param propertyPath
	 *            Example: foo.bar.key
	 * @param defaultValue
	 *            is returned when the propertyPath does not exist
	 * @return the value from the propertyPath.
	 */
	public Object getValue(String propertyPath, Object defaultValue) {
		Object o = null;
		try {
			o = getValue(propertyPath);
		} catch (PropertyException e) {
			return defaultValue;
		}
		return o;
	}

	/**
	 * Get the value from the property path as a {@link String}.
	 *
	 * @param propertyPath
	 *            Example: foo.bar.key
	 * @return the {@link String} value for the given propertyPath
	 * @throws PropertyException
	 *             If the propertyKey-Path is not a property key or is a not
	 *             part of a property path; also when the value is not a
	 *             {@link String}
	 */
	public String getString(String propertyPath) throws PropertyException {
		Object o = getValue(propertyPath);
		return o.toString();
	}

	/**
	 * Get the String-value from the property path. If the property path does
	 * not exist, the default value is returned.
	 *
	 * @param propertyPath
	 *            Example: foo.bar.key
	 * @param defaultValue
	 *            is returned when the propertyPath does not exist
	 * @return the value for the given propertyPath
	 */
	public String getString(String propertyPath, String defaultValue) {
		Object o = getValue(propertyPath, defaultValue);
		if (o != null) {
			return o.toString();
		} else {
			// if the defaultValue is null
			return (String) o;
		}
	}

	/**
	 * Get the int-value from the property path.
	 *
	 * @param propertyPath
	 *            Example: foo.bar.key
	 * @return the {@link Integer} value
	 * @throws PropertyException
	 *             If the propertyKey-Path is not a property key or is a not
	 *             part of a property path; also when the value is not an
	 *             {@link Integer}
	 */
	public int getInt(String propertyPath) throws PropertyException {
		Object o = getValue(propertyPath);
		return Integer.parseInt(o.toString());
	}

	/**
	 * Get the int-value from the property path. If the property path does not
	 * exist, the default value is returned.
	 *
	 * @param propertyPath
	 *            Example: foo.bar.key
	 * @param defaultValue
	 *            is returned when the propertyPath does not exist
	 * @return the value for the given propertyPath
	 */
	public int getInt(String propertyPath, int defaultValue) {
		Object o = getValue(propertyPath, defaultValue);
		return Integer.parseInt(o.toString());
	}

	/**
	 * Get the double-value from the property path.
	 *
	 * @param propertyPath
	 *            Example: foo.bar.key
	 *
	 * @return the {@link Double} value for the given propertyPath
	 * @throws PropertyException
	 *             If the propertyKey-Path is not a property key or is a not
	 *             part of a property path; also when the value is not a
	 *             {@link Double}
	 */
	public double getDouble(String propertyPath) throws PropertyException {
		Object o = getValue(propertyPath);
		return Double.parseDouble(o.toString());
	}

	/**
	 * Get the double-value from the property path. If the property path does
	 * not exist, the default value is returned.
	 *
	 * @param propertyPath
	 *            Example: foo.bar.key
	 * @param defaultValue
	 *            is returned when the propertyPath does not exist
	 * @return the {@link Double} value for the given propertyPath
	 */
	public double getDouble(String propertyPath, double defaultValue) {
		Object o = getValue(propertyPath, defaultValue);
		return Double.parseDouble(o.toString());
	}

	/**
	 * Get the boolean-value from the property path.
	 *
	 * @param propertyPath
	 *            Example: foo.bar.key
	 * @return the {@link Boolean} value for the given propertyPath
	 * @throws PropertyException
	 *             If the propertyKey-Path is not a property key or is a not
	 *             part of a property path; also when the value is not a
	 *             {@link Boolean}
	 */
	public boolean getBoolean(String propertyPath) throws PropertyException {
		Object o = getValue(propertyPath);
		return Boolean.parseBoolean(o.toString());
	}

	/**
	 * Get the boolean-value from the property path. If the property path does
	 * not exist, the default value is returned.
	 *
	 * @param propertyPath
	 *            Example: foo.bar.key
	 * @param defaultValue
	 *            is returned when the propertyPath does not exist
	 * @return the {@link Boolean} value for the given propertyPath
	 */
	public boolean getBoolean(String propertyPath, boolean defaultValue) {
		Object o = getValue(propertyPath, defaultValue);
		return Boolean.parseBoolean(o.toString());
	}

	/**
	 * Return all keys of this {@link Properties} object.
	 *
	 * @throws PropertyException
	 *             if loading fails
	 * @return a {@link Set} containing all property keys
	 */
	public Set<String> getKeySet() throws PropertyException {
		return load().keySet();
	}

	/**
	 * Remove the last part of a property path.
	 *
	 * @param propertyPath
	 *            Example: foo.bar.key
	 * @return Example: foo.bar
	 */
	@SuppressWarnings("unused")
	private String removeLastToken(String propertyPath) {
		String[] propertyKeyArray = propertyPath.split("\\.");

		StringBuilder sb = new StringBuilder();
		for (int i = 0; i < propertyKeyArray.length - 2; i++) {
			sb.append(propertyKeyArray[i]);
			sb.append('.');
		}
		sb.append(propertyKeyArray[propertyKeyArray.length - 2]);
		return sb.toString();
	}

	/**
	 *
	 * Add a property path to an existing.
	 *
	 * @param propertyPath
	 *            a property path that will be the prefix to a new concatenated
	 *            path. Example: foo.bar
	 * @param pathToAdded
	 *            a property path to be appended new.property
	 * @return Example: foo.bar.new.property; if propertyPath is null or an
	 *         empty {@link String}, then pathToAdded is returned.
	 */
	private String addPath(String propertyPath, String pathToAdded) {
		if (propertyPath == null || propertyPath.equals("")) {
			return pathToAdded;
		} else {
			return new StringBuilder(propertyPath).append('.')
					.append(pathToAdded).toString();
		}
	}

	/**
	 * Adds a given value object to the properties {@link Map} corresponding to
	 * the configuration file, creates deeper nested maps if necessary.
	 *
	 * @param propertyPath
	 *            a property path where the value will be stored at
	 * @param propertyValue
	 *            the value to be added to the root map
	 * @throws PropertyException
	 *             If the propertyKey-Path is not a property key or is a not
	 *             part of a property path
	 */
	@SuppressWarnings("unchecked")
	private void addToRootMap(String propertyPath, Object propertyValue)
			throws PropertyException {
		// split propertyPath
		String[] propertyKeyArray = propertyPath.split("\\.");

		// load configMap from disk
		Map<String, Object> configMap = load();

		// if simple token add too root map
		if (propertyKeyArray.length == 1) {
			configMap.put(propertyPath, propertyValue);
		}

		// add to root map
		Map<String, Object> deeperNestedMap = configMap;
		Object foundedValue = null;

		// search the subMap with the key[i] without the last key
		for (int i = 0; i < propertyKeyArray.length - 1; i++) {
			foundedValue = deeperNestedMap.get(propertyKeyArray[i]);
			if (foundedValue instanceof Map) {
				// go deeper if something was found
				deeperNestedMap = (Map<String, Object>) foundedValue;
			} else {
				// if foundedValue == null or not a Map
				// then build from the i position new nested map(s)
				String[] subArray = getCopyFrom(i + 1, propertyKeyArray);
				Map<String, Object> newNestedMap = buildNewNestedMap(subArray,
						propertyValue);

				// add the newNestedMap map to the deeperNestedMap
				deeperNestedMap.put(propertyKeyArray[i], newNestedMap);
				break;
			}

			if (i == propertyKeyArray.length - 2) {
				deeperNestedMap.put(
						propertyKeyArray[propertyKeyArray.length - 1],
						propertyValue);
			}
		}

		// save all
		save(configMap);
	}

	/**
	 * Creates a new {@link Map} from a given array of keys and a value. If
	 * necessary, nested maps will be created for all given keys.
	 *
	 * @param mapKeys
	 *            array of keys as {@link String}
	 * @param propertyValue
	 *            the value to be stored at the "deeped" nested map
	 * @return a {@link Map}, containing nested maps for all keys and the given
	 *         value
	 */
	public Map<String, Object> buildNewNestedMap(String[] mapKeys,
			Object propertyValue) {
		Map<String, Object> rootMap = new HashMap<String, Object>();
		Map<String, Object> higherNestedMap = new HashMap<String, Object>();
		Map<String, Object> deeperNestedMap = new HashMap<String, Object>();

		// if only one key, add to the rootMap and return
		if (mapKeys.length - 1 == 0) {
			rootMap.put(mapKeys[0], propertyValue);
			return rootMap;
		}
		// after if we need min. one nestedMap

		// add deeperNestedMap to the higherNestedMap with the first key
		higherNestedMap.put(mapKeys[0], deeperNestedMap);

		// add the first nested map to the root map
		rootMap.putAll(higherNestedMap);

		// switch the higherNestedMap to the deeperNestedMap
		higherNestedMap = deeperNestedMap;

		// if mapKeys.length > 1
		for (int i = 1; i <= mapKeys.length - 1; i++) {
			if (i == mapKeys.length - 1) {
				higherNestedMap.put(mapKeys[i], propertyValue);
				break;
			}

			// make a new deeperNestedMap
			deeperNestedMap = new HashMap<String, Object>();

			// add deeperNestedMap to the higherNestedMap with the first key[i]
			higherNestedMap.put(mapKeys[i], deeperNestedMap);

			// switch the higherNestedMap to the deeperNestedMap
			higherNestedMap = deeperNestedMap;
		}

		return rootMap;
	}

	/**
	 * Copies a given {@link String} array from a given start index to the end.
	 *
	 * @param from
	 *            start index for copying
	 * @param propertyKeyArray
	 *            a {@link String} array
	 * @return a copy of the {@link String} array
	 */
	private String[] getCopyFrom(int from, String[] propertyKeyArray) {
		return Arrays.copyOfRange(propertyKeyArray, from,
				propertyKeyArray.length);
	}

	/**
	 * Save the value with a given key. {@link Map} and {@link List} can only
	 * contain simple data-types.
	 *
	 * @param propertyPath
	 *            Example: foo.bar.key
	 * @param propertyValue
	 *            only {@link String}, {@link Integer}, {@link Double},
	 *            {@link Boolean}, {@link Map} and {@link List} are supported
	 * @throws PropertyException
	 *             If the propertyKey-Path is not a property key or is not part
	 *             of a property path.
	 */
	public void set(String propertyPath, Object propertyValue)
			throws PropertyException {
		// check propertyPath
		ObjectChecks
				.checkForNullReference(propertyPath, "propertyPath is null");

		// check propertyValue
		if (!(propertyValue instanceof String)
				&& !(propertyValue instanceof Integer)
				&& !(propertyValue instanceof Double)
				&& !(propertyValue instanceof Boolean)
				&& !(propertyValue instanceof Map)
				&& !(propertyValue instanceof List)) {
			throw new PropertyException(
					"Only String, int, double, boolean, Map and List are supported.");
		}

		// add the mPreFixPropertyPath if this Property is a copy with a deeper
		// level
		String fullPropertyPath = addPath(mPropertyPathPrefix, propertyPath);

		// add propertyValue with the fullPropertyPath
		addToRootMap(fullPropertyPath, propertyValue);

		mLogger.debug("Set the property value: " + propertyValue + " on key: "
				+ fullPropertyPath);
	}

	@Override
	public String toString() {
		try {
			return load().toString();
		} catch (PropertyException e) {
			return e.toString();
		}
	}
}
