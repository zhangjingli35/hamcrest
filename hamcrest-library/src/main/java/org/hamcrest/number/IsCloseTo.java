/*  Copyright (c) 2000-2006 hamcrest.org
 */
package org.hamcrest.number;

import org.hamcrest.Description;
import org.hamcrest.Matcher;
import org.hamcrest.Factory;


/**
 * Is the value a number equal to a value within some range of
 * acceptable error?
 */
public class IsCloseTo extends DoubleMatcher {
    private final double delta;
    private final double value;

    public IsCloseTo(double value, double error) {
        this.delta = error;
        this.value = value;
    }

    @Override
    public boolean matchesSafely(Double item, Description mismatchDescription) {
        if (actualDelta(item) > 0.0) {
            mismatchDescription.appendValue(item)
                    .appendText(" differed by ")
                    .appendValue(actualDelta(item));
            return false;
        }
        return true;
    }

    public void describeTo(Description description) {
        description.appendText("a numeric value within ")
                .appendValue(delta)
                .appendText(" of ")
                .appendValue(value);
    }

    private double actualDelta(Double item) {
      return (Math.abs((item - value)) - delta);
    }


    @Factory
    public static Matcher<Double> closeTo(double operand, double error) {
        return new IsCloseTo(operand, error);
    }

}
