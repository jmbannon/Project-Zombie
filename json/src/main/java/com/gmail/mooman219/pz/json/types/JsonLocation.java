package com.gmail.mooman219.pz.json.types;

import com.fasterxml.jackson.core.JsonFactory;
import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonToken;
import com.gmail.mooman219.pz.json.JsonHelper;
import com.gmail.mooman219.pz.json.JsonProxy;
import java.io.IOException;
import java.lang.ref.WeakReference;
import java.util.Objects;
import java.util.UUID;
import org.bukkit.Location;
import org.bukkit.World;

public final class JsonLocation extends JsonProxy<Location> {

    private final JsonWorld world;
    private final int x;
    private final int y;
    private final int z;

    private transient WeakReference<Location> weakLocation;

    public JsonLocation(JsonWorld w, int x, int y, int z) {
        this.world = w;
        this.x = x;
        this.y = y;
        this.z = z;
    }

    public JsonLocation(Location location) {
        this.world = new JsonWorld(location.getWorld());
        this.x = location.getBlockX();
        this.y = location.getBlockY();
        this.z = location.getBlockZ();
    }

    /**
     * Gets the world the location is in.
     *
     * @return the world the location is in
     */
    public World getWorld() {
        return world.fetch();
    }

    /**
     * Gets the name of the world the location is in.
     *
     * @return the name of the world the location is in
     */
    public String getWorldName() {
        return world.getName();
    }

    /**
     * Gets the UUID of the world the location is in.
     *
     * @return the UUID of the world the location is in
     */
    public UUID getWorldUUID() {
        return world.getUUID();
    }

    /**
     * Gets the x coordinate of the location.
     *
     * @return the x coordinate of the location
     */
    public int getX() {
        return x;
    }

    /**
     * Gets the y coordinate of the location.
     *
     * @return the y coordinate of the location
     */
    public int getY() {
        return y;
    }

    /**
     * Gets the z coordinate of the location.
     *
     * @return the z coordinate of the location
     */
    public int getZ() {
        return z;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Location fetch() {
        if (weakLocation == null || weakLocation.get() == null) {
            weakLocation = new WeakReference<>(new Location(world.fetch(), x, y, z));
        }
        return weakLocation.get();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean match(Location data) {
        if (data == null) {
            return false;
        }
        if (!this.world.match(data.getWorld())) {
            return false;
        }
        if (this.x != data.getBlockX()) {
            return false;
        }
        if (this.y != data.getBlockY()) {
            return false;
        }
        if (this.z != data.getBlockZ()) {
            return false;
        }
        return true;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected void write(JsonGenerator g) throws IOException {
        g.writeObjectFieldStart("w");
        this.world.write(g);
        g.writeNumberField("x", this.x);
        g.writeNumberField("y", this.y);
        g.writeNumberField("z", this.z);
        g.writeEndObject();
    }

    /**
     * Attempts to deserialize the data on the given parser as a JsonLocation
     * object. If the parser is missing data, default values will be used.
     *
     * @param p the parser with the serialized JsonLocation on it
     * @return a new JsonLocation object representing the data on the given
     * parser
     * @throws IOException if an error occurred with the parser
     */
    public static JsonLocation deserialize(JsonParser p) throws IOException {
        JsonWorld world = null;
        int x = 0;
        int y = 0;
        int z = 0;
        while (p.nextToken() != JsonToken.END_OBJECT) {
            String field = p.getCurrentName();
            p.nextToken();
            switch (field) {
                case "w":
                    world = JsonWorld.deserialize(p);
                    break;
                case "x":
                    x = p.getIntValue();
                    break;
                case "y":
                    y = p.getIntValue();
                    break;
                case "z":
                    z = p.getIntValue();
                    break;
            }
        }
        return new JsonLocation(world, x, y, z);
    }

    /**
     * Attempts to deserialize the data as a JsonLocation. If there is an
     * exception in the process, null is returned. If the parser is missing
     * data, default values will be used.
     *
     * @param data the string containing a serialized JsonLocation on it
     * @return a new JsonLocation object representing the given data
     */
    public static JsonLocation deserialize(String data) {
        JsonFactory factory = JsonHelper.getFactory();
        JsonLocation o = null;
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
        int hash = 5;
        hash = 19 * hash + Objects.hashCode(this.world);
        hash = 19 * hash + this.x;
        hash = 19 * hash + this.y;
        hash = 19 * hash + this.z;
        return hash;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final JsonLocation other = (JsonLocation) obj;
        if (!Objects.equals(this.world, other.world)) {
            return false;
        }
        if (this.x != other.x) {
            return false;
        }
        if (this.y != other.y) {
            return false;
        }
        if (this.z != other.z) {
            return false;
        }
        return true;
    }
}
