package com.practical_plushies_animals.particle;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.model.Model;
import net.minecraft.client.particle.Particle;
import net.minecraft.client.particle.ParticleFactory;
import net.minecraft.client.particle.ParticleTextureSheet;
import net.minecraft.client.particle.SpriteProvider;
import net.minecraft.client.render.*;
import net.minecraft.client.render.entity.ElderGuardianEntityRenderer;
import net.minecraft.client.render.entity.PigEntityRenderer;
import net.minecraft.client.render.entity.model.EntityModelLayers;
import net.minecraft.client.render.entity.model.GuardianEntityModel;
import net.minecraft.client.render.entity.model.PigEntityModel;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.client.world.ClientWorld;
import net.minecraft.particle.DefaultParticleType;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.RotationAxis;

@Environment(EnvType.CLIENT)
public class PlushieAppearanceParticle extends Particle {
    private final Model model;
    private final RenderLayer layer;

    PlushieAppearanceParticle(ClientWorld clientWorld, double d, double e, double f, String test) {
        super(clientWorld, d, e, f);
        this.layer = RenderLayer.getEntityTranslucent(new Identifier("textures/entity/pig/pig.png"));
        this.model = new PigEntityModel(MinecraftClient.getInstance().getEntityModelLoader().getModelPart(EntityModelLayers.PIG));
        this.gravityStrength = 0.0F;
        this.maxAge = 30;
    }

    public ParticleTextureSheet getType() {
        return ParticleTextureSheet.CUSTOM;
    }

    public void buildGeometry(VertexConsumer vertexConsumer, Camera camera, float tickDelta) {
        float f = ((float)this.age + tickDelta) / (float)this.maxAge;
        float g = 0.05F + 0.5F * MathHelper.sin(f * 3.1415927F);
        MatrixStack matrixStack = new MatrixStack();
        matrixStack.multiply(camera.getRotation());
        matrixStack.multiply(RotationAxis.POSITIVE_X.rotationDegrees(150.0F * f - 60.0F));
        matrixStack.scale(-1.0F, -1.0F, 1.0F);
        matrixStack.translate(0.0F, -1.101F, 1.5F);
        VertexConsumerProvider.Immediate immediate = MinecraftClient.getInstance().getBufferBuilders().getEntityVertexConsumers();
        VertexConsumer vertexConsumer2 = immediate.getBuffer(this.layer);
        this.model.render(matrixStack, vertexConsumer2, 15728880, OverlayTexture.DEFAULT_UV, 1.0F, 1.0F, 1.0F, g);
        immediate.draw();
    }

    @Environment(EnvType.CLIENT)
    public static class Factory implements ParticleFactory<DefaultParticleType> {
        public Factory() {
        }

        public Particle createParticle(DefaultParticleType defaultParticleType, ClientWorld clientWorld, double d, double e, double f, double g, double h, double i) {
            return new PlushieAppearanceParticle(clientWorld, d, e, f, "hey!");
        }
    }
}
