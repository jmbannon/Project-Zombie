package com.gmail.mooman219.pz.json.types;

import com.gmail.mooman219.pz.json.JsonBuilder;
import com.gmail.mooman219.pz.json.JsonProxy;
import java.lang.ref.WeakReference;
import java.util.Objects;
import java.util.UUID;
import org.bukkit.Bukkit;
import org.bukkit.World;

public final class JsonWorld extends JsonProxy<World> {

    private final String w;
    private final UUID u;
    private transient WeakReference<World> weakWorld;

    public JsonWorld(String w, UUID u) {
        this.w = w;
        this.u = u;
    }

    public JsonWorld(World world) {
        this.w = world.getName();
        this.u = world.getUID();
        this.weakWorld = new WeakReference<>(world);
    }

    /**
     * Gets the world's name.
     *
     * @return the world's name
     */
    public String getName() {
        return w;
    }

    /**
     * Gets the world's UUID.
     *
     * @return the world's UUID
     */
    public UUID getUUID() {
        return u;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public World fetch() {
        if (weakWorld == null || weakWorld.get() == null) {
            World world = Bukkit.getWorld(this.u);
            if (world == null) {
                world = Bukkit.getWorld(this.w);
                if (world == null) {
                    throw new IllegalStateException("Cannot find world (" + this.w + ", " + this.u + ").");
                }
            }
            weakWorld = new WeakReference<>(world);
        }
        return weakWorld.get();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean match(World data) {
        if (data == null) {
            return false;
        }
        if (!data.getUID().equals(this.u)) {
            return false;
        }
        if (!data.getName().equals(this.w)) {
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
        b.appendStringField("w", w);
        b.appendStringField("u", u.toString());
        b.appendEndObject();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public int hashCode() {
        int hash = 7;
        hash = 89 * hash + Objects.hashCode(this.w);
        hash = 89 * hash + Objects.hashCode(this.u);
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
        final JsonWorld other = (JsonWorld) obj;
        if (!Objects.equals(this.w, other.w)) {
            return false;
        }
        if (!Objects.equals(this.u, other.u)) {
            return false;
        }
        return true;
    }
}
