package org.hamcrest;

/**
 * A decorator that indents a description.
 */
public class IndentedDescription extends BaseDescription {
    private final String indent;
    private final Description raw;
    private boolean needsIndenting = true;

    public IndentedDescription(Description raw) {
        this("  ", raw);
    }

    public IndentedDescription(String indent, Description raw) {
        this.indent = indent;
        this.raw = raw;
    }

    @Override
    protected void append(char c) {
        if (needsIndenting) {
            raw.appendText(indent);
            needsIndenting = false;
        }
        raw.appendText(Character.toString(c));
        if (c == '\n') {
            needsIndenting = true;
        }
    }

}
