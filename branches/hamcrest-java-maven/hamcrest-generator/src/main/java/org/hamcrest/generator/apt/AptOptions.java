/*  Copyright (c) 2000-2007 hamcrest.org
 */
package org.hamcrest.generator.apt;

import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.sun.mirror.apt.Messager;

public class AptOptions {
	private final static Pattern OPTION = Pattern.compile("-A([^=]+)(?:=(.*))?");

	private final Messager messager;
	private final Map<String, String> options;

	public AptOptions(final Map<String, String> aptOptions) {
		this(null, aptOptions);
	}

	public AptOptions(final Messager messager,
			final Map<String, String> aptOptions) {
		this.messager = messager;
		this.options = new HashMap<String, String>();
		for (final String key : aptOptions.keySet()) {
			final Matcher matcher = OPTION.matcher(key);
			if (matcher.matches()) {
				final String option = matcher.group(1);
				String value = aptOptions.get(key);
				if (value == null) {
					value = matcher.group(2);
				}
				this.options.put(option, value);
			}
		}
	}

	public boolean hasOption(final String key) {
		return this.options.containsKey(key);
	}

	public String getOption(final String key) {
		return this.options.get(key);
	}

	public String getOption(final String key, final String def) {
		return getOption(this.messager, key, def, def);
	}

	public boolean isEnabled(final String key) {
		return "true".equalsIgnoreCase(getOption(null, key, "true", null));
	}

	private String getOption(final Messager msgr, final String key,
			final String defNull, final String defNotSet) {
		String value = getOption(key);
		if (value == null) {
			String message;
			if (hasOption(key)) {
				message = "Option: " + key + " is null, ";
				value = defNull;
			} else {
				message = "Option: " + key + " not set, ";
				value = defNotSet;
			}
			message += "using default value: " + value;
			if (msgr != null) {
				msgr.printWarning(message);
			}
		}
		return value;
	}
}
