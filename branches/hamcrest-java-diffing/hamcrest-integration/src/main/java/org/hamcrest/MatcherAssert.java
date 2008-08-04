/*  Copyright (c) 2000-2006 hamcrest.org
 */
package org.hamcrest;


public class MatcherAssert {
    public static <T> void assertThat(T actual, Matcher<? super T> matcher) {
        assertThat("", actual, matcher);
    }

    public static <T> void assertThat(String reason, T actual, Matcher<? super T> matcher) {
    	StringMismatchDescription mismatchDescription = new StringMismatchDescription();
    	if (!mismatchDescription.describeMatch(reason, actual, matcher)) {
    		throw new AssertionError(mismatchDescription.toString());
    	}
    }

    public static void assertThat(String reason, boolean assertion) {
        if (!assertion) {
            throw new AssertionError(reason);
        }
    }
}
