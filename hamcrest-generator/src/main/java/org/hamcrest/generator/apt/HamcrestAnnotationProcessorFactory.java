/*  Copyright (c) 2000-2006 hamcrest.org
 */
package org.hamcrest.generator.apt;

import static java.util.Collections.unmodifiableCollection;

import java.util.Arrays;
import java.util.Collection;
import java.util.Set;

import com.sun.mirror.apt.AnnotationProcessor;
import com.sun.mirror.apt.AnnotationProcessorEnvironment;
import com.sun.mirror.apt.AnnotationProcessorFactory;
import com.sun.mirror.apt.AnnotationProcessors;
import com.sun.mirror.declaration.AnnotationTypeDeclaration;

public class HamcrestAnnotationProcessorFactory implements
		AnnotationProcessorFactory {
	private static final Collection<String> SUPPORTED_ANNOTATIONS =
		unmodifiableCollection(Arrays.asList(org.hamcrest.Factory.class.getName()));

	private static final Collection<String> SUPPORTED_OPTIONS =
		unmodifiableCollection(Arrays.asList("-Averbose", "-AgeneratedClass"));

	public AnnotationProcessor getProcessorFor(
			final Set<AnnotationTypeDeclaration> atds,
			final AnnotationProcessorEnvironment env) {
		if (atds.isEmpty()) {
			return AnnotationProcessors.NO_OP;
		}
		return new HamcrestAnnotationProcessor(env, atds.iterator().next());
	}

	public Collection<String> supportedAnnotationTypes() {
		return SUPPORTED_ANNOTATIONS;
	}

	public Collection<String> supportedOptions() {
		return SUPPORTED_OPTIONS;
	}
}
