
package net.mcreator.deaceased.entity;

import software.bernie.geckolib.util.GeckoLibUtil;
import software.bernie.geckolib.core.object.PlayState;
import software.bernie.geckolib.core.animation.RawAnimation;
import software.bernie.geckolib.core.animation.AnimationState;
import software.bernie.geckolib.core.animation.AnimationController;
import software.bernie.geckolib.core.animation.AnimatableManager;
import software.bernie.geckolib.core.animatable.instance.AnimatableInstanceCache;
import software.bernie.geckolib.animatable.GeoEntity;

import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.network.PlayMessages;
import net.minecraftforge.network.NetworkHooks;

import net.minecraft.world.level.Level;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.npc.Villager;
import net.minecraft.world.entity.monster.Monster;
import net.minecraft.world.entity.animal.IronGolem;
import net.minecraft.world.entity.ai.goal.target.NearestAttackableTargetGoal;
import net.minecraft.world.entity.ai.goal.target.HurtByTargetGoal;
import net.minecraft.world.entity.ai.goal.RandomStrollGoal;
import net.minecraft.world.entity.ai.goal.RandomLookAroundGoal;
import net.minecraft.world.entity.ai.goal.MeleeAttackGoal;
import net.minecraft.world.entity.ai.goal.FloatGoal;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.Pose;
import net.minecraft.world.entity.MobType;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.EntityDimensions;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.damagesource.DamageTypes;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.protocol.game.ClientGamePacketListener;
import net.minecraft.network.protocol.Packet;
import net.minecraft.nbt.CompoundTag;

import net.mcreator.deaceased.procedures.ThefaceOnEntityTickUpdateProcedure;
import net.mcreator.deaceased.procedures.CancelwalkProcedure;
import net.mcreator.deaceased.init.DeaceasedModEntities;

public class ThefaceEntity extends Monster implements GeoEntity {
	public static final EntityDataAccessor<Boolean> SHOOT = SynchedEntityData.defineId(ThefaceEntity.class, EntityDataSerializers.BOOLEAN);
	public static final EntityDataAccessor<String> ANIMATION = SynchedEntityData.defineId(ThefaceEntity.class, EntityDataSerializers.STRING);
	public static final EntityDataAccessor<String> TEXTURE = SynchedEntityData.defineId(ThefaceEntity.class, EntityDataSerializers.STRING);
	public static final EntityDataAccessor<Boolean> DATA_Inrange = SynchedEntityData.defineId(ThefaceEntity.class, EntityDataSerializers.BOOLEAN);
	public static final EntityDataAccessor<Boolean> DATA_open = SynchedEntityData.defineId(ThefaceEntity.class, EntityDataSerializers.BOOLEAN);
	public static final EntityDataAccessor<Integer> DATA_doable = SynchedEntityData.defineId(ThefaceEntity.class, EntityDataSerializers.INT);
	public static final EntityDataAccessor<Boolean> DATA_shot = SynchedEntityData.defineId(ThefaceEntity.class, EntityDataSerializers.BOOLEAN);
	public static final EntityDataAccessor<Integer> DATA_countdown = SynchedEntityData.defineId(ThefaceEntity.class, EntityDataSerializers.INT);
	public static final EntityDataAccessor<Integer> DATA_countdown2 = SynchedEntityData.defineId(ThefaceEntity.class, EntityDataSerializers.INT);
	public static final EntityDataAccessor<Integer> DATA_countdown3 = SynchedEntityData.defineId(ThefaceEntity.class, EntityDataSerializers.INT);
	public static final EntityDataAccessor<Integer> DATA_countdown4 = SynchedEntityData.defineId(ThefaceEntity.class, EntityDataSerializers.INT);
	public static final EntityDataAccessor<Integer> DATA_ps = SynchedEntityData.defineId(ThefaceEntity.class, EntityDataSerializers.INT);
	public static final EntityDataAccessor<Boolean> DATA_lockc1 = SynchedEntityData.defineId(ThefaceEntity.class, EntityDataSerializers.BOOLEAN);
	public static final EntityDataAccessor<Boolean> DATA_lockc2 = SynchedEntityData.defineId(ThefaceEntity.class, EntityDataSerializers.BOOLEAN);
	public static final EntityDataAccessor<Integer> DATA_stonecount = SynchedEntityData.defineId(ThefaceEntity.class, EntityDataSerializers.INT);
	public static final EntityDataAccessor<Integer> DATA_forcecount = SynchedEntityData.defineId(ThefaceEntity.class, EntityDataSerializers.INT);
	private final AnimatableInstanceCache cache = GeckoLibUtil.createInstanceCache(this);
	private boolean swinging;
	private boolean lastloop;
	private long lastSwing;
	public String animationprocedure = "empty";

	public ThefaceEntity(PlayMessages.SpawnEntity packet, Level world) {
		this(DeaceasedModEntities.THEFACE.get(), world);
	}

	public ThefaceEntity(EntityType<ThefaceEntity> type, Level world) {
		super(type, world);
		xpReward = 0;
		setNoAi(false);
		setMaxUpStep(2f);
		setPersistenceRequired();
	}

	@Override
	protected void defineSynchedData() {
		super.defineSynchedData();
		this.entityData.define(SHOOT, false);
		this.entityData.define(ANIMATION, "undefined");
		this.entityData.define(TEXTURE, "theface");
		this.entityData.define(DATA_Inrange, false);
		this.entityData.define(DATA_open, false);
		this.entityData.define(DATA_doable, 0);
		this.entityData.define(DATA_shot, true);
		this.entityData.define(DATA_countdown, 0);
		this.entityData.define(DATA_countdown2, -1);
		this.entityData.define(DATA_countdown3, -1);
		this.entityData.define(DATA_countdown4, 0);
		this.entityData.define(DATA_ps, 0);
		this.entityData.define(DATA_lockc1, false);
		this.entityData.define(DATA_lockc2, false);
		this.entityData.define(DATA_stonecount, 0);
		this.entityData.define(DATA_forcecount, 0);
	}

	public void setTexture(String texture) {
		this.entityData.set(TEXTURE, texture);
	}

	public String getTexture() {
		return this.entityData.get(TEXTURE);
	}

	@Override
	public Packet<ClientGamePacketListener> getAddEntityPacket() {
		return NetworkHooks.getEntitySpawningPacket(this);
	}

	@Override
	protected void registerGoals() {
		super.registerGoals();
		this.goalSelector.addGoal(1, new MeleeAttackGoal(this, 1.2, false) {
			@Override
			protected double getAttackReachSqr(LivingEntity entity) {
				return this.mob.getBbWidth() * this.mob.getBbWidth() + entity.getBbWidth();
			}

			@Override
			public boolean canUse() {
				double x = ThefaceEntity.this.getX();
				double y = ThefaceEntity.this.getY();
				double z = ThefaceEntity.this.getZ();
				Entity entity = ThefaceEntity.this;
				Level world = ThefaceEntity.this.level();
				return super.canUse() && CancelwalkProcedure.execute(entity);
			}

			@Override
			public boolean canContinueToUse() {
				double x = ThefaceEntity.this.getX();
				double y = ThefaceEntity.this.getY();
				double z = ThefaceEntity.this.getZ();
				Entity entity = ThefaceEntity.this;
				Level world = ThefaceEntity.this.level();
				return super.canContinueToUse() && CancelwalkProcedure.execute(entity);
			}

		});
		this.targetSelector.addGoal(2, new HurtByTargetGoal(this));
		this.targetSelector.addGoal(3, new NearestAttackableTargetGoal(this, Player.class, false, false));
		this.targetSelector.addGoal(4, new NearestAttackableTargetGoal(this, Villager.class, false, false));
		this.targetSelector.addGoal(5, new NearestAttackableTargetGoal(this, IronGolem.class, false, false));
		this.goalSelector.addGoal(6, new RandomStrollGoal(this, 0.8));
		this.goalSelector.addGoal(7, new FloatGoal(this));
		this.goalSelector.addGoal(8, new RandomLookAroundGoal(this));
	}

	@Override
	public MobType getMobType() {
		return MobType.UNDEAD;
	}

	@Override
	public boolean removeWhenFarAway(double distanceToClosestPlayer) {
		return false;
	}

	@Override
	public SoundEvent getHurtSound(DamageSource ds) {
		return ForgeRegistries.SOUND_EVENTS.getValue(new ResourceLocation("entity.generic.hurt"));
	}

	@Override
	public SoundEvent getDeathSound() {
		return ForgeRegistries.SOUND_EVENTS.getValue(new ResourceLocation("entity.generic.death"));
	}

	@Override
	public boolean hurt(DamageSource source, float amount) {
		if (source.is(DamageTypes.FALL))
			return false;
		return super.hurt(source, amount);
	}

	@Override
	public void addAdditionalSaveData(CompoundTag compound) {
		super.addAdditionalSaveData(compound);
		compound.putString("Texture", this.getTexture());
		compound.putBoolean("DataInrange", this.entityData.get(DATA_Inrange));
		compound.putBoolean("Dataopen", this.entityData.get(DATA_open));
		compound.putInt("Datadoable", this.entityData.get(DATA_doable));
		compound.putBoolean("Datashot", this.entityData.get(DATA_shot));
		compound.putInt("Datacountdown", this.entityData.get(DATA_countdown));
		compound.putInt("Datacountdown2", this.entityData.get(DATA_countdown2));
		compound.putInt("Datacountdown3", this.entityData.get(DATA_countdown3));
		compound.putInt("Datacountdown4", this.entityData.get(DATA_countdown4));
		compound.putInt("Dataps", this.entityData.get(DATA_ps));
		compound.putBoolean("Datalockc1", this.entityData.get(DATA_lockc1));
		compound.putBoolean("Datalockc2", this.entityData.get(DATA_lockc2));
		compound.putInt("Datastonecount", this.entityData.get(DATA_stonecount));
		compound.putInt("Dataforcecount", this.entityData.get(DATA_forcecount));
	}

	@Override
	public void readAdditionalSaveData(CompoundTag compound) {
		super.readAdditionalSaveData(compound);
		if (compound.contains("Texture"))
			this.setTexture(compound.getString("Texture"));
		if (compound.contains("DataInrange"))
			this.entityData.set(DATA_Inrange, compound.getBoolean("DataInrange"));
		if (compound.contains("Dataopen"))
			this.entityData.set(DATA_open, compound.getBoolean("Dataopen"));
		if (compound.contains("Datadoable"))
			this.entityData.set(DATA_doable, compound.getInt("Datadoable"));
		if (compound.contains("Datashot"))
			this.entityData.set(DATA_shot, compound.getBoolean("Datashot"));
		if (compound.contains("Datacountdown"))
			this.entityData.set(DATA_countdown, compound.getInt("Datacountdown"));
		if (compound.contains("Datacountdown2"))
			this.entityData.set(DATA_countdown2, compound.getInt("Datacountdown2"));
		if (compound.contains("Datacountdown3"))
			this.entityData.set(DATA_countdown3, compound.getInt("Datacountdown3"));
		if (compound.contains("Datacountdown4"))
			this.entityData.set(DATA_countdown4, compound.getInt("Datacountdown4"));
		if (compound.contains("Dataps"))
			this.entityData.set(DATA_ps, compound.getInt("Dataps"));
		if (compound.contains("Datalockc1"))
			this.entityData.set(DATA_lockc1, compound.getBoolean("Datalockc1"));
		if (compound.contains("Datalockc2"))
			this.entityData.set(DATA_lockc2, compound.getBoolean("Datalockc2"));
		if (compound.contains("Datastonecount"))
			this.entityData.set(DATA_stonecount, compound.getInt("Datastonecount"));
		if (compound.contains("Dataforcecount"))
			this.entityData.set(DATA_forcecount, compound.getInt("Dataforcecount"));
	}

	@Override
	public void baseTick() {
		super.baseTick();
		ThefaceOnEntityTickUpdateProcedure.execute(this.level(), this.getX(), this.getY(), this.getZ(), this);
		this.refreshDimensions();
	}

	@Override
	public EntityDimensions getDimensions(Pose p_33597_) {
		return super.getDimensions(p_33597_).scale((float) 1);
	}

	public static void init() {
	}

	public static AttributeSupplier.Builder createAttributes() {
		AttributeSupplier.Builder builder = Mob.createMobAttributes();
		builder = builder.add(Attributes.MOVEMENT_SPEED, 0.22);
		builder = builder.add(Attributes.MAX_HEALTH, 100);
		builder = builder.add(Attributes.ARMOR, 1.8);
		builder = builder.add(Attributes.ATTACK_DAMAGE, 8);
		builder = builder.add(Attributes.FOLLOW_RANGE, 64);
		builder = builder.add(Attributes.KNOCKBACK_RESISTANCE, 4);
		builder = builder.add(Attributes.ATTACK_KNOCKBACK, 2.5);
		return builder;
	}

	private PlayState movementPredicate(AnimationState event) {
		if (this.animationprocedure.equals("empty")) {
			if ((event.isMoving() || !(event.getLimbSwingAmount() > -0.15F && event.getLimbSwingAmount() < 0.15F))

			) {
				return event.setAndContinue(RawAnimation.begin().thenLoop("move"));
			}
			return event.setAndContinue(RawAnimation.begin().thenLoop("idle"));
		}
		return PlayState.STOP;
	}

	private PlayState attackingPredicate(AnimationState event) {
		double d1 = this.getX() - this.xOld;
		double d0 = this.getZ() - this.zOld;
		float velocity = (float) Math.sqrt(d1 * d1 + d0 * d0);
		if (getAttackAnim(event.getPartialTick()) > 0f && !this.swinging) {
			this.swinging = true;
			this.lastSwing = level().getGameTime();
		}
		if (this.swinging && this.lastSwing + 7L <= level().getGameTime()) {
			this.swinging = false;
		}
		if (this.swinging && event.getController().getAnimationState() == AnimationController.State.STOPPED) {
			event.getController().forceAnimationReset();
			return event.setAndContinue(RawAnimation.begin().thenPlay("hit"));
		}
		return PlayState.CONTINUE;
	}

	String prevAnim = "empty";

	private PlayState procedurePredicate(AnimationState event) {
		if (!animationprocedure.equals("empty") && event.getController().getAnimationState() == AnimationController.State.STOPPED || (!this.animationprocedure.equals(prevAnim) && !this.animationprocedure.equals("empty"))) {
			if (!this.animationprocedure.equals(prevAnim))
				event.getController().forceAnimationReset();
			event.getController().setAnimation(RawAnimation.begin().thenPlay(this.animationprocedure));
			if (event.getController().getAnimationState() == AnimationController.State.STOPPED) {
				this.animationprocedure = "empty";
				event.getController().forceAnimationReset();
			}
		} else if (animationprocedure.equals("empty")) {
			prevAnim = "empty";
			return PlayState.STOP;
		}
		prevAnim = this.animationprocedure;
		return PlayState.CONTINUE;
	}

	@Override
	protected void tickDeath() {
		++this.deathTime;
		if (this.deathTime == 20) {
			this.remove(ThefaceEntity.RemovalReason.KILLED);
			this.dropExperience();
		}
	}

	public String getSyncedAnimation() {
		return this.entityData.get(ANIMATION);
	}

	public void setAnimation(String animation) {
		this.entityData.set(ANIMATION, animation);
	}

	@Override
	public void registerControllers(AnimatableManager.ControllerRegistrar data) {
		data.add(new AnimationController<>(this, "movement", 4, this::movementPredicate));
		data.add(new AnimationController<>(this, "attacking", 4, this::attackingPredicate));
		data.add(new AnimationController<>(this, "procedure", 4, this::procedurePredicate));
	}

	@Override
	public AnimatableInstanceCache getAnimatableInstanceCache() {
		return this.cache;
	}
}
