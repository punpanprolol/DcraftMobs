package net.mcreator.deaceased.procedures;

import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.effect.MobEffectInstance;

import net.mcreator.deaceased.entity.TestfloatEntity;

public class TestfloatOnInitialEntitySpawnProcedure {
	public static void execute(Entity entity) {
		if (entity == null)
			return;
		if (entity instanceof TestfloatEntity _datEntSetI)
			_datEntSetI.getEntityData().set(TestfloatEntity.DATA_countdown_ck, 150);
		if (entity instanceof LivingEntity _entity && !_entity.level().isClientSide())
			_entity.addEffect(new MobEffectInstance(MobEffects.LEVITATION, 150, 0));
	}
}
