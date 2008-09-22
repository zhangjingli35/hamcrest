package org.hamcrest.collection;

import java.util.Arrays;

import org.hamcrest.Description;
import org.hamcrest.Matcher;
import org.hamcrest.TypeSafeDiagnosingMatcher;

/**
 * Matcher for array whose elements satisfy a sequence of matchers.
 * The array size must equal the number of element matchers.
 */
public class IsArray<T> extends TypeSafeDiagnosingMatcher<T[]> {
    protected final Matcher<T>[] elementMatchers;
    
    public IsArray(Matcher<T>[] elementMatchers) {
        this.elementMatchers = elementMatchers.clone();
    }
    
    @Override
    public boolean matchesSafely(T[] array, Description description) {
        if (array.length != elementMatchers.length) {
          description.appendText("wrong number of values")
                     .appendValueList(descriptionStart(), descriptionSeparator(), descriptionEnd(), array);
          return false;
        }
        
        return matchesValuesSafely(array, elementMatchers, description);
    }

    protected boolean matchesValuesSafely(T[] values, Matcher<T>[] matchers, Description description) {
      for (int i = 0; i < values.length; i++) {
          final Matcher<T> matcher = matchers[i];
          if (!matcher.matches(values[i])) {
            description.appendText("[");
            matcher.describeMismatch(values[i], description);
            description.appendText("]");
            return false;
          }
          description.appendText("[Matched]");
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
    public static <T> IsArray<T> array(Matcher<T>... elementMatchers) {
        return new IsArray<T>(elementMatchers);
    }

}
