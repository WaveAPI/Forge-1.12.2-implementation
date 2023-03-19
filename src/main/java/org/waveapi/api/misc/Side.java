package org.waveapi.api.misc;

import net.minecraftforge.fml.common.FMLCommonHandler;

public class Side {
    private static final net.minecraftforge.fml.relauncher.Side side = FMLCommonHandler.instance().getSide();
    public static boolean isServer() {
        return side.isServer();
    }

    public static boolean isClient() {
        return side.isClient();
    }
}
