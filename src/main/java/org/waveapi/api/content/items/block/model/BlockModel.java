package org.waveapi.api.content.items.block.model;

import org.waveapi.api.content.items.block.WaveBlock;
import org.waveapi.api.content.items.models.ItemModel;

import java.io.File;

public abstract class BlockModel extends ItemModel {
    public abstract void buildBlock(File pack, WaveBlock block, boolean buildItem, boolean buildBlockState, String additional);
}
