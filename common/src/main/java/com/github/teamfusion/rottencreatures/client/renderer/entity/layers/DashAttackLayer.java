package com.github.teamfusion.rottencreatures.client.renderer.entity.layers;

import com.github.teamfusion.rottencreatures.client.model.ImmortalModel;
import com.github.teamfusion.rottencreatures.client.model.LayerBuilder;
import com.github.teamfusion.rottencreatures.common.entities.Immortal;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import com.mojang.math.Vector3f;
import net.minecraft.client.model.geom.EntityModelSet;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.CubeListBuilder;
import net.minecraft.client.model.geom.builders.LayerDefinition;
import net.minecraft.client.model.geom.builders.MeshDefinition;
import net.minecraft.client.model.geom.builders.PartDefinition;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.RenderLayerParent;
import net.minecraft.client.renderer.entity.layers.RenderLayer;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.resources.ResourceLocation;

public class DashAttackLayer<T extends Immortal> extends RenderLayer<T, ImmortalModel<T>> {
    public static final LayerBuilder LAYER = LayerBuilder.of("dash_attack");
    private final ModelPart box;

    public DashAttackLayer(RenderLayerParent<T, ImmortalModel<T>> renderLayerParent, EntityModelSet modelSet) {
        super(renderLayerParent);
        this.box = modelSet.bakeLayer(LAYER.getMain()).getChild("box");
    }

    public static LayerDefinition createLayer() {
        MeshDefinition mesh = new MeshDefinition();
        PartDefinition root = mesh.getRoot();
        root.addOrReplaceChild("box", CubeListBuilder.create().texOffs(0, 0).addBox(-8.0F, -16.0F, -8.0F, 16.0F, 32.0F, 16.0F), PartPose.ZERO);
        return LayerDefinition.create(mesh, 64, 64);
    }

    @Override
    public void render(PoseStack matrices, MultiBufferSource source, int light, T entity, float angle, float distance, float tickDelta, float animationProgress, float yaw, float pitch) {
        if (entity.isAutoSpinAttack()) {
            VertexConsumer vertices = source.getBuffer(RenderType.entityCutoutNoCull(new ResourceLocation("textures/entity/trident_riptide.png")));

            for(int i = 0; i < 3; ++i) {
                matrices.pushPose();
                float degrees = animationProgress * (float)(-(45 + i * 5));
                matrices.mulPose(Vector3f.YP.rotationDegrees(degrees));
                float scale = 0.75F * (float)i;
                matrices.scale(scale, scale, scale);
                matrices.translate(0.0, -0.2F + 0.6F * (float)i, 0.0);
                this.box.render(matrices, vertices, light, OverlayTexture.NO_OVERLAY);
                matrices.popPose();
            }
        }
    }
}