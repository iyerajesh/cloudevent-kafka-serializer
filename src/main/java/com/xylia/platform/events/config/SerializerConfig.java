package com.xylia.platform.events.config;

public class SerializerConfig {

    private static boolean disableDecryption = false;

    public static final boolean isDisableDecryption() {
        return disableDecryption;
    }

    public static SerializerConfigBuilder builder() {
        return new SerializerConfigBuilder();
    }

    public static final class SerializerConfigBuilder {

        private boolean disableDecryption = false;

        public SerializerConfigBuilder() {
        }

        public SerializerConfigBuilder disableDecryption(boolean disableDecryption) {
            this.disableDecryption = disableDecryption;
            return this;
        }

        public void build() {
            SerializerConfig.disableDecryption = disableDecryption;
        }
    }
}
