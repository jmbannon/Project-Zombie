package com.gmail.mooman219.pz.json;

public abstract class JsonData {

    public JsonData() {
    }

    /**
     * Writes the contents of the class to the builder.
     *
     * @param b the builder
     */
    public abstract void write(JsonBuilder b);

    /**
     * Produces a compact string representation of this class.
     *
     * @return The json string representing this class
     */
    public String serialize() {
        return serialize(false);
    }

    /**
     * Produces a string representation of this class.
     *
     * @param pretty true, a pretty string representation will be generated,
     * else a compact version of the json is generated.
     */
    public String serialize(boolean pretty) {
        JsonBuilder b = new JsonBuilder();
        write(b);
        return pretty ? b.toPrettyString() : b.toString();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String toString() {
        return this.serialize();
    }
}
