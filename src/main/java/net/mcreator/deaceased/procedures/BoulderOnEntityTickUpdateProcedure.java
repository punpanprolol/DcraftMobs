package net.mcreator.deaceased.procedures;

import net.minecraftforge.registries.ForgeRegistries;

import net.minecraft.world.phys.Vec3;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.Level;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.damagesource.DamageTypes;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.sounds.SoundSource;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.core.registries.Registries;
import net.minecraft.core.BlockPos;

import net.mcreator.deaceased.init.DeaceasedModMobEffects;
import net.mcreator.deaceased.entity.BoulderEntity;

import java.util.List;
import java.util.Comparator;

public class BoulderOnEntityTickUpdateProcedure {
	public static void execute(LevelAccessor world, double x, double y, double z, Entity entity) {
		if (entity == null)
			return;
		if ((entity instanceof BoulderEntity _datEntL0 && _datEntL0.getEntityData().get(BoulderEntity.DATA_picked)) == true) {
			if (entity instanceof BoulderEntity) {
				((BoulderEntity) entity).setAnimation("idle");
			}
		}
		if (entity instanceof LivingEntity _livEnt2 && _livEnt2.hasEffect(DeaceasedModMobEffects.WARPPEDDELAY.get())) {
			if (entity instanceof BoulderEntity _datEntSetL)
				_datEntSetL.getEntityData().set(BoulderEntity.DATA_picked, true);
		}
		if ((entity instanceof BoulderEntity _datEntL4 && _datEntL4.getEntityData().get(BoulderEntity.DATA_picked)) == true) {
			{
				final Vec3 _center = new Vec3(x, y, z);
				List<Entity> _entfound = world.getEntitiesOfClass(Entity.class, new AABB(_center, _center).inflate((entity.getBbHeight()) / 2d), e -> true).stream().sorted(Comparator.comparingDouble(_entcnd -> _entcnd.distanceToSqr(_center)))
						.toList();
				for (Entity entityiterator : _entfound) {
					if ((entity instanceof Mob _mobEnt ? (Entity) _mobEnt.getTarget() : null) == entityiterator) {
						if (entityiterator instanceof LivingEntity _livEnt8 && _livEnt8.isBlocking()) {
							if (!entity.level().isClientSide())
								entity.discard();
							world.levelEvent(2001, BlockPos.containing(x, y, z), Block.getId(Blocks.STONE.defaultBlockState()));
							if (world instanceof Level _level) {
								if (!_level.isClientSide()) {
									_level.playSound(null, BlockPos.containing(x, y, z), ForgeRegistries.SOUND_EVENTS.getValue(new ResourceLocation("entity.wither.break_block")), SoundSource.NEUTRAL, 1, 1);
								} else {
									_level.playLocalSound(x, y, z, ForgeRegistries.SOUND_EVENTS.getValue(new ResourceLocation("entity.wither.break_block")), SoundSource.NEUTRAL, 1, 1, false);
								}
							}
							entity.setDeltaMovement(new Vec3((Math.sin(Math.toRadians(entityiterator.getYRot() + 180)) * 1.25 * 1.1), ((Math.sin(Math.toRadians(0 - entityiterator.getXRot())) + 0.5) * 1),
									(Math.cos(Math.toRadians(entityiterator.getYRot())) * 1.25 * 1.3)));
						} else {
							if (!entity.level().isClientSide())
								entity.discard();
							if (world instanceof Level _level) {
								if (!_level.isClientSide()) {
									_level.playSound(null, BlockPos.containing(x, y, z), ForgeRegistries.SOUND_EVENTS.getValue(new ResourceLocation("entity.wither.break_block")), SoundSource.NEUTRAL, 1, 1);
								} else {
									_level.playLocalSound(x, y, z, ForgeRegistries.SOUND_EVENTS.getValue(new ResourceLocation("entity.wither.break_block")), SoundSource.NEUTRAL, 1, 1, false);
								}
							}
							world.levelEvent(2001, BlockPos.containing(x, y, z), Block.getId(Blocks.STONE.defaultBlockState()));
							entityiterator.hurt(new DamageSource(world.registryAccess().registryOrThrow(Registries.DAMAGE_TYPE).getHolderOrThrow(DamageTypes.MOB_PROJECTILE), entity),
									(float) (entity instanceof LivingEntity _livingEntity19 && _livingEntity19.getAttributes().hasAttribute(Attributes.ATTACK_DAMAGE) ? _livingEntity19.getAttribute(Attributes.ATTACK_DAMAGE).getValue() : 0));
							entityiterator.setDeltaMovement(new Vec3((Math.sin(Math.toRadians(entityiterator.getYRot() + 180)) * 1.25 * (-1.1)), ((Math.sin(Math.toRadians(0 - entityiterator.getXRot())) + 0.5) * 1.12),
									(Math.cos(Math.toRadians(entityiterator.getYRot())) * 1.25 * (-1.3))));
						}
					}
				}
			}
		}
	}
}
