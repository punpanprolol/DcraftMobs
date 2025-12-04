package net.mcreator.deaceased.procedures;

import net.minecraftforge.registries.ForgeRegistries;

import net.minecraft.world.phys.Vec3;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.Level;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.util.RandomSource;
import net.minecraft.util.Mth;
import net.minecraft.sounds.SoundSource;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.core.BlockPos;
import net.minecraft.commands.arguments.EntityAnchorArgument;

import net.mcreator.deaceased.init.DeaceasedModMobEffects;
import net.mcreator.deaceased.entity.ThefaceEntity;
import net.mcreator.deaceased.DeaceasedMod;

import java.util.List;
import java.util.Comparator;
import java.util.ArrayList;

public class ThefaceOnEntityTickUpdateProcedure {
	public static void execute(LevelAccessor world, double x, double y, double z, Entity entity) {
		if (entity == null)
			return;
		ArrayList<Object> mobs = new ArrayList<>();
		boolean found = false;
		double mob_count = 0;
		double T = 0;
		double Zo = 0;
		double Yo = 0;
		double Za = 0;
		double Xo = 0;
		double Ya = 0;
		double Xa = 0;
		double sx = 0;
		double sy = 0;
		double sz = 0;
		Entity chosenmobs = null;
		Entity targetmob = null;
		Entity truechosen = null;
		if ((entity instanceof Mob _mobEnt ? (Entity) _mobEnt.getTarget() : null) instanceof LivingEntity) {
			{
				final Vec3 _center = new Vec3((entity.getX()), (entity.getY()), (entity.getZ()));
				List<Entity> _entfound = world.getEntitiesOfClass(Entity.class, new AABB(_center, _center).inflate(128 / 2d), e -> true).stream().sorted(Comparator.comparingDouble(_entcnd -> _entcnd.distanceToSqr(_center))).toList();
				for (Entity entityiterator : _entfound) {
					if (!((entity instanceof Mob _mobEnt ? (Entity) _mobEnt.getTarget() : null) == entityiterator)) {
						if (entityiterator instanceof LivingEntity) {
							entityiterator.getPersistentData().putBoolean("behind_wallT", false);
						}
					}
					if (entityiterator.getPersistentData().getBoolean("behind_wallT") == false) {
						if (!(entityiterator == entity)) {
							if ((entity instanceof Mob _mobEnt ? (Entity) _mobEnt.getTarget() : null) == entityiterator) {
								if (entityiterator.isAlive()) {
									targetmob = entityiterator;
								}
							}
						}
					}
				}
			}
			{
				final Vec3 _center = new Vec3((entity.getX()), (entity.getY()), (entity.getZ()));
				List<Entity> _entfound = world.getEntitiesOfClass(Entity.class, new AABB(_center, _center).inflate(12 / 2d), e -> true).stream().sorted(Comparator.comparingDouble(_entcnd -> _entcnd.distanceToSqr(_center))).toList();
				for (Entity entityiterator : _entfound) {
					if (!(entityiterator == entity)) {
						if (entityiterator instanceof LivingEntity) {
							entityiterator.getPersistentData().putDouble("aoe_x", (entity.getX() - entityiterator.getX()));
							entityiterator.getPersistentData().putDouble("aoe_y", ((entity.getY() + entity.getBbHeight()) - (entityiterator.getY() + entityiterator.getBbHeight())));
							entityiterator.getPersistentData().putDouble("aoe_z", (entity.getZ() - entityiterator.getZ()));
							entityiterator.getPersistentData().putDouble("distance", 0);
							DeaceasedMod.queueServerWork(1, () -> {
								for (int index0 = 0; index0 < 20; index0++) {
									if (world.isEmptyBlock(BlockPos.containing(entity.getX() + entityiterator.getPersistentData().getDouble("aoe_x") * entityiterator.getPersistentData().getDouble("distance"),
											entity.getY() + entity.getBbHeight() + entityiterator.getPersistentData().getDouble("aoe_y") * entityiterator.getPersistentData().getDouble("distance"),
											entity.getZ() + entityiterator.getPersistentData().getDouble("aoe_z") * entityiterator.getPersistentData().getDouble("distance")))) {
										entityiterator.getPersistentData().putBoolean("behind_wall", false);
										entityiterator.getPersistentData().putDouble("distance", (entityiterator.getPersistentData().getDouble("distance") - 0.05));
									} else {
										entityiterator.getPersistentData().putBoolean("behind_wall", true);
									}
								}
							});
						}
					}
					if (entityiterator.getPersistentData().getBoolean("behind_wall") == false) {
						if (!(entityiterator == entity)) {
							if ((entityiterator instanceof Mob _mobEnt ? (Entity) _mobEnt.getTarget() : null) == (entity instanceof Mob _mobEnt ? (Entity) _mobEnt.getTarget() : null)) {
								{
									mobs.add(entityiterator);
								}
							}
						}
					}
				}
			}
			if (mobs.size() > 0) {
				if (chosenmobs == null) {
					chosenmobs = mobs.get((int) Mth.nextDouble(RandomSource.create(), 0, mobs.size() - 1)) instanceof Entity _entity59 ? _entity59 : null;
				}
			}
			if ((entity instanceof ThefaceEntity _datEntL60 && _datEntL60.getEntityData().get(ThefaceEntity.DATA_shot)) == false) {
				if (!(chosenmobs == null) && chosenmobs.isAlive()) {
					if ((entity instanceof ThefaceEntity _datEntI ? _datEntI.getEntityData().get(ThefaceEntity.DATA_countdown2) : 0) == 0) {
						if (world instanceof Level _level) {
							if (!_level.isClientSide()) {
								_level.playSound(null, BlockPos.containing(x, y, z), ForgeRegistries.SOUND_EVENTS.getValue(new ResourceLocation("entity.arrow.shoot")), SoundSource.NEUTRAL, 1, 1);
							} else {
								_level.playLocalSound(x, y, z, ForgeRegistries.SOUND_EVENTS.getValue(new ResourceLocation("entity.arrow.shoot")), SoundSource.NEUTRAL, 1, 1, false);
							}
						}
						if (entity instanceof ThefaceEntity _datEntSetI)
							_datEntSetI.getEntityData().set(ThefaceEntity.DATA_countdown, 200);
						if (entity instanceof ThefaceEntity _datEntSetI)
							_datEntSetI.getEntityData().set(ThefaceEntity.DATA_countdown2, -1);
						if (entity instanceof ThefaceEntity _datEntSetL)
							_datEntSetL.getEntityData().set(ThefaceEntity.DATA_shot, true);
					}
				}
			}
			if ((entity instanceof ThefaceEntity _datEntI ? _datEntI.getEntityData().get(ThefaceEntity.DATA_countdown) : 0) > 0) {
				if (entity instanceof ThefaceEntity _datEntSetI)
					_datEntSetI.getEntityData().set(ThefaceEntity.DATA_countdown, (int) ((entity instanceof ThefaceEntity _datEntI ? _datEntI.getEntityData().get(ThefaceEntity.DATA_countdown) : 0) - 1));
			}
			if ((entity instanceof ThefaceEntity _datEntI ? _datEntI.getEntityData().get(ThefaceEntity.DATA_countdown) : 0) == 0) {
				if (entity instanceof ThefaceEntity _datEntSetL)
					_datEntSetL.getEntityData().set(ThefaceEntity.DATA_shot, false);
			}
			if ((entity instanceof ThefaceEntity _datEntI ? _datEntI.getEntityData().get(ThefaceEntity.DATA_countdown2) : 0) > 0) {
				if (entity instanceof ThefaceEntity _datEntSetI)
					_datEntSetI.getEntityData().set(ThefaceEntity.DATA_countdown2, (int) ((entity instanceof ThefaceEntity _datEntI ? _datEntI.getEntityData().get(ThefaceEntity.DATA_countdown2) : 0) - 1));
			}
			if ((entity instanceof ThefaceEntity _datEntI ? _datEntI.getEntityData().get(ThefaceEntity.DATA_countdown3) : 0) > 0) {
				if (entity instanceof ThefaceEntity _datEntSetI)
					_datEntSetI.getEntityData().set(ThefaceEntity.DATA_countdown3, (int) ((entity instanceof ThefaceEntity _datEntI ? _datEntI.getEntityData().get(ThefaceEntity.DATA_countdown2) : 0) - 1));
			}
			{
				final Vec3 _center = new Vec3(x, y, z);
				List<Entity> _entfound = world.getEntitiesOfClass(Entity.class, new AABB(_center, _center).inflate(32 / 2d), e -> true).stream().sorted(Comparator.comparingDouble(_entcnd -> _entcnd.distanceToSqr(_center))).toList();
				for (Entity entityiterator : _entfound) {
					if (!(targetmob == null) && targetmob.isAlive()) {
						if (targetmob == entityiterator) {
							if (entity instanceof ThefaceEntity _datEntSetI)
								_datEntSetI.getEntityData().set(ThefaceEntity.DATA_lockc2, 1);
							entity.lookAt(EntityAnchorArgument.Anchor.EYES, new Vec3((entityiterator.getX()), (entityiterator.getY()), (entityiterator.getZ())));
						} else {
							if (entity instanceof ThefaceEntity _datEntSetI)
								_datEntSetI.getEntityData().set(ThefaceEntity.DATA_lockc2, 0);
						}
					} else {
						if (entity instanceof ThefaceEntity _datEntSetI)
							_datEntSetI.getEntityData().set(ThefaceEntity.DATA_lockc2, 0);
					}
				}
			}
			{
				final Vec3 _center = new Vec3(x, y, z);
				List<Entity> _entfound = world.getEntitiesOfClass(Entity.class, new AABB(_center, _center).inflate(5 / 2d), e -> true).stream().sorted(Comparator.comparingDouble(_entcnd -> _entcnd.distanceToSqr(_center))).toList();
				for (Entity entityiterator : _entfound) {
					if ((entity instanceof Mob _mobEnt ? (Entity) _mobEnt.getTarget() : null) == entityiterator) {
						if (entity instanceof ThefaceEntity _datEntSetI)
							_datEntSetI.getEntityData().set(ThefaceEntity.DATA_lockc1, 0);
					} else {
						if (entity instanceof ThefaceEntity _datEntSetI)
							_datEntSetI.getEntityData().set(ThefaceEntity.DATA_lockc1, 1);
					}
				}
			}
			if ((entity instanceof ThefaceEntity _datEntI ? _datEntI.getEntityData().get(ThefaceEntity.DATA_lockc2) : 0) == 1 && (entity instanceof ThefaceEntity _datEntI ? _datEntI.getEntityData().get(ThefaceEntity.DATA_lockc1) : 0) == 1) {
				if (entity instanceof ThefaceEntity _datEntSetI)
					_datEntSetI.getEntityData().set(ThefaceEntity.DATA_doable, 1);
				if (entity instanceof LivingEntity _entity && !_entity.level().isClientSide())
					_entity.addEffect(new MobEffectInstance(MobEffects.MOVEMENT_SLOWDOWN, 5, 30, false, false));
			} else {
				if (entity instanceof ThefaceEntity _datEntSetI)
					_datEntSetI.getEntityData().set(ThefaceEntity.DATA_doable, 0);
			}
			if ((entity instanceof ThefaceEntity _datEntI ? _datEntI.getEntityData().get(ThefaceEntity.DATA_doable) : 0) == 1) {
				if ((entity instanceof ThefaceEntity _datEntL101 && _datEntL101.getEntityData().get(ThefaceEntity.DATA_open)) == false) {
					if ((entity instanceof Mob _mobEnt ? (Entity) _mobEnt.getTarget() : null) instanceof LivingEntity) {
						if (entity.isAlive()) {
							if ((chosenmobs instanceof Mob _mobEnt ? (Entity) _mobEnt.getTarget() : null) instanceof LivingEntity) {
								if (entity instanceof ThefaceEntity) {
									((ThefaceEntity) entity).setAnimation("open");
								}
								if (entity instanceof ThefaceEntity _datEntSetI)
									_datEntSetI.getEntityData().set(ThefaceEntity.DATA_countdown2, 120);
								if (entity instanceof ThefaceEntity _datEntSetI)
									_datEntSetI.getEntityData().set(ThefaceEntity.DATA_countdown3, 20);
								if (entity instanceof LivingEntity _entity && !_entity.level().isClientSide())
									_entity.addEffect(new MobEffectInstance(MobEffects.LEVITATION, 100, 0, false, false));
								if (entity instanceof LivingEntity _entity && !_entity.level().isClientSide())
									_entity.addEffect(new MobEffectInstance(MobEffects.MOVEMENT_SLOWDOWN, 120, 30, false, false));
								if (entity instanceof ThefaceEntity _datEntSetL)
									_datEntSetL.getEntityData().set(ThefaceEntity.DATA_open, true);
								if (world instanceof Level _level) {
									if (!_level.isClientSide()) {
										_level.playSound(null, BlockPos.containing(x, y, z), ForgeRegistries.SOUND_EVENTS.getValue(new ResourceLocation("entity.cat.death")), SoundSource.NEUTRAL, 1, 1);
									} else {
										_level.playLocalSound(x, y, z, ForgeRegistries.SOUND_EVENTS.getValue(new ResourceLocation("entity.cat.death")), SoundSource.NEUTRAL, 1, 1, false);
									}
								}
								if (chosenmobs instanceof LivingEntity _entity && !_entity.level().isClientSide())
									_entity.addEffect(new MobEffectInstance(MobEffects.LEVITATION, 20, 2, false, false));
								if (chosenmobs instanceof LivingEntity _entity && !_entity.level().isClientSide())
									_entity.addEffect(new MobEffectInstance(MobEffects.MOVEMENT_SLOWDOWN, 20, 30, false, false));
								if (chosenmobs instanceof LivingEntity _entity && !_entity.level().isClientSide())
									_entity.addEffect(new MobEffectInstance(DeaceasedModMobEffects.WARPPEDDELAY.get(), 20, 0, false, false));
							}
						}
					}
				}
			}
			if ((entity instanceof ThefaceEntity _datEntI ? _datEntI.getEntityData().get(ThefaceEntity.DATA_countdown3) : 0) == 0) {
				if (entity instanceof ThefaceEntity _datEntSetI)
					_datEntSetI.getEntityData().set(ThefaceEntity.DATA_countdown3, -1);
				if (entity instanceof ThefaceEntity _datEntSetL)
					_datEntSetL.getEntityData().set(ThefaceEntity.DATA_shot, false);
				DeaceasedMod.queueServerWork(100, () -> {
					if (entity instanceof ThefaceEntity _datEntSetL)
						_datEntSetL.getEntityData().set(ThefaceEntity.DATA_open, false);
					if (entity instanceof ThefaceEntity _datEntSetI)
						_datEntSetI.getEntityData().set(ThefaceEntity.DATA_countdown3, -1);
					if (entity instanceof ThefaceEntity _datEntSetI)
						_datEntSetI.getEntityData().set(ThefaceEntity.DATA_countdown2, -1);
				});
			}
		}
		{
			final Vec3 _center = new Vec3(x, y, z);
			List<Entity> _entfound = world.getEntitiesOfClass(Entity.class, new AABB(_center, _center).inflate(25 / 2d), e -> true).stream().sorted(Comparator.comparingDouble(_entcnd -> _entcnd.distanceToSqr(_center))).toList();
			for (Entity entityiterator : _entfound) {
				if (entityiterator instanceof LivingEntity _livEnt124 && _livEnt124.hasEffect(DeaceasedModMobEffects.WARPPEDDELAY.get())) {
					entityiterator.lookAt(EntityAnchorArgument.Anchor.EYES, new Vec3(((entityiterator instanceof Mob _mobEnt ? (Entity) _mobEnt.getTarget() : null).getX()),
							((entityiterator instanceof Mob _mobEnt ? (Entity) _mobEnt.getTarget() : null).getY()), ((entityiterator instanceof Mob _mobEnt ? (Entity) _mobEnt.getTarget() : null).getZ())));
				}
				if (entityiterator instanceof LivingEntity _livEnt132 && _livEnt132.hasEffect(DeaceasedModMobEffects.WRAPPED.get())) {
					if (!(entityiterator instanceof LivingEntity _livEnt133 && _livEnt133.hasEffect(DeaceasedModMobEffects.WARPPEDDELAY.get()))) {
						if (entityiterator instanceof LivingEntity _entity)
							_entity.removeEffect(DeaceasedModMobEffects.WRAPPED.get());
						if (entityiterator instanceof LivingEntity _entity && !_entity.level().isClientSide())
							_entity.addEffect(new MobEffectInstance(DeaceasedModMobEffects.WARPPEDDELAY.get(), 60, 0, false, false));
						entityiterator.setDeltaMovement(
								new Vec3((Math.sin(Math.toRadians(entityiterator.getYRot() + 180)) * 1 * 1.25), ((Math.sin(Math.toRadians(0 - entityiterator.getXRot())) + 0.5) * 1.3), (Math.cos(Math.toRadians(entityiterator.getYRot())) * 1 * 1.5)));
					}
				}
			}
		}
		{
			final Vec3 _center = new Vec3(x, y, z);
			List<Entity> _entfound = world.getEntitiesOfClass(Entity.class, new AABB(_center, _center).inflate(16 / 2d), e -> true).stream().sorted(Comparator.comparingDouble(_entcnd -> _entcnd.distanceToSqr(_center))).toList();
			for (Entity entityiterator : _entfound) {
				if (entityiterator instanceof LivingEntity _livEnt141 && _livEnt141.hasEffect(DeaceasedModMobEffects.WRAPPED.get())) {
					Xo = entity.getX() - entityiterator.getX();
					Yo = entity.getY() - entityiterator.getY();
					Zo = entity.getZ() - entityiterator.getZ();
					if (Math.floor(entity.getX()) <= Math.floor(entityiterator.getX())) {
						if (Math.floor(entity.getX()) == Math.floor(entityiterator.getX())) {
							if (Math.floor(entity.getY()) == Math.floor(entityiterator.getY())) {
								if (Math.floor(entity.getZ()) <= Math.floor(entityiterator.getZ())) {
									Za = Math.floor(entity.getZ()) + 0.2;
									while (Za <= Math.floor(entityiterator.getZ())) {
										T = (Za - entity.getZ()) / Zo;
										Ya = entity.getBbHeight() / 2 + entity.getY() + Yo * T;
										Xa = entity.getX() + Xo * T;
										if (world instanceof ServerLevel _level)
											_level.sendParticles(ParticleTypes.FLAME, Xa, Ya, Za, 1, 0, 0, 0, 0);
										Za = 0.2 + Za;
									}
								} else {
									Za = Math.floor(entityiterator.getZ()) + 0.2;
									while (Za <= Math.floor(entity.getZ())) {
										T = (Za - entity.getZ()) / Zo;
										Ya = entity.getBbHeight() / 2 + entity.getY() + Yo * T;
										Xa = entity.getX() + Xo * T;
										if (world instanceof ServerLevel _level)
											_level.sendParticles(ParticleTypes.FLAME, Xa, Ya, Za, 1, 0, 0, 0, 0);
										Za = 0.2 + Za;
									}
								}
							} else {
								if (Math.floor(entity.getY()) <= Math.floor(entityiterator.getY())) {
									Ya = Math.floor(entity.getY()) + 0.2;
									while (Ya <= Math.floor(entityiterator.getY())) {
										T = (Ya - entity.getY()) / Yo;
										Xa = entity.getX() + Xo * T;
										Za = entity.getZ() + Zo * T;
										if (world instanceof ServerLevel _level)
											_level.sendParticles(ParticleTypes.FLAME, Xa, Ya, Za, 1, 0, 0, 0, 0);
										Ya = 0.2 + Ya;
									}
								} else {
									Ya = Math.floor(entityiterator.getY()) + 0.2;
									while (Ya <= Math.floor(entity.getY())) {
										T = (Ya - entity.getY()) / Yo;
										Xa = entity.getX() + Xo * T;
										Za = entity.getZ() + Zo * T;
										if (world instanceof ServerLevel _level)
											_level.sendParticles(ParticleTypes.FLAME, Xa, Ya, Za, 1, 0, 0, 0, 0);
										Ya = 0.2 + Ya;
									}
								}
							}
						} else {
							Xa = Math.floor(entity.getX()) + 0.2;
							while (Xa <= Math.floor(entityiterator.getX())) {
								T = (Xa - entity.getX()) / Xo;
								Ya = entity.getBbHeight() / 2 + entity.getY() + Yo * T;
								Za = entity.getZ() + Zo * T;
								if (world instanceof ServerLevel _level)
									_level.sendParticles(ParticleTypes.FLAME, Xa, Ya, Za, 1, 0, 0, 0, 0);
								Xa = 0.2 + Xa;
							}
						}
					} else {
						Xa = entityiterator.getX() + 0.2;
						while (Xa < Math.floor(entity.getX())) {
							T = (Xa - entity.getX()) / Xo;
							Ya = entity.getBbHeight() / 2 + entity.getY() + Yo * T;
							Za = entity.getZ() + Zo * T;
							if (world instanceof ServerLevel _level)
								_level.sendParticles(ParticleTypes.FLAME, Xa, Ya, Za, 1, 0, 0, 0, 0);
							Xa = 0.2 + Xa;
						}
					}
				}
			}
		}
	}
}
