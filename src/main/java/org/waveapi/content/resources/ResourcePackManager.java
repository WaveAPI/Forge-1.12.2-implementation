package org.waveapi.content.resources;

import org.waveapi.Main;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;

public class ResourcePackManager {
    private static ResourcePackManager instance;

    public File getPackDir() {
        return packDir;
    }


    private final File packDir;
    public ResourcePackManager() {
        instance = this;
        packDir = new File(Main.mainFolder, "resource_pack");

        if (!packDir.exists()) {
            packDir.mkdirs();
        }
        File mcmeta = new File(packDir, "pack.mcmeta");
        if (!mcmeta.exists()) {
            try {
                Files.write(mcmeta.toPath(), ("{\"pack\": {\"pack_format\": 8, \"description\": \"Auto generated mcmeta\"}}").getBytes());
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
    }

    public static ResourcePackManager getInstance() {
        return instance;
    }
}
