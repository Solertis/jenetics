/*
 * Java Genetic Algorithm Library (@__identifier__@).
 * Copyright (c) @__year__@ Franz Wilhelmstötter
 *
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
 *
 * Author:
 *    Franz Wilhelmstötter (franz.wilhelmstoetter@gmx.at)
 */
package org.jenetics.internal.util;

import static java.util.Objects.requireNonNull;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import javax.xml.bind.DataBindingException;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;

import org.jenetics.util.ISeq;

/**
 * Caches the JAXB classes and lets you add additional one.
 *
 * @author <a href="mailto:franz.wilhelmstoetter@gmx.at">Franz Wilhelmstötter</a>
 * @version !__version__!
 * @since !__version__!
 */
public final class JAXBContextCache {
	private JAXBContextCache() {require.noInstance();}

	private static final List<Class<?>> CLASSES = new ArrayList<>();
	static {
		addPackage("org.jenetics");
		addPackage("org.jenetics.engine");
		addPackage("org.jenetics.internal.util");
	}

	private static JAXBContext _context;

	public static synchronized JAXBContext context() {
		if (_context == null) {
			try {
				_context = JAXBContext
					.newInstance(CLASSES.toArray(new Class<?>[CLASSES.size()]));
			} catch (JAXBException e) {
				throw new DataBindingException(
					"Something went wrong while creating JAXBContext.", e
				);
			}
		}

		return _context;
	}

	public static synchronized void addPackage(final String pkg) {
		requireNonNull(pkg);

		final List<Class<?>> classes = jaxbClasses(pkg);
		if (!classes.isEmpty()) {
			_context = null;
			CLASSES.addAll(jaxbClasses(pkg));
		}
	}

	public static synchronized void addClass(final Class<?> cls) {
		requireNonNull(cls);

		_context = null;
		CLASSES.add(cls);
	}

	@SuppressWarnings("unchecked")
	private static List<Class<?>> jaxbClasses(final String pkg) {
		try {
			final Field field = Class
				.forName(pkg + ".jaxb")
				.getField("CLASSES");
			field.setAccessible(true);

			return ((ISeq<Class<?>>)field.get(null)).asList();
		} catch (ReflectiveOperationException e) {
			return Collections.emptyList();
		}
	}
}