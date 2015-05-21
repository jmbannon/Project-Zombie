package com.gmail.mooman219.pz.json;

/**
 * @author Joseph Cumbo (mooman219)
 */
public final class JsonBuilder {

    private final StringBuilder builder;

    public JsonBuilder(int capacity) {
        this.builder = new StringBuilder(capacity);
    }

    public JsonBuilder() {
        this.builder = new StringBuilder(256);
    }

    public JsonBuilder appendStartObject() {
        builder.append("{");
        return this;
    }

    public JsonBuilder appendStartObjectField(String name) {
        builder.append("\"").append(name).append("\":{");
        return this;
    }

    public JsonBuilder appendEndObject() {
        if (builder.charAt(builder.length() - 1) == ',') {
            builder.setLength(builder.length() - 1);
        }
        builder.append("},");
        return this;
    }

    public JsonBuilder appendStartArray() {
        builder.append("[");
        return this;
    }

    public JsonBuilder appendStartArrayField(String name) {
        builder.append("\"").append(name).append("\":[");
        return this;
    }

    public JsonBuilder appendEndArray() {
        if (builder.charAt(builder.length() - 1) == ',') {
            builder.setLength(builder.length() - 1);
        }
        builder.append("],");
        return this;
    }

    public JsonBuilder appendField(String name) {
        builder.append("\"").append(name).append("\":");
        return this;
    }

    public JsonBuilder appendString(String string) {
        return appendString(string, true);
    }

    public JsonBuilder appendString(String string, boolean escape) {
        builder.append("\"").append(escape ? escape(string) : string).append("\",");
        return this;
    }

    public JsonBuilder appendStringField(String name, String string) {
        builder.append("\"").append(name).append("\":\"").append(string).append("\",");
        return this;
    }

    public JsonBuilder appendNumber(short num) {
        builder.append(num).append(",");
        return this;
    }

    public JsonBuilder appendNumber(int num) {
        builder.append(num).append(",");
        return this;
    }

    public JsonBuilder appendNumber(float num) {
        builder.append(num).append(",");
        return this;
    }

    public JsonBuilder appendNumber(long num) {
        builder.append(num).append(",");
        return this;
    }

    public JsonBuilder appendNumber(double num) {
        builder.append(num).append(",");
        return this;
    }

    public JsonBuilder appendNumberField(String name, short num) {
        builder.append("\"").append(name).append("\":").append(num).append(",");
        return this;
    }

    public JsonBuilder appendNumberField(String name, int num) {
        builder.append("\"").append(name).append("\":").append(num).append(",");
        return this;
    }

    public JsonBuilder appendNumberField(String name, float num) {
        builder.append("\"").append(name).append("\":").append(num).append(",");
        return this;
    }

    public JsonBuilder appendNumberField(String name, long num) {
        builder.append("\"").append(name).append("\":").append(num).append(",");
        return this;
    }

    public JsonBuilder appendNumberField(String name, double num) {
        builder.append("\"").append(name).append("\":").append(num).append(",");
        return this;
    }

    public JsonBuilder appendBoolean(Boolean state) {
        builder.append(state).append(",");
        return this;
    }

    public JsonBuilder appendBooleanField(String name, Boolean state) {
        builder.append("\"").append(name).append("\":").append(state).append(",");
        return this;
    }

    public JsonBuilder appendObject(Object obj) {
        if (obj == null) {
            return this.appendNull();
        } else if (obj instanceof Short) {
            return this.appendNumber((short) obj);
        } else if (obj instanceof Integer) {
            return this.appendNumber((int) obj);
        } else if (obj instanceof Float) {
            return this.appendNumber((float) obj);
        } else if (obj instanceof Long) {
            return this.appendNumber((long) obj);
        } else if (obj instanceof Double) {
            return this.appendNumber((double) obj);
        } else if (obj instanceof Boolean) {
            return this.appendBoolean((boolean) obj);
        } else if (obj instanceof String) {
            return this.appendString((String) obj);
        } else {
            return this.appendString(obj.toString());
        }
    }

    public JsonBuilder appendObjectField(String name, Object obj) {
        if (obj == null) {
            return this.appendNullField(name);
        } else if (obj instanceof Short) {
            return this.appendNumberField(name, (short) obj);
        } else if (obj instanceof Integer) {
            return this.appendNumberField(name, (int) obj);
        } else if (obj instanceof Float) {
            return this.appendNumberField(name, (float) obj);
        } else if (obj instanceof Long) {
            return this.appendNumberField(name, (long) obj);
        } else if (obj instanceof Double) {
            return this.appendNumberField(name, (double) obj);
        } else if (obj instanceof Boolean) {
            return this.appendBooleanField(name, (boolean) obj);
        } else if (obj instanceof String) {
            return this.appendStringField(name, (String) obj);
        } else {
            return this.appendStringField(name, obj.toString());
        }
    }

    public JsonBuilder appendNull() {
        builder.append("null,");
        return this;
    }

    public JsonBuilder appendNullField(String name) {
        builder.append("\"").append(name).append("\":null,");
        return this;
    }

    @Override
    public String toString() {
        if (builder.charAt(builder.length() - 1) == ',') {
            return builder.substring(0, builder.length() - 1);
        }
        return builder.toString();
    }

    /**
     * Generates a readable string representation of the JsonBuilder. The string
     * incorporate tabs and spaces to improve readability.
     *
     * @return a pretty string representation of the JsonBuilder.
     */
    public String toPrettyString() {
        StringBuilder p = new StringBuilder(builder.length() * 2);
        p.append(builder);
        if (p.charAt(p.length() - 1) == ',') {
            p.setLength(p.length() - 1);
        }
        int tab = 0;
        for (int i = 0; i < p.length(); i++) {
            if (p.charAt(i) == '{' || p.charAt(i) == '[') {
                i += _breakIndent(p, i + 1, ++tab);
            } else if (p.charAt(i) == '}' || p.charAt(i) == ']') {
                i += _breakIndent(p, i, --tab);
            } else if (p.charAt(i) == ',') {
                i += _breakIndent(p, i + 1, tab);
            } else if (p.charAt(i) == ':') {
                p.insert(++i, ' ');
            }
        }
        return p.toString();
    }

    /**
     * Helper method for toPrettyString().
     */
    private static int _breakIndent(StringBuilder p, int i, int tab) {
        for (int j = 0; j < tab; j++) {
            p.insert(i, "    ");
        }
        p.insert(i, '\n');
        return 1 + (4 * tab);
    }

    /**
     * Escapes a string before it's appended to prevent the string from
     * invalidating the object.
     *
     * @param string the string to escape
     * @return a new, escaped string that's safe to put into a json object.
     */
    public static String escape(String string) {
        if (string == null || string.length() == 0) {
            return "";
        }
        char c;
        int i;
        int len = string.length();
        StringBuilder b = new StringBuilder(len + 4);
        String t;
        for (i = 0; i < len; i += 1) {
            c = string.charAt(i);
            switch (c) {
                case '\\':
                case '"':
                    b.append('\\');
                    b.append(c);
                    break;
                case '/':
                    if (i > 0 && string.charAt(i - 1) == '<') {
                        b.append('\\');
                    }
                    b.append(c);
                    break;
                case '\b':
                    b.append("\\b");
                    break;
                case '\t':
                    b.append("\\t");
                    break;
                case '\n':
                    b.append("\\n");
                    break;
                case '\f':
                    b.append("\\f");
                    break;
                case '\r':
                    b.append("\\r");
                    break;
                default:
                    if (c < ' ') {
                        t = "000" + Integer.toHexString(c);
                        b.append("\\u").append(t.substring(t.length() - 4));
                    } else {
                        b.append(c);
                    }
            }
        }
        return b.toString();
    }
}
