package org.hamcrest.core;

import org.hamcrest.Description;
import org.hamcrest.DiagnosingMatcher;

public abstract class StringMatcher extends DiagnosingMatcher<String> {
    @Override
    public boolean matches(Object item, Description mismatchDescription) {
        boolean result = false;
        if (item == null || !(item instanceof String)) {
            mismatchDescription.appendText("was ").appendValue(item);
        } else {
            result = matchesSafely((String)item, mismatchDescription);
        }
        return result;
    }

    protected abstract boolean matchesSafely(String string, Description mismatchDescription);

    protected boolean isWhitespace(char ch) {
        return Character.isWhitespace(ch);
    }
}
