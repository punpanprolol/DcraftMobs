
/*
 *	MCreator note: This file will be REGENERATED on each build.
 */
package net.mcreator.deaceased.init;

import net.minecraftforge.registries.RegistryObject;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.DeferredRegister;

import net.minecraft.world.effect.MobEffect;

import net.mcreator.deaceased.potion.WrappedMobEffect;
import net.mcreator.deaceased.potion.WarppeddelayMobEffect;
import net.mcreator.deaceased.DeaceasedMod;

public class DeaceasedModMobEffects {
	public static final DeferredRegister<MobEffect> REGISTRY = DeferredRegister.create(ForgeRegistries.MOB_EFFECTS, DeaceasedMod.MODID);
	public static final RegistryObject<MobEffect> WRAPPED = REGISTRY.register("wrapped", () -> new WrappedMobEffect());
	public static final RegistryObject<MobEffect> WARPPEDDELAY = REGISTRY.register("warppeddelay", () -> new WarppeddelayMobEffect());
}
