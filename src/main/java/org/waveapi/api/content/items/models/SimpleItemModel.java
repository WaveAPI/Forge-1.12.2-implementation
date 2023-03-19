package org.waveapi.api.content.items.models;

import org.waveapi.api.content.items.WaveItem;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;

public class SimpleItemModel extends ItemModel {

    public String path;
    public SimpleItemModel (String texturePath) {
        path = texturePath;
    }

    @Override
    public void build(File pack, WaveItem item) {
        File model = new File(pack, "assets/" + item.getMod().name + "/models/item/" + item.getId() + ".json");
        File texture = new File(pack, "assets/" + item.getMod().name + "/textures/item/" + item.getId() + ".png");

        model.getParentFile().mkdirs();
        texture.getParentFile().mkdirs();

        try {
            Files.write(model.toPath(), ("{\n" +
                    "  \"parent\": \"minecraft:item/generated\",\n" +
                    "  \"textures\": {\n" +
                    "    \"layer0\": \"" + item.getMod().name + ":item/" + item.getId() + "\"\n" +
                    "  }\n" +
                    "}").getBytes());

            InputStream in = item.getMod().getClass().getClassLoader().getResourceAsStream(path);
            if (in == null) {
                throw new RuntimeException("File " + path + " not found.");
            }

            if (texture.exists()) {
                texture.delete();
            }

            Files.copy(in, texture.toPath());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }
}
