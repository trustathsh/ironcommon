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
 * Helper class with methods for testing.
 *
 * @author Marcel Reichenbach
 *
 */
public final class TestUtilities {

	/**
	 * Only static calls are allowed.
	 */
	private TestUtilities() {
	}

	/**
	 * <ol>
	 * <li>Cleans the system after the test</li>
	 * <li>Tests if the test file exists.</li>
	 * <li>Deletes the test file.</li>
	 * </ol>
	 *
	 * @param fileName
	 *            the absolute path of the testfile
	 */
	public static void checkAndDeleteTestFile(String fileName) {
		checkExistsTestFile(fileName);
		deleteTestFile(fileName);
	}

	/**
	 * Tests if the test file exists.
	 *
	 * @param fileName
	 *            the absolute path of the testfile
	 */
	public static void checkExistsTestFile(String fileName) {
		File testFile = new File(fileName);
		if (!testFile.exists()) {
			throw new RuntimeException("The test file " + fileName
					+ " not exists!");
		}
	}

	/**
	 * Deletes the test file.
	 *
	 * @param fileName
	 *            the absolute path of the testfile
	 */
	public static void deleteTestFile(String fileName) {
		File testFile = new File(fileName);
		if (testFile.exists()) {
			if (!testFile.delete()) {
				throw new RuntimeException("Test file " + fileName
						+ " could not be deleted!");
			}
		}
	}

	/**
	 * Creates a {@link List} with dummy values.
	 *
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
	 * Creates a {@link Set} with dummy values.
	 *
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
	 * Creates a {@link Map} with a dummy {@link List} and dummy {@link Set}.
	 *
	 * @return {@link Map} containing a {@link List} and a {@link Set}
	 */
	public static Map<String, Object> buildTestMap() {
		Map<String, Object> collectionsTestMap = new HashMap<String, Object>();
		collectionsTestMap.put("mTestList", buildTestList());
		collectionsTestMap.put("mTestSet", buildTestSet());
		return collectionsTestMap;
	}
}
