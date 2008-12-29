package org.hamcrest.collection;

import org.hamcrest.Description;
import org.hamcrest.Matcher;

import java.util.Arrays;

/**
 * Matcher for array whose elements satisfy a sequence of matchers.
 * The array size must equal the number of element matchers.
 */
public class IsArray<T> extends ArrayMatcher<T> {
    private final Matcher<? super T>[] elementMatchers;

    @SuppressWarnings("unchecked")
    public IsArray(Matcher<? super T>[] elementMatchers) {
        this.elementMatchers = new Matcher[elementMatchers.length];
        System.arraycopy(elementMatchers, 0, this.elementMatchers, 0, elementMatchers.length);
    }

    @Override
    public boolean matchesSafely(T[] array, Description mismatchDescription) {
        if (array.length != elementMatchers.length) {
            mismatchDescription.appendText("array length was " + array.length);
            return false;
        }

        for (int i = 0; i < array.length; i++) {
            if (!elementMatchers[i].matches(array[i])) {
                mismatchDescription.appendText("element " + i + " was ").appendValue(array[i]);
                return false;
            }
        }

        return true;
    }

    public void describeTo(Description description) {
        description.appendList(descriptionStart(), descriptionSeparator(), descriptionEnd(), 
                               Arrays.asList(elementMatchers));
    }
    
    /**
     * Returns the string that starts the description.
     * 
     * Can be overridden in subclasses to customise how the matcher is
     * described.
     */
    protected String descriptionStart() {
        return "[";
    }

    /**
     * Returns the string that separates the elements in the description.
     * 
     * Can be overridden in subclasses to customise how the matcher is
     * described.
     */
    protected String descriptionSeparator() {
        return ", ";
    }

    /**
     * Returns the string that ends the description.
     * 
     * Can be overridden in subclasses to customise how the matcher is
     * described.
     */
    protected String descriptionEnd() {
        return "]";
    }

    /**
     * Evaluates to true only if each matcher[i] is satisfied by array[i].
     */
    public static <T> IsArray<T> array(Matcher<? super T>... elementMatchers) {
        return new IsArray<T>(elementMatchers);
    }
}
