package com.gmail.mooman219.pz.json.types;

import com.gmail.mooman219.pz.json.JsonBuilder;
import com.gmail.mooman219.pz.json.JsonProxy;
import java.lang.ref.WeakReference;
import java.util.Objects;
import java.util.UUID;
import org.bukkit.Chunk;
import org.bukkit.World;

public final class JsonChunkLocation extends JsonProxy<Chunk> {

    private final JsonWorld world;
    private final int x;
    private final int z;

    private transient WeakReference<Chunk> weakChunk;

    public JsonChunkLocation(JsonWorld w, int x, int z) {
        this.world = w;
        this.x = x;
        this.z = z;
    }

    public JsonChunkLocation(Chunk chunk) {
        this.world = new JsonWorld(chunk.getWorld());
        this.x = chunk.getX();
        this.z = chunk.getZ();
    }

    /**
     * Gets the world the chunk is in.
     *
     * @return the world the chunk is in
     */
    public World getWorld() {
        return world.fetch();
    }

    /**
     * Gets the name of the world the chunk is in.
     *
     * @return the name of the world the chunk is in
     */
    public String getWorldName() {
        return world.getName();
    }

    /**
     * Gets the UUID of the world the chunk is in.
     *
     * @return the UUID of the world the chunk is in
     */
    public UUID getWorldUUID() {
        return world.getUUID();
    }

    /**
     * Gets the x coordinate of the chunk.
     *
     * @return the x coordinate of the chunk
     */
    public int getX() {
        return x;
    }

    /**
     * Gets the z coordinate of the chunk.
     *
     * @return the z coordinate of the chunk
     */
    public int getZ() {
        return z;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Chunk fetch() {
        if (weakChunk == null || weakChunk.get() == null) {
            weakChunk = new WeakReference<>(world.fetch().getChunkAt(x, z));
        }
        return weakChunk.get();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean match(Chunk data) {
        if (data == null) {
            return false;
        }
        if (!this.world.match(data.getWorld())) {
            return false;
        }
        if (this.x != data.getX()) {
            return false;
        }
        if (this.z != data.getZ()) {

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
        b.appendNumberField("z", this.z);
        b.appendEndObject();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public int hashCode() {
        int hash = 7;
        hash = 53 * hash + Objects.hashCode(this.world);
        hash = 53 * hash + this.x;
        hash = 53 * hash + this.z;
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
        final JsonChunkLocation other = (JsonChunkLocation) obj;
        if (!Objects.equals(this.world, other.world)) {
            return false;
        }
        if (this.x != other.x) {
            return false;
        }
        if (this.z != other.z) {
            return false;
        }
        return true;
    }
}
