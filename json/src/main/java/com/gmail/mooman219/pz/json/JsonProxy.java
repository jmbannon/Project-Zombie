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
     * Fetches the data that is being proxied.
     *
     * @return the data that is being proxied
     */
    public abstract T fetch();

    /**
     * Compares the given data against the contents of the proxy.
     *
     * @param data an object of the proxied type
     * @return true if the contents of the proxy match the given data object,
     * false otherwise
     */
    public abstract boolean match(T data);
}
