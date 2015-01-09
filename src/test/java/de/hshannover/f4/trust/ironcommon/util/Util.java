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
package de.hshannover.f4.trust.ironcommon.util;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * @author Marcel Reichenbach
 *
 */
public final class Util {

	private Util() {
	}

	/**
	 * 1. Cleans the system after the test. 2. Tests if the test file exists. 3.
	 * Tests if all input or output streams were closed.
	 *
	 * @param filePath
	 */
	public static void checkAndDeleteTestFile(String filePath) {
		checkExistsTestFile(filePath);
		deleteTestFile(filePath);
	}

	/**
	 * 1. Tests if the test file exists.
	 *
	 * @param filePath
	 */
	public static void checkExistsTestFile(String filePath) {
		File testFile = new File(filePath);
		if (!testFile.exists()) {
			throw new RuntimeException("The test file " + filePath
					+ " not exists!");
		}
	}

	/**
	 * 1. Cleans the system after the test. 2. Tests if all input or output
	 * streams were closed.
	 *
	 * @param filePath
	 */
	public static void deleteTestFile(String filePath) {
		File testFile = new File(filePath);
		if (testFile.exists()) {
			if (!testFile.delete()) {
				throw new RuntimeException("Test file " + filePath
						+ " could not be deleted!");
			}
		}
	}

	/**
	 * @return a {@link List} containing some exemplary values
	 */
	public static List<String> buildTestList() {
		ArrayList<String> collectionsTestList = new ArrayList<String>();
		collectionsTestList.add("foo");
		collectionsTestList.add("bar");
		collectionsTestList.add("fubar");
		collectionsTestList.add("baz");
		collectionsTestList.add("qux");
		collectionsTestList.add("quux");
		collectionsTestList.add("The test String!");
		collectionsTestList.add("!\"§$%&/\\()=?#'+*~-_.:,;µ<>|@€^°");
		return collectionsTestList;
	}

	/**
	 * @return a {@link Set} containing some exemplary values
	 */
	public static Set<String> buildTestSet() {
		Set<String> collectionsTestSet = new HashSet<String>();
		collectionsTestSet.add("!\"§$%&/\\()=?#'+*~-_.:,;µ<>|@€^°");
		collectionsTestSet.add("The test String!");
		collectionsTestSet.add("quux");
		collectionsTestSet.add("qux");
		collectionsTestSet.add("baz");
		collectionsTestSet.add("fubar");
		collectionsTestSet.add("bar");
		collectionsTestSet.add("foo");
		return collectionsTestSet;
	}

	/**
	 * @return {@link Map} containing a {@link List} and a {@link Set}
	 */
	public static Map<String, Object> buildTestMap() {
		Map<String, Object> collectionsTestMap = new HashMap<String, Object>();
		collectionsTestMap.put("mTestList", buildTestList());
		collectionsTestMap.put("mTestSet", buildTestSet());
		return collectionsTestMap;
	}
}
