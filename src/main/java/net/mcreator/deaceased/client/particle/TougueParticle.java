package net.mcreator.deaceased.client.particle;

import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.api.distmarker.Dist;

import net.minecraft.core.particles.SimpleParticleType;
import net.minecraft.client.Camera;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.particle.TextureSheetParticle;
import net.minecraft.client.particle.SpriteSet;
import net.minecraft.client.particle.ParticleRenderType;
import net.minecraft.client.particle.ParticleProvider;
import net.minecraft.client.particle.Particle;

import com.mojang.blaze3d.vertex.VertexConsumer;
import com.mojang.blaze3d.vertex.PoseStack;
import org.joml.Quaternionf;

@OnlyIn(Dist.CLIENT)
public class TougueParticle extends TextureSheetParticle {
    public static TougueParticleProvider provider(SpriteSet spriteSet) {
        return new TougueParticleProvider(spriteSet);
    }

    public static class TougueParticleProvider implements ParticleProvider<SimpleParticleType> {
        private final SpriteSet spriteSet;

        public TougueParticleProvider(SpriteSet spriteSet) {
            this.spriteSet = spriteSet;
        }

        @Override
        public Particle createParticle(SimpleParticleType typeIn, ClientLevel worldIn,
                double x, double y, double z, double xSpeed, double ySpeed, double zSpeed) {

            return new TougueParticle(worldIn, x, y, z, xSpeed, ySpeed, zSpeed, this.spriteSet);
        }
    }

    private final float yawDeg;
    private final float pitchDeg;

    protected TougueParticle(ClientLevel world, double x, double y, double z,
            double vx, double vy, double vz, SpriteSet spriteSet) {

        super(world, x, y, z);
        this.setSize(0.15f, 0.15f);
        this.quadSize = 0.15f;

        this.lifetime = 1;
        this.gravity = 0;
        this.hasPhysics = false;

        // compute direction
        double len = Math.sqrt(vx * vx + vy * vy + vz * vz);
        if (len == 0) len = 0.0001;

        double dx = vx / len;
        double dy = vy / len;
        double dz = vz / len;

        this.yawDeg = (float) Math.toDegrees(Math.atan2(dz, dx)) - 90f;
        this.pitchDeg = (float) -Math.toDegrees(Math.atan2(dy, Math.sqrt(dx * dx + dz * dz)));

        // don't move
        this.xd = this.yd = this.zd = 0;

        this.pickSprite(spriteSet);
    }

    @Override
    public ParticleRenderType getRenderType() {
        return ParticleRenderType.PARTICLE_SHEET_TRANSLUCENT;
    }

    @Override
    public void render(VertexConsumer buffer, Camera camera, float partialTicks) {
        PoseStack pose = new PoseStack();
        pose.pushPose();

        // translate from camera
        double camX = camera.getPosition().x;
        double camY = camera.getPosition().y;
        double camZ = camera.getPosition().z;

        pose.translate(this.x - camX, this.y - camY, this.z - camZ);

        // rotation
        Quaternionf rot = new Quaternionf()
                .rotateY((float) Math.toRadians(-this.yawDeg))
                .rotateX((float) Math.toRadians(this.pitchDeg));

        pose.mulPose(rot);

        // draw using vanilla billboard quad (but rotated!)
        super.render(buffer, camera, partialTicks);

        pose.popPose();
    }
}