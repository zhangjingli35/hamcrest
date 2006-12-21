/*  Copyright (c) 2000-2006 hamcrest.org
 */
package org.hamcrest.generator.apt;

import java.util.HashMap;
import java.util.Map;

import junit.framework.Assert;
import junit.framework.TestCase;

import org.hamcrest.generator.apt.AptOptions;

import com.sun.mirror.apt.Messager;
import com.sun.mirror.util.SourcePosition;

public class AptOptionsTest extends TestCase {

    public void testHasOption() {
        final AptOptions options = getOptions("-Akey", null);
        assertTrue(options.hasOption("key"));
    }

    public void testHasOptionFails() {
        final AptOptions options = getOptions("key", null);
        assertFalse(options.hasOption("key"));
    }

    public void testGetOption() {
        final AptOptions options = getOptions("-Akey", null);
        assertNull(options.getOption("key"));
    }

    public void testGetNonexistingOption() {
        final AptOptions options = getOptions("key", null);
        assertNull(options.getOption("key"));
    }

    public void testGetOptionWithEquals() {
        final AptOptions options = getOptions("-Akey=value", null);
        assertEquals("value", options.getOption("key"));
    }

    public void testGetOptionWithValue() {
        final AptOptions options = getOptions("-Akey", "value");
        assertEquals("value", options.getOption("key"));
    }

    public void testGetOptionWithDefault() {
        final AptOptions options = getOptions("-Akey=value", null);
        assertEquals("value", options.getOption("key", "default"));
    }

    public void testGetOptionWithDefaultNoMessager() {
        final AptOptions options = getOptions("-Akey", null);
        assertEquals("def", options.getOption("key", "def"));
    }

    public void testGetOptionWithDefaultIsDefined() {
        final MockMessager mockMessager = new MockMessager();
        final AptOptions options = getOptions(mockMessager, "-Akey", null);
        assertEquals("def", options.getOption("key", "def"));
        assertEquals(1, mockMessager.getCallCount());
        assertEquals("Option: key is null, using default value: def", mockMessager.getMessage());
    }

    public void testGetOptionWithDefaultUseDefault() {
        final MockMessager mockMessager = new MockMessager();
        final AptOptions options = getOptions(mockMessager, "-Akey", null);
        assertEquals("def", options.getOption("nokey", "def"));
        assertEquals(1, mockMessager.getCallCount());
        assertEquals("Option: nokey not set, using default value: def", mockMessager.getMessage());
    }

    public void testIsEnabled() {
        final AptOptions options = getOptions("-Akey", null);
        assertTrue(options.isEnabled("key"));
    }

    public void testIsEnabledNoKey() {
        final AptOptions options = getOptions("-Akey", null);
        assertFalse(options.isEnabled("nokey"));
    }

    public void testIsEnabledWithTrue() {
        final AptOptions options = getOptions("-Akey=true", null);
        assertTrue(options.isEnabled("key"));
    }

    public void testIsEnabledWithValue() {
        final AptOptions options = getOptions("-Akey=value", null);
        assertFalse(options.isEnabled("key"));
    }

    private AptOptions getOptions(final String key, final String value) {
        final Map<String, String> options = new HashMap<String, String>(1);
        options.put(key, value);
        return new AptOptions(options);
    }

    private AptOptions getOptions(final Messager messager, final String key,
            final String value) {
        final Map<String, String> options = new HashMap<String, String>(1);
        options.put(key, value);
        return new AptOptions(messager, options);
    }


    static final class MockMessager implements Messager {

        private int callCount;
        private String message;

        public void printError(String msg) {
            Assert.fail("Unexpected call to Messager");
        }

        public void printError(SourcePosition pos, String msg) {
            Assert.fail("Unexpected call to Messager");
        }

        public void printNotice(String msg) {
            Assert.fail("Unexpected call to Messager");
        }

        public void printNotice(SourcePosition pos, String msg) {
            Assert.fail("Unexpected call to Messager");
        }

        public void printWarning(String msg) {
            this.callCount++;
            this.message = msg;
        }

        public void printWarning(SourcePosition pos, String msg) {
            Assert.fail("Unexpected call to Messager");
        }

        public int getCallCount() {
            return this.callCount;
        }

        public String getMessage() {
            return this.message;
        }
    }
}
