package com.github.teamfusion.platform.client.forge;

import com.github.teamfusion.rottencreatures.RottenCreatures;
import net.minecraft.client.model.geom.ModelLayerLocation;
import net.minecraft.client.model.geom.builders.LayerDefinition;
import net.minecraft.client.renderer.ItemBlockRenderTypes;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.blockentity.BlockEntityRendererProvider;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.EntityRenderersEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.Consumer;
import java.util.function.Supplier;

@Mod.EventBusSubscriber(modid = RottenCreatures.MOD_ID, value = Dist.CLIENT, bus = Mod.EventBusSubscriber.Bus.MOD)
public class RenderHandlerImpl {
    private static final Set<Consumer<EntityRenderersEvent.RegisterRenderers>> RENDERERS = ConcurrentHashMap.newKeySet();
    private static final Set<Consumer<EntityRenderersEvent.RegisterLayerDefinitions>> DEFINITIONS = ConcurrentHashMap.newKeySet();

    public static void setBlockRenderType(RenderType type, Block... blocks) {
        for (Block block : blocks) ItemBlockRenderTypes.setRenderLayer(block, type);
    }

    @SubscribeEvent
    public static void onEvent(EntityRenderersEvent.RegisterRenderers event) {
        RENDERERS.forEach(handler -> handler.accept(event));
    }

    @SubscribeEvent
    public static void onEvent(EntityRenderersEvent.RegisterLayerDefinitions event) {
        DEFINITIONS.forEach(handler -> handler.accept(event));
    }

    public static <T extends Entity> void setEntityRenderer(Supplier<? extends EntityType<? extends T>> type, EntityRendererProvider<T> provider) {
        RENDERERS.add(event -> event.registerEntityRenderer(type.get(), provider));
    }

    public static void setModelLayerDefinition(ModelLayerLocation modelLayer, Supplier<LayerDefinition> definition) {
        DEFINITIONS.add(event -> event.registerLayerDefinition(modelLayer, definition));
    }
}