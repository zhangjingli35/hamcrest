package org.hamcrest.collection;

import org.hamcrest.Description;
import org.hamcrest.DiagnosingMatcher;

import java.util.Map;

public abstract class MapMatcher<M extends Map<?, ?>> extends DiagnosingMatcher<M> {
    @Override
    @SuppressWarnings("unchecked")
    public boolean matches(Object item, Description mismatchDescription) {
        if (!(item instanceof Map)) {
            mismatchDescription.appendText("was ").appendValue(item);
            return false;
        }
        return matchesSafely((M) item, mismatchDescription);
    }

    protected abstract boolean matchesSafely(M map, Description mismatchDescription);
}
