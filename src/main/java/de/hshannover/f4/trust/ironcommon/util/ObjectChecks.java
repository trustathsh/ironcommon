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
 * This file is part of ironcommon, version 0.1.2, implemented by the Trust@HsH
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

/**
 * Helper class for checking objects (null checks, equality checks, ...)
 *
 * @author Arne Welzel
 * @author Marcel Reichenbach
 */
public final class ObjectChecks {

	/**
	 * Only static calls are allowed.
	 */
	private ObjectChecks() {
	}

	/**
	 * Copied from project: irond (https://github.com/trustathsh/irond.git
	 * Package: src.de.fhhannover.inform.iron.mapserver.utils
	 *
	 * If the given object is null, a {@link NullPointerException} is thrown
	 * with a given message.
	 *
	 * @param object
	 *            the {@link Object} that is checked for being a null reference
	 * @param message
	 *            a {@link String} that is given to the
	 *            {@link NullPointerException}
	 */
	public static void checkForNullReference(Object object, String message) {
		if (object == null) {
			throw new NullPointerException(message);
		}
	}

	/**
	 * Checks equality of two {@link Object}s. If both are null, true is
	 * returned. Otherwise, one.equals(other) or other.equals(one) are called,
	 * using the not null-value as the first operator.
	 *
	 * @param one
	 *            a {@link Object}
	 * @param other
	 *            another {@link Object}
	 * @return true, if both {@link Object} are equal or both are null
	 */
	public static boolean equalsWithNullReferenceAllowed(Object one,
			Object other) {
		if (one != null) {
			return one.equals(other);
		} else if (other != null) {
			return other.equals(one);
		} else {
			return true;
		}
	}
}
