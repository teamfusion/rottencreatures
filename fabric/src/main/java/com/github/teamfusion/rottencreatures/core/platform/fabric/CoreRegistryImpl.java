package com.github.teamfusion.rottencreatures.core.platform.fabric;

import com.github.teamfusion.rottencreatures.core.platform.CoreRegistry;
import net.minecraft.core.Registry;
import net.minecraft.resources.ResourceLocation;

import java.util.function.Supplier;

public class CoreRegistryImpl<T> extends CoreRegistry<T> {
    protected CoreRegistryImpl(Registry<T> registry, String modId) {
        super(registry, modId);
    }

    public static <T> CoreRegistry<T> create(Registry<T> registry, String modId) {
        return new CoreRegistryImpl<>(registry, modId);
    }

    @Override
    public <E extends T> Supplier<E> register(String key, Supplier<E> entry) {
        E registry = Registry.register(this.registry, new ResourceLocation(this.modId, key), entry.get());
        return () -> registry;
    }

    @Override
    protected void bootstrap() {}
}