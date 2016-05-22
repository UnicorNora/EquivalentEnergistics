package com.mordenkainen.equivalentenergistics.proxy;

import com.mordenkainen.equivalentenergistics.exception.ServerUnmetDependencyException;
import com.mordenkainen.equivalentenergistics.integration.Integration;
import com.mordenkainen.equivalentenergistics.lib.Reference;
import com.mordenkainen.equivalentenergistics.registries.BlockEnum;
import com.mordenkainen.equivalentenergistics.registries.ItemEnum;
import com.mordenkainen.equivalentenergistics.registries.TextureEnum;
import com.mordenkainen.equivalentenergistics.tiles.TileEMCCondenser;
import com.mordenkainen.equivalentenergistics.tiles.TileEMCCrafter;

import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import cpw.mods.fml.common.registry.GameRegistry;

import net.minecraft.client.renderer.texture.TextureMap;
import net.minecraftforge.client.event.TextureStitchEvent;
import net.minecraftforge.common.MinecraftForge;

public class CommonProxy {
	
	public int crafterRenderer;

	public void preInit() {
		Integration.preInit();
		registerBlocks();
		MinecraftForge.EVENT_BUS.register(this);
	}
	
	public void init() {
		Integration.init();
		registerTileEntities();
    	registerItems();
    	initRenderers();
    	//final IGridCacheRegistry gcr = AEApi.instance().registries().gridCache();
    	//gcr.registerGridCache( EMCStorageCache.class, EMCStorageCache.class );   	
	}
	
	public void postInit() {
		Integration.postInit();
	}
	
	public boolean isClient() {
		return false;
	}

	public boolean isServer() {
		return true;
	}
	
	public void registerItems() {
		for (final ItemEnum current : ItemEnum.values()) {
			if(current.isEnabled()) {
				GameRegistry.registerItem(current.getItem(), current.getInternalName());
			}
		}
	}
	
	public void registerBlocks() {
		for (final BlockEnum current : BlockEnum.values()) {
			if(current.isEnabled()) {
				GameRegistry.registerBlock(current.getBlock(), current.getItemBlockClass(), current.getInternalName());
			}
		}
	}
	
	public void registerTileEntities() {
		GameRegistry.registerTileEntity(TileEMCCondenser.class, Reference.MOD_ID + "TileEMCCondenser");
		GameRegistry.registerTileEntity(TileEMCCrafter.class, Reference.MOD_ID + "TileEMCCrafter");
	}
	
	public void initRenderers() {}
	
	public void unmetDependency() {
		throw new ServerUnmetDependencyException("Equivalent Energistics requires either Equivalent Exchange 3 or ProjectE to be installed and enabled!");
	}
	
	@SubscribeEvent
	public void registerTextures(final TextureStitchEvent.Pre textureStitchEvent) {
		final TextureMap map = textureStitchEvent.map;
		for (final TextureEnum currentTexture : TextureEnum.values()) {
			currentTexture.registerTexture(map);
		}
	}
	
 }
