/*  Copyright (c) 2000-2006 hamcrest.org
 */
package org.hamcrest.core;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.IsInstanceOf.instanceOf;
import static org.hamcrest.core.IsNot.not;

import org.hamcrest.AbstractMatcherTest;
import org.hamcrest.Matcher;

public class IsInstanceOfTest extends AbstractMatcherTest {

    @Override
	protected Matcher<?> createMatcher() {
        return instanceOf(Number.class);
    }

    public void testEvaluatesToTrueIfArgumentIsInstanceOfASpecificClass() {
        assertThat(1, instanceOf(Number.class));
        assertThat(1.0, instanceOf(Number.class));
        assertThat(null, not(instanceOf(Number.class)));
        assertThat("hello", not(instanceOf(Number.class)));
    }

    public void testHasAReadableDescription() {
        assertDescription("an instance of java.lang.Number", instanceOf(Number.class));
    }

    public void testMismatchDescriptionForIncompatibleType() throws Exception {
    	// TODO(ngd): This is probably a good example of custom value description
		assertMismatchDescription("Incompatible type org.hamcrest.AbstractMatcherTest$UnknownType", new UnknownType(), instanceOf(Number.class));
	}
}
