/*  Copyright (c) 2000-2006 hamcrest.org
 */
package org.hamcrest.core;

import org.hamcrest.BaseMatcher;
import org.hamcrest.Description;
import org.hamcrest.Factory;
import org.hamcrest.MismatchDescription;
import org.hamcrest.Matcher;


/**
 * Tests whether the value is an instance of a class.
 */
public class IsInstanceOf extends BaseMatcher<Object> {
    private final Class<?> theClass;

    /**
     * Creates a new instance of IsInstanceOf
     *
     * @param theClass The predicate evaluates to true for instances of this class
     *                 or one of its subclasses.
     */
    public IsInstanceOf(Class<?> theClass) {
        this.theClass = theClass;
    }

    @Override
	public boolean matches(Object item, MismatchDescription description) {
    	if (item == null) {
    		return false;
    	}
    	boolean isInstance = theClass.isInstance(item);
    	if (!isInstance) {
    		description.appendText("Incompatible type ")
    			.appendText(item.getClass().getName());
    	}
    	return isInstance;
    }

    public void describeTo(Description description) {
        description.appendText("an instance of ")
                .appendText(theClass.getName());
    }

    /**
     * Is the value an instance of a particular type?
     */
    @Factory
    public static Matcher<Object> instanceOf(Class<?> type) {
        return new IsInstanceOf(type);
    }
}
