package com.gmail.mooman219.pz.json.types;

import com.gmail.mooman219.pz.json.JsonBuilder;
import com.gmail.mooman219.pz.json.JsonHelper;
import com.gmail.mooman219.pz.json.JsonProxy;
import java.lang.ref.WeakReference;
import java.util.Map;
import java.util.Objects;
import java.util.UUID;
import org.boon.core.Value;
import org.boon.core.value.LazyValueMap;
import org.bukkit.Location;
import org.bukkit.World;

public final class JsonRichLocation extends JsonProxy<Location> {

    private final double EPSILON = 0.01;
    private final JsonWorld world;
    private final double x;
    private final double y;
    private final double z;
    private final float yaw;
    private final float pitch;

    private transient WeakReference<Location> weakLocation;

    public JsonRichLocation(JsonWorld world, double x, double y, double z, float yaw, float pitch) {
        this.world = world;
        this.x = x;
        this.y = y;
        this.z = z;
        this.yaw = yaw;
        this.pitch = pitch;
    }

    public JsonRichLocation(JsonRichLocation location) {
        this.world = location.world;
        this.x = location.x;
        this.y = location.y;
        this.z = location.z;
        this.yaw = location.yaw;
        this.pitch = location.pitch;
        this.weakLocation = location.weakLocation;
    }

    public JsonRichLocation(Location location) {
        this.world = new JsonWorld(location.getWorld());
        this.x = location.getX();
        this.y = location.getY();
        this.z = location.getZ();
        this.yaw = location.getYaw();
        this.pitch = location.getPitch();
        this.weakLocation = new WeakReference<>(location);
    }

    /*
     * Getters
     */
    public World getWorld() {
        return world.fetch();
    }

    public String getWorldName() {
        return world.getName();
    }

    public UUID getWorldUUID() {
        return world.getUUID();
    }

    public double getX() {
        return x;
    }

    public double getY() {
        return y;
    }

    public double getZ() {
        return z;
    }

    public float getYaw() {
        return yaw;
    }

    public float getPitch() {
        return pitch;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Location fetch() {
        if (weakLocation == null || weakLocation.get() == null) {
            weakLocation = new WeakReference<>(new Location(world.fetch(), x, y, z, yaw, pitch));
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
        if (Math.abs(data.getX() - this.x) > EPSILON) {
            return false;
        }
        if (Math.abs(data.getY() - this.y) > EPSILON) {
            return false;
        }
        if (Math.abs(data.getZ() - this.z) > EPSILON) {
            return false;
        }
        if (Math.abs(data.getYaw() - this.yaw) > EPSILON) {
            return false;
        }
        if (Math.abs(data.getPitch() - this.pitch) > EPSILON) {
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
        b.appendNumberField("ya", this.yaw);
        b.appendNumberField("pi", this.pitch);
        b.appendEndObject();
    }

    public static JsonRichLocation deserializeRichLocation(String json) {
        return deserializeRichLocation(JsonHelper.deserializeJson(json));
    }

    public static JsonRichLocation deserializeRichLocation(LazyValueMap raw) {
        JsonWorld world = null;
        double x = 0;
        double y = 0;
        double z = 0;
        float yaw = 0;
        float pitch = 0;
        Map.Entry<String, Value>[] items = raw.items();
        for (int i = 0; i < raw.len(); i++) {
            Value v = items[i].getValue();
            switch (items[i].getKey()) {
                case "w":
                    world = JsonWorld.deserializeWorld((LazyValueMap) v.toValue());
                    break;
                case "x":
                    x = v.doubleValue();
                    break;
                case "y":
                    y = v.doubleValue();
                    break;
                case "z":
                    z = v.doubleValue();
                    break;
                case "ya":
                    yaw = v.floatValue();
                    break;
                case "pi":
                    pitch = v.floatValue();
                    break;
            }
        }
        return new JsonRichLocation(world, x, y, z, yaw, pitch);
    }

    public static JsonBuilder serializeRichLocation(Location location) {
        return serializeRichLocation(location, new JsonBuilder());
    }

    public static JsonBuilder serializeRichLocation(Location location, JsonBuilder b) {
        b.appendStartObject();
        b.appendField("w");
        JsonWorld.serializeWorld(location.getWorld(), b);
        b.appendNumberField("x", location.getX());
        b.appendNumberField("y", location.getY());
        b.appendNumberField("z", location.getZ());
        b.appendEndObject();
        return b;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public int hashCode() {
        int hash = 7;
        hash = 97 * hash + Objects.hashCode(this.world);
        hash = 97 * hash + (int) (Double.doubleToLongBits(this.x) ^ (Double.doubleToLongBits(this.x) >>> 32));
        hash = 97 * hash + (int) (Double.doubleToLongBits(this.y) ^ (Double.doubleToLongBits(this.y) >>> 32));
        hash = 97 * hash + (int) (Double.doubleToLongBits(this.z) ^ (Double.doubleToLongBits(this.z) >>> 32));
        hash = 97 * hash + Float.floatToIntBits(this.yaw);
        hash = 97 * hash + Float.floatToIntBits(this.pitch);
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
        final JsonRichLocation other = (JsonRichLocation) obj;
        if (!Objects.equals(this.world, other.world)) {
            return false;
        }
        if (Double.doubleToLongBits(this.x) != Double.doubleToLongBits(other.x)) {
            return false;
        }
        if (Double.doubleToLongBits(this.y) != Double.doubleToLongBits(other.y)) {
            return false;
        }
        if (Double.doubleToLongBits(this.z) != Double.doubleToLongBits(other.z)) {
            return false;
        }
        if (Float.floatToIntBits(this.yaw) != Float.floatToIntBits(other.yaw)) {
            return false;
        }
        if (Float.floatToIntBits(this.pitch) != Float.floatToIntBits(other.pitch)) {
            return false;
        }
        return true;
    }
}
