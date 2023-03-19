package org.waveapi.api.misc;


import net.minecraft.client.Minecraft;
import net.minecraft.entity.player.EntityPlayer;

public class ClientOnly {

    public static EntityPlayer getPlayer() {
        return Minecraft.getMinecraft().player;
    }

}
