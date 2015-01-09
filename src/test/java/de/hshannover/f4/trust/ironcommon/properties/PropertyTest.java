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

package de.hshannover.f4.trust.ironcommon.properties;

import static org.junit.Assert.assertEquals;

import java.io.File;
import java.util.List;
import java.util.Map;

import org.junit.Before;
import org.junit.Test;

import de.hshannover.f4.trust.ironcommon.util.Util;

/**
 * @author Marcel Reichenbach
 *
 */
public class PropertyTest {

	private final String mNotExistingFilePath = "src/test/resources/123456789/test.yml";
	private final String mFilePathForEqualityTest = "src/test/resources/testEquality.yml";

	private final String mKeyOneTokenKey = "foo1";
	private final String mKeyTwoTokenKeys = "foo2.bar";
	private final String mKeyTwoTokenKeys2 = "foo2.bar2";
	private final String mKeyManyTokenKeys = "foo3.bar.fubar.baz";
	private final String mKeyManyTokenKeys2 = "foo3.bar.fubar.baz2";

	private final String mKeyValueOneTokenKey = "KeyValue_oneTokenKey";
	private final String mKeyValueTwoTokenKeys = "KeyValue_twoTokenKeys";
	private final String mKeyValueManyTokenKeys = "KeyValue_manyTokenKeys";

	private final List<String> mCollectionsTestList = Util.buildTestList();

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
	 * 1. Tests the PropertyException when the file path not exists.
	 *
	 * @throws PropertyException
	 */
	@Test(expected = PropertyException.class)
	public void testPropertyException() throws PropertyException {
		new Properties(mNotExistingFilePath).load();
	}

	/**
	 * 1. Tests the PropertyException from Properties when the filename
	 * parameter is null.
	 *
	 * @throws PropertyException
	 */
	@Test(expected = NullPointerException.class)
	public void testPropertyExceptionWithEmptyFilenameParameter()
			throws PropertyException {
		new Properties(null);
	}

	/**
	 * 1. Tests if String-Value can be stored and loaded under one token key. 2.
	 * Tests if String-Value can be stored and loaded under two token keys. 3.
	 * Tests if String-Value can be stored and loaded under many token keys.
	 *
	 * @throws PropertyException
	 */
	@Test
	public void testForEqualityWithEmptyProperties() throws PropertyException {
		// clean the system
		Util.deleteTestFile(mFilePathForEqualityTest);

		// with one Token key
		new Properties(mFilePathForEqualityTest).set(mKeyOneTokenKey,
				mKeyValueOneTokenKey);
		assertEquals(mKeyValueOneTokenKey, new Properties(
				mFilePathForEqualityTest).getValue(mKeyOneTokenKey).toString());
		Util.checkAndDeleteTestFile(mFilePathForEqualityTest);

		// with two Token keys
		new Properties(mFilePathForEqualityTest).set(mKeyTwoTokenKeys,
				mKeyValueTwoTokenKeys);
		assertEquals(mKeyValueTwoTokenKeys, new Properties(
				mFilePathForEqualityTest).getValue(mKeyTwoTokenKeys).toString());
		Util.checkAndDeleteTestFile(mFilePathForEqualityTest);

		// with many Token keys
		new Properties(mFilePathForEqualityTest).set(mKeyManyTokenKeys,
				mKeyValueManyTokenKeys);
		assertEquals(mKeyValueManyTokenKeys, new Properties(
				mFilePathForEqualityTest).getValue(mKeyManyTokenKeys)
				.toString());
		Util.checkAndDeleteTestFile(mFilePathForEqualityTest);
	}

	/**
	 * 1. Tests whether existing values ​​are overwritten with new values.
	 *
	 * @throws PropertyException
	 */
	@Test
	public void testForEqualityWithFilledProperties() throws PropertyException {
		// clean the system
		Util.deleteTestFile(mFilePathForEqualityTest);

		// setUp
		Properties properties = new Properties(mFilePathForEqualityTest);
		properties.set(mKeyManyTokenKeys, mCollectionsTestList);
		properties.set(mKeyTwoTokenKeys, mCollectionsTestList);

		// equals setUp Values
		Properties properties2 = new Properties(mFilePathForEqualityTest);
		assertEquals(mCollectionsTestList,
				properties2.getValue(mKeyManyTokenKeys));
		assertEquals(mCollectionsTestList,
				properties2.getValue(mKeyTwoTokenKeys));

		// add new values
		properties2.set(mKeyManyTokenKeys2, mKeyValueManyTokenKeys);
		properties2.set(mKeyTwoTokenKeys2, mKeyValueTwoTokenKeys);

		// equals setUp values and new values
		Properties properties3 = new Properties(mFilePathForEqualityTest);
		assertEquals(mCollectionsTestList,
				properties3.getValue(mKeyManyTokenKeys));
		assertEquals(mCollectionsTestList,
				properties3.getValue(mKeyTwoTokenKeys));
		assertEquals(mKeyValueManyTokenKeys,
				properties3.getValue(mKeyManyTokenKeys2));
		assertEquals(mKeyValueTwoTokenKeys,
				properties3.getValue(mKeyTwoTokenKeys2));

		// tests if exists and clean the system
		Util.checkAndDeleteTestFile(mFilePathForEqualityTest);
	}

	/**
	 * 1. Tests whether existing values ​​are overwritten with new values. This
	 * new value is now a new Map. Example: foo: bar: fubar: test
	 *
	 * The new value is: foo: bar: fubar: baz: TEST
	 *
	 * @throws PropertyException
	 */
	@Test
	public void testForEqualityWithFilledPropertiesAddNewMapProperty()
			throws PropertyException {

		// clean the system
		Util.deleteTestFile(mFilePathForEqualityTest);
		testForEqualityWithFilledPropertiesAddNewMapPropertyOneMap();
		// tests if exists and clean the system
		Util.checkAndDeleteTestFile(mFilePathForEqualityTest);

		// clean the system
		Util.deleteTestFile(mFilePathForEqualityTest);
		testForEqualityWithFilledPropertiesAddNewMapPropertyTwoMaps();
		// tests if exists and clean the system
		Util.checkAndDeleteTestFile(mFilePathForEqualityTest);

		// clean the system
		Util.deleteTestFile(mFilePathForEqualityTest);
		testForEqualityWithFilledPropertiesAddNewMapPropertyManyMaps();
		// tests if exists and clean the system
		Util.checkAndDeleteTestFile(mFilePathForEqualityTest);

	}

	private void testForEqualityWithFilledPropertiesAddNewMapPropertyOneMap()
			throws PropertyException {
		// setUp
		Properties properties = new Properties(mFilePathForEqualityTest);
		Map<String, Object> equalsMap = properties.buildNewNestedMap(
				new String[] { "foo", "bar", "fubar", "baz" }, "TEST");
		properties.set("foo.bar.fubar", "test");

		// add one Map
		Properties properties2 = new Properties(mFilePathForEqualityTest);
		properties2.set("foo.bar.fubar.baz", "TEST");

		// equals
		Properties equalsProperties = new Properties(mFilePathForEqualityTest);

		// is the right value under foo.bar.fubar.baz
		assertEquals("TEST", equalsProperties.getValue("foo.bar.fubar.baz"));

		@SuppressWarnings("unchecked")
		// get root map
		Map<String, Object> fooSubMap = (Map<String, Object>) equalsProperties
				.getValue("");
		// is the Map right
		assertEquals(true, fooSubMap.equals(equalsMap));

	}

	private void testForEqualityWithFilledPropertiesAddNewMapPropertyTwoMaps()
			throws PropertyException {
		// setUp
		Properties properties = new Properties(mFilePathForEqualityTest);
		Map<String, Object> equalsMap = properties.buildNewNestedMap(
				new String[] { "bar", "fubar", "baz", "qux" }, "TEST");
		properties.set("foo.bar.fubar", "test");

		// add two Maps
		Properties properties2 = new Properties(mFilePathForEqualityTest);
		properties2.set("foo.bar.fubar.baz.qux", "TEST");

		// equals
		Properties equalsProperties = new Properties(mFilePathForEqualityTest);

		// is the right value under foo.bar.fubar.baz.qux
		assertEquals("TEST", equalsProperties.getValue("foo.bar.fubar.baz.qux"));

		@SuppressWarnings("unchecked")
		Map<String, Object> fooSubMap = (Map<String, Object>) equalsProperties
				.getValue("foo");
		// is the Map right
		assertEquals(true, fooSubMap.equals(equalsMap));

	}

	private void testForEqualityWithFilledPropertiesAddNewMapPropertyManyMaps()
			throws PropertyException {
		// setUp
		Properties properties = new Properties(mFilePathForEqualityTest);
		Map<String, Object> equalsMap = properties.buildNewNestedMap(
				new String[] { "bar", "fubar", "baz", "qux", "quux", "qux2",
						"quux2", }, "TEST");
		properties.set("foo.bar.fubar", "test");

		// add many Maps
		Properties properties2 = new Properties(mFilePathForEqualityTest);
		properties2.set("foo.bar.fubar.baz.qux.quux.qux2.quux2", "TEST");

		// equals
		Properties equalsProperties = new Properties(mFilePathForEqualityTest);

		// is the right value under foo.bar.fubar.baz.qux.quux.qux2.quux2
		assertEquals("TEST",
				equalsProperties
						.getValue("foo.bar.fubar.baz.qux.quux.qux2.quux2"));

		@SuppressWarnings("unchecked")
		Map<String, Object> fooSubMap = (Map<String, Object>) equalsProperties
				.getValue("foo");
		// is the Map right
		assertEquals(true, fooSubMap.equals(equalsMap));

	}

	/**
	 * 1. Tests the set(), when Properties is in a subMap
	 *
	 * @throws PropertyException
	 */
	@Test
	public void testForEqualityWithFilledPropertiesSetFromAGet()
			throws PropertyException {

		// clean the system
		Util.deleteTestFile(mFilePathForEqualityTest);

		// setUp
		Properties properties = new Properties(mFilePathForEqualityTest);
		Map<String, Object> equalsMap = properties.buildNewNestedMap(
				new String[] { "bar", "fubar" }, "TEST");
		properties.set("foo.bar.fubar", "test");

		// get and set
		Properties properties2 = new Properties(mFilePathForEqualityTest);
		// the set is called from a subMap foo.bar
		properties2.get("foo").get("bar").set("fubar", "TEST");

		// equals
		Properties equalsProperties = new Properties(mFilePathForEqualityTest);

		// is the right value under foo.bar.fubar.baz.qux.quux.qux2.quux2
		assertEquals("TEST", equalsProperties.getValue("foo.bar.fubar"));

		@SuppressWarnings("unchecked")
		Map<String, Object> fooSubMap = (Map<String, Object>) equalsProperties
				.getValue("foo");
		// is the Map right
		assertEquals(true, fooSubMap.equals(equalsMap));

		// tests if exists and clean the system
		Util.checkAndDeleteTestFile(mFilePathForEqualityTest);

	}
}
