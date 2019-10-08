package com.mclans.mod.dlamp;

import com.mclans.mod.dlamp.command.DLampCommand;
import com.mclans.mod.dlamp.listener.DeviceListener;
import net.minecraft.init.Blocks;
import net.minecraftforge.client.ClientCommandHandler;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.network.FMLEventChannel;
import net.minecraftforge.fml.common.network.NetworkRegistry;
import org.apache.logging.log4j.Logger;
@Mod(modid = DLamp.MODID, name = DLamp.NAME, version = DLamp.VERSION)
public class DLamp {
    static final String MODID = "dlamp";
    static final String NAME = "Dimension Lamp Mod";
    static final String VERSION = "1.0";
    static FMLEventChannel networkEvent = NetworkRegistry.INSTANCE.newEventDrivenChannel("DLamp");
    private static Logger logger;

    @Mod.EventHandler
    public void preInit(FMLPreInitializationEvent event)
    {
        logger = event.getModLog();
    }

    @Mod.EventHandler
    public void init(FMLInitializationEvent event)
    {
        networkEvent.register(this);
        ClientCommandHandler.instance.registerCommand(new DLampCommand());
        MinecraftForge.EVENT_BUS.register(new DeviceListener());
        // some example code
        logger.info("1111111111111       111111111111   DIRT BLOCK >> {}", Blocks.DIRT.getRegistryName());

    }

}
