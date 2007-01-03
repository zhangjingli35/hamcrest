/*  Copyright (c) 2000-2007 hamcrest.org
 */
package org.hamcrest.generator.apt;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.hamcrest.generator.FactoryMethod;
import org.hamcrest.generator.HamcrestFactoryWriter;
import org.hamcrest.generator.QuickReferenceWriter;
import org.hamcrest.generator.SugarGenerator;

import com.sun.mirror.apt.AnnotationProcessor;
import com.sun.mirror.apt.AnnotationProcessorEnvironment;
import com.sun.mirror.declaration.AnnotationTypeDeclaration;
import com.sun.mirror.declaration.Declaration;
import com.sun.mirror.declaration.MethodDeclaration;
import com.sun.mirror.declaration.Modifier;
import com.sun.mirror.declaration.ParameterDeclaration;
import com.sun.mirror.declaration.TypeParameterDeclaration;
import com.sun.mirror.type.DeclaredType;
import com.sun.mirror.type.ReferenceType;
import com.sun.mirror.type.TypeMirror;
import com.sun.mirror.util.SimpleDeclarationVisitor;

public class HamcrestAnnotationProcessor extends SimpleDeclarationVisitor
		implements AnnotationProcessor {
	private final static Pattern TYPE_PATTERN = Pattern.compile("[^<]+(?:<(.*)>)?");

	private final AnnotationProcessorEnvironment env;
	private final AnnotationTypeDeclaration factoryAnnotation;
	private final SugarGenerator sugarGenerator;

	public HamcrestAnnotationProcessor(
			final AnnotationProcessorEnvironment env,
			final AnnotationTypeDeclaration annotation) {
		this.env = env;
		this.factoryAnnotation = annotation;
		this.sugarGenerator = new SugarGenerator();

		final AptOptions options = new AptOptions(env.getMessager(), env.getOptions());
		final String generatedClass = options.getOption("generatedClass", "org.hamcrest.Matchers");
		try {
			this.sugarGenerator.addWriter(new HamcrestFactoryWriter(
					getPackageName(generatedClass),
					getSimpleName(generatedClass),
					this.env.getFiler().createSourceFile(generatedClass)));
		} catch (final IOException ex) {
			printError("Unable to create source file for: " + generatedClass);
		}
		if (options.isEnabled("verbose")) {
			this.sugarGenerator.addWriter(new QuickReferenceWriter());
		}
	}

	public void process() {
		final Collection<Declaration> declarations = this.env
				.getDeclarationsAnnotatedWith(this.factoryAnnotation);
		if (declarations.size() > 0) {
			try {
				for (Declaration declaration : declarations) {
					declaration.accept(this);
				}
				try {
					this.sugarGenerator.generate();
				} finally {
					this.sugarGenerator.close();
				}
			} catch (final IOException ex) {
				printError("Unable to generate factory class", ex);
			}
		}
	}

	@Override
	public void visitMethodDeclaration(MethodDeclaration decl) {
		if (isFactoryMethod(decl)) {
			this.sugarGenerator.addFactoryMethod(buildFactoryMethod(decl));
		}
	}

	@Override
	public void visitDeclaration(Declaration declaration) {
		printWarning("Should only be possible to annotate methods");
	}

	private String getPackageName(final String className) {
		int dotIndex = className.lastIndexOf(".");
		return dotIndex == -1 ? "" : className.substring(0, dotIndex);
	}

	private String getSimpleName(final String className) {
		int dotIndex = className.lastIndexOf(".");
		return className.substring(dotIndex + 1);
	}

	protected boolean isFactoryMethod(MethodDeclaration method) {
		final Collection<Modifier> modifiers = method.getModifiers();
		final TypeMirror returnType = method.getReturnType();
		DeclaredType declaredType = this.env.getTypeUtils().getDeclaredType(
				this.env.getTypeDeclaration("org.hamcrest.Matcher"));

		return modifiers.contains(Modifier.PUBLIC)
				&& modifiers.contains(Modifier.STATIC)
				&& this.env.getTypeUtils().isAssignable(declaredType,
						returnType);
	}

	private FactoryMethod buildFactoryMethod(MethodDeclaration method) {
		FactoryMethod result = new FactoryMethod(method.getDeclaringType()
				.getQualifiedName(), method.getSimpleName());

		for (final TypeParameterDeclaration typeParameter : method
				.getFormalTypeParameters()) {
			Collection<String> bounds = new ArrayList<String>();
			for (ReferenceType bound : typeParameter.getBounds()) {
				final String boundStr = bound.toString();
				if (!("java.lang.Object".equals(boundStr))) {
					bounds.add(boundStr);
				}
			}
			StringBuilder type = new StringBuilder();
			type.append(typeParameter.getSimpleName());
			boolean first = true;
			for (String bound : bounds) {
				if (first) {
					type.append(" extends ");
					first = false;
				} else {
					type.append(" & ");
				}
				type.append(bound.toString());
			}
			result.addGenericTypeParameter(type.toString());
		}

		final TypeMirror returnType = method.getReturnType();
		result.setGenerifiedType(getTypeParameter(returnType.toString()));

		int paramNumber = 1;
		final Collection<ParameterDeclaration> parameters = method
				.getParameters();
		for (final ParameterDeclaration parameter : parameters) {
			String type = parameter.getType().toString();
			// Special case for var args methods.... String[] -> String...
			if (method.isVarArgs() && paramNumber == parameters.size()) {
				type = type.replaceFirst("\\[\\]$", "...");
			}
			result.addParameter(type, parameter.getSimpleName());
			paramNumber++;
		}

		for (final ReferenceType exception : method.getThrownTypes()) {
			result.addException(exception.toString());
		}

		return result;
	}

	private String getTypeParameter(final String type) {
		final Matcher matcher = TYPE_PATTERN.matcher(type);
		if (matcher.matches()) {
			return matcher.group(1);
		}
		return null;
	}
	
	private void printError(final String message) {
		this.env.getMessager().printError(message);
	}
	
	private void printError(final String message, final Throwable throwable) {
		printError(message + ": " + throwable.getMessage());
	}
	
	private void printWarning(final String message) {
		this.env.getMessager().printWarning(message);
	}
}
