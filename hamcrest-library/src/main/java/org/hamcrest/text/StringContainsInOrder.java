package org.hamcrest.text;

import org.hamcrest.Description;
import org.hamcrest.Factory;
import org.hamcrest.Matcher;
import org.hamcrest.core.StringMatcher;

public class StringContainsInOrder extends StringMatcher {
    private final Iterable<String> substrings;

    public StringContainsInOrder(Iterable<String> substrings) {
        this.substrings = substrings;
    }
    
    @Override
    public boolean matchesSafely(String s, Description mismatchDescription) {
        int fromIndex = 0;
        
        for (String substring : substrings) {
            fromIndex = s.indexOf(substring, fromIndex);
            if (fromIndex == -1) {
                mismatchDescription.appendText("string was \"").appendText(s).appendText("\"");
                return false;
            }
        }
        
        return true;
    }

    public void describeTo(Description description) {
        description.appendText("a string containing ")
                   .appendValueList("", ", ", "", substrings)
                   .appendText(" in order");
    }
    
    @Factory
    public static Matcher<String> stringContainsInOrder(Iterable<String> substrings) {
        return new StringContainsInOrder(substrings);
    }
}
