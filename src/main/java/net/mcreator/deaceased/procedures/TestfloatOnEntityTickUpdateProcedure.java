package net.mcreator.deaceased.procedures;

import net.minecraft.world.phys.Vec3;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.Entity;

import net.mcreator.deaceased.entity.TestfloatEntity;
import net.mcreator.deaceased.DeaceasedMod;

import java.util.List;
import java.util.Comparator;
import java.util.ArrayList;

public class TestfloatOnEntityTickUpdateProcedure {
	public static void execute(LevelAccessor world, double x, double y, double z, Entity entity) {
		if (entity == null)
			return;
		ArrayList<Object> mobs = new ArrayList<>();
		Entity chosenmobs = null;
		Entity targetmob = null;
		double T = 0;
		double Zo = 0;
		double Yo = 0;
		double Za = 0;
		double Xo = 0;
		double Ya = 0;
		double Xa = 0;
		double dx = 0;
		double dy = 0;
		double dz = 0;
		double dist = 0;
		if ((entity instanceof TestfloatEntity _datEntI ? _datEntI.getEntityData().get(TestfloatEntity.DATA_countdown_ck) : 0) > 0) {
			if ((entity instanceof TestfloatEntity _datEntI ? _datEntI.getEntityData().get(TestfloatEntity.DATA_shot) : 0) == 0) {
				if (entity instanceof TestfloatEntity _datEntSetI)
					_datEntSetI.getEntityData().set(TestfloatEntity.DATA_countdown_ck, (int) ((entity instanceof TestfloatEntity _datEntI ? _datEntI.getEntityData().get(TestfloatEntity.DATA_countdown_ck) : 0) - 1));
			}
		}
		if ((entity instanceof TestfloatEntity _datEntI ? _datEntI.getEntityData().get(TestfloatEntity.DATA_countdown_ck) : 0) == 0) {
			if (entity instanceof TestfloatEntity _datEntSetI)
				_datEntSetI.getEntityData().set(TestfloatEntity.DATA_shot, 1);
			{
				final Vec3 _center = new Vec3(x, y, z);
				List<Entity> _entfound = world.getEntitiesOfClass(Entity.class, new AABB(_center, _center).inflate(32 / 2d), e -> true).stream().sorted(Comparator.comparingDouble(_entcnd -> _entcnd.distanceToSqr(_center))).toList();
				for (Entity entityiterator : _entfound) {
					dx = entityiterator.getX() - entity.getX();
					dy = entityiterator.getY() - entity.getY();
					dz = entityiterator.getZ() - entity.getZ();
					dist = Math.sqrt(dz * dz + dy * dy + dx * dx);
					dx = dx / dist;
					dy = dy / dist;
					dz = dz / dist;
					dx = dx * (-2);
					dy = dy * 2;
					dz = dz * (-2);
					if ((entity instanceof Mob _mobEnt ? (Entity) _mobEnt.getTarget() : null) == entityiterator) {
						entity.setDeltaMovement(new Vec3(dx, dy, dz));
					}
				}
			}
			DeaceasedMod.queueServerWork(20, () -> {
				if (!entity.level().isClientSide())
					entity.discard();
			});
		}
	}
}
