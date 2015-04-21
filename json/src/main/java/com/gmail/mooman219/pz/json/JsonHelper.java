package com.gmail.mooman219.pz.json;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.PropertyAccessor;
import com.fasterxml.jackson.core.JsonFactory;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.module.afterburner.AfterburnerModule;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;

public class JsonHelper {

    private static final ObjectMapper mapper = new ObjectMapper();
    private static final ObjectMapper fancyMapper = new ObjectMapper();
    private static final JsonFactory factory = new JsonFactory();

    /**
     * Configure the mapper and fancyMapper objects to play nicely with
     * malformated data.
     */
    static {
        JsonHelper.mapper.registerModule(new AfterburnerModule());
        JsonHelper.mapper.setSerializationInclusion(JsonInclude.Include.NON_NULL);
        JsonHelper.mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        JsonHelper.mapper.configure(SerializationFeature.FAIL_ON_EMPTY_BEANS, false);
        JsonHelper.mapper.setVisibility(PropertyAccessor.FIELD, JsonAutoDetect.Visibility.ANY);

        JsonHelper.fancyMapper.registerModule(new AfterburnerModule());
        JsonHelper.fancyMapper.setSerializationInclusion(JsonInclude.Include.NON_NULL);
        JsonHelper.fancyMapper.configure(SerializationFeature.INDENT_OUTPUT, true);
        JsonHelper.fancyMapper.configure(SerializationFeature.FAIL_ON_EMPTY_BEANS, false);
        JsonHelper.fancyMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        JsonHelper.fancyMapper.setVisibility(PropertyAccessor.FIELD, JsonAutoDetect.Visibility.ANY);
    }

    /**
     * Gets the default JSON factory.
     *
     * @return the default JSON factory
     */
    public static JsonFactory getFactory() {
        return factory;
    }

    /**
     * Gets the default JSON mapper to be used with data-binding.
     *
     * @return the default JSON mapper
     */
    public static ObjectMapper getMapper() {
        return mapper;
    }

    /**
     * Gets the fancy JSON mapper to be used with data-binding. This will output
     * JSON that is more human readable adding indentation.
     *
     * @return the default JSON mapper
     */
    public static ObjectMapper getFancyMapper() {
        return fancyMapper;
    }

    /**
     * Attempts to read the given data according to the given template type.
     *
     * @param data the data to be parsed into the given type
     * @param type the pojo that will be used as the template
     * @return a new object of given type populated with the values read from
     * the given data. This returns null if there was an exception during the
     * parsing of the data.
     * @throws IllegalArgumentException if the data isn't of the accepted types:
     * File, InputStream, String.
     */
    public static <T> T fromJson(Object data, Class<T> type) {
        if (data != null) {
            try {
                if (data instanceof String) {
                    String data_string = (String) data;
                    if (data_string.length() > 0) {
                        return mapper.readValue(data_string, type);
                    }
                } else if (data instanceof File) {
                    return mapper.readValue((File) data, type);
                } else if (data instanceof InputStream) {
                    return mapper.readValue((InputStream) data, type);
                } else {
                    throw new IllegalArgumentException("Unsupported data type.");
                }
            } catch (IOException | IllegalArgumentException e) {
                e.printStackTrace();
            }
        }
        return null;
    }
}
