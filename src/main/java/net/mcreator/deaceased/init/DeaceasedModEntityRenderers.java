
/*
 *    MCreator note: This file will be REGENERATED on each build.
 */
package net.mcreator.deaceased.init;

import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.client.event.EntityRenderersEvent;
import net.minecraftforge.api.distmarker.Dist;

import net.mcreator.deaceased.client.renderer.ThefaceRenderer;
import net.mcreator.deaceased.client.renderer.TestfloatRenderer;

@Mod.EventBusSubscriber(bus = Mod.EventBusSubscriber.Bus.MOD, value = Dist.CLIENT)
public class DeaceasedModEntityRenderers {
	@SubscribeEvent
	public static void registerEntityRenderers(EntityRenderersEvent.RegisterRenderers event) {
		event.registerEntityRenderer(DeaceasedModEntities.THEFACE.get(), ThefaceRenderer::new);
		event.registerEntityRenderer(DeaceasedModEntities.TESTFLOAT.get(), TestfloatRenderer::new);
	}
}
