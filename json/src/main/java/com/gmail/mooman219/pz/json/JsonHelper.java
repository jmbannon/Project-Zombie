package com.gmail.mooman219.pz.json;

import com.gmail.mooman219.pz.json.types.JsonChunkLocation;
import com.gmail.mooman219.pz.json.types.JsonLocation;
import com.gmail.mooman219.pz.json.types.JsonRichLocation;
import com.gmail.mooman219.pz.json.types.JsonWorld;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.UUID;
import org.boon.core.TypeType;
import org.boon.core.Value;
import org.boon.core.value.LazyValueMap;
import org.boon.core.value.ValueContainer;
import org.boon.json.implementation.JsonFastParser;
import org.bukkit.Chunk;
import org.bukkit.Color;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.configuration.serialization.ConfigurationSerializable;
import org.bukkit.configuration.serialization.ConfigurationSerialization;
import org.bukkit.inventory.ItemStack;

/**
 * @author Joseph Cumbo (mooman219)
 */
public final class JsonHelper {

    private static final Class<? extends ConfigurationSerializable> ITEMMETA_CLASS;
    private static final ThreadLocal<JsonFastParser> PARSER_LOCAL = new ThreadLocal<JsonFastParser>() {
        @Override
        protected JsonFastParser initialValue() {
            return new JsonFastParser();
        }
    };

    static {
        ITEMMETA_CLASS = ConfigurationSerialization.getClassByAlias("ItemMeta");
    }

    public static LazyValueMap deserialize(String json) {
        return ((LazyValueMap) PARSER_LOCAL.get().parse(json));
    }

    public static JsonChunkLocation deserializeChunkLocation(String json) {
        return deserializeChunkLocation(JsonHelper.deserialize(json));
    }

    public static JsonChunkLocation deserializeChunkLocation(LazyValueMap raw) {
        JsonWorld world = null;
        int x = 0;
        int z = 0;
        Map.Entry<String, Value>[] items = raw.items();
        for (int i = 0; i < raw.len(); i++) {
            Value v = items[i].getValue();
            switch (items[i].getKey()) {
                case "w":
                    world = JsonHelper.deserializeWorld((LazyValueMap) v.toValue());
                    break;
                case "x":
                    x = v.intValue();
                    break;
                case "z":
                    z = v.intValue();
                    break;
            }
        }
        return new JsonChunkLocation(world, x, z);
    }

    public static Color deserializeColor(String json) {
        return deserializeColor(JsonHelper.deserialize(json));
    }

    public static Color deserializeColor(LazyValueMap raw) {
        int red = 0;
        int green = 0;
        int blue = 0;
        Map.Entry<String, Value>[] items = raw.items();
        for (int i = 0; i < raw.len(); i++) {
            Value v = items[i].getValue();
            switch (items[i].getKey()) {
                case "t":
                    red = v.intValue();
                    break;
                case "g":
                    green = v.intValue();
                    break;
                case "b":
                    blue = v.intValue();
                    break;
            }
        }
        return Color.fromRGB(red, green, blue);
    }

    public static ItemStack deserializeItemStack(String json) {
        return deserializeItemStack(JsonHelper.deserialize(json));
    }

    public static ItemStack deserializeItemStack(LazyValueMap raw) {
        Entry<String, Value>[] items = raw.items();
        for (int i = 0; i < raw.len(); i++) {
            if (items[i].getKey().equals("meta")) {
                items[i] = new SimpleEntry(
                        items[i].getKey(),
                        new ValueContainer(
                                ConfigurationSerialization.deserializeObject((LazyValueMap) items[i].getValue().toValue(), ITEMMETA_CLASS),
                                TypeType.OBJECT,
                                false
                        )
                );
                break;
            }
        }
        return ItemStack.deserialize(raw);
    }

    public static JsonLocation deserializeLocation(String json) {
        return deserializeLocation(JsonHelper.deserialize(json));
    }

    public static JsonLocation deserializeLocation(LazyValueMap raw) {
        JsonWorld world = null;
        int x = 0;
        int y = 0;
        int z = 0;
        Map.Entry<String, Value>[] items = raw.items();
        for (int i = 0; i < raw.len(); i++) {
            Value v = items[i].getValue();
            switch (items[i].getKey()) {
                case "w":
                    world = JsonHelper.deserializeWorld((LazyValueMap) v.toValue());
                    break;
                case "x":
                    x = v.intValue();
                    break;
                case "y":
                    y = v.intValue();
                    break;
                case "z":
                    z = v.intValue();
                    break;
            }
        }
        return new JsonLocation(world, x, y, z);
    }

    public static JsonRichLocation deserializeRichLocation(String json) {
        return deserializeRichLocation(JsonHelper.deserialize(json));
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
                    world = JsonHelper.deserializeWorld((LazyValueMap) v.toValue());
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

    public static JsonWorld deserializeWorld(String json) {
        return deserializeWorld(JsonHelper.deserialize(json));
    }

    public static JsonWorld deserializeWorld(LazyValueMap raw) {
        String world = null;
        UUID uuid = null;
        Map.Entry<String, Value>[] items = raw.items();
        for (int i = 0; i < raw.len(); i++) {
            Value v = items[i].getValue();
            switch (items[i].getKey()) {
                case "w":
                    world = v.stringValue();
                    break;
                case "u":
                    uuid = UUID.fromString(v.stringValue());
                    break;
            }
        }
        return new JsonWorld(world, uuid);
    }

    public static JsonBuilder serializeChunkLocation(Chunk chunk) {
        return serializeChunkLocation(chunk, new JsonBuilder());
    }

    public static JsonBuilder serializeChunkLocation(Chunk chunk, JsonBuilder b) {
        b.appendStartObject();
        b.appendField("w");
        JsonHelper.serializeWorld(chunk.getWorld(), b);
        b.appendNumberField("x", chunk.getX());
        b.appendNumberField("z", chunk.getZ());
        b.appendEndObject();
        return b;
    }

    public static JsonBuilder serializeColor(Color color) {
        return serializeColor(color, new JsonBuilder());
    }

    public static JsonBuilder serializeColor(Color color, JsonBuilder b) {
        b.appendStartObject();
        b.appendNumberField("r", color.getRed());
        b.appendNumberField("g", color.getGreen());
        b.appendNumberField("b", color.getBlue());
        b.appendEndObject();
        return b;
    }

    public static JsonBuilder serializeLocation(Location location) {
        return serializeLocation(location, new JsonBuilder());
    }

    public static JsonBuilder serializeLocation(Location location, JsonBuilder b) {
        b.appendStartObject();
        b.appendField("w");
        JsonHelper.serializeWorld(location.getWorld(), b);
        b.appendNumberField("x", location.getBlockX());
        b.appendNumberField("y", location.getBlockY());
        b.appendNumberField("z", location.getBlockZ());
        b.appendEndObject();
        return b;
    }

    public static JsonBuilder serializeRichLocation(Location location) {
        return serializeRichLocation(location, new JsonBuilder());
    }

    public static JsonBuilder serializeRichLocation(Location location, JsonBuilder b) {
        b.appendStartObject();
        b.appendField("w");
        JsonHelper.serializeWorld(location.getWorld(), b);
        b.appendNumberField("x", location.getX());
        b.appendNumberField("y", location.getY());
        b.appendNumberField("z", location.getZ());
        b.appendEndObject();
        return b;
    }

    public static JsonBuilder serializeWorld(World world) {
        return serializeWorld(world, new JsonBuilder());
    }

    public static JsonBuilder serializeWorld(World world, JsonBuilder b) {
        b.appendStartObject();
        b.appendStringField("w", world.getName());
        b.appendStringField("u", world.getUID().toString());
        b.appendEndObject();
        return b;
    }

    /**
     * Generates a JsonBuilder for the given ConfigurationSerializable. This
     * will sanitize any input in the ConfigurationSerializable before it's
     * appended to the JsonBuilder.
     *
     * @param cs the ConfigurationSerializable to serialize
     * @return a JsonBuilder with data representing that which was stored in the
     * given ConfigurationSerializable.
     */
    public static JsonBuilder serializeConfigurationSerializable(ConfigurationSerializable cs) {
        return serializeConfigurationSerializable(cs, new JsonBuilder());
    }

    public static JsonBuilder serializeConfigurationSerializable(ConfigurationSerializable cs, JsonBuilder b) {
        serializeSC(cs, b);
        return b;
    }

    /**
     * Helper method for serialize(ConfigurationSerializable).
     */
    private static void serializeSC(Object obj, JsonBuilder b) {
        if (obj instanceof Map) {
            Map<String, Object> map = (Map<String, Object>) obj;
            b.appendStartObject();
            for (Map.Entry<String, Object> entry : map.entrySet()) {
                String key = entry.getKey();
                Object value = entry.getValue();
                b.appendField(key);
                serializeSC(value, b);
            }
            b.appendEndObject();
        } else if (obj instanceof List) {
            b.appendStartArray();
            List list = (List) obj;
            for (Object item : list) {
                serializeSC(item, b);
            }
            b.appendEndArray();
        } else if (obj instanceof ConfigurationSerializable) {
            serializeSC(((ConfigurationSerializable) obj).serialize(), b);
        } else if (obj instanceof String) {
            b.appendString((String) obj);
        } else {
            b.appendObject(obj);
        }
    }

    /**
     * Breaks down the given object into its components and prints it out to the
     * console. Used for debugging.
     *
     * @param object the object to break down.
     */
    public static void debugMappable(Object object) {
        StringBuilder builder = new StringBuilder(256);
        print(object, 0, builder);
        System.out.println("\n" + builder.toString());
    }

    /**
     * Helper method for breakdown(Object).
     */
    private static void print(Object object, int tab, StringBuilder b) {
        if (object instanceof ConfigurationSerializable) {
            b.append("@" + object.getClass().getSimpleName());
            print(((ConfigurationSerializable) object).serialize(), tab, b);
        } else if (object instanceof Map) {
            tab("{\n", tab, b);
            for (Map.Entry<String, Object> entrySet : ((Map<String, Object>) object).entrySet()) {
                String key = entrySet.getKey();
                Object value = entrySet.getValue();
                tab(key + ": ", tab + 1, b);
                print(value, tab + 1, b);
            }
            tab("}@" + object.getClass().getSimpleName() + ",\n", tab, b);
        } else if (object instanceof List) {
            b.append("[\n");
            for (Object value : (List) object) {
                tab("", tab + 1, b);
                print(value, tab + 1, b);
            }
            tab("]@" + object.getClass().getSimpleName() + ",\n", tab, b);
        } else {
            b.append(object.toString() + "@" + object.getClass().getSimpleName() + ",\n");
        }
    }

    /**
     * Helper method for breakdown(Object).
     */
    private static void tab(String string, int tab, StringBuilder b) {
        for (int i = 0; i < tab; i++) {
            b.append("    ");
        }
        b.append(string);
    }

    /**
     * Used for adding entries into a lazy map.
     */
    private static final class SimpleEntry implements Entry<String, Value> {

        private final String key;
        private final Value value;

        public SimpleEntry(String key, Value value) {
            this.key = key;
            this.value = value;
        }

        @Override
        public String getKey() {
            return key;
        }

        @Override
        public Value getValue() {
            return value;
        }

        @Override
        public Value setValue(Value value) {
            throw new UnsupportedOperationException();
        }
    }
}
