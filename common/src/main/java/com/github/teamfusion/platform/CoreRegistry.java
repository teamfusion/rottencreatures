package com.github.teamfusion.platform;

import dev.architectury.injectables.annotations.ExpectPlatform;
import net.minecraft.core.Registry;

import java.util.function.Supplier;

public abstract class CoreRegistry<T> {
    protected final Registry<T> registry;
    protected final String modId;
    protected boolean isPresent;

    protected CoreRegistry(Registry<T> registry, String modId) {
        this.registry = registry;
        this.modId = modId;
        this.isPresent = false;
    }

    @ExpectPlatform
    public static <T> CoreRegistry<T> create(Registry<T> registry, String modId) {
        throw new AssertionError();
    }

    public abstract <E extends T> Supplier<E> register(String key, Supplier<E> entry);

    public void register() {
        if (this.isPresent) throw new IllegalArgumentException("Duplication of Registry: " + this.registry);
        this.isPresent = true;
        this.bootstrap();
    }

    protected abstract void bootstrap();
}