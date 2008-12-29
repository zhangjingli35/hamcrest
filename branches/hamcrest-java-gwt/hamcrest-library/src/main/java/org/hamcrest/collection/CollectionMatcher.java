package org.hamcrest.collection;

import org.hamcrest.Description;
import org.hamcrest.DiagnosingMatcher;

import java.util.Collection;

public abstract class CollectionMatcher<C extends Collection<?>> extends DiagnosingMatcher<C> {
    @Override
    @SuppressWarnings("unchecked")
    public boolean matches(Object item, Description mismatchDescription) {
        if (!(item instanceof Collection)) {
            mismatchDescription.appendText("was ").appendValue(item);
            return false;
        }
        return matchesSafely((C)item, mismatchDescription);
    }
    
    protected abstract boolean matchesSafely(C collection, Description mismatchDescription);
}
