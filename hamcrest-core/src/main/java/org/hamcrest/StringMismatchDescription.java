package org.hamcrest;

public class StringMismatchDescription implements MismatchDescription {
	private final Description mismatchDescription;

	public StringMismatchDescription() {
		this(new StringDescription());
	}

	public StringMismatchDescription(Description description) {
		this.mismatchDescription = description;
	}

	public MismatchDescription appendText(String text) {
		mismatchDescription.appendText(text);
		return this;
	}

	boolean describeMatch(String title, Object item, Matcher<?> matcher) {
		return describeMismatch(title, item, matcher, this.mismatchDescription);
	}

	public boolean appendMismatchDescription(Object item, Matcher<?> matcher) {
		return describeMismatch("", item, matcher, new IndentedDescription(mismatchDescription));
	}

	private boolean describeMismatch(String title, Object item, Matcher<?> matcher, Description description) {
		StringDescription nestedDescription = new StringDescription();
		boolean matches = matcher.matches(item, new StringMismatchDescription(nestedDescription));
		if (!matches) {
			description.appendText(title)
				.appendText("\nExpected: ")
        		.appendDescriptionOf(matcher)
        		.appendText("\n     got: ")
        		.appendValue(item)
        		.appendText("\n     but: ")
        		.appendText(nestedDescription.toString())
        		.appendText("\n");
		}
		return matches;
	}

	@Override
	public String toString() {
		return mismatchDescription.toString();
	}
}
