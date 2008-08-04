package org.hamcrest;

public interface MismatchDescription {
	/**
	 * A Null object implementation that does nothing!
	 */
	public MismatchDescription NONE = new MismatchDescription() {
		public MismatchDescription appendText(String text) {
			return this;
		}

		public boolean appendMismatchDescription(Object item, Matcher<?> matcher) {
			return matcher.matches(item);
		}
	};

	MismatchDescription appendText(String text);

	/**
	 * Evaluates the given {@code matcher} against {@code item}.
	 * If a mismatch occurs appends a description of that mismatch.
	 * @returns Whether or not the matcher was satisifed by item.
	 */
	boolean appendMismatchDescription(Object item, Matcher<?> matcher);
}
