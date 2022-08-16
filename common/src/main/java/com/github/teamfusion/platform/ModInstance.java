package com.github.teamfusion.platform;

import dev.architectury.injectables.annotations.ExpectPlatform;

public abstract class ModInstance {
    public final String modId;
    public Runnable onCommon;
    public Runnable onPostCommon;
    public Runnable onClient;
    public Runnable onPostClient;

    protected ModInstance(String modId, Runnable onCommon, Runnable onPostCommon, Runnable onClient, Runnable onPostClient) {
        this.modId = modId;
        this.onCommon = onCommon;
        this.onPostCommon = onPostCommon;
        this.onClient = onClient;
        this.onPostClient = onPostClient;
        this.populateIfEmpty();
    }

    public static Builder create(String modId) {
        return new Builder(modId);
    }

    public abstract void bootstrap();

    private void commonSetup(Runnable common) {
        this.onCommon = common;
    }

    private void postCommonSetup(Runnable common) {
        this.onPostCommon = common;
    }

    private void clientSetup(Runnable client) {
        this.onClient = client;
    }

    private void postClientSetup(Runnable client) {
        this.onPostClient = client;
    }

    private void populateIfEmpty() {
        if (this.onCommon == null) this.commonSetup(this::empty);
        if (this.onPostCommon == null) this.postCommonSetup(this::empty);
        if (this.onClient == null) this.clientSetup(this::empty);
        if (this.onPostClient == null) this.postClientSetup(this::empty);
    }

    private void empty() {}

    public static class Builder {
        private final String modId;
        private Runnable onCommon;
        private Runnable onPostCommon;
        private Runnable onClient;
        private Runnable onPostClient;

        protected Builder(String modId) {
            this.modId = modId;
        }

        public Builder common(Runnable common) {
            this.onCommon = common;
            return this;
        }

        public Builder postCommon(Runnable common) {
            this.onPostCommon = common;
            return this;
        }

        public Builder client(Runnable client) {
            this.onClient = client;
            return this;
        }

        public Builder postClient(Runnable client) {
            this.onPostClient = client;
            return this;
        }

        public ModInstance build() {
            return builder(this.modId, this.onCommon, this.onPostCommon, this.onClient, this.onPostClient);
        }

        @ExpectPlatform
        public static ModInstance builder(String modId, Runnable common, Runnable postCommon, Runnable client, Runnable postClient) {
            throw new AssertionError();
        }
    }
}