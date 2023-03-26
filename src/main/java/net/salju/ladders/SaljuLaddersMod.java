package net.salju.ladders;

import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.LogManager;

import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.common.MinecraftForge;

@Mod("salju_ladders")
public class SaljuLaddersMod {
	public static final Logger LOGGER = LogManager.getLogger(SaljuLaddersMod.class);
	public static final String MODID = "salju_ladders";

	public SaljuLaddersMod() {
		MinecraftForge.EVENT_BUS.register(this);
		IEventBus bus = FMLJavaModLoadingContext.get().getModEventBus();
	}
}