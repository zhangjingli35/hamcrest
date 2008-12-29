package org.hamcrest.collection;

import org.hamcrest.Description;
import org.hamcrest.DiagnosingMatcher;

public abstract class ArrayMatcher<T> extends DiagnosingMatcher<T[]> {
    @Override
    @SuppressWarnings("unchecked")
    public boolean matches(Object item, Description mismatchDescription) {
        if (!(item instanceof Object[])) {
            mismatchDescription.appendText("was ").appendValue(item);
            return false;
        }
        return matchesSafely((T[])item, mismatchDescription);
    }
    
    protected abstract boolean matchesSafely(T[] array, Description mismatchDescription);
}
