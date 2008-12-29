package org.hamcrest.number;

import org.hamcrest.Description;
import org.hamcrest.DiagnosingMatcher;

public abstract class ComparableMatcher<T extends Comparable<T>> extends DiagnosingMatcher<T> {
    @Override
    @SuppressWarnings("unchecked")
    public boolean matches(Object item, Description mismatchDescription) {
        if (!(item instanceof Comparable)) {
            mismatchDescription.appendText("was ").appendValue(item);
            return false;
        }
        return matchesSafely((T)item, mismatchDescription);
    }

    protected abstract boolean matchesSafely(T comparable, Description mismatchDescription);
}
