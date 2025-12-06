package net.mcreator.deaceased.procedures;

import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.entity.Entity;

import net.mcreator.deaceased.DeaceasedMod;

public class BoulderOnInitialEntitySpawnProcedure {
	public static void execute(LevelAccessor world, Entity entity) {
		if (entity == null)
			return;
		DeaceasedMod.queueServerWork(2000, () -> {
			if (!entity.level().isClientSide())
				entity.discard();
		});
	}
}
