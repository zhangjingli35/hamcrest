package org.hamcrest.number;

import org.hamcrest.Description;
import org.hamcrest.DiagnosingMatcher;

public abstract class DoubleMatcher extends DiagnosingMatcher<Double> {
    @Override
    public boolean matches(Object item, Description mismatchDescription) {
        if (!(item instanceof Double)) {
            mismatchDescription.appendText("was ").appendValue(item);
            return false;
        }
        return matchesSafely((Double)item, mismatchDescription);
    }

    protected abstract boolean matchesSafely(Double d, Description mismatchDescription);
}
