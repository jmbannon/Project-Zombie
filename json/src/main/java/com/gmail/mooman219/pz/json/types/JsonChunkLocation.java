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
    protected void write(JsonGenerator g) throws IOException {
        g.writeObjectFieldStart("w");
        this.world.write(g);
        g.writeNumberField("x", this.x);
        g.writeNumberField("z", this.z);
        g.writeEndObject();
    }

    /**
     * Attempts to deserialize the data on the given parser as a
     * JsonChunkLocation object. If the parser is missing data, default values
     * will be used.
     *
     * @param p the parser with the serialized JsonChunkLocation on it
     * @return a new JsonChunkLocation object representing the data on the given
     * parser
     * @throws IOException if an error occurred with the parser
     */
    public static JsonChunkLocation deserialize(JsonParser p) throws IOException {
        JsonWorld world = null;
        int x = 0;
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
                case "z":
                    z = p.getIntValue();
                    break;
            }
        }
        return new JsonChunkLocation(world, x, z);
    }

    /**
     * Attempts to deserialize the data as a JsonChunkLocation. If there is an
     * exception in the process, null is returned. If the parser is missing
     * data, default values will be used.
     *
     * @param data the string containing a serialized JsonChunkLocation on it
     * @return a new JsonChunkLocation object representing the given data
     */
    public static JsonChunkLocation deserialize(String data) {
        JsonFactory factory = JsonHelper.getFactory();
        JsonChunkLocation o = null;
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
