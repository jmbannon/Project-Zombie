package com.gmail.mooman219.pz.json;

import java.io.File;
import java.io.IOException;

public abstract class JsonConfiguration<T> {

    private transient final Class<T> type;
    private transient final File file;
    private T settings;

    public JsonConfiguration(T settings, String directory, String name) {
        this.settings = settings;
        this.type = (Class<T>) settings.getClass();
        new File("plugins/" + directory + "/").mkdirs();
        this.file = new File("plugins/" + directory + "/" + name);
    }

    /**
     * Reads the file defined in the constructor and assigns the read data to
     * the settings object.
     */
    public final void load() {
        System.out.println("[Config] Loading " + file.getName() + " (" + settings.getClass().getSimpleName() + ")");
        if (file.exists()) {
            setSettings(JsonHelper.fromJson(file, type));
        }
    }

    /**
     * Overwrites the file defined in the constructor with the data in the
     * settings object.
     */
    public final void save() {
        System.out.println("[Config] Saving " + file.getName() + " (" + settings.getClass().getSimpleName() + ")");
        try {
            if (!file.exists()) {
                file.createNewFile();
            }
            JsonHelper.getFancyMapper().writeValue(file, getSettings());
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    /**
     * Gets the settings object this class wraps.
     *
     * @return the settings object
     */
    public T getSettings() {
        return settings;
    }

    /**
     * Sets the settings object this class wraps.
     *
     * @param data the settings object
     */
    public void setSettings(T settings) {
        this.settings = settings;
    }
}
