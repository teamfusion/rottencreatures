package com.github.teamfusion.platform.config;

import dev.architectury.injectables.annotations.ExpectPlatform;

public class Config {
    @ExpectPlatform
    public static void bootstrap() {
        throw new AssertionError();
    }

    public static <T> Entry<T> create(String name, T value, String comment) {
        return new Entry<>(name, value, comment);
    }

    public record Entry<T>(String name, T value, String comment) {}
}