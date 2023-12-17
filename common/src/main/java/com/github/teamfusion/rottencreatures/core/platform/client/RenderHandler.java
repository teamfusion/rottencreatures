package com.github.teamfusion.rottencreatures.core.platform.client;

import dev.architectury.injectables.annotations.ExpectPlatform;
import net.minecraft.client.model.geom.ModelLayerLocation;
import net.minecraft.client.model.geom.builders.LayerDefinition;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.level.block.Block;

import java.util.function.Supplier;

public class RenderHandler {
    @ExpectPlatform
    public static void setBlockRenderType(RenderType type, Block... blocks) {
        throw new AssertionError();
    }

    @ExpectPlatform
    public static <T extends Entity> void setEntityRenderer(Supplier<? extends EntityType<? extends T>> type, EntityRendererProvider<T> provider) {
        throw new AssertionError();
    }

    @ExpectPlatform
    public static void setModelLayerDefinition(ModelLayerLocation modelLayer, Supplier<LayerDefinition> definition) {
        throw new AssertionError();
    }
}