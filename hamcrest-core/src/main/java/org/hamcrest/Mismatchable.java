package org.hamcrest;

public interface Mismatchable extends SelfDescribing {

  void describeMismatch(Object item, Description description);

}