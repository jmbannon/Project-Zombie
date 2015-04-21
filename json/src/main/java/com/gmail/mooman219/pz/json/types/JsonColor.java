package com.gmail.mooman219.pz.json.types;

import com.fasterxml.jackson.core.JsonFactory;
import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonToken;
import com.gmail.mooman219.pz.json.JsonHelper;
import com.gmail.mooman219.pz.json.JsonProxy;
import java.io.IOException;
import java.lang.ref.WeakReference;
import org.bukkit.Color;

public final class JsonColor extends JsonProxy<Color> {

    private final int red;
    private final int blue;
    private final int green;

    private transient WeakReference<Color> weakColor;

    public JsonColor(int r, int b, int g) {
        this.red = r;
        this.blue = b;
        this.green = g;
    }

    public JsonColor(Color color) {
        this.red = color.getRed();
        this.blue = color.getBlue();
        this.green = color.getGreen();
    }

    /**
     * Get the red value.
     *
     * @return the red value
     */
    public int getRed() {
        return red;
    }

    /**
     * Get the blue value.
     *
     * @return the blue value
     */
    public int getBlue() {
        return blue;
    }

    /**
     * Get the green value.
     *
     * @return the green value
     */
    public int getGreen() {
        return green;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Color fetch() {
        if (weakColor == null || weakColor.get() == null) {
            weakColor = new WeakReference<>(Color.fromRGB(red, green, blue));
        }
        return weakColor.get();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean match(Color color) {
        if (color == null) {
            return false;
        }
        if (color.getRed() != red) {
            return false;
        }
        if (color.getBlue() != blue) {
            return false;
        }
        if (color.getGreen() != green) {
            return false;
        }
        return true;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected void write(JsonGenerator g) throws IOException {
        g.writeNumberField("r", this.red);
        g.writeNumberField("b", this.blue);
        g.writeNumberField("g", this.green);
        g.writeEndObject();
    }

    /**
     * Attempts to deserialize the data on the given parser as a JsonColor
     * object. If the parser is missing data, default values will be used.
     *
     * @param p the parser with the serialized JsonColor on it
     * @return a new JsonColor object representing the data on the given parser
     * @throws IOException if an error occurred with the parser
     */
    public static JsonColor deserialize(JsonParser p) throws IOException {
        int red = 0;
        int blue = 0;
        int green = 0;
        while (p.nextToken() != JsonToken.END_OBJECT) {
            String field = p.getCurrentName();
            p.nextToken();
            switch (field) {
                case "t":
                    red = p.getIntValue();
                    break;
                case "b":
                    blue = p.getIntValue();
                    break;
                case "g":
                    green = p.getIntValue();
                    break;
            }
        }
        return new JsonColor(red, blue, green);
    }

    public static JsonColor deserialize(String data) {
        JsonFactory factory = JsonHelper.getFactory();
        JsonColor o = null;
        try {
            JsonParser p = factory.createParser(data);
            p.nextToken();
            o = deserialize(p);
            p.close();
        } catch (IOException ex) {
            ex.printStackTrace();
        }
        return o;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + blue;
        result = prime * result + green;
        result = prime * result + red;
        return result;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        JsonColor other = (JsonColor) obj;
        if (blue != other.blue) {
            return false;
        }
        if (green != other.green) {
            return false;
        }
        if (red != other.red) {
            return false;
        }
        return true;
    }
}
