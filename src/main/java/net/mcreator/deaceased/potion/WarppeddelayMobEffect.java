
package net.mcreator.deaceased.potion;

import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.effect.MobEffectCategory;
import net.minecraft.world.effect.MobEffect;

import net.mcreator.deaceased.procedures.WarppeddelayOnEffectActiveTickProcedure;

public class WarppeddelayMobEffect extends MobEffect {
	public WarppeddelayMobEffect() {
		super(MobEffectCategory.HARMFUL, -10092544);
	}

	@Override
	public void applyEffectTick(LivingEntity entity, int amplifier) {
		WarppeddelayOnEffectActiveTickProcedure.execute(entity);
	}

	@Override
	public boolean isDurationEffectTick(int duration, int amplifier) {
		return true;
	}
}
