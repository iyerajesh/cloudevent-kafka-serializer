package com.xylia.platform.events.model;

import org.junit.jupiter.api.Test;

import java.util.Arrays;

import static org.assertj.core.api.Assertions.assertThat;

public class ApplyEventTypesTest {

    static {
    }

    public static final String INVALID_TYPE = "invalid-type";

    @Test
    public void testIfEventNameExists() {
        assertThat(ApplyEventTypes.exists(ApplyEventTypes.APPLICATION_APPROVED_EVENT.getType())).isTrue();
        assertThat(ApplyEventTypes.exists(INVALID_TYPE)).isFalse();
    }

    @Test
    public void getters() {
        Arrays.stream(ApplyEventTypes.values()).forEach(
                applyEventTypes -> {
                    assertThat(applyEventTypes.getType()).isNotNull();
                    assertThat(applyEventTypes.getTopic()).isNotNull();
                });
    }
}