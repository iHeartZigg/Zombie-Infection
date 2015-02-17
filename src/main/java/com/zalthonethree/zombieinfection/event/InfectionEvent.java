package com.zalthonethree.zombieinfection.event;

import morph.api.Api;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityList;
import net.minecraft.entity.IEntityLivingData;
import net.minecraft.entity.monster.EntityZombie;
import net.minecraft.entity.passive.EntityChicken;
import net.minecraft.entity.passive.EntityCow;
import net.minecraft.entity.passive.EntityPig;
import net.minecraft.entity.passive.EntitySheep;
import net.minecraft.entity.passive.EntityVillager;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.Potion;
import net.minecraft.util.ChatComponentTranslation;
import net.minecraft.util.EntityDamageSource;
import net.minecraftforge.event.entity.living.LivingDeathEvent;
import net.minecraftforge.event.entity.living.LivingHurtEvent;
import net.minecraftforge.event.entity.player.PlayerUseItemEvent;

import com.zalthonethree.zombieinfection.ZombieInfection;
import com.zalthonethree.zombieinfection.api.ZombieInfectionAPI;
import com.zalthonethree.zombieinfection.entity.*;
import com.zalthonethree.zombieinfection.handler.ConfigurationHandler;
import com.zalthonethree.zombieinfection.potion.PotionHelper;
import com.zalthonethree.zombieinfection.utility.Utilities;

import cpw.mods.fml.common.eventhandler.SubscribeEvent;

public class InfectionEvent /*extends EntityDragon*/ {
	@SubscribeEvent public void onAttack(LivingHurtEvent event) {
		if (event.source instanceof EntityDamageSource) {
			EntityDamageSource source = (EntityDamageSource) event.source;
			Entity attacker = source.getEntity();
			boolean infectiousMob = false;
			int infectionChance = 0;
			for (int entityId : ZombieInfectionAPI.getCustionInfectiousMobs()) {
				if (EntityList.getEntityID(attacker) == entityId) {
					infectiousMob = true;
					infectionChance = ZombieInfectionAPI.getCustomInfectionChances().get(entityId);
				}
			}
			if (infectiousMob) {
				Entity target = event.entity;
				if (target instanceof EntityPlayer) {
					EntityPlayer attacked = (EntityPlayer) target;
					if (Api.getMorphEntity(attacked.getCommandSenderName(), false) != null) {
						if (Api.getMorphEntity(attacked.getCommandSenderName(), false) instanceof EntityZombie) infectionChance = (int) infectionChance / 2;
					}
					if ((attacked.getRNG().nextInt(100) + 1) <= infectionChance) {
						if (!attacked.isPotionActive(ZombieInfection.potionInfection)) {
							attacked.addChatMessage(new ChatComponentTranslation("zombieinfection.chat.infected"));
							attacked.addPotionEffect(PotionHelper.createInfection(0));
						}
					}
				}
			} else if (attacker instanceof EntityPlayer) {
				Entity target = event.entity;
				if (target instanceof EntityPlayer) {
					EntityPlayer attacked = (EntityPlayer) target;
					EntityPlayer possiblespreader = (EntityPlayer) attacker;
					if (possiblespreader.isPotionActive(ZombieInfection.potionInfection)
					&& !attacked.isPotionActive(ZombieInfection.potionInfection)) {
						attacked.addChatMessage(new ChatComponentTranslation("zombieinfection.chat.playerinfected"));
						attacked.addPotionEffect(PotionHelper.createInfection(0));
					}
				} else if (target instanceof EntityVillager) {
					EntityVillager attacked = (EntityVillager) target;
					EntityPlayer possiblespreader = (EntityPlayer) attacker;
					if (possiblespreader.isPotionActive(ZombieInfection.potionInfection)) {
						if (attacked.getRNG().nextInt(100) + 1 <= ConfigurationHandler.getVillagerInfectionChance()) {
							if (!attacked.isPotionActive(Potion.wither)) {
								attacked.addPotionEffect(PotionHelper.createWither(0));
							}
						}
					}
				} else if (target instanceof EntityCow) {
					EntityCow attacked = (EntityCow) target;
					EntityPlayer possiblespreader = (EntityPlayer) attacker;
					if (possiblespreader.isPotionActive(ZombieInfection.potionInfection)) {
						if (attacked.getRNG().nextInt(100) + 1 <= ConfigurationHandler.getAnimalInfectionChance()) {
							if (!attacked.isPotionActive(Potion.wither)) {
								attacked.addPotionEffect(PotionHelper.createWither(0));
							}
						}
					}
				} else if (target instanceof EntityPig) {
					EntityPig attacked = (EntityPig) target;
					EntityPlayer possiblespreader = (EntityPlayer) attacker;
					if (possiblespreader.isPotionActive(ZombieInfection.potionInfection)) {
						if (attacked.getRNG().nextInt(100) + 1 <= ConfigurationHandler.getAnimalInfectionChance()) {
							if (!attacked.isPotionActive(Potion.wither)) {
								attacked.addPotionEffect(PotionHelper.createWither(0));
							}
						}
					}
				} else if (target instanceof EntityChicken) {
					EntityChicken attacked = (EntityChicken) target;
					EntityPlayer possiblespreader = (EntityPlayer) attacker;
					if (possiblespreader.isPotionActive(ZombieInfection.potionInfection)) {
						if (attacked.getRNG().nextInt(100) + 1 <= ConfigurationHandler.getAnimalInfectionChance()) {
							if (!attacked.isPotionActive(Potion.wither)) {
								attacked.addPotionEffect(PotionHelper.createWither(0));
							}
						}
					}
				} else if (target instanceof EntitySheep) {
					EntitySheep attacked = (EntitySheep) target;
					EntityPlayer possiblespreader = (EntityPlayer) attacker;
					if (possiblespreader.isPotionActive(ZombieInfection.potionInfection)) {
						if (attacked.getRNG().nextInt(100) + 1 <= ConfigurationHandler.getAnimalInfectionChance()) {
							if (!attacked.isPotionActive(Potion.wither)) {
								attacked.addPotionEffect(PotionHelper.createWither(0));
							}
						}
					}
				}
			}
		}
	}
	
	@SubscribeEvent public void onDeath(LivingDeathEvent event) {
		if (event.source instanceof EntityDamageSource) {
			EntityDamageSource source = (EntityDamageSource) event.source;
			Entity attacker = source.getEntity();
			if (attacker instanceof EntityPlayer) {
				Entity target = event.entity;
				
				if (target instanceof EntityVillager) {
					EntityVillager attacked = (EntityVillager) target;
					EntityPlayer possiblespreader = (EntityPlayer) attacker;
					if (possiblespreader.isPotionActive(ZombieInfection.potionInfection)) {
						if (attacked.isPotionActive(Potion.wither)) {
							if (attacked.getRNG().nextInt(100) + 1 <= ConfigurationHandler.getVillagerInfectionChance()) {
								EntityZombie entityzombie = new EntityZombie(attacked.worldObj);
								entityzombie.copyLocationAndAnglesFrom(attacked);
								attacked.worldObj.removeEntity(attacked);
								entityzombie.onSpawnWithEgg((IEntityLivingData) null);
								entityzombie.setVillager(true);
								
								if (attacked.isChild()) {
									entityzombie.setChild(true);
								}
								
								attacked.worldObj.spawnEntityInWorld(entityzombie);
								attacked.worldObj.playAuxSFXAtEntity((EntityPlayer) null, 1016, (int) attacked.posX, (int) attacked.posY, (int) attacked.posZ, 0);
							}
						}
					}
				} else if (target instanceof EntityCow) {
					EntityCow attacked = (EntityCow) target;
					EntityPlayer possiblespreader = (EntityPlayer) attacker;
					if (possiblespreader.isPotionActive(ZombieInfection.potionInfection)) {
						if (attacked.isPotionActive(Potion.wither) && !attacked.isChild()) {
							if (attacked.getRNG().nextInt(100) + 1 <= ConfigurationHandler.getAnimalInfectionChance()) {
								EntityZombieCow entityzombiecow = new EntityZombieCow(attacked.worldObj);
								entityzombiecow.copyLocationAndAnglesFrom(attacked);
								attacked.worldObj.removeEntity(attacked);
								entityzombiecow.onSpawnWithEgg((IEntityLivingData) null);
								
								attacked.worldObj.spawnEntityInWorld(entityzombiecow);
								attacked.worldObj.playAuxSFXAtEntity((EntityPlayer) null, 1016, (int) attacked.posX, (int) attacked.posY, (int) attacked.posZ, 0);
							}
						}
					}
				} else if (target instanceof EntityPig) {
					EntityPig attacked = (EntityPig) target;
					EntityPlayer possiblespreader = (EntityPlayer) attacker;
					if (possiblespreader.isPotionActive(ZombieInfection.potionInfection)) {
						if (attacked.isPotionActive(Potion.wither) && !attacked.isChild()) {
							if (attacked.getRNG().nextInt(100) + 1 <= ConfigurationHandler.getAnimalInfectionChance()) {
								EntityZombiePig entityzombiepig = new EntityZombiePig(attacked.worldObj);
								entityzombiepig.copyLocationAndAnglesFrom(attacked);
								attacked.worldObj.removeEntity(attacked);
								entityzombiepig.onSpawnWithEgg((IEntityLivingData) null);
								
								attacked.worldObj.spawnEntityInWorld(entityzombiepig);
								attacked.worldObj.playAuxSFXAtEntity((EntityPlayer) null, 1016, (int) attacked.posX, (int) attacked.posY, (int) attacked.posZ, 0);
							}
						}
					}
				} else if (target instanceof EntityChicken) {
					EntityChicken attacked = (EntityChicken) target;
					EntityPlayer possiblespreader = (EntityPlayer) attacker;
					if (possiblespreader.isPotionActive(ZombieInfection.potionInfection)) {
						if (attacked.isPotionActive(Potion.wither) && !attacked.isChild()) {
							if (attacked.getRNG().nextInt(100) + 1 <= ConfigurationHandler.getAnimalInfectionChance()) {
								EntityZombieChicken entityzombiechicken = new EntityZombieChicken(attacked.worldObj);
								entityzombiechicken.copyLocationAndAnglesFrom(attacked);
								attacked.worldObj.removeEntity(attacked);
								entityzombiechicken.onSpawnWithEgg((IEntityLivingData) null);
								
								attacked.worldObj.spawnEntityInWorld(entityzombiechicken);
								attacked.worldObj.playAuxSFXAtEntity((EntityPlayer) null, 1016, (int) attacked.posX, (int) attacked.posY, (int) attacked.posZ, 0);
							}
						}
					}
				} else if (target instanceof EntitySheep) {
					EntitySheep attacked = (EntitySheep) target;
					EntityPlayer possiblespreader = (EntityPlayer) attacker;
					if (possiblespreader.isPotionActive(ZombieInfection.potionInfection)) {
						if (attacked.isPotionActive(Potion.wither) && !attacked.isChild()) {
							if (attacked.getRNG().nextInt(100) + 1 <= ConfigurationHandler.getAnimalInfectionChance()) {
								EntityZombieSheep entityzombiesheep = new EntityZombieSheep(attacked.worldObj);
								entityzombiesheep.copyLocationAndAnglesFrom(attacked);
								attacked.worldObj.removeEntity(attacked);
								entityzombiesheep.onSpawnWithEgg((IEntityLivingData) null);
								
								attacked.worldObj.spawnEntityInWorld(entityzombiesheep);
								attacked.worldObj.playAuxSFXAtEntity((EntityPlayer) null, 1016, (int) attacked.posX, (int) attacked.posY, (int) attacked.posZ, 0);
							}
						}
					}
				}
			}
		}
	}
	
	@SubscribeEvent public void onEaten(PlayerUseItemEvent.Finish event) {
		if (Utilities.isServerSide()) {
			EntityPlayer player = event.entityPlayer;
			ItemStack stack = event.item;
			int infectionChance = 0;
			for (String itemName : ZombieInfectionAPI.getCustomFoodInfections().keySet()) {
				if (stack.getUnlocalizedName().equalsIgnoreCase(itemName)) {
					infectionChance = ZombieInfectionAPI.getCustomFoodInfections().get(itemName);
					if (player.getRNG().nextInt(100) + 1 <= infectionChance) {
						if (!player.isPotionActive(ZombieInfection.potionInfection)) {
							player.addChatMessage(new ChatComponentTranslation("zombieinfection.chat.rotteninfection"));
							player.addPotionEffect(PotionHelper.createInfection(0));
						}
					}
					break;
				}
			}
		}
	}
}