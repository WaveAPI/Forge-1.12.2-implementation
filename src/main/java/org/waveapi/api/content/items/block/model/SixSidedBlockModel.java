package org.waveapi.api.content.items.block.model;

import org.waveapi.api.content.items.WaveItem;
import org.waveapi.api.content.items.block.WaveBlock;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;

public class SixSidedBlockModel extends BlockModel {
    private String path;

    public SixSidedBlockModel (String texturePath) {
        path = texturePath;
    }

    @Override
    public void build(File pack, WaveItem mod) {

    }

    @Override
    public void buildBlock(File pack, WaveBlock block, boolean buildItem, boolean buildBlockState, String additional) {
        String name = block.getId() + additional;
        File model = new File(pack, "assets/" + block.getMod().name + "/models/block/" + name + ".json");
        File item = new File(pack, "assets/" + block.getMod().name + "/models/item/" + name + ".json");
        File texture = new File(pack, "assets/" + block.getMod().name + "/textures/block/" + name + ".png");
        File state = new File(pack, "assets/" + block.getMod().name + "/blockstates/" + block.getId() + ".json");


        model.getParentFile().mkdirs();
        item.getParentFile().mkdirs();
        texture.getParentFile().mkdirs();
        state.getParentFile().mkdirs();

        try {

            Files.write(model.toPath(), ("{\n" +
                    "  \"parent\": \"minecraft:block/cube_all\",\n" +
                    "  \"textures\": {\n" +
                    "    \"all\": \"" + block.getMod().name + ":block/" + name + "\"\n" +
                    "  }\n" +
                    "}").getBytes());

            if (buildItem) {
                Files.write(item.toPath(), ("{\n" +
                        "  \"parent\": \"" + block.getMod().name + ":block/" + name + "\"\n" +
                        "}").getBytes());
            }
            if (buildBlockState) {
                Files.write(state.toPath(), ("{\n" +
                        "  \"variants\": {\n" +
                        "    \"\": {\n" +
                        "      \"model\": \"" + block.getMod().name + ":block/" + name + "\"\n" +
                        "    }\n" +
                        "  }\n" +
                        "}").getBytes());
            }

            InputStream in = block.getMod().getClass().getClassLoader().getResourceAsStream(path);
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
