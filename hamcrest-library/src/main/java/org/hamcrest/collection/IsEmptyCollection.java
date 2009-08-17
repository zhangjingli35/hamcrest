package org.hamcrest.collection;

import java.util.Collection;

import org.hamcrest.Description;
import org.hamcrest.Factory;
import org.hamcrest.Matcher;

/**
 * Tests if collection is empty.
 */
public class IsEmptyCollection<E> extends CollectionMatcher<Collection<E>> {

	@Override
	public boolean matchesSafely(Collection<E> item, Description mismatchDescription) {
		if (!item.isEmpty()) {
			mismatchDescription.appendValue(item);
            return false;
		}
		return true;
	}

  public void describeTo(Description description) {
		description.appendText("an empty collection");
	}

    /**
     * Matches an empty collection.
     */
	@Factory
	public static <E> Matcher<Collection<E>> empty() {
		return new IsEmptyCollection<E>();
	}
}
