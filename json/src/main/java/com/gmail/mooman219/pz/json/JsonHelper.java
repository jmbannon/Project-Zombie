package com.gmail.mooman219.pz.json;

import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import org.boon.core.TypeType;
import org.boon.core.Value;
import org.boon.core.value.LazyValueMap;
import org.boon.core.value.ValueContainer;
import org.boon.json.implementation.JsonFastParser;
import org.bukkit.Color;
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

    public static Color deserializeColor(String json) {
        return deserializeColor(JsonHelper.deserializeJson(json));
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
        LazyValueMap raw = deserializeJson(json);
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

    public static LazyValueMap deserializeJson(String json) {
        return ((LazyValueMap) PARSER_LOCAL.get().parse(json));
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
        JsonBuilder b = new JsonBuilder();
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
