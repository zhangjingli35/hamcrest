package org.hamcrest.core;

import org.hamcrest.BaseMatcher;
import org.hamcrest.Description;
import org.hamcrest.MismatchDescription;
import org.hamcrest.Matcher;

abstract class ShortcutCombination<T> extends BaseMatcher<T> {

    private final Iterable<Matcher<? super T>> matchers;

    public ShortcutCombination(Iterable<Matcher<? super T>> matchers) {
        this.matchers = matchers;
    }

    @Override
	public abstract boolean matches(Object o, MismatchDescription description);

    public abstract void describeTo(Description description);

    protected boolean matches(Object o, boolean shortcut, MismatchDescription description) {
        for (Matcher<? super T> matcher : matchers) {
        	boolean matches = description.appendMismatchDescription(o, matcher);
            if (matches == shortcut) {
                return shortcut;
            }
        }
        return !shortcut;
    }

    public void describeTo(Description description, String operator) {
        description.appendList("(", " " + operator + " ", ")", matchers);
    }
}
