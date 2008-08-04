package org.hamcrest;

import java.lang.reflect.Method;

/**
 * Convenient base class for Matchers that require a non-null value of a specific type.
 * This simply implements the null check, checks the type and then casts.
 *
 * @author Joe Walnes
 */
public abstract class TypeSafeMatcher<T> extends BaseMatcher<T> {
    private final Class<?> expectedType;

    /**
     * Subclasses should implement this. The item will already have been checked for
     * the specific type and will never be null.
     */
    public boolean matchesSafely(T item) {
    	return matchesSafely(item, MismatchDescription.NONE);
    }

    /**
     * Subclasses should implement this. The item will already have been checked for
     * the specific type and will never be null.
     */
    public boolean matchesSafely(T item, MismatchDescription description) {
    	return matchesSafely(item);
    }

    protected TypeSafeMatcher() {
        expectedType = findExpectedType(getClass());
    }

    private static Class<?> findExpectedType(Class<?> fromClass) {
        for (Class<?> c = fromClass; c != Object.class; c = c.getSuperclass()) {
            for (Method method : c.getDeclaredMethods()) {
                if (isMatchesSafelyMethod(method)) {
                    return method.getParameterTypes()[0];
                }
            }
        }

        throw new Error("Cannot determine correct type for matchesSafely() method.");
    }

    private static boolean isMatchesSafelyMethod(Method method) {
    	int numberOfParams = method.getParameterTypes().length;
        return method.getName().equals("matchesSafely")
            && (numberOfParams == 1 || numberOfParams == 2)
            && !method.isSynthetic();
    }

    protected TypeSafeMatcher(Class<T> expectedType) {
        this.expectedType = expectedType;
    }

    /**
     * Method made final to prevent accidental override.
     * If you need to override this, there's no point on extending TypeSafeMatcher.
     * Instead, extend the {@link BaseMatcher}.
     */
    @Override
    @SuppressWarnings({"unchecked"})
    public final boolean matches(Object item, MismatchDescription description) {
        return item != null
                && expectedType.isInstance(item)
                && matchesSafely((T) item, description);
    }
}
