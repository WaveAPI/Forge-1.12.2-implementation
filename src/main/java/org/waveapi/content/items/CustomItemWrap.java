package org.waveapi.content.items;

import net.minecraft.item.Item;
import org.waveapi.api.content.items.WaveItem;

public class CustomItemWrap extends Item {

    private final WaveItem item;
    public CustomItemWrap(WaveItem item) {
        super();
        this.item = item;
    }
}
