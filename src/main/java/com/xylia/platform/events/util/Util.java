package com.xylia.platform.events.util;

import java.util.Arrays;

public class Util {

    private static final String EPASS_ENV = "EPASS_ENV";

    public static final String getActiveRuntimeProfile() {
        if (System.getenv(EPASS_ENV) != null)
            return System.getenv(EPASS_ENV);

        else if (System.getProperty(EPASS_ENV) != null)
            return System.getProperty(EPASS_ENV);
        return null;
    }

    public static final byte[] copyOf(byte[] sourceArray) {
        if (sourceArray == null)
            return sourceArray;
        else
            return Arrays.copyOf(sourceArray, sourceArray.length);
    }
}
