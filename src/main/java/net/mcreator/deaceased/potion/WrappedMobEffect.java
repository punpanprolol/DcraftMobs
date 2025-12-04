
package net.mcreator.deaceased.potion;

import net.minecraft.world.effect.MobEffectCategory;
import net.minecraft.world.effect.MobEffect;

public class WrappedMobEffect extends MobEffect {
	public WrappedMobEffect() {
		super(MobEffectCategory.HARMFUL, -10092544);
	}

	@Override
	public boolean isDurationEffectTick(int duration, int amplifier) {
		return true;
	}
}
