package org.waveapi.api.content.items.block;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBlock;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import org.waveapi.Main;
import org.waveapi.api.WaveMod;
import org.waveapi.api.content.items.WaveTab;
import org.waveapi.api.content.items.block.model.BlockModel;
import org.waveapi.api.misc.Side;
import org.waveapi.content.items.BlockHelper;
import org.waveapi.content.items.CustomBlockWrap;
import org.waveapi.content.resources.LangManager;
import org.waveapi.content.resources.ResourcePackManager;
import org.waveapi.utils.ClassHelper;

import java.lang.reflect.InvocationTargetException;
import java.util.LinkedList;
import java.util.List;

import static org.waveapi.Main.bake;

public class WaveBlock {
    private final String id;
    private final WaveMod mod;
    private Block block;

    private static LinkedList<WaveBlock> toRegister = new LinkedList<>();

    private Class<CustomBlockWrap> blockBase;
    private boolean hasItem = true;

    private Material mat;

    public WaveBlock(String id, WaveMod mod, BlockMaterial material) {
        this.id = id;
        this.mod = mod;
        blockBase = CustomBlockWrap.class;
        this.mat = material.mat;

        toRegister.add(this);
    }

    public WaveBlock(String id, WaveMod mod) {
        this(id, mod, BlockMaterial.STONE);
    }

    public WaveBlock(Block block) {
        this.block = block;
        ResourceLocation ResourceLocation = Block.REGISTRY.getNameForObject(block);
        toRegister.add(this);
        mod = null;  // todo: change to actual mod
        id = ResourceLocation.getResourcePath();
    }

    @SubscribeEvent
    public static void register(RegistryEvent.Register<Block> event, RegistryEvent.Register<Item> itemEvent) {
        for (WaveBlock block : toRegister) {
            Block bl;
            try {
                bl = (Block) ClassHelper.LoadOrGenerateCompoundClass(block.getClass().getName() + "$mcBlock",
                        new ClassHelper.Generator<Block>() {
                            @Override
                            public Class<Block> getBaseClass() {
                                return Block.class;
                            }

                            @Override
                            public Class<?> getBaseMethods() {
                                return block.blockBase;
                            }

                            @Override
                            public List<ClassHelper.InterfaceImpl> getInterfaces() {
                                return BlockHelper.searchUp(block.getClass());
                            }
                        }
                        , Main.bake).getConstructor(Material.class, WaveBlock.class).newInstance(block.mat, block);
            } catch (InstantiationException | IllegalAccessException | InvocationTargetException |
                     NoSuchMethodException e) {
                throw new RuntimeException(e);
            }

            block.block = bl.setRegistryName(block.mod.name, block.id);
            event.getRegistry().register(block.block);
            if (block.hasItem()) {
                itemEvent.getRegistry().register(new ItemBlock(block.block).setRegistryName(block.mod.name, block.id));
            }
        }
        toRegister = null;
    }

    public WaveBlock setTab(WaveTab tab) {
        block.setCreativeTab(tab.group);
        return this;
    }

    public WaveBlock addTranslation(String language, String name) {
        if (Side.isClient() && bake) {
            LangManager.addTranslation(mod.name, language, "block." + mod.name + "." + id, name);
        }
        return this;
    }

    public WaveBlock setModels(BlockModel model) {
        if (Side.isClient() && bake) {
            model.buildBlock(ResourcePackManager.getInstance().getPackDir(), this, true, true, "");
        }
        return this;
    }

    public boolean hasItem() {
        return hasItem;
    }

    public WaveBlock setHasItem(boolean hasItem) {
        this.hasItem = hasItem;
        return this;
    }

    public String getId() {
        return id;
    }

    public WaveMod getMod() {
        return mod;
    }

    public Block getBlock() {
        return block;
    }
}