package org.hamcrest;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import junit.framework.TestCase;

public class MatcherAssertTest extends TestCase {

    public void testIncludesDescriptionOfTestedValueInErrorMessage() {
        String expected = "expected";
        String actual = "actual";

        String expectedMessage = "identifier\nExpected: \"expected\"\n     got: \"actual\"\n";

        try {
            assertThat("identifier", actual, equalTo(expected));
        }
        catch (AssertionError e) {
            assertTrue(e.getMessage().startsWith(expectedMessage));
            return;
        }

        fail("should have failed");
    }

    public void testDescriptionCanBeElided() {
        String expected = "expected";
        String actual = "actual";

        String expectedMessage = "\nExpected: \"expected\"\n     got: \"actual\"\n";

        try {
            assertThat(actual, equalTo(expected));
        }
        catch (AssertionError e) {
            assertTrue(e.getMessage().startsWith(expectedMessage));
            return;
        }

        fail("should have failed");
    }

    public void testCanTestBooleanDirectly() {
        assertThat("success reason message", true);

        try {
            assertThat("failing reason message", false);
        }
        catch (AssertionError e) {
            assertEquals("failing reason message", e.getMessage());
            return;
        }

        fail("should have failed");
    }

    public void testDescriptionContainsMismatch() {
        String expected = "expected";
        String actual = "actual";

        String expectedMessage = "\nExpected: \"expected\"\n"
        	+ "     got: \"actual\"\n"
        	+ "     but: Not equal using Object#equals(Object)\n";

        try {
            assertThat(actual, equalTo(expected));
        }
        catch (AssertionError e) {
            assertEquals(expectedMessage, e.getMessage());
            return;
        }

        fail("should have failed");
    }
}
