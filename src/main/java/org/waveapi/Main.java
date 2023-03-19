package org.waveapi;

import net.minecraft.init.Blocks;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.Mod.EventHandler;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import org.apache.logging.log4j.Logger;
import org.waveapi.api.WaveLoader;

import java.io.File;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

@Mod(modid = Main.MODID, name = Main.NAME, version = Main.VERSION)
public class Main
{
    public static final String MODID = "waveapi";
    public static final String NAME = "WaveApi";
    public static final String VERSION = "1.0";

    public static Logger LOGGER;

    public static final File mainFolder = new File("./waveAPI");

    public static boolean bake;

    @EventHandler
    public void preInit(FMLPreInitializationEvent event)
    {
        LOGGER = event.getModLog();
    }

    @EventHandler
    public void init(FMLInitializationEvent event)
    {
        LOGGER.info("Initializing");
        long initialTime = System.currentTimeMillis();
        Set<String> loaded = new HashSet<>();

        for (Map.Entry<String, WaveLoader.WrappedWaveMod> mod : WaveLoader.getMods().entrySet()) {

        }
    }
}
