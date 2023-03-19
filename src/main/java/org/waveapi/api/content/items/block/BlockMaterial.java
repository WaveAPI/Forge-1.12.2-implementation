package org.waveapi.api.content.items.block;

import net.minecraft.block.material.Material;

public enum BlockMaterial {
    GLASS(Material.GLASS),
    STONE(Material.ROCK),
    SOIL(Material.GROUND);

    public final Material mat;
    BlockMaterial(Material glass) {
        mat = glass;
    }
}
