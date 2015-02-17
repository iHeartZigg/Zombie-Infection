package com.zalthonethree.zombieinfection.init;

import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityList;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.EnumCreatureType;
import net.minecraft.world.biome.BiomeGenBase;

import com.zalthonethree.zombieinfection.entity.EntityZombieChicken;
import com.zalthonethree.zombieinfection.entity.EntityZombieCow;
import com.zalthonethree.zombieinfection.entity.EntityZombiePig;
import com.zalthonethree.zombieinfection.entity.EntityZombieSheep;

import cpw.mods.fml.common.registry.EntityRegistry;

public class EntityInit /*extends EntityDragon*/ {
	private static void registerEntity(Class<? extends Entity> clazz, int bkEggColor, int fgEggColor) { registerEntity(clazz, clazz.getName().toLowerCase(), bkEggColor, fgEggColor); }
	@SuppressWarnings("unchecked") private static void registerEntity(Class<? extends Entity> clazz, String name, int bkEggColor, int fgEggColor) {
		int id = EntityRegistry.findGlobalUniqueEntityId();
		
		EntityRegistry.registerGlobalEntityID(clazz, name, id);
		EntityList.entityEggs.put(id, new EntityList.EntityEggInfo(id, bkEggColor, fgEggColor));
	}
	
	private static void addSpawn(Class<? extends EntityLiving> entityClass, int spawnProb, int min, int max, BiomeGenBase[] biomes) {
		EntityRegistry.addSpawn(entityClass, spawnProb, min, max, EnumCreatureType.creature, biomes);
	}
	
	
	public static void init() {
		registerEntities();
	}
	
	private static void registerEntities() {
		registerEntity(EntityZombieChicken.class, 0xa3a3a3, 0x566c58);
		registerEntity(EntityZombieCow.class, 0x3e2e09, 0x566c58);
		registerEntity(EntityZombiePig.class, 0xc87e7e, 0x566c58);
		registerEntity(EntityZombieSheep.class, 0xe8e8e8, 0x566c58);
		
		for (int i = 0; i < BiomeGenBase.getBiomeGenArray().length; i++)
		{	
			if (BiomeGenBase.getBiomeGenArray()[i] != null);
			{
				//EntityRegistry.addSpawn(EntityZombieCow.class, 1, 1, 1, EnumCreatureType.monster, BiomeGenBase.getBiomeGenArray() [i]);

			}
		}
	}
}