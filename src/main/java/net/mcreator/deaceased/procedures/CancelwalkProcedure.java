package net.mcreator.deaceased.procedures;

import net.minecraft.world.entity.Entity;

import net.mcreator.deaceased.entity.ThefaceEntity;

public class CancelwalkProcedure {
	public static boolean execute(Entity entity) {
		if (entity == null)
			return false;
		return (entity instanceof ThefaceEntity _datEntL0 && _datEntL0.getEntityData().get(ThefaceEntity.DATA_Inrange)) == false;
	}
}
