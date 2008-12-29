package org.hamcrest.core;

import org.hamcrest.Description;
import org.hamcrest.DiagnosingMatcher;

public abstract class StringMatcher extends DiagnosingMatcher<String> {
    @Override
    public boolean matches(Object item, Description mismatchDescription) {
        boolean result = false;
        if (item == null || !(item instanceof String)) {
            super.describeMismatch(item, mismatchDescription);
        } else {
            result = matchesSafely((String)item, mismatchDescription);
        }
        return result;
    }

    protected abstract boolean matchesSafely(String string, Description mismatchDescription);

    protected boolean isWhitespace(char ch) {
        return (ch >= 0x09 && ch <= 0x0D) || (ch >= 0x1C && ch <= 0x20);
    }
}
