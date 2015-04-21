package com.gmail.mooman219.pz.json;

import com.fasterxml.jackson.core.JsonFactory;
import com.fasterxml.jackson.core.JsonGenerator;
import java.io.IOException;
import java.io.StringWriter;

public abstract class JsonData {

    private final boolean isObject;

    /**
     * Constructor used to define is this is an object or array of objects.
     *
     * @param isObject true, this is an object and will be wrapped by "{}",
     * false this is an array and will be wrapped by "[]"
     */
    public JsonData(boolean isObject) {
        this.isObject = isObject;
    }

    public JsonData() {
        this.isObject = true;
    }

    /**
     * Writes the contents of the class to the generator.
     *
     * @param g the generator
     * @throws IOException this should never be thrown
     */
    protected abstract void write(JsonGenerator g) throws IOException;

    /**
     * Produces a non-fancy string representation of this class.
     *
     * @return the json string representing this class
     */
    public String serialize() {
        return serialize(false);
    }

    /**
     * Produces a string representation of this class.
     *
     * @param fancy if true the pretty printing will be use, else the standard
     * formatting will be used. Pretty printing makes the resulting string more
     * human readable adding indentation
     * @return the json string representing this class
     */
    public String serialize(boolean fancy) {
        JsonFactory factory = JsonHelper.getFactory();
        StringWriter writer = new StringWriter();
        try {
            JsonGenerator g = factory.createGenerator(writer);
            if (fancy) {
                g.useDefaultPrettyPrinter();
            }
            if (isObject) {
                g.writeStartObject();
            } else {
                g.writeStartArray();
            }
            write(g);
            g.close();
            return writer.toString();
        } catch (IOException ex) {
            System.err.println("Unable to serialize " + this.getClass().getSimpleName() + ".");
            ex.printStackTrace();
            return "{}";
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String toString() {
        return this.serialize();
    }
}
