package com.gmail.mooman219.pz.json;

import java.io.File;
import java.io.IOException;
import org.apache.commons.io.FileUtils;
import org.boon.core.value.LazyValueMap;

public abstract class JsonConfiguration {

    private final File file;

    public JsonConfiguration(String directory, String name) {
        this.file = new File("plugins/" + directory + "/" + name);
    }

    protected abstract void serialize(JsonBuilder b);

    protected abstract void deserialize(LazyValueMap map);

    public final void init() {
        System.out.println("[Config] Init " + file.getName());
        try {
            if (file.exists()) {
                deserialize(JsonHelper.deserializeJson(FileUtils.readFileToString(file)));
            }
            JsonBuilder b = new JsonBuilder();
            serialize(b);
            FileUtils.write(file, b.toPrettyString());
        } catch (IOException e) {
            System.out.println("[Config] Unable to init " + file.getName());
            e.printStackTrace();
        }
    }

    public final void load() {
        System.out.println("[Config] Load " + file.getName());
        try {
            if (file.exists()) {
                deserialize(JsonHelper.deserializeJson(FileUtils.readFileToString(file)));
            }
        } catch (IOException e) {
            System.out.println("[Config] Unable to load " + file.getName());
            e.printStackTrace();
        }
    }

    public final void save() {
        System.out.println("[Config] Save " + file.getName());
        try {
            JsonBuilder b = new JsonBuilder();
            serialize(b);
            FileUtils.write(file, b.toPrettyString());
        } catch (IOException ex) {
            System.out.println("[Config] Unable to save " + file.getName());
            ex.printStackTrace();
        }
    }
}
