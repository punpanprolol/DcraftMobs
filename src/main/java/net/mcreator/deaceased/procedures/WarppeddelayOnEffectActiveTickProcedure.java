package net.mcreator.deaceased.procedures;

import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.effect.MobEffectInstance;

import net.mcreator.deaceased.init.DeaceasedModMobEffects;

public class WarppeddelayOnEffectActiveTickProcedure {
	public static void execute(Entity entity) {
		if (entity == null)
			return;
		if (entity instanceof LivingEntity _entity && !_entity.level().isClientSide())
			_entity.addEffect(new MobEffectInstance(DeaceasedModMobEffects.WRAPPED.get(), 10, 0, false, false));
	}
}
