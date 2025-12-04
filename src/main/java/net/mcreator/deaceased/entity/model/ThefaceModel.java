package net.mcreator.deaceased.entity.model;

import software.bernie.geckolib.model.GeoModel;

import net.minecraft.resources.ResourceLocation;

import net.mcreator.deaceased.entity.ThefaceEntity;

public class ThefaceModel extends GeoModel<ThefaceEntity> {
	@Override
	public ResourceLocation getAnimationResource(ThefaceEntity entity) {
		return new ResourceLocation("deaceased", "animations/delicate.animation.json");
	}

	@Override
	public ResourceLocation getModelResource(ThefaceEntity entity) {
		return new ResourceLocation("deaceased", "geo/delicate.geo.json");
	}

	@Override
	public ResourceLocation getTextureResource(ThefaceEntity entity) {
		return new ResourceLocation("deaceased", "textures/entities/" + entity.getTexture() + ".png");
	}

}
