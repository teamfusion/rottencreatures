package com.github.teamfusion.rottencreatures.core.platform.forge;

import com.github.teamfusion.rottencreatures.core.platform.CoreRegistry;
import net.minecraft.core.Registry;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.IForgeRegistryEntry;

import java.util.function.Supplier;

public class CoreRegistryImpl<T extends IForgeRegistryEntry<T>> extends CoreRegistry<T> {
    private final DeferredRegister<T> registry;

    protected CoreRegistryImpl(Registry<T> registry, String modId) {
        super(registry, modId);
        this.registry = DeferredRegister.create(registry.key(), modId);
    }

    @SuppressWarnings({"unchecked", "rawtypes"})
    public static <T> CoreRegistry<T> create(Registry<T> registry, String modId) {
        return new CoreRegistryImpl(registry, modId);
    }

    @Override
    public <E extends T> Supplier<E> register(String key, Supplier<E> entry) {
        return this.registry.register(key, entry);
    }

    @Override
    protected void bootstrap() {
        this.registry.register(FMLJavaModLoadingContext.get().getModEventBus());
    }
}