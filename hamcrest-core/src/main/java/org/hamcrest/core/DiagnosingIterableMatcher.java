package org.hamcrest.core;

import org.hamcrest.Description;
import org.hamcrest.DiagnosingMatcher;

public abstract class DiagnosingIterableMatcher<I extends Iterable<?>> extends DiagnosingMatcher<I> {
    @Override
    @SuppressWarnings("unchecked")
    protected boolean matches(Object item, Description mismatchDescription) {
        boolean result = false;
        if (item == null || !(item instanceof Iterable)) {
            mismatchDescription.appendText("was ").appendValue(item);
        } else {
            result = matchesSafely((I)item, mismatchDescription);
        }
        return result;
    }

    protected abstract boolean matchesSafely(I iterable, Description mismatchDescription);
}
