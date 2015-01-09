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

import static org.junit.Assert.assertEquals;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.junit.Before;
import org.junit.Test;

import de.hshannover.f4.trust.ironcommon.util.Util;

/**
 * @author Marcel Reichenbach
 *
 */
public class YamlTest {

	private final String mNotExistingFilePath = "src/test/resources/123456789/test.yml";
	private final String mFilePathYamlReaderCreateNewFileTest = "src/test/resources/yamlReaderCreateNewFileTest.yml";
	private final String mFilePathYamlWriterCreateNewFileTest = "src/test/resources/yamlWriterCreateNewFileTest.yml";
	private final String mFilePathSimpleDatatypeTest = "src/test/resources/testSimpleDatatyps.yml";
	private final String mFilePathCollectionsTest = "src/test/resources/testCollections.yml";
	private final String mFilePathEmptyMapTest = "src/test/resources/testEmptyMap.yml";

	private final String mStringYamlWriterSaveObject = "YamlWriterSaveObject";
	private final String mSimpleDatatypeTestString = "String-Value Object";
	private final Integer mSimpleDatatypeTestInteger = 123456789;
	private final Double mSimpleDatatypeTestDouble = 123456789.123456789;
	private final Boolean mSimpleDatatypeTestBoolean = true;

	private final Map<String, Object> mCollectionsTestMap = Util.buildTestMap();
	private final List<String> mCollectionsTestList = Util.buildTestList();
	private final Set<String> mCollectionsTestSet = Util.buildTestSet();

	/**
	 *
	 */
	@Before
	public void setUp() {
		File f = new File("src/test/resources/");
		if (!f.exists()) {
			// If it doens't exist, try to create it.
			f.mkdir();
		}
	}

	/**
	 * 1. Tests the IOException from YamlWriter when the file path not exists.
	 *
	 * @throws IOException
	 */
	@Test(expected = IOException.class)
	public void testYamlWriterIOException() throws IOException {
		YamlWriter.persist(mNotExistingFilePath, mStringYamlWriterSaveObject);
	}

	/**
	 * 1. Tests the NullPointerException from YamlWriter when the filename
	 * parameter is null.
	 *
	 * @throws IOException
	 */
	@Test(expected = NullPointerException.class)
	public void testYamlWriterNullPointerExceptionWithEmptyFilenameParameter()
			throws IOException {
		YamlWriter.persist(null, mStringYamlWriterSaveObject);
	}

	/**
	 * 1. Tests the NullPointerException from YamlWriter when the object
	 * parameter is null.
	 *
	 * @throws IOException
	 */
	@Test(expected = NullPointerException.class)
	public void testYamlWriterNullPointerExceptionWithEmptyObjectParameter()
			throws IOException {
		YamlWriter.persist(mNotExistingFilePath, null);
	}

	/**
	 * 1. Tests the YamlWriter when the file does not exist if it is created.
	 *
	 * @throws IOException
	 */
	@Test
	public void testYamlWriterCreateNewFile() throws IOException {
		// clean the system
		Util.deleteTestFile(mFilePathYamlWriterCreateNewFileTest);

		// create the file
		YamlWriter.persist(mFilePathYamlWriterCreateNewFileTest,
				mStringYamlWriterSaveObject);

		// tests if exists and clean the system
		Util.checkAndDeleteTestFile(mFilePathYamlWriterCreateNewFileTest);
	}

	/**
	 * 1. Tests the IOException from YamlReader when the file path not exists.
	 *
	 * @throws IOException
	 */
	@Test(expected = IOException.class)
	public void testYamlReaderIOException() throws IOException {
		YamlReader.loadAs(mNotExistingFilePath, String.class);
	}

	/**
	 * 1. Tests the NullPointerException from YamlReader when the filename
	 * parameter is null.
	 *
	 * @throws IOException
	 */
	@Test(expected = NullPointerException.class)
	public void testYamlReaderNullPointerExceptionWithEmptyFilenameParameter()
			throws IOException {
		YamlReader.loadAs(null, String.class);
	}

	/**
	 * 1. Tests the NullPointerException from YamlReader when the class
	 * parameter is null.
	 *
	 * @throws IOException
	 */
	@Test(expected = NullPointerException.class)
	public void testYamlReaderNullPointerExceptionWithEmptyClassParameter()
			throws IOException {
		YamlReader.loadAs(mNotExistingFilePath, null);
	}

	/**
	 * 1. Tests the YamlReader when the file does not exist if it is created.
	 *
	 * @throws IOException
	 */
	@Test
	public void testYamlReaderCreateNewFile() throws IOException {
		// clean the system
		Util.deleteTestFile(mFilePathYamlReaderCreateNewFileTest);

		// create the file
		YamlReader.loadAs(mFilePathYamlReaderCreateNewFileTest, String.class);

		// tests if exists and clean the system
		Util.checkAndDeleteTestFile(mFilePathYamlReaderCreateNewFileTest);
	}

	/**
	 * @throws IOException
	 */
	@Test
	public void testYamlReaderEmptyMap() throws IOException {
		// clean the system
		Util.deleteTestFile(mFilePathEmptyMapTest);

		// create the file
		Map<String, Object> loadedMap = YamlReader
				.loadMap(mFilePathEmptyMapTest);

		// check if map is not null
		if (loadedMap == null) {
			throw new RuntimeException("The map is null!");
		}

		// check if map is empty
		if (!loadedMap.isEmpty()) {
			throw new RuntimeException("The map is not empty!");
		}

		// tests if exists and clean the system
		Util.checkAndDeleteTestFile(mFilePathEmptyMapTest);
	}

	/**
	 * 1. Tests if String|int|double|boolean Collections can be stored. 2. Tests
	 * if String|int|double|boolean Collections can be loaded. 3. Checked for
	 * equality
	 *
	 * @throws IOException
	 */
	@Test
	public void testSimpleDatatypes() throws IOException {
		// String Test
		YamlWriter.persist(mFilePathSimpleDatatypeTest,
				mSimpleDatatypeTestString);
		assertEquals(mSimpleDatatypeTestString,
				YamlReader.loadAs(mFilePathSimpleDatatypeTest, String.class));

		// int Test
		YamlWriter.persist(mFilePathSimpleDatatypeTest,
				mSimpleDatatypeTestInteger);
		assertEquals(mSimpleDatatypeTestInteger,
				YamlReader.loadAs(mFilePathSimpleDatatypeTest, Integer.class));

		// double Test
		YamlWriter.persist(mFilePathSimpleDatatypeTest,
				mSimpleDatatypeTestDouble);
		assertEquals(mSimpleDatatypeTestDouble,
				YamlReader.loadAs(mFilePathSimpleDatatypeTest, Double.class));

		// boolean Test
		YamlWriter.persist(mFilePathSimpleDatatypeTest,
				mSimpleDatatypeTestBoolean);
		assertEquals(mSimpleDatatypeTestBoolean,
				YamlReader.loadAs(mFilePathSimpleDatatypeTest, Boolean.class));

		Util.checkAndDeleteTestFile(mFilePathSimpleDatatypeTest);
	}

	/**
	 * 1. Tests if List|Set|Map Collections can be stored. 2. Tests if
	 * List|Set|Map Collections can be loaded. 3. Checked for equality
	 *
	 * @throws IOException
	 */
	@Test
	public void testCollections() throws IOException {
		// List Test
		YamlWriter.persist(mFilePathCollectionsTest, mCollectionsTestList);
		assertEquals(mCollectionsTestList,
				YamlReader.loadAs(mFilePathCollectionsTest, List.class));

		// Set Test
		YamlWriter.persist(mFilePathCollectionsTest, mCollectionsTestSet);
		assertEquals(mCollectionsTestSet,
				YamlReader.loadAs(mFilePathCollectionsTest, Set.class));

		// Map Test
		YamlWriter.persist(mFilePathCollectionsTest, mCollectionsTestMap);
		assertEquals(mCollectionsTestMap,
				YamlReader.loadAs(mFilePathCollectionsTest, Map.class));
		assertEquals(mCollectionsTestMap,
				YamlReader.loadMap(mFilePathCollectionsTest));

		Util.checkAndDeleteTestFile(mFilePathCollectionsTest);
	}

}
