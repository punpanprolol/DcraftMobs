
/*
 *    MCreator note: This file will be REGENERATED on each build.
 */
package net.mcreator.deaceased.init;

import net.minecraftforge.registries.RegistryObject;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.common.ForgeSpawnEggItem;

import net.minecraft.world.item.Item;

import net.mcreator.deaceased.DeaceasedMod;

public class DeaceasedModItems {
	public static final DeferredRegister<Item> REGISTRY = DeferredRegister.create(ForgeRegistries.ITEMS, DeaceasedMod.MODID);
	public static final RegistryObject<Item> THEFACE_SPAWN_EGG = REGISTRY.register("theface_spawn_egg", () -> new ForgeSpawnEggItem(DeaceasedModEntities.THEFACE, -1, -1, new Item.Properties()));
	public static final RegistryObject<Item> TESTFLOAT_SPAWN_EGG = REGISTRY.register("testfloat_spawn_egg", () -> new ForgeSpawnEggItem(DeaceasedModEntities.TESTFLOAT, -1, -1, new Item.Properties()));
	public static final RegistryObject<Item> BOULDER_SPAWN_EGG = REGISTRY.register("boulder_spawn_egg", () -> new ForgeSpawnEggItem(DeaceasedModEntities.BOULDER, -10066330, -13421773, new Item.Properties()));
	// Start of user code block custom items
	// End of user code block custom items
}
