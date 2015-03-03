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

import de.hshannover.f4.trust.ironcommon.util.TestUtilities;

/**
 * Test class for {@link YamlReader} and {@link YamlWriter}.
 *
 * @author Marcel Reichenbach
 *
 */
public class YamlTest {

	private static final String DIRECTORY_TEST_RESOURCES = "src/test/resources/";

	private final String mNotExistingFilePath = DIRECTORY_TEST_RESOURCES
			+ "123456789/test.yml";
	private final String mFilePathYamlReaderCreateNewFileTest = DIRECTORY_TEST_RESOURCES
			+ "yamlReaderCreateNewFileTest.yml";
	private final String mFilePathYamlWriterCreateNewFileTest = DIRECTORY_TEST_RESOURCES
			+ "yamlWriterCreateNewFileTest.yml";
	private final String mFilePathSimpleDatatypeTest = DIRECTORY_TEST_RESOURCES
			+ "testSimpleDatatyps.yml";
	private final String mFilePathCollectionsTest = DIRECTORY_TEST_RESOURCES
			+ "testCollections.yml";
	private final String mFilePathEmptyMapTest = DIRECTORY_TEST_RESOURCES
			+ "testEmptyMap.yml";

	private final String mStringYamlWriterSaveObject = "YamlWriterSaveObject";
	private final String mSimpleDatatypeTestString = "String-Value Object";
	private final Integer mSimpleDatatypeTestInteger = 123456789;
	private final Double mSimpleDatatypeTestDouble = 123456789.123456789;
	private final Boolean mSimpleDatatypeTestBoolean = true;

	private final Map<String, Object> mCollectionsTestMap = TestUtilities
			.buildTestMap();
	private final List<String> mCollectionsTestList = TestUtilities
			.buildTestList();
	private final Set<String> mCollectionsTestSet = TestUtilities
			.buildTestSet();

	/**
	 * Sets up every test by creating the folder for storing temporary test
	 * files.
	 */
	@Before
	public void setUp() {
		File f = new File(DIRECTORY_TEST_RESOURCES);
		if (!f.exists()) {
			f.mkdir();
		}
	}

	/**
	 * Tests the {@link IOException} from {@link YamlWriter} when the file does
	 * not exists.
	 *
	 * @throws IOException
	 *             thrown when the file does not exist
	 */
	@Test(expected = IOException.class)
	public void testYamlWriterIoException() throws IOException {
		YamlWriter.persist(mNotExistingFilePath, mStringYamlWriterSaveObject);
	}

	/**
	 * Tests the {@link NullPointerException} from {@link YamlWriter} when the
	 * filename parameter is null.
	 *
	 * @throws IOException
	 *             thrown when the file does not exist
	 */
	@Test(expected = NullPointerException.class)
	public void testYamlWriterNullPointerExceptionWithEmptyFilenameParameter()
			throws IOException {
		YamlWriter.persist(null, mStringYamlWriterSaveObject);
	}

	/**
	 * Tests the {@link NullPointerException} from {@link YamlWriter} when the
	 * object parameter is null.
	 *
	 * @throws IOException
	 *             thrown when the file does not exist
	 */
	@Test(expected = NullPointerException.class)
	public void testYamlWriterNullPointerExceptionWithEmptyObjectParameter()
			throws IOException {
		YamlWriter.persist(mNotExistingFilePath, null);
	}

	/**
	 * Tests the if the {@link YamlWriter} creates the file when it does not
	 * exists.
	 *
	 * @throws IOException
	 *             thrown when the file does not exist
	 */
	@Test
	public void testYamlWriterCreateNewFile() throws IOException {
		// clean the system
		TestUtilities.deleteTestFile(mFilePathYamlWriterCreateNewFileTest);

		// create the file
		YamlWriter.persist(mFilePathYamlWriterCreateNewFileTest,
				mStringYamlWriterSaveObject);

		// tests if exists and clean the system
		TestUtilities
				.checkAndDeleteTestFile(mFilePathYamlWriterCreateNewFileTest);
	}

	/**
	 * Tests the {@link IOException} from {@link YamlReader} when the file does
	 * not exists.
	 *
	 * @throws IOException
	 *             thrown when the file does not exist
	 */
	@Test(expected = IOException.class)
	public void testYamlReaderIoException() throws IOException {
		YamlReader.loadAs(mNotExistingFilePath, String.class);
	}

	/**
	 * Tests the {@link NullPointerException} from {@link YamlReader} when the
	 * filename parameter is null.
	 *
	 * @throws IOException
	 *             thrown when the file does not exist
	 */
	@Test(expected = NullPointerException.class)
	public void testYamlReaderNullPointerExceptionWithEmptyFilenameParameter()
			throws IOException {
		YamlReader.loadAs(null, String.class);
	}

	/**
	 * Tests the {@link NullPointerException} from {@link YamlReader} when the
	 * class parameter is null.
	 *
	 * @throws IOException
	 *             thrown when the file does not exist
	 */
	@Test(expected = NullPointerException.class)
	public void testYamlReaderNullPointerExceptionWithEmptyClassParameter()
			throws IOException {
		YamlReader.loadAs(mNotExistingFilePath, null);
	}

	/**
	 * Tests the if the {@link YamlReader} creates the file when it does not
	 * exists.
	 *
	 * @throws IOException
	 *             thrown when the file does not exist
	 */
	@Test
	public void testYamlReaderCreateNewFile() throws IOException {
		// clean the system
		TestUtilities.deleteTestFile(mFilePathYamlReaderCreateNewFileTest);

		// create the file
		YamlReader.loadAs(mFilePathYamlReaderCreateNewFileTest, String.class);

		// tests if exists and clean the system
		TestUtilities
				.checkAndDeleteTestFile(mFilePathYamlReaderCreateNewFileTest);
	}

	/**
	 * Tests if the result of a load-call of a {@link YamlReader} returns an
	 * empty list, when the file does not exists.
	 *
	 * @throws IOException
	 *             thrown when the file does not exist
	 */
	@Test
	public void testYamlReaderEmptyMap() throws IOException {
		// clean the system
		TestUtilities.deleteTestFile(mFilePathEmptyMapTest);

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
		TestUtilities.checkAndDeleteTestFile(mFilePathEmptyMapTest);
	}

	/**
	 * <ol>
	 * <li>Tests if String|int|double|boolean Collections can be stored.</li>
	 * <li>Tests if String|int|double|boolean Collections can be loaded.</li>
	 * <li>Checked for equality</li>
	 * </ol>
	 *
	 * @throws IOException
	 *             thrown when the file does not exist
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

		TestUtilities.checkAndDeleteTestFile(mFilePathSimpleDatatypeTest);
	}

	/**
	 * <ol>
	 * <li>Tests if List|Set|Map Collections can be stored.</li>
	 * <li>Tests if List|Set|Map Collections can be loaded.</li>
	 * <li>Checked for equality</li>
	 * </ol>
	 *
	 * @throws IOException
	 *             thrown when the file does not exist
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

		TestUtilities.checkAndDeleteTestFile(mFilePathCollectionsTest);
	}

}
