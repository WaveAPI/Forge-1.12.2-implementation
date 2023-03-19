package org.waveapi.content.items;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import org.waveapi.api.content.items.block.WaveBlock;

public class CustomBlockWrap extends Block {
    private final WaveBlock block;

    public CustomBlockWrap(Material material, WaveBlock block) {
        super(material);
        this.block = block;
    }
}