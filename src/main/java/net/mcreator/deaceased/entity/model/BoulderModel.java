package net.mcreator.deaceased.entity.model;

import software.bernie.geckolib.model.GeoModel;

import net.minecraft.resources.ResourceLocation;

import net.mcreator.deaceased.entity.BoulderEntity;

public class BoulderModel extends GeoModel<BoulderEntity> {
	@Override
	public ResourceLocation getAnimationResource(BoulderEntity entity) {
		return new ResourceLocation("deaceased", "animations/stone_throw.animation.json");
	}

	@Override
	public ResourceLocation getModelResource(BoulderEntity entity) {
		return new ResourceLocation("deaceased", "geo/stone_throw.geo.json");
	}

	@Override
	public ResourceLocation getTextureResource(BoulderEntity entity) {
		return new ResourceLocation("deaceased", "textures/entities/" + entity.getTexture() + ".png");
	}

}
