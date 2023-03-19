package org.waveapi.api.content.items.models;

import org.waveapi.api.content.items.WaveItem;

import java.io.File;

public abstract class ItemModel {
    public abstract void build(File pack, WaveItem mod);
}
