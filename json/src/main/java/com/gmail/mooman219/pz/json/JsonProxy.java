package com.gmail.mooman219.pz.json;

/*
 * Must have a empty protected/public constructor
 *
 * Should have public static JacksonData deserialize(String data) {}
 *
 * Should implement its own .hashCode() and .equals()
 */
public abstract class JsonProxy<T> extends JsonData {

    /**
     * Fetches the data that is being wrapped.
     *
     * @return The data that is being wrapped
     */
    public abstract T fetch();

    /**
     * Compares {@code data} against the contents of the wrapper.
     *
     * @param data an object of the wrapped type
     * @return true if the contents of the wrapper match the {@code data} object
     */
    public abstract boolean match(T data);
}
