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
    protected void write(JsonGenerator g) throws IOException {
        g.writeStringField("w", w);
        g.writeStringField("u", u.toString());
        g.writeEndObject();
    }

    /**
     * Attempts to deserialize the data on the given parser as a JsonWorld
     * object. If the parser is missing data, default values will be used.
     *
     * @param p the parser with the serialized JsonWorld on it
     * @return a new JsonWorld object representing the data on the given parser
     * @throws IOException if an error occurred with the parser
     */
    public static JsonWorld deserialize(JsonParser p) throws IOException {
        String world = null;
        UUID uuid = null;
        while (p.nextToken() != JsonToken.END_OBJECT) {
            String field = p.getCurrentName();
            p.nextToken();
            switch (field) {
                case "w":
                    world = p.getText();
                    break;
                case "u":
                    uuid = UUID.fromString(p.getText());
                    break;
            }
        }
        return new JsonWorld(world, uuid);
    }

    /**
     * Attempts to deserialize the data as a JsonWorld. If there is an exception
     * in the process, null is returned. If the parser is missing data, default
     * values will be used.
     *
     * @param data the string containing a serialized JsonWorld on it
     * @return a new JsonWorld object representing the given data
     */
    public static JsonWorld deserialize(String data) {
        JsonFactory factory = JsonHelper.getFactory();
        JsonWorld o = null;
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
