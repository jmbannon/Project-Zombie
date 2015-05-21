package com.gmail.mooman219.pz.json.types;

import com.gmail.mooman219.pz.json.JsonBuilder;
import com.gmail.mooman219.pz.json.JsonProxy;
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
    public void write(JsonBuilder b) {
        b.appendStartObject();
        b.appendField("w");
        this.world.write(b);
        b.appendNumberField("x", this.x);
        b.appendNumberField("y", this.y);
        b.appendNumberField("z", this.z);
        b.appendEndObject();
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
