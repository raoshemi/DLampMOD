package com.mclans.mod.dlamp;

import net.minecraft.init.Blocks;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import org.apache.logging.log4j.Logger;
@Mod(modid = DLamp.MODID, name = DLamp.NAME, version = DLamp.VERSION)
public class DLamp {
    public static final String MODID = "dlampmod";
    public static final String NAME = "Dimension Lamp Mod";
    public static final String VERSION = "1.0";

    private static Logger logger;

    @Mod.EventHandler
    public void preInit(FMLPreInitializationEvent event)
    {
        logger = event.getModLog();
    }

    @Mod.EventHandler
    public void init(FMLInitializationEvent event)
    {

        // some example code
        logger.info("DIRT BLOCK >> {}", Blocks.DIRT.getRegistryName());

    }
}
