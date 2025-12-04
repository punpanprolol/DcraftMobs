
package net.mcreator.deaceased.client.renderer;

import net.minecraft.resources.ResourceLocation;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.model.geom.ModelLayers;
import net.minecraft.client.model.ChickenModel;

import net.mcreator.deaceased.entity.TestfloatEntity;

public class TestfloatRenderer extends MobRenderer<TestfloatEntity, ChickenModel<TestfloatEntity>> {
	public TestfloatRenderer(EntityRendererProvider.Context context) {
		super(context, new ChickenModel<TestfloatEntity>(context.bakeLayer(ModelLayers.CHICKEN)), 0.5f);
	}

	@Override
	public ResourceLocation getTextureLocation(TestfloatEntity entity) {
		return new ResourceLocation("deaceased:textures/entities/chk.png");
	}
}
